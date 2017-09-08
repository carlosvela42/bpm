package co.jp.nej.earth.service;

import co.jp.nej.earth.dao.MenuAuthorityDao;
import co.jp.nej.earth.dao.ProfileDao;
import co.jp.nej.earth.dao.TemplateAuthorityDao;
import co.jp.nej.earth.dao.UserDao;
import co.jp.nej.earth.dao.UserProfileDao;
import co.jp.nej.earth.dao.WorkspaceDao;
import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.model.Message;
import co.jp.nej.earth.model.MgrWorkspace;
import co.jp.nej.earth.model.MultipleTransactionManager;
import co.jp.nej.earth.model.ProfileAccessRight;
import co.jp.nej.earth.model.TemplateKey;
import co.jp.nej.earth.model.TransactionManager;
import co.jp.nej.earth.model.UserAccessRight;
import co.jp.nej.earth.model.UsersProfile;
import co.jp.nej.earth.model.constant.Constant;
import co.jp.nej.earth.model.constant.Constant.ErrorCode;
import co.jp.nej.earth.model.constant.Constant.ScreenItem;
import co.jp.nej.earth.model.entity.MgrMenu;
import co.jp.nej.earth.model.entity.MgrProfile;
import co.jp.nej.earth.model.entity.MgrUserProfile;
import co.jp.nej.earth.model.sql.QMgrProfile;
import co.jp.nej.earth.util.ConversionUtil;
import co.jp.nej.earth.util.DateUtil;
import co.jp.nej.earth.util.EMessageResource;
import co.jp.nej.earth.util.EStringUtil;
import co.jp.nej.earth.util.UserAccessRightUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProfileServiceImpl extends BaseService implements ProfileService {

    @Autowired
    private EMessageResource eMessageResource;

    @Autowired
    private ProfileDao profileDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private MenuAuthorityDao menuAuthorityDao;

    @Autowired
    private TemplateAuthorityDao templateAuthorityDao;

    @Autowired
    private MenuService menuService;

    @Autowired
    private UserProfileDao userProfileDao;

    @Autowired
    private WorkspaceDao workspaceDao;

    private static final Logger LOG = LoggerFactory.getLogger(ProfileServiceImpl.class);

    @Override
    public List<MgrProfile> getAll() throws EarthException {
        TransactionManager transactionManager = new TransactionManager(Constant.EARTH_WORKSPACE_ID);
        try {
            List<MgrProfile> mgrProfiles = profileDao.findAll(Constant.EARTH_WORKSPACE_ID,
                QMgrProfile.newInstance().profileId.asc());
            transactionManager.getManager().commit(transactionManager.getTxStatus());
            return mgrProfiles;
        } catch (Exception ex) {
            transactionManager.getManager().rollback(transactionManager.getTxStatus());
            LOG.error(ex.getMessage());
            throw new EarthException(ex);
        }
    }

    @Override
    public List<MgrProfile> getProfilesByUserId(String userId) throws EarthException {
        TransactionManager transactionManager = new TransactionManager(Constant.EARTH_WORKSPACE_ID);
        try {
            List<MgrProfile> mgrProfiles = profileDao.getProfilesByUserId(userId);
            transactionManager.getManager().commit(transactionManager.getTxStatus());
            return mgrProfiles;
        } catch (Exception ex) {
            transactionManager.getManager().rollback(transactionManager.getTxStatus());
            LOG.error(ex.getMessage());
            throw new EarthException(ex);
        }
    }

    @Override
    public Map<String, Object> getDetail(String profileId) throws EarthException {
        Map<String, Object> map = new HashMap<>();
        return ConversionUtil.castObject(executeTransaction(() -> {
            Map<String, Object> detail = new HashMap<>();
            try {

                MgrProfile mgrProfile = profileDao.getById(profileId);
                if(mgrProfile != null) {
                    List<String> userIds = userDao.getUserIdsByProfileId(profileId);
                    List<UsersProfile> usersProfiles = userDao.getUsersByProfileId(profileId);

                    detail.put("mgrProfile", mgrProfile);
                    detail.put("userIds", userIds);
                    detail.put("usersProfiles", usersProfiles);
                }
            } catch (Exception ex) {
                LOG.error(ex.getMessage());
                throw new EarthException(ex);
            }
            return detail;
        }), map.getClass());
    }

    @Override
    public List<Message> validate(MgrProfile mgrProfile, boolean insert) {
        List<Message> listMessage = new ArrayList<>();
        try {
            if (insert) {
                if (isExist(mgrProfile.getProfileId())) {
                    Message message = new Message(ErrorCode.E0003, eMessageResource.get(ErrorCode.E0003,
                        new String[]{ScreenItem.PROFILE_ID, ScreenItem.PROFILE}));
                    listMessage.add(message);
                }
            }
            return listMessage;
        } catch (Exception ex) {
            Message message = new Message(ErrorCode.E1009,
                eMessageResource.get(ErrorCode.E1009, new String[]{EStringUtil.EMPTY}));
            listMessage.add(message);
            return listMessage;
        }
    }

    @Override
    public boolean insertAndAssignUsers(MgrProfile mgrProfile, List<String> userIds) throws EarthException {
        TransactionManager transactionManager = null;
        try {
            transactionManager = new TransactionManager(Constant.EARTH_WORKSPACE_ID);
            mgrProfile.setLastUpdateTime(
                DateUtil.getCurrentDate(DateUtil.getCurrentDate(Constant.DatePattern.DATE_FORMAT_YYYY_MM_DD)));

            profileDao.insertOne(mgrProfile);
            boolean assignUser = true;
            if (userIds.size() > 0 && !userIds.contains("")) {
                assignUser = profileDao.assignUsers(mgrProfile.getProfileId(), userIds) == userIds.size();
            }
            if (!assignUser) {
                throw new EarthException("AssignUsers unsuccessfully!");
            }

            transactionManager.getManager().commit(transactionManager.getTxStatus());
            return true;
        } catch (EarthException ex) {
            if (transactionManager != null) {
                transactionManager.getManager().rollback(transactionManager.getTxStatus());
            }

            LOG.error(ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean updateAndAssignUsers(MgrProfile mgrProfile, List<String> userIds) throws EarthException {

        MultipleTransactionManager multipleTransactionManager = new MultipleTransactionManager();
        multipleTransactionManager.add(new TransactionManager(Constant.EARTH_WORKSPACE_ID));
        boolean updateResult = true;
        try {
            if (profileDao.updateOne(mgrProfile) <= 0) {
                throw new EarthException("Update information of Profile fail");
            }
            List<String> profileIds = new ArrayList<>();
            profileIds.add(mgrProfile.getProfileId());
            userProfileDao.deleteListByProfileIds(profileIds);
            long size = userIds.size();
            if (userIds.contains("")) {
                size = 0L;
            }
            if (profileDao.assignUsers(mgrProfile.getProfileId(), userIds) != size) {
                throw new EarthException("Assign User to Profile fail");
            }

            List<MgrMenu> mgrMenus = menuService.getMenuByProfileId(profileIds);

            // Insert mix authority for menus.
            insertMenusMixAuthority(mgrMenus);

            // Insert mix authority for templates.
            List<MgrWorkspace> mgrWorkspaces = workspaceDao.getAll();
            if (mgrWorkspaces != null && mgrWorkspaces.size() > 0) {
                List<MgrUserProfile> mgrUserProfiles = userProfileDao.getListByProfileIds(profileIds);
                for (MgrWorkspace mgrWorkspace : mgrWorkspaces) {
                    multipleTransactionManager.add(new TransactionManager(mgrWorkspace.getStringWorkspaceId()));
                    List<TemplateKey> templateKeys = templateAuthorityDao
                        .getTemplateKeysByProfile(mgrWorkspace.getStringWorkspaceId(), mgrProfile.getProfileId());
                    if (!CollectionUtils.isEmpty(templateKeys)) {
                        insertTemplatesMixAuthority(templateKeys, mgrUserProfiles);
                    }
                }
            }

            // Commit transactions.
            multipleTransactionManager.commit();

        } catch (EarthException ex) {
            // Rollback transactions.
            multipleTransactionManager.rollback();
            LOG.error(ex.getMessage());
            ex.printStackTrace();
            updateResult = false;
        }
        return updateResult;
    }

    private void insertTemplatesMixAuthority(List<TemplateKey> templateKeys, List<MgrUserProfile> mgrUserProfiles)
        throws EarthException {
        for (TemplateKey templateKey : templateKeys) {
            templateAuthorityDao.deleteAllMixAuthority(templateKey);

            List<ProfileAccessRight> profileAccessRights = templateAuthorityDao.getProfileAuthority(templateKey);
            Map<String, Integer> mapAccessRightValueP = new HashMap<>();

            if (profileAccessRights != null) {
                for (ProfileAccessRight profileAccessRight : profileAccessRights) {
                    mapAccessRightValueP.put(profileAccessRight.getProfileId(),
                        profileAccessRight.getAccessRightValue());
                }
                List<UserAccessRight> userAccessRightByProfiles = UserAccessRightUtil
                    .getUserAccessRightProfiles(mgrUserProfiles, mapAccessRightValueP);
                List<UserAccessRight> userAccessRights = templateAuthorityDao.getUserAuthority(templateKey);

                List<UserAccessRight> templateAccessRights = UserAccessRightUtil.mixAuthority(userAccessRights,
                    userAccessRightByProfiles);

                if (templateAuthorityDao.insertMixAuthority(templateKey,
                    templateAccessRights) != templateAccessRights.size()) {
                    throw new EarthException("Insert Mix template " + templateKey.getTemplateId() + " in workspace "
                                             + templateKey.getTemplateId());
                }
            }
        }
    }

    private void insertMenusMixAuthority(List<MgrMenu> mgrMenus) throws EarthException {
        if (mgrMenus != null) {
            // Insert mix authority for menu.
            for (MgrMenu mgrMenu : mgrMenus) {
                menuAuthorityDao.deleteAllMixAuthority(new ArrayList<>(Arrays.asList(mgrMenu.getFunctionId())));

                List<UserAccessRight> userAccessRightByProfiles = menuAuthorityDao
                    .getUserAuthorityByProfiles(mgrMenu.getFunctionId());
                List<UserAccessRight> userAccessRights = menuAuthorityDao.getUserAuthority(mgrMenu.getFunctionId());
                if ((userAccessRightByProfiles != null)
                    && (userAccessRights != null)) {
                    List<UserAccessRight> menuAccessRights = UserAccessRightUtil.mixAuthority(userAccessRights,
                        userAccessRightByProfiles);
                    if (menuAuthorityDao.insertMixAuthority(mgrMenu.getFunctionId(),
                        menuAccessRights) != menuAccessRights.size()) {
                        throw new EarthException("Insert Mix Menu fail");
                    }
                }
            }
        }
    }

    @Override
    public boolean deleteList(List<String> profileIds) throws EarthException {
        MultipleTransactionManager multipleTransactionManager = new MultipleTransactionManager();
        TransactionManager transactionManager = new TransactionManager(Constant.EARTH_WORKSPACE_ID);
        multipleTransactionManager.add(transactionManager);
        try {

            // Insert mix authority for templates.
            List<MgrWorkspace> mgrWorkspaces = workspaceDao.getAll();
            if (mgrWorkspaces != null && mgrWorkspaces.size() > 0) {

                for (MgrWorkspace mgrWorkspace : mgrWorkspaces) {
                    multipleTransactionManager.add(new TransactionManager(mgrWorkspace.getStringWorkspaceId()));
                    for (String profileId : profileIds) {
                        List<TemplateKey> templateKeys = templateAuthorityDao
                            .getTemplateKeysByProfile(mgrWorkspace.getStringWorkspaceId(), profileId);
                        templateAuthorityDao.deleteListByProfileIds(mgrWorkspace.getStringWorkspaceId(),
                            new ArrayList<>(Arrays.asList(profileId)));
                        List<String> profileIdsTemplate = new ArrayList<>();
                        for (TemplateKey templateKey : templateKeys) {
                            profileIdsTemplate = templateAuthorityDao.getProfiles(templateKey);
                        }
                        List<MgrUserProfile> mgrUserProfiles = userProfileDao.getListByProfileIds(profileIdsTemplate);
                        if (templateKeys != null && templateKeys.size() > 0 && mgrUserProfiles != null) {
                            insertTemplatesMixAuthority(templateKeys, mgrUserProfiles);
                        }
                    }
                }
            }

            List<MgrMenu> mgrMenus = menuService.getMenuByProfileId(profileIds);

            menuAuthorityDao.deleteListByProfileIds(profileIds);

            // Insert mix authority for menus.
            insertMenusMixAuthority(mgrMenus);

            profileDao.deleteList(profileIds);

            userProfileDao.deleteListByProfileIds(profileIds);

        } catch (Exception ex) {
            // Rollback transactions.
            multipleTransactionManager.rollback();
            LOG.error(ex.getMessage());
            return false;
        }
        // Commit transactions.
        multipleTransactionManager.commit();
        return true;
    }

    @Override
    public boolean insertOne(MgrProfile mgrProfile) throws EarthException {
        TransactionManager transactionManager = new TransactionManager(Constant.EARTH_WORKSPACE_ID);
        try {
            long insertOne = profileDao.insertOne(mgrProfile);
            transactionManager.getManager().commit(transactionManager.getTxStatus());
            return insertOne > 0L;
        } catch (Exception ex) {
            LOG.error(ex.getMessage());
            transactionManager.getManager().rollback(transactionManager.getTxStatus());
            return false;
        }
    }

    private boolean isExist(String profileId) throws EarthException {
        TransactionManager transactionManager = new TransactionManager(Constant.EARTH_WORKSPACE_ID);
        try {
            MgrProfile mgrProfile = profileDao.getById(profileId);
            transactionManager.getManager().commit(transactionManager.getTxStatus());
            return (mgrProfile != null);
        } catch (Exception ex) {
            LOG.error(ex.getMessage());
            transactionManager.getManager().rollback(transactionManager.getTxStatus());
            return true;
        }
    }

    @Override
    public boolean assignUsers(String profileId, List<String> userIds) throws EarthException {
        TransactionManager transactionManager = new TransactionManager(Constant.EARTH_WORKSPACE_ID);
        try {
            long asign = profileDao.assignUsers(profileId, userIds);
            transactionManager.getManager().commit(transactionManager.getTxStatus());
            return asign == userIds.size();
        } catch (Exception ex) {
            LOG.error(ex.getMessage());
            transactionManager.getManager().rollback(transactionManager.getTxStatus());
            return false;
        }
    }

}