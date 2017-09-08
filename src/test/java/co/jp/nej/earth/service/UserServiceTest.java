package co.jp.nej.earth.service;

import co.jp.nej.earth.BaseTest;
import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.model.Message;
import co.jp.nej.earth.model.constant.Constant;
import co.jp.nej.earth.model.entity.MgrProfile;
import co.jp.nej.earth.model.entity.MgrUser;
import co.jp.nej.earth.model.entity.MgrUserProfile;
import co.jp.nej.earth.util.ConversionUtil;
import co.jp.nej.earth.util.DateUtil;
import co.jp.nej.earth.util.EStringUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class UserServiceTest extends BaseTest {

    private static final Logger LOG = LoggerFactory.getLogger(UserServiceTest.class);

    public static final int RECORD = 5;
    public static final int RECORDFALSE = 8;
    public static final String USER = "MinhTVUser";
    public static final String NAME = "MinhTVName";
    public static final String PASS = "User!23";
    public static final String PROFILE = "MinhTVProfile_";
    public static final String DESCRIPTION = "MinhTVDescription_";
    public static final String LAP = "Lap_";
    private List<MgrUser> mgrUsers = new ArrayList<MgrUser>();
    private List<MgrProfile> mgrProfiles = new ArrayList<MgrProfile>();
    private List<MgrUserProfile> mgrUserProfiles = new ArrayList<MgrUserProfile>();
    private List<String> userIds = new ArrayList<String>();
    private List<String> profileIds = new ArrayList<String>();


    @Autowired
    private UserService userService;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private PreparingDataService preparingDataService;

    @Before
    public void before() throws EarthException {
        for (Integer i = 1; i <= RECORD; i++) {
            String userId = USER + i.toString();
            String name = NAME + i.toString();
            String password = PASS + i.toString();
            boolean changePassword = true;
            MgrUser mgrUser = new MgrUser(userId, name, password, password, changePassword,
                    DateUtil.getCurrentDate(Constant.DatePattern.DATE_FORMAT_YYYYMMDDHHMMSSSSS));
            userService.insertOne(mgrUser);
            String profileId = PROFILE + i.toString();
            String description = DESCRIPTION + i.toString();
            String ldapIdentifier = LAP + i.toString();
            MgrProfile mgrProfile = new MgrProfile(profileId, i, description, ldapIdentifier);
            profileService.insertOne(mgrProfile);
            mgrUsers.add(mgrUser);
            mgrProfiles.add(mgrProfile);
            userIds.add(userId);
            profileIds.add(profileId);
        }

        for (MgrProfile mgrProfile1 : mgrProfiles) {
            MgrUserProfile mgrUserProfile = new MgrUserProfile(mgrUsers.get(0).getUserId(), mgrProfile1.getProfileId());
            profileService.assignUsers(mgrProfile1.getProfileId(), new ArrayList<>(
                    Arrays.asList(mgrUsers.get(0).getUserId())));
            mgrUserProfiles.add(mgrUserProfile);
        }
    }

    @After
    public void after() throws EarthException {
        LOG.info("After : " + userIds.size());
        preparingDataService.deleteUserProfile(userIds);
        preparingDataService.deleteListUsers(userIds);
        preparingDataService.deleteListProfiles(profileIds);
        LOG.info("After : " + userIds.size());
    }

    @Test
    public void getAll() throws EarthException {
        List<MgrUser> mgrUsers = null;
        mgrUsers = userService.getAll();
        LOG.info("Get All user : " + userIds.size() + " records");
        Assert.assertTrue(mgrUsers.size() >= RECORD);
    }

    @Test
    public void getById() throws EarthException {
        Map<String, Object> detail = null;
        LOG.info("UserId: " + mgrUsers.get(0).getUserId());
        detail = userService.getDetail(mgrUsers.get(0).getUserId());
        MgrUser mgrUser = (MgrUser) detail.get("mgrUser");
        List<MgrProfile> mgrProfiles = ConversionUtil.castList(detail.get("mgrProfiles"), MgrProfile.class);
        LOG.info("UserId: " + mgrUser.getUserId() + " " + mgrProfiles.size());
        Assert.assertTrue(EStringUtil.contains(mgrUser.getUserId(), mgrUsers.get(0).getUserId())
                && mgrProfiles.size() == RECORD);
    }

    @Test
    public void insertOne() throws EarthException {
        boolean insertUser = false;
        String userId = USER + (RECORD + 1);
        String name = NAME + (RECORD + 1);
        String password = PASS + (RECORD + 1);
        boolean changePassword = true;
        MgrUser mgrUser = new MgrUser(userId, name, password, password, changePassword,
                DateUtil.getCurrentDate(Constant.DatePattern.DATE_FORMAT_YYYYMMDDHHMMSSSSS));
        List<Message> messages = userService.validate(mgrUser, changePassword);
        Assert.assertFalse(messages != null && messages.size() > 0);
        insertUser = userService.insertOne(mgrUser);
        if (insertUser) {
            userIds.add(userId);
        }
        LOG.info("Insert User : " + mgrUser.getUserId());
        Assert.assertTrue(insertUser);
    }

    @Test
    public void updateOne() throws EarthException {
        boolean updateUser;
        String userId = USER + (RECORD);
        String name = NAME + (RECORD + 1);
        String password = PASS + (RECORD + 1);
        boolean changePassword = false;
        MgrUser mgrUser = new MgrUser(userId, name, password, password, changePassword,
                DateUtil.getCurrentDate(Constant.DatePattern.DATE_FORMAT_YYYYMMDDHHMMSSSSS));
        List<Message> messages = userService.validate(mgrUser, false);
        Assert.assertFalse(messages != null && messages.size() > 0);
        updateUser = userService.updateOne(mgrUser);
        Assert.assertTrue(updateUser);

        changePassword = false;
        messages = userService.validate(mgrUser, false);
        mgrUser = new MgrUser(userId, name, password, password, changePassword,
                DateUtil.getCurrentDate(Constant.DatePattern.DATE_FORMAT_YYYYMMDDHHMMSSSSS));
        Assert.assertFalse(messages != null && messages.size() > 0);
        updateUser = userService.updateOne(mgrUser);
        Assert.assertTrue(updateUser);
    }

    @Test
    public void deleteList() throws EarthException {
        boolean deleteUser = false;
        deleteUser = userService.deleteList(userIds);
        LOG.info("Delete true: " + deleteUser);
        Assert.assertTrue(deleteUser);
        userIds = new ArrayList<String>();
    }

    @Test
    public void deleteListFalse() throws EarthException {
        boolean deleteUser = false;
        List<String> userIdsFalse = new ArrayList<>();
        userIdsFalse.add(USER + RECORDFALSE);
        deleteUser = userService.deleteList(userIdsFalse);
        LOG.info("Delete false: " + deleteUser);
        Assert.assertFalse(deleteUser);
    }

    @Test
    public void insertOneTest() throws EarthException {
        boolean insertUser = false;
        List<MgrUser> mgrUsers1 = new ArrayList<MgrUser>();
        for (int i = 1; i <= 2; i++) {
            String userId = USER + (RECORD + i);
            String name = NAME + (RECORD + i);
            String password = PASS + (RECORD + i);
            boolean changePassword = true;
            MgrUser mgrUser = new MgrUser(userId, name, password, password, changePassword,
                    DateUtil.getCurrentDate(Constant.DatePattern.DATE_FORMAT_YYYYMMDDHHMMSSSSS));
            mgrUsers1.add(mgrUser);
            userIds.add(userId);
            LOG.info("Insert User : " + mgrUser.getUserId());
        }
        Assert.assertTrue(insertUser);
    }

}
