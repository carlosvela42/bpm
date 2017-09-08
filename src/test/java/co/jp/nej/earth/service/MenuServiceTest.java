package co.jp.nej.earth.service;

import co.jp.nej.earth.BaseTest;
import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.model.ProfileAccessRight;
import co.jp.nej.earth.model.UserAccessRight;
import co.jp.nej.earth.model.constant.Constant;
import co.jp.nej.earth.model.entity.CtlMenu;
import co.jp.nej.earth.model.entity.MgrMenu;
import co.jp.nej.earth.model.entity.MgrMenuP;
import co.jp.nej.earth.model.entity.MgrMenuU;
import co.jp.nej.earth.model.entity.MgrProfile;
import co.jp.nej.earth.model.entity.MgrUser;
import co.jp.nej.earth.model.entity.MgrUserProfile;
import co.jp.nej.earth.model.enums.AccessRight;
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
import java.util.List;
import java.util.Random;

public class MenuServiceTest extends BaseTest {

    private static final Logger LOG = LoggerFactory.getLogger(MenuServiceTest.class);
    private static final int RECORD_FUNCTION = 3;
    private static final int RECORD_PARENT = 3;
    private static final int RECORD_USER = 3;
    private static final int RECORD_PROFILE = 3;
    private static final String FUNCTIONCATEGORYID = "FUNCTIONCATEGORYID_MINHTV_";
    private static final String FNCTIONCATRGOTYNAME = "FNCTIONCATRGOTYNAME_MINHTV_";
    private static final String FUNCTIONID = "FUNCTIONID_MINHTV_";
    private static final String FUNCTION_NAME = "FUNCTION_NAME_MINHTV_";
    private static final String FUNCTION_INFORMATION = "FUNCTION_INFORMATION_";
    private static final String USER_ID = "MinhTVUser";
    private static final String USER_NAME = "UserName";
    private static final String PASSWORD = "User!23";
    private static final String PROFILE_ID = "MinhTVProfile";
    private static final String DESCPRIPTION = "Description_";
    private static final String LAP = "Lap_";
    private static final int ACCESSRIGHT = 5;

    // Menu
    private List<MgrMenu> mgrMenus = new ArrayList<>();
    private List<String> functionIds = new ArrayList<>();
    private List<MgrMenuU> mgrMenuUs = new ArrayList<>();
    private List<MgrMenuP> mgrMenuPs = new ArrayList<>();
    private List<CtlMenu> ctlMenus = new ArrayList<>();
    //User and Profile
    private List<MgrUser> mgrUsers = new ArrayList<>();
    private List<String> userIds = new ArrayList<>();
    private List<MgrProfile> mgrProfiles = new ArrayList<>();
    private List<String> profileIds = new ArrayList<>();
    private List<MgrUserProfile> mgrUserProfiles = new ArrayList<>();

    @Autowired
    private MenuService menuService;

    @Autowired
    private PreparingDataService preparingDataService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProfileService profileService;

    @Before
    public void before() throws EarthException {
        LOG.info("Before Insert MgrMenu");
        for (int j = 1; j <= RECORD_PARENT; j++) {
            String functionCategoryId = FUNCTIONCATEGORYID + j;
            String functionCategoryName = FNCTIONCATRGOTYNAME + j;
            for (int i = 1; i <= RECORD_FUNCTION; i++) {
                String functionId = FUNCTIONID + (i + (j - 1) * RECORD_FUNCTION);
                String functionName = FUNCTION_NAME + (i + (j - 1) * RECORD_FUNCTION);
                int functionSortNo = (i + (j - 1) * RECORD_FUNCTION);
                String functionInformation = FUNCTION_INFORMATION + (i + (j - 1) * RECORD_FUNCTION);
                MgrMenu mgrMenu = new MgrMenu(functionId, functionName, functionCategoryId, functionCategoryName,
                        functionSortNo, functionInformation);
                preparingDataService.insertMenu(mgrMenu);
                functionIds.add(functionId);
                mgrMenus.add(mgrMenu);
            }
        }
        LOG.info("After Insert MgrMenu : " + mgrMenus.size());

        // Insert User
        LOG.info("Before Insert User");
        for (int i = 1; i <= RECORD_USER; i++) {
            MgrUser mgrUser = new MgrUser(USER_ID + i, USER_NAME + i, PASSWORD + i, PASSWORD + i, true,
                    DateUtil.getCurrentDate(Constant.DatePattern.DATE_FORMAT_YYYYMMDDHHMMSSSSS));
            userService.insertOne(mgrUser);
            userIds.add(USER_ID + i);
            mgrUsers.add(mgrUser);
            for (String functionId : functionIds) {
                MgrMenuU mgrMenuU = new MgrMenuU(functionId, USER_ID + i, new Random().nextInt(ACCESSRIGHT));
                preparingDataService.insertMenuU(mgrMenuU);
                mgrMenuUs.add(mgrMenuU);

            }
        }
        LOG.info("After Insert User : " + mgrUsers.size());

        // Insert Profile and UserProfile
        LOG.info("Before Insert Profile and UserProfile");
        for (int i = 1; i <= RECORD_PROFILE; i++) {
            MgrProfile mgrProfile = new MgrProfile(PROFILE_ID + i, i, DESCPRIPTION + i, LAP + i);
            profileService.insertOne(mgrProfile);
            profileIds.add(PROFILE_ID + i);
            mgrProfiles.add(mgrProfile);

            for (String userId : userIds) {
                MgrUserProfile mgrUserProfile = new MgrUserProfile(userId, PROFILE_ID + i);
                preparingDataService.insertMgrUserProfile(mgrUserProfile);
                mgrUserProfiles.add(mgrUserProfile);
            }
            for (String functionId : functionIds) {
                MgrMenuP mgrMenuP = new MgrMenuP(functionId, PROFILE_ID + i, new Random().nextInt(ACCESSRIGHT));
                preparingDataService.insertMenuP(mgrMenuP);
                mgrMenuPs.add(mgrMenuP);
            }
        }
        LOG.info("After Insert Profile and UserProfile : " + mgrProfiles.size() + " Profiles, "
                + mgrUserProfiles.size() + " UserProfiles, ");

    }

