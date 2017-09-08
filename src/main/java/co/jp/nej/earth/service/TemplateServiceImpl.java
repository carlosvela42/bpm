package co.jp.nej.earth.service;

import co.jp.nej.earth.dao.MstCodeDao;
import co.jp.nej.earth.dao.ProfileDao;
import co.jp.nej.earth.dao.TemplateAuthorityDao;
import co.jp.nej.earth.dao.TemplateDao;
import co.jp.nej.earth.dao.UserDao;
import co.jp.nej.earth.dao.UserProfileDao;
import co.jp.nej.earth.dao.WorkspaceDao;
import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.model.Field;
import co.jp.nej.earth.model.Message;
import co.jp.nej.earth.model.MgrWorkspace;
import co.jp.nej.earth.model.ProfileAccessRight;
import co.jp.nej.earth.model.TemplateKey;
import co.jp.nej.earth.model.UserAccessRight;
import co.jp.nej.earth.model.constant.Constant;
import co.jp.nej.earth.model.constant.Constant.ErrorCode;
import co.jp.nej.earth.model.entity.MgrProfile;
import co.jp.nej.earth.model.entity.MgrTemplate;
import co.jp.nej.earth.model.entity.MgrUser;
import co.jp.nej.earth.model.entity.MgrUserProfile;
import co.jp.nej.earth.model.enums.AccessRight;
import co.jp.nej.earth.model.enums.DatabaseType;
import co.jp.nej.earth.model.enums.TemplateType;
import co.jp.nej.earth.model.sql.QMgrTemplate;
import co.jp.nej.earth.util.ConversionUtil;
import co.jp.nej.earth.util.EMessageResource;
import co.jp.nej.earth.util.UserAccessRightUtil;
import co.jp.nej.earth.web.form.SearchForm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TemplateServiceImpl extends BaseService implements TemplateService {

    @Autowired
    private TemplateDao templateDao;

    @Autowired
    private WorkspaceDao workspaceDao;

    @Autowired
    private TemplateAuthorityDao templateAuthorityDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserProfileDao userProfileDao;

    @Autowired
    private ProfileDao profileDao;

    @Autowired
    private EMessageResource messageSource;

    @Autowired
    private DatabaseType databaseType;

    @Autowired
    private MstCodeDao mstCodeDao;

    public List<MgrWorkspace> getAllWorkspace() throws EarthException {
        return ConversionUtil.castList(executeTransaction(Constant.EARTH_WORKSPACE_ID, () -> {
            return workspaceDao.getAll();
        }), MgrWorkspace.class);

    }

    public List<MgrTemplate> getTemplateListInfo(String workspaceId) throws EarthException {
        return ConversionUtil.castList(executeTransaction(workspaceId, () -> {
            return templateDao.getAllByWorkspace(workspaceId);
        }), MgrTemplate.class);
    }

    public List<UserAccessRight> getUserAuthority(TemplateKey templateKey) throws EarthException {
        return ConversionUtil.castList(executeTransaction(Constant.EARTH_WORKSPACE_ID, () -> {
            return templateAuthorityDao.getUserAuthority(templateKey);
        }), UserAccessRight.class);
    }

    @Override
    public List<ProfileAccessRight> getProfileAuthority(TemplateKey templateKey) throws EarthException {
        return ConversionUtil.castList(executeTransaction(Constant.EARTH_WORKSPACE_ID, () -> {
            return templateAuthorityDao.getProfileAuthority(templateKey);
        }), ProfileAccessRight.class);
    }

    @Override
    public MgrTemplate getById(TemplateKey templateKey) throws EarthException {
        return ConversionUtil.castObject(executeTransaction(templateKey.getWorkspaceId(), () -> {
            return templateDao.getById(templateKey);
        }), MgrTemplate.class);
    }

    @Override
    public List<MgrUser> getAllUser() throws EarthException {
        return ConversionUtil.castList(executeTransaction(Constant.EARTH_WORKSPACE_ID, () -> {
            return userDao.getAll();
        }), MgrUser.class);
    }

    @Override
    public List<MgrProfile> getAllProfile() throws EarthException {
        return ConversionUtil.castList(executeTransaction(Constant.EARTH_WORKSPACE_ID, () -> {
            return profileDao.getAll();
        }), MgrProfile.class);
    }

    @Override
    public List<MgrTemplate> getAllByWorkspace(String workspaceId, String userId) throws EarthException {
        return ConversionUtil.castList(executeTransaction(workspaceId, () -> {
            return templateDao.getAllByWorkspace(workspaceId, userId);
        }), MgrTemplate.class);
    }

    @Override
    public List<MgrTemplate> getAllByWorkspace(String workspaceId, String templateType, String userId)
        throws EarthException {
//        QMgrTemplate qMgrTemplate = QMgrTemplate.newInstance();
//        BooleanBuilder condition = new BooleanBuilder();
//        Predicate pre1 = qMgrTemplate.templateType.eq(templateType);
//        condition.and(pre1);
//        List<OrderSpecifier<?>> orderBys = new ArrayList<>();
//        orderBys.add(qMgrTemplate.templateTableName.asc());
        return ConversionUtil.castList(executeTransaction(workspaceId, () -> {
            return templateDao.getTemplateByType(workspaceId, templateType, userId);
        }), MgrTemplate.class);
    }


    /**
     * @param templateKey determine which template are being executed
     * @param tUsers      list of UserAccessRight object to be inserted into table
     * @param tProfiles   list of ProfileAccessRight object to be inserted into table
     * @return number of updated templates authority.
     * @author p-tvo-thuynd.
     * <p>
     * This method is to save authority of user/profile to template to equivalent table
     */
    @Override
    public long saveAuthority(TemplateKey templateKey, List<UserAccessRight> tUsers, List<ProfileAccessRight> tProfiles)
        throws EarthException {

        return (long) executeTransaction(templateKey.getWorkspaceId(), () -> {
            templateAuthorityDao.deleteAllUserAuthority(templateKey);

            if (tUsers != null && tUsers.size() > 0) {
                templateAuthorityDao.insertUserAuthority(templateKey, tUsers);
            }

            templateAuthorityDao.deleteAllProfileAuthority(templateKey);

            if (tProfiles != null && tProfiles.size() > 0) {
                templateAuthorityDao.insertProfileAuthority(templateKey, tProfiles);
            }

            templateAuthorityDao.deleteAllMixAuthority(templateKey);

            List<String> profileIds = new ArrayList<String>();
            for (ProfileAccessRight profileAccessRight : tProfiles) {
                profileIds.add(profileAccessRight.getProfileId());
            }
            List<MgrUserProfile> mgrUserProfiles = userProfileDao.getListByProfileIds(profileIds);

            Map<String, AccessRight> accessRightPMap = new HashMap<String, AccessRight>();
            Map<String, Integer> accessRightValuePMap = new HashMap<String, Integer>();
            for (ProfileAccessRight profileAccessRight : tProfiles) {
                accessRightValuePMap.put(profileAccessRight.getProfileId(), profileAccessRight.getAccessRightValue());
            }

            List<UserAccessRight> userAccessRights = UserAccessRightUtil.getUserAccessRightProfiles(mgrUserProfiles,
                accessRightValuePMap);
            List<UserAccessRight> templateAccessRights = UserAccessRightUtil.mixAuthority(tUsers, userAccessRights);

            return templateAuthorityDao.insertMixAuthority(templateKey, templateAccessRights);
        });
    }

    @Override
    public List<MgrTemplate> getTemplateByType(String workspaceId, String templateType) throws EarthException {
        return ConversionUtil.castList(executeTransaction(workspaceId, () -> {
            return templateDao.getTemplateByType(workspaceId, templateType);
        }), MgrTemplate.class);
    }

    @Override
    public List<MgrTemplate> getTemplateByType(String workspaceId, String templateType, String userId) throws
        EarthException {
        return ConversionUtil.castList(executeTransaction(workspaceId, () -> {
            return templateDao.getTemplateByType(workspaceId, templateType, userId);
        }), MgrTemplate.class);
    }

    @Override
    public boolean deleteTemplates(List<String> templateIds, String workspaceId) throws EarthException {
        return (boolean) (executeTransaction(workspaceId, () -> {
            // Delete templates in mgr template.
            templateDao.deleteTemplates(templateIds, workspaceId);
            templateDao.resetNodeItemUsingTemplateId(workspaceId, templateIds);
            deleteTemplateRightRecord(workspaceId, templateIds);
            return true;
        }));
    }

    @Override
    public boolean insertOne(String workspaceId, MgrTemplate mgrTemplate) throws EarthException {
        return (boolean) (executeTransaction(workspaceId, () -> {
            try {
                List<Field> fields = mgrTemplate.getTemplateFields();
                String json = new ObjectMapper().writeValueAsString(fields);
                mgrTemplate.setTemplateField(json);

                templateDao.insertOne(workspaceId, mgrTemplate);
                templateDao.createTemplateData(workspaceId, mgrTemplate);
                return true;
            } catch (JsonProcessingException e) {
                throw new EarthException(e);
            }
        }));
    }

    @Override
    public boolean updateOne(String workspaceId, MgrTemplate mgrTemplate) throws EarthException {
        return (boolean) (executeTransaction(workspaceId, () -> {
            try {
                String json = new ObjectMapper().writeValueAsString(mgrTemplate.getTemplateFields());
                mgrTemplate.setTemplateField(json);
                long updated = templateDao.updateOne(workspaceId, mgrTemplate);
                if (updated <= 0) {
                    return false;
                }
                return true;
            } catch (JsonProcessingException e) {
                throw new EarthException(e.getMessage());
            }
        }));
    }

    @Override
    public List<Message> checkExistsTemplate(String workspaceId, MgrTemplate mgrTemplate, String dbUser)
        throws EarthException {
        return ConversionUtil.castList(executeTransaction(workspaceId, () -> {
            List<Message> messages = new ArrayList<Message>();
            if (!StringUtils.isEmpty(workspaceId) && !StringUtils.isEmpty(mgrTemplate.getTemplateName())
                && !StringUtils.isEmpty(mgrTemplate.getTemplateTableName())
                && !StringUtils.isEmpty(mgrTemplate.getTemplateFields())) {

                if (isExistTemplate(workspaceId, mgrTemplate.getTemplateId())) {
                    Message message = new Message(ErrorCode.E0003,
                        messageSource.get(ErrorCode.E0003, new String[]{"template.name"}));
                    messages.add(message);
                }

                if (isExistTemplateName(workspaceId,
                    mgrTemplate.getTemplateId(), mgrTemplate.getTemplateName())) {
                    Message message = new Message(ErrorCode.E0003,
                        messageSource.get(ErrorCode.E0003, new String[]{"template.name"}));
                    messages.add(message);
                }
                String userDb = dbUser;
                if (databaseType.isOracle()) {
                    userDb = Constant.WorkSpace.CHARACTER_COMMON + dbUser;
                }
                if (isExistTable(workspaceId, mgrTemplate.getTemplateTableName(),
                    (userDb).toUpperCase())) {
                    Message message = new Message(ErrorCode.E0003,
                        messageSource.get(ErrorCode.E0003, new String[]{"template.tableName"}));
                    messages.add(message);
                }
            }
            return messages;
        }), Message.class);
    }

    private boolean isExistTemplateName(String workspaceId,
                                        String templateId, String templateName) throws EarthException {

        BooleanBuilder condition = new BooleanBuilder();
        QMgrTemplate qMgrTemplate = QMgrTemplate.newInstance();

        Predicate predicateNameId = qMgrTemplate.templateId.ne(templateId);
        condition.and(predicateNameId);
        Predicate predicateName = qMgrTemplate.templateName.eq(templateName);
        condition.and(predicateName);

        return templateDao.search(workspaceId, condition).size() > 0;
    }

    private boolean isExistTemplate(String workspaceId, String templateId) throws EarthException {
        TemplateKey templateKey = new TemplateKey();
        templateKey.setTemplateId(templateId);
        templateKey.setWorkspaceId(workspaceId);
        return templateDao.getById(templateKey) != null;
    }

    private boolean isExistTable(String workspaceId, String templateTableName, String dbUser) throws EarthException {
        long isExit = templateDao.isExistsTableData(workspaceId, templateTableName, dbUser);
        return isExit > 0L;
    }

    @Override
    public String getFieldJson(String workspaceId, SearchForm searchForm) throws EarthException {
        return (String) executeTransaction(workspaceId, () -> {
            return templateDao.getFieldJson(workspaceId, searchForm);
        });
    }

    /**
     * Get List MgrTemplate by Ids
     *
     * @param ids         List template id
     * @param workspaceId Workspace ID
     * @param type        Template type
     * @return List MgrTemplate if OK, otherwise throw EarthException
     */
    @Override
    public List<MgrTemplate> getByIdsAndType(String workspaceId, List<String> ids, TemplateType type) throws
        EarthException {
        return ConversionUtil.castList(executeTransaction(workspaceId, () -> {
            return templateDao.getByIdsAndType(workspaceId, ids, type);
        }), MgrTemplate.class);
    }

    private void deleteTemplateRightRecord(String workspaceId, List<String> templateIds) throws EarthException {
        // Delete mix template authority.
        templateAuthorityDao.deleteMixAuthority(workspaceId, templateIds);

        // Delete profiles template authority.
        templateAuthorityDao.deletePAuthority(workspaceId, templateIds);

        // Delete users template authority.
        templateAuthorityDao.deleteUAuthority(workspaceId, templateIds);
    }
}