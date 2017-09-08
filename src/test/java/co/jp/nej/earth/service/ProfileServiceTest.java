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

public class ProfileServiceTest extends BaseTest {

    private static final Logger LOG = LoggerFactory.getLogger(UserServiceTest.class);

    public static final int RECORD = 5;
    public static final int RECORDFALSE = 8;
    public static final String USER = "MinhTVUser";
    public static final String NAME = "Name";
    public static final String PASS = "User!23";
    public static final String PROFILE = "MinhTVProfile";
    public static final String DESCRIPTION = "Description_";
    public static final String LAP = "Lap_";
    private List<MgrUser> mgrUsers = new ArrayList<MgrUser>();
    private List<MgrProfile> mgrProfiles = new ArrayList<MgrProfile>();
    private List<MgrUserProfile> mgrUserProfiles = new ArrayList<MgrUserProfile>();
    private List<String> userIds = new ArrayList<String>();
    private List<String> profileIds = new ArrayList<String>();

    @Autowired
    private ProfileService profileService;

    @Autowired
    private UserService userService;

    @Autowired
    private PreparingDataService preparingDataService;

    @Before
    public void before() throws EarthException {
        MgrUser mgrUser;
        MgrProfile mgrProfile;

        for (Integer i = 1; i <= RECORD; i++) {

            String userId = USER + i.toString();
            String name = NAME + i.toString();
            String password = PASS + i.toString();
            boolean changePassword = true;
            mgrUser = new MgrUser(userId, name, password, password, changePassword, DateUtil.getCurrentDate(Constant
                    .DatePattern.DATE_FORMAT_YYYYMMDDHHMMSSSSS));
            userService.insertOne(mgrUser);

            String profileId = PROFILE + i.toString();
            String description = DESCRIPTION + i.toString();
            String ldapIdentifier = LAP + i.toString();
            mgrProfile = new MgrProfile(profileId, i, description, ldapIdentifier);
            profileService.insertOne(mgrProfile);
            mgrUsers.add(mgrUser);
            mgrProfiles.add(mgrProfile);

            userIds.add(userId);
            profileIds.add(profileId);
        }
        MgrUserProfile mgrUserProfile;
        for (MgrProfile mgrProfile1 : mgrProfiles) {
            mgrUserProfile = new MgrUserProfile(mgrUsers.get(0).getUserId(), mgrProfile1.getProfileId());
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
        LOG.info("After profile: " + userIds.size());
    }

    @Test
    public void getAll() throws EarthException {
        List<MgrProfile> mgrProfiles = null;
        mgrProfiles = profileService.getAll();
        LOG.info("Get All Profile  : " + mgrProfiles.size() + " records");
        Assert.assertTrue(mgrProfiles.size() >= RECORD);
    }

    @Test
    public void getById() throws EarthException {
        Map<String, Object> detail = null;
        LOG.info("UserId: " + mgrUsers.get(0).getUserId());
        detail = profileService.getDetail(mgrUserProfiles.get(0).getProfileId());
        MgrProfile mgrProfile = (MgrProfile) detail.get("mgrProfile");
        List<MgrUser> mgrUsers = ConversionUtil.castList(detail.get("mgrUsers"), MgrUser.class);
        LOG.info("ProfileId: " + mgrProfile.getProfileId() + " " + mgrUsers.size());
        Assert.assertTrue(EStringUtil.contains(mgrProfile.getProfileId(), mgrProfiles.get(0).getProfileId())
                && mgrProfiles.size() >= RECORD);
    }

    @Test
    public void insertOne() throws EarthException {
        boolean insertProfile = false;
        String profileId = PROFILE + (RECORD + 1);
        String description = DESCRIPTION + (RECORD + 1);
        String ilap = LAP + (RECORD + 1);
        int availableLicenceCount = RECORD;
        MgrProfile mgrProfile = new MgrProfile(profileId, availableLicenceCount, description, ilap);
        List<Message> messages = profileService.validate(mgrProfile, true);
        Assert.assertFalse(messages != null && messages.size() > 0);
        insertProfile = profileService.insertAndAssignUsers(mgrProfile, userIds);
        profileIds.add(profileId);
        LOG.info("Insert Profile : " + mgrProfile.getProfileId());
        Assert.assertTrue(insertProfile);
    }

    @Test
    public void updateOne() throws EarthException {
        boolean updateProfile = false;
        String profileId = PROFILE + (RECORD);
        String description = DESCRIPTION + (RECORD + 1);
        String ilap = LAP + (RECORD + 1);
        int availableLicenceCount = RECORD;
        MgrProfile mgrProfile = new MgrProfile(profileId, availableLicenceCount, description, ilap);
        List<Message> messages = profileService.validate(mgrProfile, false);
        Assert.assertFalse(messages != null && messages.size() > 0);
        List<String> userIdsNew = new ArrayList<>(Arrays.asList(userIds.get(0), userIds.get(2)));
//        updateProfile = profileService.updateAndAssignUsers(mgrProfile, userIdsNew);
        LOG.info("Update Profile : " + mgrProfile.getProfileId());
        Assert.assertTrue(updateProfile);
    }

    @Test
    public void deleteList() throws EarthException {
        boolean deleteProfile = false;
        deleteProfile = profileService.deleteList(profileIds);
        LOG.info("Delete profile true: " + deleteProfile);
        Assert.assertTrue(deleteProfile);
    }

    @Test
    public void deleteListFalse() throws EarthException {
        boolean deleteProfile = false;
        List<String> profileIdsFalse = new ArrayList<>();
        profileIdsFalse.add(PROFILE + (RECORD + RECORDFALSE));
        deleteProfile = profileService.deleteList(profileIdsFalse);
        LOG.info("Delete profile false: " + deleteProfile);
        Assert.assertFalse(deleteProfile);
    }

}