    @After
    public void after() throws EarthException {
        LOG.info("Before Delete MgrMenu : " + mgrMenus.size());
        LOG.info("After Delete MgrMenu:" + preparingDataService.deleteMenuList(functionIds));
        LOG.info("Before Delete User : " + mgrUsers.size());
        LOG.info("After Delete User:" + preparingDataService.deleteListUsers(userIds));
        LOG.info("Before Delete Profile : " + mgrProfiles.size());
        LOG.info("After Delete Profile:" + preparingDataService.deleteListProfiles(profileIds));
        LOG.info("Before Delete UserProfile : " + mgrUserProfiles.size());
        LOG.info("After Delete UserProfile:" + preparingDataService.deleteUserProfile(userIds));
        LOG.info("Before Delete UserMenu : " + mgrMenuUs.size());
        LOG.info("After Delete UserMenu:" + preparingDataService.deleteUserAccessRight(functionIds));
        LOG.info("Before Delete ProfileMenu : " + mgrMenuPs.size());
        LOG.info("After Delete ProfileMenu:" + preparingDataService.deleteProfileAccessRight(functionIds));
        LOG.info("Before Delete CtlMenu : " + ctlMenus.size());
        LOG.info("After Delete ProfileMenu:" + preparingDataService.deleteMixMenuAuthority(functionIds));
    }

    @Test
    public void getAll() throws EarthException {
        List<MgrMenu> ctlLogins = menuService.getAll();
        LOG.info("Get All user : " + ctlLogins.size() + " records");
        Assert.assertTrue(ctlLogins.size() >= RECORD_FUNCTION * RECORD_PARENT);
    }

    @Test
    public void getDetail() throws EarthException {
        String functionId = functionIds.get(0);
        MgrMenu mgrMenu = menuService.getDetail(functionId);
        List<UserAccessRight> userAccessRights = menuService.getUserAuthority(functionId);
        List<ProfileAccessRight> profileAccessRights = menuService.getProfileAuthority(functionId);
        Assert.assertTrue(userAccessRights.size() == mgrUsers.size());
        Assert.assertTrue(profileAccessRights.size() == mgrProfiles.size());
        Assert.assertTrue(EStringUtil.equals(mgrMenu.getFunctionName(), mgrMenus.get(0).getFunctionName()));
    }

    @Test
    public void updateAuthority() throws EarthException {
        String functionId = functionIds.get(0);
        List<UserAccessRight> userAccessRights = menuService.getUserAuthority(functionId);
        List<ProfileAccessRight> profileAccessRights = menuService.getProfileAuthority(functionId);
        Assert.assertTrue(userAccessRights.size() == mgrUsers.size());
        Assert.assertTrue(profileAccessRights.size() == mgrProfiles.size());
        userAccessRights.get(0).setAccessRight(AccessRight.NONE);
        String userId = userAccessRights.get(0).getUserId();
        menuService.saveAuthority(functionId, userAccessRights, profileAccessRights);
        ctlMenus = preparingDataService.getMixMenuAuthority(functionId);
        for (CtlMenu ctlMenu : ctlMenus) {
            if (EStringUtil.equals(ctlMenu.getUserId(), userId)) {
                Assert.assertTrue(ctlMenu.getAccessAuthority() == AccessRight.NONE.getValue());
            }
        }

    }
}
