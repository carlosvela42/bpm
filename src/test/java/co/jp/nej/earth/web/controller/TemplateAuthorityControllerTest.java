//package co.jp.nej.earth.web.controller;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.junit.Assert;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import co.jp.nej.earth.BaseTest;
//import co.jp.nej.earth.exception.EarthException;
//import co.jp.nej.earth.model.MgrWorkspace;
//import co.jp.nej.earth.model.ProfileAccessRight;
//import co.jp.nej.earth.model.TemplateKey;
//import co.jp.nej.earth.model.UserAccessRight;
//import co.jp.nej.earth.model.entity.MgrProfile;
//import co.jp.nej.earth.model.entity.MgrTemplate;
//import co.jp.nej.earth.model.entity.MgrUser;
//import co.jp.nej.earth.model.enums.AccessRight;
//import co.jp.nej.earth.service.TemplateService;
//
//public class TemplateAuthorityControllerTest extends BaseTest {
//
//    @Autowired
//    private TemplateService templateService;
//
//    @Test
//    public void testGetAllWorkspace() throws EarthException {
//        List<MgrWorkspace> mgrWorkspaces = templateService.getAllWorkspace();
//        Assert.assertTrue(mgrWorkspaces != null && mgrWorkspaces.size() > 0);
//    }
//
//    @Test
//    public void testGetTemplateListInfo() throws EarthException {
//        List<MgrTemplate> mgrTemplates = templateService.getTemplateListInfo("001");
//        Assert.assertTrue(mgrTemplates != null && mgrTemplates.size() > 0);
//    }
//
//    @Test
//    public void testGetById() throws EarthException {
//        TemplateKey templateKey = new TemplateKey();
//        templateKey.setTemplateId("1");
//        templateKey.setWorkspaceId("001");
//        MgrTemplate mgrTemplate = templateService.getById(templateKey);
//        Assert.assertTrue(mgrTemplate != null);
//    }
//
//    @Test
//    public void testGetUserAuthority() throws EarthException {
//        TemplateKey templateKey = new TemplateKey();
//        templateKey.setTemplateId("1");
//        templateKey.setWorkspaceId("001");
//        List<UserAccessRight> userAccessRights = templateService.getUserAuthority(templateKey);
//        Assert.assertTrue(userAccessRights != null && userAccessRights.size() > 0);
//    }
//
//    @Test
//    public void testGetProfileAuthority() throws EarthException {
//        TemplateKey templateKey = new TemplateKey();
//        templateKey.setTemplateId("1");
//        templateKey.setWorkspaceId("001");
//        List<ProfileAccessRight> profileAccessRights = templateService.getProfileAuthority(templateKey);
//        Assert.assertTrue(profileAccessRights != null && profileAccessRights.size() > 0);
//    }
//
//    @Test
//    public void testGetAllUser() throws EarthException {
//        List<MgrUser> mgrUsers = templateService.getAllUser();
//        Assert.assertTrue(mgrUsers != null && mgrUsers.size() > 0);
//    }
//
//    @Test
//    public void testGetAllProfile() throws EarthException {
//        List<MgrProfile> mgrProfiles = templateService.getAllProfile();
//        Assert.assertTrue(mgrProfiles != null && mgrProfiles.size() > 0);
//    }
//
//    @Test
//    public void testSaveAuthority() throws EarthException {
//        TemplateKey templateKey = new TemplateKey();
//        templateKey.setTemplateId("1");
//        templateKey.setWorkspaceId("001");
//
//        List<UserAccessRight> userAccessRights = new ArrayList<UserAccessRight>();
//        UserAccessRight userAccessRight1 = new UserAccessRight();
//        userAccessRight1.setUserId("admin");
//        userAccessRight1.setAccessRight(AccessRight.SO);
//        userAccessRights.add(userAccessRight1);
//
//        UserAccessRight userAccessRight3 = new UserAccessRight();
//        userAccessRight3.setUserId("HanTTN");
//        userAccessRight3.setAccessRight(AccessRight.FULL);
//        userAccessRights.add(userAccessRight3);
//
//        UserAccessRight userAccessRight2 = new UserAccessRight();
//        userAccessRight2.setUserId("minhtv123");
//        userAccessRight2.setAccessRight(AccessRight.RW);
//        userAccessRights.add(userAccessRight2);
//
//        List<ProfileAccessRight> profileAccessRights = new ArrayList<ProfileAccessRight>();
//        ProfileAccessRight profileAccessRights1 = new ProfileAccessRight();
//        profileAccessRights1.setProfileId("profile_4");
//        profileAccessRights1.setAccessRight(AccessRight.RO);
//        profileAccessRights.add(profileAccessRights1);
//
//        ProfileAccessRight profileAccessRights2 = new ProfileAccessRight();
//        profileAccessRights2.setProfileId("profile_6");
//        profileAccessRights2.setAccessRight(AccessRight.FULL);
//        profileAccessRights.add(profileAccessRights2);
//
//        Assert.assertTrue(templateService.saveAuthority(templateKey, userAccessRights, profileAccessRights));
//    }
//}