package co.jp.nej.earth.service;

import co.jp.nej.earth.dao.LicenseHistoryDao;
import co.jp.nej.earth.dao.LoginControlDao;
import co.jp.nej.earth.dao.MenuAuthorityDao;
import co.jp.nej.earth.dao.MenuDao;
import co.jp.nej.earth.dao.ProfileDao;
import co.jp.nej.earth.dao.TemplateAuthorityDao;
import co.jp.nej.earth.dao.TemplateDao;
import co.jp.nej.earth.dao.TestTemplateAuthorityDao;
import co.jp.nej.earth.dao.UserDao;
import co.jp.nej.earth.dao.UserProfileDao;
import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.model.TemplateKey;
import co.jp.nej.earth.model.UserAccessRight;
import co.jp.nej.earth.model.constant.Constant;
import co.jp.nej.earth.model.entity.CtlLogin;
import co.jp.nej.earth.model.entity.CtlMenu;
import co.jp.nej.earth.model.entity.CtlTemplate;
import co.jp.nej.earth.model.entity.MgrMenu;
import co.jp.nej.earth.model.entity.MgrMenuP;
import co.jp.nej.earth.model.entity.MgrMenuU;
import co.jp.nej.earth.model.entity.MgrTemplate;
import co.jp.nej.earth.model.entity.MgrUserProfile;
import co.jp.nej.earth.model.entity.StrCal;
import co.jp.nej.earth.model.enums.AccessRight;
import co.jp.nej.earth.model.sql.QCtlMenu;
import co.jp.nej.earth.model.sql.QMgrMenu;
import co.jp.nej.earth.model.sql.QMgrProfile;
import co.jp.nej.earth.model.sql.QMgrUserProfile;
import co.jp.nej.earth.util.ConversionUtil;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PreparingDataService extends BaseService {

    @Autowired
    private LicenseHistoryDao licenseHistoryDao;
    @Autowired
    private TemplateAuthorityDao templateAuthorityDao;

    @Autowired
    private MenuAuthorityDao menuAuthorityDao;

    @Autowired
    private UserProfileDao userProfileDao;

    @Autowired
    private ProfileDao profileDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private TestTemplateAuthorityDao testTemplateAuthorityDao;

    @Autowired
    private TemplateDao templateDao;

    @Autowired
    private LoginControlDao loginControlDao;

    @Autowired
    private MenuDao menuDao;

    private static final Logger LOG = LoggerFactory.getLogger(ProfileServiceImpl.class);

    public void insertCtlTemplate(String workspaceId, CtlTemplate ctlTemplate) {
        try {
            executeTransaction(workspaceId, () -> {
                return templateAuthorityDao.add(workspaceId, ctlTemplate);
            });
        } catch (EarthException e) {
            e.printStackTrace();
        }
    }

    public  Map<TemplateKey, AccessRight> getMixAuthorityTemplate(String userId, String workspaceId)
            throws EarthException {
        return ConversionUtil.castObject(executeTransaction(workspaceId, () -> {
            templateAuthorityDao.getMixAuthority("admin", workspaceId);
            return true;
        }), (new HashMap<TemplateKey, AccessRight>()).getClass());
    }

//    public Map<String, MenuAccessRight> getMixAuthorityMenu(String userId) throws EarthException {
//        PlatformTransactionManager transactionManager = null;
//        TransactionStatus txStatus = null;
//        Map<String, MenuAccessRight> accessMap = new HashMap<>();
//        try {
//            TransactionDefinition txDef = new DefaultTransactionDefinition();
//            transactionManager = ConnectionManager.getTransactionManager(Constant.EARTH_WORKSPACE_ID);
//            txStatus = transactionManager.getTransaction(txDef);
//            accessMap = menuAuthorityDao.getMixAuthority(userId);
//            transactionManager.commit(txStatus);
//        } catch (EarthException e) {
//            if (transactionManager != null) {
//                transactionManager.rollback(txStatus);
//            }
//        }
//        return accessMap;
//    }

    // MinhTV Test UserService
    public boolean deleteUserProfile(List<String> userIds) throws EarthException {
        return (boolean) executeTransaction(() -> {
            QMgrUserProfile qMgrUserProfile = QMgrUserProfile.newInstance();
            userProfileDao.deleteListByUserIds(userIds);
            BooleanBuilder condition = new BooleanBuilder();
            Predicate pre1 = qMgrUserProfile.userId.in(userIds);
            condition.and(pre1);
            if (userProfileDao.search(Constant.EARTH_WORKSPACE_ID, pre1).size() > 0) {
                throw new EarthException("Delete UserProfile fail");
            }
            return true;
        });
    }

    public CtlLogin insertOneCtlLogin(CtlLogin ctlLogin) throws EarthException {
        return (CtlLogin) executeTransaction(() -> {
            return loginControlDao.add(Constant.EARTH_WORKSPACE_ID, ctlLogin);
        });
    }

    public boolean deleteListUsers(List<String> userIds) throws EarthException {
        return (boolean) executeTransaction(() -> {
            boolean del = false;
            try {
                del = userDao.deleteList(userIds) == userIds.size();

            } catch (EarthException ex) {
                return false;
            }
            return del;
        });
    }

    public boolean deleteListProfiles(List<String> profileIds) throws EarthException {
        return (boolean) executeTransaction(() -> {
            try {
                QMgrProfile qMgrProfile = QMgrProfile.newInstance();
                profileDao.deleteList(profileIds);
                BooleanBuilder condition = new BooleanBuilder();
                Predicate pre1 = qMgrProfile.profileId.in(profileIds);
                condition.and(pre1);
                if (profileDao.search(Constant.EARTH_WORKSPACE_ID, pre1).size() > 0) {
                    throw new EarthException("Delete Profile fail");
                }
                return true;
            } catch (Exception ex) {
                LOG.error(ex.getMessage());
                return false;
            }
        });
    }

    public void insertMgrTemplate(String workspaceId, MgrTemplate mgrTemplate) throws EarthException {
        PlatformTransactionManager transactionManager = null;
        TransactionStatus txStatus = null;
        executeTransaction(() -> {
            long count = templateDao.add(workspaceId, mgrTemplate);
            if (count < 0) {
                throw new EarthException("Insert MgrTemplate unsuccessfully!");
            }
            return true;
        });
    }

    public void deleteOneCtlTemplate(String workspaceId) {
        try {
            executeTransaction(workspaceId, () -> {
                long count = templateAuthorityDao.deleteAll(workspaceId);
                if (count < 0) {
                    throw new EarthException("Delete CtlTemplate unsuccessfully!");
                }
                return true;
            });
        } catch (EarthException e) {
            e.printStackTrace();
        }
    }

    public void deleteAllCtlTemplate() {
        deleteOneCtlTemplate("001");
        deleteOneCtlTemplate("002");
        deleteOneCtlTemplate("003");
    }

    public void deleteAllMgrTemplate() {
        deleteOneMgrTemplate("001");
        deleteOneMgrTemplate("002");
        deleteOneMgrTemplate("003");
    }

    public void deleteOneMgrTemplate(String workspaceId) {
        try {
            executeTransaction(workspaceId, () -> {
                long count = templateDao.deleteAll(workspaceId);
                if (count < 0) {
                    throw new EarthException("Delete MgrTemplate unsuccessfully!");
                }
                return true;
            });
        } catch (EarthException e) {
            e.printStackTrace();
        }
    }

    public void insertLicenseHistory(StrCal strCal) throws EarthException {
        executeTransaction(() -> {
            long count = licenseHistoryDao.add(Constant.EARTH_WORKSPACE_ID, strCal);
            if (count < 0) {
                throw new EarthException("Insert StrCal unsuccessfully!");

            };
            return true;
        });
    }

    public void deleteAllLicenseHistory() {
        try {
            executeTransaction(() -> {
                long count = licenseHistoryDao.deleteAll(Constant.EARTH_WORKSPACE_ID);
                if (count < 0) {
                    throw new EarthException("Delete StrCal unsuccessfully!");
                }
                return true;
            });
        } catch (EarthException e) {
            e.printStackTrace();
        }
    }

    public long deleteMgrTemplateU(TemplateKey templateKey) throws EarthException {
        try {
            return (long) executeTransaction(() -> {
                long numOfRecordDeleted = templateAuthorityDao.deleteAllUserAuthority(templateKey);
                if (templateAuthorityDao.getUserAuthority(templateKey).size() > 0) {
                    throw new EarthException("Delete UserAuthority failed");
                }
                return numOfRecordDeleted;
            });
        } catch (EarthException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public long deleteMgrTemplateP(TemplateKey templateKey) throws EarthException {
        try {
            return (long) executeTransaction(() -> {
                long numOfRecordDeleted = templateAuthorityDao.deleteAllProfileAuthority(templateKey);
                if (templateAuthorityDao.getProfileAuthority(templateKey).size() > 0) {
                    throw new EarthException("Delete ProfileAuthority fail");
                }
                return true;
            });
        } catch (EarthException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public long deleteCtlTemplate(TemplateKey templateKey) throws EarthException {
        try {
            return (long) executeTransaction(() -> {
                long numOfRecordDeleted = templateAuthorityDao.deleteAllMixAuthority(templateKey);
                if (templateAuthorityDao.countMixAuthority(templateKey) > 0) {
                    throw new EarthException("Delete MixTemplateAuthority fail");
                }
                return numOfRecordDeleted;
            });
        } catch (EarthException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public long insertMgrUserProfile(MgrUserProfile mgrUserProfile) throws EarthException {
        return (long) executeTransaction(() -> {
            return userProfileDao.add(Constant.EARTH_WORKSPACE_ID, mgrUserProfile);
        });
    }

    public List<MgrUserProfile> getListByProfileIds(List<String> profileIds) throws EarthException {
        return ConversionUtil.castList(executeTransaction(Constant.EARTH_WORKSPACE_ID, () -> {
            return userProfileDao.getListByProfileIds(profileIds);
        }), MgrUserProfile.class);
    }

    public List<UserAccessRight> getMixAuthorityTemplate(TemplateKey templateKey) throws EarthException {
        return ConversionUtil.castList(executeTransaction(Constant.EARTH_WORKSPACE_ID, () -> {
            return testTemplateAuthorityDao.getMixAuthorityTemplate(templateKey);
        }), UserAccessRight.class);
    }

    public boolean deleteMenuList(List<String> functionIds) throws EarthException {
        QMgrMenu qMgrMenu = QMgrMenu.newInstance();
        List<Map<Path<?>, Object>> conditions = new ArrayList<>();
        for (String functionId : functionIds) {
            Map<Path<?>, Object> condition = new HashMap<>();
            condition.put(qMgrMenu.functionId, functionId);
            conditions.add(condition);
        }
        return (boolean) executeTransaction(Constant.EARTH_WORKSPACE_ID, () -> {
            return menuDao.deleteList(Constant.EARTH_WORKSPACE_ID, conditions) > 0;
        });
    }

    // Minhtv test Menu
    public boolean insertMenu(MgrMenu mgrMenu) throws EarthException {
        return (boolean) executeTransaction(Constant.EARTH_WORKSPACE_ID, () -> {
            return menuDao.add(Constant.EARTH_WORKSPACE_ID, mgrMenu) > 0L;
        });
    }

    public long insertMenuU(MgrMenuU mgrMenuU)
            throws EarthException {
        return (long) executeTransaction(() -> {
            return menuAuthorityDao.insertMenuU(mgrMenuU);
        });
    }

    public long deleteUserAccessRight(List<String> functionIds) throws EarthException {
        return (long) executeTransaction(() -> {
            long del = 0L;
            for (String functionId : functionIds) {
                del += menuAuthorityDao.deleteAllUserAuthority(functionId);
            }
            return del;
        });
    }

    public long insertMenuP(MgrMenuP mgrMenuP) throws EarthException {
        return (long) executeTransaction(() -> {
            return menuAuthorityDao.insertMenuP(mgrMenuP);
        });
    }

    public long deleteProfileAccessRight(List<String> functionIds) throws EarthException {
        return (long) executeTransaction(() -> {
            long del = 0L;
            for (String functionId : functionIds) {
                del += menuAuthorityDao.deleteAllProfileAuthority(functionId);
            }
            return del;
        });
    }

    public List<CtlMenu> getMixMenuAuthority(String functionId) throws EarthException {
        return (List<CtlMenu>) ConversionUtil.castList(executeTransaction(() -> {
            QCtlMenu qCtlMenu = QCtlMenu.newInstance();
            BooleanBuilder condition = new BooleanBuilder();
            Predicate pre1 = qCtlMenu.functionId.eq(functionId);
            condition.and(pre1);
            return menuAuthorityDao.search(Constant.EARTH_WORKSPACE_ID, condition);
        }), CtlMenu.class);
    }

    public long deleteMixMenuAuthority(List<String> functionIds) throws EarthException {
        return (long) executeTransaction(() -> {
            return menuAuthorityDao.deleteAllMixAuthority(functionIds);
        });
    }
}