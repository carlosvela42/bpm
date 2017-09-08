package co.jp.nej.earth.service;

import co.jp.nej.earth.dao.LicenseHistoryDao;
import co.jp.nej.earth.dao.LoginControlDao;
import co.jp.nej.earth.dao.MenuAuthorityDao;
import co.jp.nej.earth.dao.ProfileDao;
import co.jp.nej.earth.dao.TemplateAuthorityDao;
import co.jp.nej.earth.dao.UserDao;
import co.jp.nej.earth.dao.UserProfileDao;
import co.jp.nej.earth.dao.WorkspaceDao;
import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.manager.session.EarthSessionManager;
import co.jp.nej.earth.model.MenuAccessRight;
import co.jp.nej.earth.model.Message;
import co.jp.nej.earth.model.MessagesWithToken;
import co.jp.nej.earth.model.MgrWorkspace;
import co.jp.nej.earth.model.MultipleTransactionManager;
import co.jp.nej.earth.model.TemplateAccessRight;
import co.jp.nej.earth.model.TransactionManager;
import co.jp.nej.earth.model.UserInfo;
import co.jp.nej.earth.model.UsersProfile;
import co.jp.nej.earth.model.constant.Constant;
import co.jp.nej.earth.model.constant.Constant.DatePattern;
import co.jp.nej.earth.model.constant.Constant.ErrorCode;
import co.jp.nej.earth.model.constant.Constant.ScreenItem;
import co.jp.nej.earth.model.constant.Constant.Session;
import co.jp.nej.earth.model.entity.CtlLogin;
import co.jp.nej.earth.model.entity.MgrUser;
import co.jp.nej.earth.model.entity.StrCal;
import co.jp.nej.earth.model.enums.Channel;
import co.jp.nej.earth.model.sql.QMgrUser;
import co.jp.nej.earth.model.sql.QMgrUserProfile;
import co.jp.nej.earth.util.ConversionUtil;
import co.jp.nej.earth.util.CryptUtil;
import co.jp.nej.earth.util.DateUtil;
import co.jp.nej.earth.util.EMessageResource;
import co.jp.nej.earth.util.EStringUtil;
import co.jp.nej.earth.util.LoginUtil;
import co.jp.nej.earth.util.MenuUtil;
import co.jp.nej.earth.util.PasswordPolicy;
import co.jp.nej.earth.util.TemplateUtil;
import com.google.common.base.Joiner;
import com.querydsl.core.types.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl extends BaseService implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private LoginControlDao loginControlDao;

    @Autowired
    private LicenseHistoryDao licenseHistoryDao;

    @Autowired
    private EMessageResource eMessageResource;

    @Autowired
    private TemplateAuthorityDao templateAuthorityDao;

    @Autowired
    private WorkspaceDao workspaceDao;

    @Autowired
    private ProfileDao profileDao;

    @Autowired
    private UserProfileDao userProfileDao;

    @Autowired
    private MenuAuthorityDao menuAuthorityDao;

    @Autowired
    private PasswordPolicy passwordPolicy;

    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    /**
     * @param userId   id of user
     * @param password password of user
     * @param session  HttpSession object
     * @return list message determined that user log in successfully or not
     */
    public List<Message> login(String userId, String password, HttpSession session, Channel channel)
        throws EarthException {
        MessagesWithToken messagesWithToken = doLogin(userId, password, session, channel, null);
        return messagesWithToken.getMessages();
    }

    /**
     * @param userId   id of user
     * @param password password of user
     * @param session  HttpSession object
     * @return list message determined that user log in successfully or not
     */
    private MessagesWithToken doLogin(String userId, String password, HttpSession session, Channel channel,
                                  String processId) throws EarthException {
        LOG.info("login(String userId, String password, HttpSession session, Channel channel)");
        MessagesWithToken messagesWithToken = new MessagesWithToken();
        List<Message> listMessage = messagesWithToken.getMessages();
        if (EStringUtil.isEmpty(userId)) {
            Message message = new Message(ErrorCode.E0001,
                eMessageResource.get(ErrorCode.E0001, new String[]{ScreenItem.USER_ID}));
            listMessage.add(message);
            return messagesWithToken;
        }

        TransactionManager transactionManager = null;
        try {
            transactionManager = new TransactionManager(Constant.EARTH_WORKSPACE_ID);

            // Check user exists or not.
            String ePassword = CryptUtil.encryptData(password);
            MgrUser mgrUser = userDao.getUserByIdAndPassword(userId, ePassword);

            if (mgrUser == null) {
                Message message = new Message(ErrorCode.E0002,
                    eMessageResource.get(ErrorCode.E0002, new String[]{}));
                listMessage.add(message);
            } else {
                String loginToken;
                if (Channel.BATCH == channel) {
                    // Batch login
                    loginToken = LoginUtil.generateBatchToken(userId, processId);
                    messagesWithToken.setToken(loginToken);
                } else {
                    // Login by screen or web service
                    loginToken = LoginUtil.generateToken(userId, session);
                    EarthSessionManager.save(loginToken, session);
                }

                CtlLogin ctlLogin = new CtlLogin(loginToken, userId, DateUtil.getCurrentDateString(), null);
                loginControlDao.add(Constant.EARTH_WORKSPACE_ID, ctlLogin);
                countAndUpdateLicenseHistory(userId);
                if (!Channel.BATCH.equals(channel)) {
                    session.setAttribute(Session.USER_INFO, new UserInfo(userId, mgrUser.getName(), loginToken));

                    // Save All Menus Access right into session.
                    Map<String, MenuAccessRight> menuAccessRightMap = menuAuthorityDao.getMixAuthority(userId);
                    if (Channel.INTERNAL.equals(channel)) {
                        MenuUtil.saveToSession(session, menuAccessRightMap);
                        session.setAttribute(Session.MENU_STRUCTURE, MenuUtil.buildMenuTree(menuAccessRightMap));
                    }

                    // Save All Workspaces information into session.
                    List<MgrWorkspace> mgrWorkspaces = workspaceDao.getAll();
                    session.setAttribute(Session.WORKSPACES, mgrWorkspaces);

                    // Save All templates access right into session.
                    List<MgrWorkspace> failedWorkspaces = new ArrayList<>();
                    TemplateAccessRight templateAccessRights = getTemplatesAccessRightOfAllWorkspaces(userId,
                        mgrWorkspaces, failedWorkspaces);
                    TemplateUtil.saveToSession(session, templateAccessRights);
                    if(!CollectionUtils.isEmpty(failedWorkspaces)) {
                        Message message = new Message(ErrorCode.W0003,
                            eMessageResource.get(ErrorCode.W0003, new String[] { Joiner.on(", ")
                                .join(failedWorkspaces.stream()
                                .map(MgrWorkspace::getWorkspaceName).collect(Collectors.toList())) }),
                            Message.MessageTye.WARNING);
                        listMessage.add(message);

                        session.setAttribute(Session.FAILED_WORKSPACES, failedWorkspaces);
                    }
                }
            }

        } catch (Exception e) {
            if (transactionManager != null) {
                transactionManager.rollback();
            }
            throw new EarthException(e);
        }

        if (listMessage.size() > 0) {
            transactionManager.rollback();
        } else {
            transactionManager.commit();
        }
        return messagesWithToken;
    }

    @Override
    public MessagesWithToken loginBatch(String userId, String password, String processId)
        throws EarthException {
        return doLogin(userId, password, null, Channel.BATCH, processId);
    }

    private TemplateAccessRight getTemplatesAccessRightOfAllWorkspaces(String userId,
                                                                       List<MgrWorkspace> mgrWorkspaces,
        List<MgrWorkspace> failedWorkspaces) {
        TemplateAccessRight templateAccessRights = new TemplateAccessRight();

        TransactionManager transactionMgr = null;
        for (MgrWorkspace mgrWorkspace : mgrWorkspaces) {
            try {
                transactionMgr = new TransactionManager(mgrWorkspace.getStringWorkspaceId());
                templateAccessRights.putAll(
                    templateAuthorityDao.getMixAuthority(userId, mgrWorkspace.getStringWorkspaceId()));
                transactionMgr.getManager().commit(transactionMgr.getTxStatus());
            } catch (Exception ex) {
                LOG.error(ex.getMessage(), ex);
                LOG.error("EARTH: Reason could be can not connect with workspace with Id: "
                    + mgrWorkspace.getWorkspaceId()
                    + ". If so, consider removing this workspace record from database.");
                TransactionManager.rollbackWithCheck(transactionMgr);
                failedWorkspaces.add(mgrWorkspace);
            }
        }

        return templateAccessRights;
    }

    public boolean logout(HttpSession session) throws EarthException {
        return (boolean) executeTransaction(Constant.EARTH_WORKSPACE_ID, () -> {
            if (LoginUtil.isLogin(session)) {
                UserInfo userInfo = (UserInfo) session.getAttribute(Session.USER_INFO);
                String sessionId = userInfo.getLoginToken();

                loginControlDao.updateOutTime(sessionId, DateUtil.getCurrentDateString());
                String userId = userInfo.getUserId();
                countAndUpdateLicenseHistory(userId);
                session.invalidate();
                return true;
            }

            return false;
        });
    }

    @Override
    public boolean logoutBatch(String userId, String token) throws EarthException {
        return (boolean) executeTransaction(Constant.EARTH_WORKSPACE_ID, () -> {
            if (EStringUtil.isNotEmpty(token)) {

                loginControlDao.updateOutTime(token, DateUtil.getCurrentDateString());
                countAndUpdateLicenseHistory(userId);
                return true;
            }

            return false;
        });
    }

    private void countAndUpdateLicenseHistory(String userId) throws EarthException {
        try {
            List<StrCal> strCals = loginControlDao.getNumberOnlineUserByProfile(userId);
            List<StrCal> strCalEntireSystem = loginControlDao.getLicenceOfEntireSystem();
            if (strCalEntireSystem != null && strCalEntireSystem.size() > 0) {
                strCals.addAll(strCalEntireSystem);
            }
            String currentDate = DateUtil.getCurrentDate(DatePattern.DATE_FORMAT_YYYYMMDDHHMMSSSSS);
            for (StrCal ls : strCals) {
                ls.setProcessTime(currentDate);
                ls.setLastUpdateTime(currentDate);
                licenseHistoryDao.add(Constant.EARTH_WORKSPACE_ID, ls);
            }
        } catch (Exception e) {
            throw new EarthException(e);
        }
    }

    public List<MgrUser> getAll() throws EarthException {
        TransactionManager transactionManager = new TransactionManager(Constant.EARTH_WORKSPACE_ID);
        List<MgrUser> mgrUsers = null;
        try {
            mgrUsers = userDao.findAll(Constant.EARTH_WORKSPACE_ID, QMgrUser.newInstance().userId.asc());
            transactionManager.getManager().commit(transactionManager.getTxStatus());
        } catch (Exception ex) {
            transactionManager.getManager().rollback(transactionManager.getTxStatus());
            throw new EarthException(ex);
        }
        return mgrUsers;
    }

    @Override
    public List<UsersProfile> getUsersProfileId(String profileId) throws EarthException {
        return ConversionUtil.castList(executeTransaction(Constant.EARTH_WORKSPACE_ID, () -> {
            return userDao.getUsersByProfileId(profileId);
        }), UsersProfile.class);
    }

    public List<Message> validate(MgrUser mgrUser, boolean insert) {
        List<Message> listMessage = new ArrayList<Message>();
        try {
            if (insert) {
                if (isExist(mgrUser.getUserId())) {
                    Message message = new Message(ErrorCode.E0003, eMessageResource.get(ErrorCode.E0003,
                        new String[]{ScreenItem.USER_ID}));
                    listMessage.add(message);
                    return listMessage;
                }
            }
            if (mgrUser.isChangePassword()) {
                if (EStringUtil.isEmpty(mgrUser.getPassword())) {
                    Message message = new Message(ErrorCode.E0001,
                        eMessageResource.get(ErrorCode.E0001, new String[]{ScreenItem.NEW_PASSWORD}));
                    listMessage.add(message);
                    return listMessage;
                }
                if (EStringUtil.isEmpty(mgrUser.getConfirmPassword())) {
                    Message message = new Message(ErrorCode.E0001,
                        eMessageResource.get(ErrorCode.E0001, new String[]{ScreenItem.CONFIRM_PASSWORD}));
                    listMessage.add(message);
                    return listMessage;
                }
                if (!EStringUtil.equals(mgrUser.getConfirmPassword(), mgrUser.getPassword())) {
                    Message message = new Message(ErrorCode.E1008, eMessageResource.get(ErrorCode.E1008,
                        new String[]{ScreenItem.NEW_PASSWORD, ScreenItem.CONFIRM_PASSWORD}));
                    listMessage.add(message);
                    return listMessage;
                }
                List<String> passwordValidate = passwordPolicy.validate(mgrUser.getPassword());
                if (passwordValidate != null && passwordValidate.size() > 0) {
                    listMessage = getMessagePasswordPolicy(passwordValidate);
                    return listMessage;
                }
            }
            return listMessage;
        } catch (Exception ex) {
            Message message = new Message(ErrorCode.E1009, eMessageResource.get(ErrorCode.E1009, new String[]{""}));
            listMessage.add(message);
            return listMessage;
        }
    }

    private boolean isExist(String userId) throws EarthException {
        TransactionManager transactionManager = new TransactionManager(Constant.EARTH_WORKSPACE_ID);
        try {
            MgrUser mgrUser = userDao.getById(userId);
            transactionManager.getManager().commit(transactionManager.getTxStatus());
            return (mgrUser != null);
        } catch (Exception ex) {
            transactionManager.getManager().rollback(transactionManager.getTxStatus());
            return true;
        }
    }

    public boolean insertOne(MgrUser mgrUser) throws EarthException {
        TransactionManager transactionManager = new TransactionManager(Constant.EARTH_WORKSPACE_ID);
        try {
            mgrUser.setLastUpdateTime(DateUtil.getCurrentDate(DatePattern.DATE_FORMAT_YYYY_MM_DD));
            mgrUser.setPassword(CryptUtil.encryptData(!EStringUtil.isEmpty(mgrUser.getPassword()) ? mgrUser
                .getPassword() : ""));

            mgrUser.setConfirmPassword(CryptUtil.encryptData(!EStringUtil.isEmpty(mgrUser.getConfirmPassword())
                ? mgrUser.getConfirmPassword() : ""));
            long insert = userDao.add(Constant.EARTH_WORKSPACE_ID, mgrUser);
            if (insert == 0) {
                throw new EarthException("Insert unsuccessfully!");
            }

        } catch (Exception ex) {
            transactionManager.getManager().rollback(transactionManager.getTxStatus());
            LOG.error(ex.getMessage());
            return false;
        }
        transactionManager.getManager().commit(transactionManager.getTxStatus());
        return true;
    }

    public boolean updateOne(MgrUser mgrUser) throws EarthException {
        mgrUser.setLastUpdateTime(DateUtil.getCurrentDate(DatePattern.DATE_FORMAT_YYYY_MM_DD));
        return (boolean) executeTransaction(() -> {
            boolean updateResult = true;
            try {
                if (mgrUser.getPassword() != null) {
                    mgrUser.setPassword(CryptUtil.encryptData(mgrUser.getPassword()));
                    mgrUser.setConfirmPassword(CryptUtil.encryptData(mgrUser.getConfirmPassword()));
                }
                if (userDao.updateOne(mgrUser) <= 0) {
                    updateResult = false;
                }
            } catch (Exception ex) {
                LOG.error(ex.getMessage());
                throw new EarthException(ex);
            }
            return updateResult;
        });
    }

    public boolean deleteList(List<String> userIds) throws EarthException {
        QMgrUser qMgrUser = QMgrUser.newInstance();
        QMgrUserProfile qMgrUserProfile = QMgrUserProfile.newInstance();

        MultipleTransactionManager multipleTransactionManager = new MultipleTransactionManager();
        multipleTransactionManager.add(new TransactionManager(Constant.EARTH_WORKSPACE_ID));
        try {
            List<MgrWorkspace> mgrWorkspaces = workspaceDao.getAll();
            for (MgrWorkspace mgrWorkspace : mgrWorkspaces) {
                try {
                    multipleTransactionManager.add(new TransactionManager(mgrWorkspace.getStringWorkspaceId()));
                } catch (Exception ex) {
                    LOG.error("TTT: Can not get connection to workspace: " + mgrWorkspace.getWorkspaceId());
                    throw ex;
                }
                templateAuthorityDao.deleteListByUserIds(mgrWorkspace.getStringWorkspaceId(), userIds);
            }

            menuAuthorityDao.deleteListByUserIds(userIds);

            List<Map<Path<?>, Object>> conditions = new ArrayList<>();
            for (String userId : userIds) {
                Map<Path<?>, Object> condition = new HashMap<>();
                condition.put(qMgrUser.userId, userId);
                conditions.add(condition);
            }
            userDao.deleteList(Constant.EARTH_WORKSPACE_ID, conditions);

            List<Map<Path<?>, Object>> conditionUserProfiles = new ArrayList<>();
            for (String userId : userIds) {
                Map<Path<?>, Object> condition = new HashMap<>();
                condition.put(qMgrUserProfile.userId, userId);
                conditionUserProfiles.add(condition);
            }
            userProfileDao.deleteList(Constant.EARTH_WORKSPACE_ID, conditionUserProfiles);

        } catch (EarthException ex) {
            // Roll back transactions.
            multipleTransactionManager.rollback();
            ex.printStackTrace();
            LOG.error(ex.getMessage());
            return false;
        }

        // Commit transactions.
        multipleTransactionManager.commit();
        return true;
    }

    public Map<String, Object> getDetail(String userId) throws EarthException {
        TransactionManager transactionManager = new TransactionManager(Constant.EARTH_WORKSPACE_ID);
        try {
            Map<String, Object> detail = new HashMap<String, Object>();
            detail.put("mgrUser", userDao.getById(userId));
            detail.put("mgrProfiles", profileDao.getProfilesByUserId(userId));
            transactionManager.getManager().commit(transactionManager.getTxStatus());
            return detail;
        } catch (Exception ex) {
            transactionManager.getManager().rollback(transactionManager.getTxStatus());
            throw new EarthException(ex);
        }
    }

    public CtlLogin getCtlLoginDetail(Map<Path<?>, Object> condition) throws EarthException {
        return ConversionUtil.castObject(executeTransaction(Constant.EARTH_WORKSPACE_ID, () -> {
            return loginControlDao.findOne(Constant.EARTH_WORKSPACE_ID, condition);
        }), CtlLogin.class);
    }

    public long deleteCtlLogin(Map<Path<?>, Object> condition) throws EarthException {
        return (long) executeTransaction(Constant.EARTH_WORKSPACE_ID, () -> {
            return loginControlDao.delete(Constant.EARTH_WORKSPACE_ID, condition);
        });
    }

    public long deleteCtlLogins(List<Map<Path<?>, Object>> condition) throws EarthException {
        return (long) executeTransaction(Constant.EARTH_WORKSPACE_ID, () -> {
            return loginControlDao.deleteList(Constant.EARTH_WORKSPACE_ID, condition);
        });
    }

    public long deleteAllCtlLogins() throws EarthException {
        return (long) executeTransaction(Constant.EARTH_WORKSPACE_ID, () -> {
            return loginControlDao.deleteAll(Constant.EARTH_WORKSPACE_ID);
        });
    }

    @Override
    public long addCtlLogin(CtlLogin login) throws EarthException {
        return (long) executeTransaction(Constant.EARTH_WORKSPACE_ID, () -> {
            return loginControlDao.add(Constant.EARTH_WORKSPACE_ID, login);
        });
    }

    @Override
    public long updateCtlLogin(Map<Path<?>, Object> condition, Map<Path<?>, Object> updateMap) throws EarthException {
        return (long) executeTransaction(Constant.EARTH_WORKSPACE_ID, () -> {
            return loginControlDao.update(Constant.EARTH_WORKSPACE_ID, condition, updateMap);
        });
    }

    // List message for password.
    private List<Message> getMessagePasswordPolicy(List<String> passwordPolicys) {
        List<Message> messages = new ArrayList<Message>();
        for (String string : passwordPolicys) {
            Message message = new Message(EStringUtil.EMPTY, string);
            messages.add(message);
        }
        return messages;
    }
}