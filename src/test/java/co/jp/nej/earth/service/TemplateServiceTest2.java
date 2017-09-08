package co.jp.nej.earth.service;

import co.jp.nej.earth.BaseTest;
import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.model.ProfileAccessRight;
import co.jp.nej.earth.model.TemplateKey;
import co.jp.nej.earth.model.UserAccessRight;
import co.jp.nej.earth.model.constant.Constant.DatePattern;
import co.jp.nej.earth.model.entity.MgrUserProfile;
import co.jp.nej.earth.model.enums.AccessRight;
import co.jp.nej.earth.util.DateUtil;
import co.jp.nej.earth.util.EStringUtil;
import co.jp.nej.earth.util.UserAccessRightUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TemplateServiceTest2 extends BaseTest {

    public static final int THREE = 3;
    public static final int FOUR = 4;
    public static final int FIVE = 5;
    public static final int SIX = 6;

    @Autowired
    private PreparingDataService data;

    @Autowired
    private TemplateService templateService;

    private static TemplateKey templateKey = new TemplateKey("001", "template_1");

    @Before
    public void setUp() {
        try {
            deleteTables();

            MgrUserProfile mgrUserProfile1 = new MgrUserProfile();
            mgrUserProfile1.setUserId("minhtv");
            mgrUserProfile1.setProfileId("profile_1");
            mgrUserProfile1.setLastUpdateTime(DateUtil.getCurrentDate(DatePattern.DATE_FORMAT_YYYY_MM_DD_HH_MM_SS));
            data.insertMgrUserProfile(mgrUserProfile1);

            MgrUserProfile mgrUserProfile2 = new MgrUserProfile();
            mgrUserProfile2.setUserId("thuynd");
            mgrUserProfile2.setProfileId("profile_1");
            mgrUserProfile2.setLastUpdateTime(DateUtil.getCurrentDate(DatePattern.DATE_FORMAT_YYYY_MM_DD_HH_MM_SS));
            data.insertMgrUserProfile(mgrUserProfile2);

            MgrUserProfile mgrUserProfile3 = new MgrUserProfile();
            mgrUserProfile3.setUserId("admin");
            mgrUserProfile3.setProfileId("profile_1");
            mgrUserProfile3.setLastUpdateTime(DateUtil.getCurrentDate(DatePattern.DATE_FORMAT_YYYY_MM_DD_HH_MM_SS));
            data.insertMgrUserProfile(mgrUserProfile3);

            MgrUserProfile mgrUserProfile4 = new MgrUserProfile();
            mgrUserProfile4.setUserId("khanhnv");
            mgrUserProfile4.setProfileId("profile_1");
            mgrUserProfile4.setLastUpdateTime(DateUtil.getCurrentDate(DatePattern.DATE_FORMAT_YYYY_MM_DD_HH_MM_SS));
            data.insertMgrUserProfile(mgrUserProfile4);

            MgrUserProfile mgrUserProfile5 = new MgrUserProfile();
            mgrUserProfile5.setUserId("hantn");
            mgrUserProfile5.setProfileId("profile_2");
            mgrUserProfile5.setLastUpdateTime(DateUtil.getCurrentDate(DatePattern.DATE_FORMAT_YYYY_MM_DD_HH_MM_SS));
            data.insertMgrUserProfile(mgrUserProfile5);

            MgrUserProfile mgrUserProfile6 = new MgrUserProfile();
            mgrUserProfile6.setUserId("admin");
            mgrUserProfile6.setProfileId("profile_2");
            mgrUserProfile6.setLastUpdateTime(DateUtil.getCurrentDate(DatePattern.DATE_FORMAT_YYYY_MM_DD_HH_MM_SS));
            data.insertMgrUserProfile(mgrUserProfile6);

            MgrUserProfile mgrUserProfile7 = new MgrUserProfile();
            mgrUserProfile7.setUserId("thuynd");
            mgrUserProfile7.setProfileId("profile_2");
            mgrUserProfile7.setLastUpdateTime(DateUtil.getCurrentDate(DatePattern.DATE_FORMAT_YYYY_MM_DD_HH_MM_SS));
            data.insertMgrUserProfile(mgrUserProfile7);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Case testSaveAuthority() method run successfully.
     */
    @Test
    public void testSaveAuthority() throws EarthException {
        List<UserAccessRight> userAccessRights = new ArrayList<UserAccessRight>();

        UserAccessRight userAccessRight1 = new UserAccessRight();
        userAccessRight1.setUserId("admin");
        userAccessRight1.setAccessRight(AccessRight.RW);
        userAccessRights.add(userAccessRight1);

        UserAccessRight userAccessRight2 = new UserAccessRight();
        userAccessRight2.setUserId("minhtv");
        userAccessRight2.setAccessRight(AccessRight.NONE);
        userAccessRights.add(userAccessRight2);

        UserAccessRight userAccessRight3 = new UserAccessRight();
        userAccessRight3.setUserId("thuynd");
        userAccessRight3.setAccessRight(AccessRight.NONE);
        userAccessRights.add(userAccessRight3);

        UserAccessRight userAccessRight4 = new UserAccessRight();
        userAccessRight4.setUserId("sonta");
        userAccessRight4.setAccessRight(AccessRight.NONE);
        userAccessRights.add(userAccessRight4);

        UserAccessRight userAccessRight5 = new UserAccessRight();
        userAccessRight5.setUserId("hantn");
        userAccessRight5.setAccessRight(AccessRight.RO);
        userAccessRights.add(userAccessRight5);

        List<ProfileAccessRight> profileAccessRights = new ArrayList<ProfileAccessRight>();
        ProfileAccessRight profileAccessRight1 = new ProfileAccessRight();
        profileAccessRight1.setProfileId("profile_1");
        profileAccessRight1.setAccessRight(AccessRight.RW);
        profileAccessRights.add(profileAccessRight1);

        ProfileAccessRight profileAccessRight2 = new ProfileAccessRight();
        profileAccessRight2.setProfileId("profile_2");
        profileAccessRight2.setAccessRight(AccessRight.SO);
        profileAccessRights.add(profileAccessRight2);

        TemplateKey templateKey = new TemplateKey("001", "template_1");
        long numOfRecords = templateService.saveAuthority(templateKey, userAccessRights, profileAccessRights);
        Assert.assertEquals(SIX, numOfRecords);
        List<UserAccessRight> userAccessRights2 = templateService.getUserAuthority(templateKey);
        Assert.assertEquals(userAccessRights.size(), userAccessRights2.size());
        Assert.assertTrue(compare2ListUserAccessRight(userAccessRights, userAccessRights2));
        List<ProfileAccessRight> profileAccessRights2 = templateService.getProfileAuthority(templateKey);
        Assert.assertEquals(profileAccessRights.size(), profileAccessRights2.size());
        Assert.assertTrue(compare2ListProfileAccessRight(profileAccessRights, profileAccessRights2));

        List<String> profileIds = new ArrayList<String>();
        for (ProfileAccessRight profileAccessRight : profileAccessRights) {
            profileIds.add(profileAccessRight.getProfileId());
        }

        List<MgrUserProfile> mgrUserProfiles = data.getListByProfileIds(profileIds);
        Map<String, Integer> mapAccessRightValueP = new HashMap<String, Integer>();
        for (ProfileAccessRight profileAccessRight : profileAccessRights) {
            mapAccessRightValueP.put(profileAccessRight.getProfileId(), profileAccessRight.getAccessRightValue());
        }

        List<UserAccessRight> userAccessRights3 = UserAccessRightUtil.getUserAccessRightProfiles(mgrUserProfiles,
            mapAccessRightValueP);
        List<UserAccessRight> templateAccessRights = UserAccessRightUtil.mixAuthority(userAccessRights,
                userAccessRights3);

        List<UserAccessRight> userAccessRights4 = data.getMixAuthorityTemplate(templateKey);
        Assert.assertTrue(compare2ListUserAccessRight(templateAccessRights, userAccessRights4));
    }

    public boolean compare2ListUserAccessRight(List<UserAccessRight> userAccessRights,
                                               List<UserAccessRight> userAccessRights2) {
        int count = 0;
        if (userAccessRights.size() != userAccessRights2.size()) {
            return false;
        }
        for (UserAccessRight userAccessRight : userAccessRights) {
            for (UserAccessRight userAccessRight2 : userAccessRights2) {
                if (EStringUtil.equals(userAccessRight.getUserId(), userAccessRight2.getUserId())) {
                    if (userAccessRight.getAccessRight().getValue() != userAccessRight2.getAccessRight().getValue()) {
                        return false;
                    } else {
                        count++;
                    }
                }
            }
        }
        if (count != userAccessRights.size()) {
            return false;
        }
        return true;
    }

    public boolean compare2ListProfileAccessRight(List<ProfileAccessRight> profileAccessRights,
                                                  List<ProfileAccessRight> profileAccessRights2) {
        int count = 0;
        if (profileAccessRights.size() != profileAccessRights2.size()) {
            return false;
        }
        for (ProfileAccessRight profileAccessRight : profileAccessRights) {
            for (ProfileAccessRight profileAccessRight2 : profileAccessRights2) {
                if (EStringUtil.equals(profileAccessRight.getProfileId(), profileAccessRight2.getProfileId())) {
                    if (profileAccessRight.getAccessRight().getValue() != profileAccessRight2.getAccessRight()
                            .getValue()) {
                        return false;
                    } else {
                        count++;
                    }
                }
            }
        }
        if (count != profileAccessRights.size()) {
            return false;
        }
        return true;
    }

    @Test
    public void testSaveAuthority2() throws EarthException {
        List<UserAccessRight> userAccessRights = new ArrayList<UserAccessRight>();

        UserAccessRight userAccessRight1 = new UserAccessRight();
        userAccessRight1.setUserId("admin");
        userAccessRight1.setAccessRight(AccessRight.RW);
        userAccessRights.add(userAccessRight1);

        List<ProfileAccessRight> profileAccessRights = new ArrayList<ProfileAccessRight>();

        ProfileAccessRight profileAccessRight1 = new ProfileAccessRight();
        profileAccessRight1.setProfileId("profile_1");
        profileAccessRight1.setAccessRight(AccessRight.NONE);
        profileAccessRights.add(profileAccessRight1);

        TemplateKey templateKey = new TemplateKey("001", "template_1");
        long numOfRecords = templateService.saveAuthority(templateKey, userAccessRights, profileAccessRights);
        Assert.assertEquals(FOUR, numOfRecords);
        List<UserAccessRight> userAccessRights2 = templateService.getUserAuthority(templateKey);
        Assert.assertEquals(userAccessRights.size(), userAccessRights2.size());
        Assert.assertTrue(compare2ListUserAccessRight(userAccessRights, userAccessRights2));
        List<ProfileAccessRight> profileAccessRights2 = templateService.getProfileAuthority(templateKey);
        Assert.assertEquals(profileAccessRights.size(), profileAccessRights2.size());
        Assert.assertTrue(compare2ListProfileAccessRight(profileAccessRights, profileAccessRights2));

        List<String> profileIds = new ArrayList<String>();
        for (ProfileAccessRight profileAccessRight : profileAccessRights) {
            profileIds.add(profileAccessRight.getProfileId());
        }

        List<MgrUserProfile> mgrUserProfiles = data.getListByProfileIds(profileIds);
        Map<String, Integer> mapAccessRightValueP = new HashMap<String, Integer>();
        for (ProfileAccessRight profileAccessRight : profileAccessRights) {
            mapAccessRightValueP.put(profileAccessRight.getProfileId(), profileAccessRight.getAccessRightValue());
        }

        List<UserAccessRight> userAccessRights3 = UserAccessRightUtil.getUserAccessRightProfiles(mgrUserProfiles,
            mapAccessRightValueP);
        List<UserAccessRight> templateAccessRights = UserAccessRightUtil.mixAuthority(userAccessRights,
                userAccessRights3);

        List<UserAccessRight> userAccessRights4 = data.getMixAuthorityTemplate(templateKey);
        Assert.assertTrue(compare2ListUserAccessRight(templateAccessRights, userAccessRights4));
    }

    /**
     * In case SaveAuthority unsuccessfully!
     *
     * @throws EarthException
     */
    @Test
    public void testSaveAuthority3() throws EarthException {
        List<UserAccessRight> userAccessRights = new ArrayList<UserAccessRight>();

        UserAccessRight userAccessRight1 = new UserAccessRight();
        userAccessRight1.setUserId("admin_admin_admin_admin");
        userAccessRight1.setAccessRight(AccessRight.RW);
        userAccessRights.add(userAccessRight1);

        List<ProfileAccessRight> profileAccessRights = new ArrayList<ProfileAccessRight>();

        ProfileAccessRight profileAccessRight1 = new ProfileAccessRight();
        profileAccessRight1.setProfileId("profile_1");
        profileAccessRight1.setAccessRight(AccessRight.RO);
        profileAccessRights.add(profileAccessRight1);

        TemplateKey templateKey = new TemplateKey("001", "template_1");
        long numOfRecords = 0;
        try {
            numOfRecords = templateService.saveAuthority(templateKey, userAccessRights, profileAccessRights);
        } catch (EarthException e) {
            Assert.assertEquals(0, numOfRecords);
            List<UserAccessRight> userAccessRights2 = templateService.getUserAuthority(templateKey);
            Assert.assertNotEquals(userAccessRights.size(), userAccessRights2.size());
            Assert.assertFalse(compare2ListUserAccessRight(userAccessRights, userAccessRights2));
            List<ProfileAccessRight> profileAccessRights2 = templateService.getProfileAuthority(templateKey);
            Assert.assertNotEquals(profileAccessRights.size(), profileAccessRights2.size());
            Assert.assertFalse(compare2ListProfileAccessRight(profileAccessRights, profileAccessRights2));

            List<String> profileIds = new ArrayList<String>();
            for (ProfileAccessRight profileAccessRight : profileAccessRights) {
                profileIds.add(profileAccessRight.getProfileId());
            }

            List<MgrUserProfile> mgrUserProfiles = data.getListByProfileIds(profileIds);
            Map<String, Integer> mapAccessRightValueP = new HashMap<String, Integer>();
            for (ProfileAccessRight profileAccessRight : profileAccessRights) {
                mapAccessRightValueP.put(profileAccessRight.getProfileId(), profileAccessRight.getAccessRightValue());
            }

            List<UserAccessRight> userAccessRights3 = UserAccessRightUtil.getUserAccessRightProfiles(mgrUserProfiles,
                mapAccessRightValueP);
            List<UserAccessRight> templateAccessRights = UserAccessRightUtil.mixAuthority(userAccessRights,
                    userAccessRights3);

            List<UserAccessRight> userAccessRights4 = data.getMixAuthorityTemplate(templateKey);
            Assert.assertFalse(compare2ListUserAccessRight(templateAccessRights, userAccessRights4));
        }

    }

    @After
    public void tearDown() throws EarthException {
        deleteTables();
    }

    public void deleteTables() throws EarthException {
        List<String> userIds = new ArrayList<String>();
        userIds.add("minhtv");
        userIds.add("thuynd");
        userIds.add("admin");
        userIds.add("khanhnv");
        userIds.add("hantn");
        data.deleteUserProfile(userIds);
        data.deleteMgrTemplateU(templateKey);
        data.deleteMgrTemplateP(templateKey);
        data.deleteCtlTemplate(templateKey);
    }
}