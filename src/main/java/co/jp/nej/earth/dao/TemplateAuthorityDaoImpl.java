package co.jp.nej.earth.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import com.querydsl.sql.dml.SQLInsertClause;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.manager.connection.ConnectionManager;
import co.jp.nej.earth.manager.connection.EarthQueryFactory;
import co.jp.nej.earth.model.ProfileAccessRight;
import co.jp.nej.earth.model.TemplateKey;
import co.jp.nej.earth.model.UserAccessRight;
import co.jp.nej.earth.model.constant.Constant;
import co.jp.nej.earth.model.entity.CtlTemplate;
import co.jp.nej.earth.model.entity.MgrTemplateU;
import co.jp.nej.earth.model.enums.AccessRight;
import co.jp.nej.earth.model.enums.ColumnNames;
import co.jp.nej.earth.model.sql.QCtlTemplate;
import co.jp.nej.earth.model.sql.QMgrTemplateP;
import co.jp.nej.earth.model.sql.QMgrTemplateU;
import co.jp.nej.earth.util.DateUtil;

/**
 * @author p-tvo-thuynd
 *         <p>
 *         This class implements TemplateAuthorityDao, to do some
 *         action related to authority of user/profile to template
 */
@Repository
public class TemplateAuthorityDaoImpl extends BaseDaoImpl<CtlTemplate> implements TemplateAuthorityDao {

    public TemplateAuthorityDaoImpl() throws Exception {
        super();
    }

    /**
     * get map of AccessRight object from DB.
     *
     * @param userId      id of current user.
     * @param workspaceId id of working workspace.
     * @return map of AccessRight object with key is TemplateKey object.
     */
    @Override
    public Map<TemplateKey, AccessRight> getMixAuthority(String userId, String workspaceId)
        throws EarthException {
        Map<TemplateKey, AccessRight> templateAccessRightMap = new HashMap<TemplateKey, AccessRight>();
        try {
            EarthQueryFactory factory = ConnectionManager.getEarthQueryFactory(workspaceId);
            QCtlTemplate qCtlTemplate = QCtlTemplate.newInstance();
            ResultSet resultSet = factory.select(qCtlTemplate.templateId, qCtlTemplate.accessAuthority)
                                         .from(qCtlTemplate).where(qCtlTemplate.userId.eq(userId)).getResults();
            while (resultSet.next()) {
                TemplateKey templateKey = new TemplateKey();
                templateKey.setWorkspaceId(workspaceId);
                templateKey.setTemplateId(resultSet.getString(ColumnNames.TEMPLATE_ID.toString()));
                templateAccessRightMap.put(templateKey,
                                       AccessRight.values()[resultSet.getInt(ColumnNames.ACCESS_AUTHORITY.toString())]);
            }
        } catch (Exception e) {
            throw new EarthException(e);
        }
        return templateAccessRightMap;
    }

    @Override
    public long deleteListByProfileIds(String workspaceId, List<String> profileIds) throws EarthException {
        try {
            QMgrTemplateP qMgrTemplateP = QMgrTemplateP.newInstance();
            EarthQueryFactory earthQueryFactory = ConnectionManager.getEarthQueryFactory(workspaceId);
            return earthQueryFactory.delete(qMgrTemplateP).where(qMgrTemplateP.profileId.in(profileIds)).execute();
        } catch (Exception ex) {
            throw new EarthException(ex);
        }
    }

    @Override
    public long deleteListByUserIds(String workspaceId, List<String> userIds) throws EarthException {
        try {
            QMgrTemplateU qMgrTemplateU = QMgrTemplateU.newInstance();
            QCtlTemplate qCtlTemplate = QCtlTemplate.newInstance();
            EarthQueryFactory earthQueryFactory = ConnectionManager.getEarthQueryFactory(workspaceId);
            earthQueryFactory.delete(qCtlTemplate).where(qCtlTemplate.userId.in(userIds)).execute();
            return earthQueryFactory.delete(qMgrTemplateU).where(qMgrTemplateU.userId.in(userIds)).execute();
        } catch (Exception ex) {
            throw new EarthException(ex);
        }
    }

    @Override
    public long insertMixAuthority(TemplateKey templateKey, List<UserAccessRight> userAccessRights)
        throws EarthException {
        try {
            if(userAccessRights.size()>0){
                EarthQueryFactory earthQueryFactory =
                    ConnectionManager.getEarthQueryFactory(templateKey.getWorkspaceId());
                QCtlTemplate qCtlTemplate = QCtlTemplate.newInstance();
                SQLInsertClause insert = earthQueryFactory.insert(qCtlTemplate);
                for (UserAccessRight userAccessRight : userAccessRights) {
                    insert.set(qCtlTemplate.templateId, templateKey.getTemplateId())
                        .set(qCtlTemplate.userId, userAccessRight.getUserId())
                        .set(qCtlTemplate.accessAuthority, String.valueOf(userAccessRight.getAccessRightValue()))
                        .set(qCtlTemplate.lastUpdateTime,
                            DateUtil.getCurrentDate(Constant.DatePattern.DATE_FORMAT_YYYYMMDDHHMMSSSSS))
                        .addBatch();
                }

                return insert.execute();
            }
            return 0;
        } catch (Exception e) {
            throw new EarthException(e);
        }
    }

    @Override
    public long searchAllMixAuthority(TemplateKey templateKey) throws EarthException {
        BooleanBuilder condition = new BooleanBuilder();
        QCtlTemplate qCtlTemplate = QCtlTemplate.newInstance();
        Predicate pre1 = qCtlTemplate.templateId.eq(templateKey.getTemplateId());
        condition.and(pre1);
        return search(templateKey.getWorkspaceId(), condition).size();
    }

    @Override
    public long deleteAllMixAuthority(TemplateKey templateKey) throws EarthException {
        QCtlTemplate qCtlTemplate = QCtlTemplate.newInstance();
        Map<Path<?>, Object> condition = new HashMap<>();
        condition.put(qCtlTemplate.templateId, templateKey.getTemplateId());
        return delete(templateKey.getWorkspaceId(), condition);
    }

    @Override
    public List<TemplateKey> getTemplateKeysByProfile(String workspaceId, String profileId) throws EarthException {
        try {
            QMgrTemplateP qMgrTemplateP = QMgrTemplateP.newInstance();
            List<TemplateKey> templateKeys = new ArrayList<TemplateKey>();
            EarthQueryFactory earthQueryFactory = ConnectionManager.getEarthQueryFactory(workspaceId);
            List<String> templateIds = (List<String>) earthQueryFactory.select(qMgrTemplateP.templateId)
                .from(qMgrTemplateP).where(qMgrTemplateP.profileId.eq(profileId)).fetch();
            for (String templateId : templateIds) {
                TemplateKey templateKey = new TemplateKey();
                templateKey.setTemplateId(templateId);
                templateKey.setWorkspaceId(workspaceId);
                templateKeys.add(templateKey);
            }
            return templateKeys;
        } catch (Exception ex) {
            throw new EarthException(ex);

        }
    }

    @Override
    public List<UserAccessRight> getUserAuthority(TemplateKey templateKey) throws EarthException {
        List<UserAccessRight> userAccessRights = new ArrayList<UserAccessRight>();
        try {
            QMgrTemplateU qMgrTemplateU = QMgrTemplateU.newInstance();
            QBean<MgrTemplateU> selectList = Projections.bean(MgrTemplateU.class, qMgrTemplateU.all());
            EarthQueryFactory earthQueryFactory = ConnectionManager.getEarthQueryFactory(templateKey.getWorkspaceId());
            List<MgrTemplateU> mgrTemplateUs = earthQueryFactory.select(selectList).from(qMgrTemplateU)
                .where(qMgrTemplateU.templateId.eq(templateKey.getTemplateId())).orderBy(qMgrTemplateU.userId.asc())
                .fetch();
            for (MgrTemplateU mgrTemplateU : mgrTemplateUs) {
                UserAccessRight userAccessRight = new UserAccessRight();
                userAccessRight.setUserId(mgrTemplateU.getUserId());
                userAccessRight.setAccessRight(AccessRight.values()[mgrTemplateU.getAccessAuthority()]);
                userAccessRight.setAccessRightValue(mgrTemplateU.getAccessAuthority());
                userAccessRights.add(userAccessRight);
            }
            return userAccessRights;
        } catch (Exception e) {
            throw new EarthException(e);
        }
    }

    @Override
    public List<ProfileAccessRight> getProfileAuthority(TemplateKey templateKey) throws EarthException {
        List<ProfileAccessRight> profileAccessRights = new ArrayList<ProfileAccessRight>();
        try {
            QMgrTemplateP qMgrTemplateP = QMgrTemplateP.newInstance();
            ResultSet resultSet = ConnectionManager.getEarthQueryFactory(templateKey.getWorkspaceId())
                .select(qMgrTemplateP.profileId, qMgrTemplateP.accessAuthority).from(qMgrTemplateP)
                .where(qMgrTemplateP.templateId.eq(templateKey.getTemplateId()))
                .orderBy(qMgrTemplateP.profileId.asc())
                .getResults();
            while (resultSet.next()) {
                ProfileAccessRight profileAccessRight = new ProfileAccessRight();
                profileAccessRight.setProfileId(resultSet.getString(ColumnNames.PROFILE_ID.toString()));
                profileAccessRight.setAccessRight(
                    AccessRight.values()[resultSet.getInt(ColumnNames.ACCESS_AUTHORITY.toString())]);
                profileAccessRight.setAccessRightValue(resultSet.getInt(ColumnNames.ACCESS_AUTHORITY.toString()));
                profileAccessRights.add(profileAccessRight);
            }
        } catch (Exception e) {
            throw new EarthException(e);
        }
        return profileAccessRights;
    }

    @Override
    public List<String> getProfiles(TemplateKey templateKey) throws EarthException {
        List<String> profileIds = new ArrayList<>();
        try {
            QMgrTemplateP qMgrTemplateP = QMgrTemplateP.newInstance();
            profileIds = ConnectionManager.getEarthQueryFactory(templateKey.getWorkspaceId())
                .select(qMgrTemplateP.profileId).from(qMgrTemplateP)
                .where(qMgrTemplateP.templateId.eq(templateKey.getTemplateId())).fetch();
        } catch (Exception e) {
            throw new EarthException(e);
        }
        return profileIds;
    }

    @Override
    public long deleteAllUserAuthority(TemplateKey templateKey) throws EarthException {
        try {
            QMgrTemplateU qMgrTemplateU = QMgrTemplateU.newInstance();
            EarthQueryFactory earthQueryFactory = ConnectionManager.getEarthQueryFactory(templateKey.getWorkspaceId());
            long deletedRecordNumber = earthQueryFactory.delete(qMgrTemplateU)
                .where(qMgrTemplateU.templateId.eq(templateKey.getTemplateId())).execute();
            return deletedRecordNumber;
        } catch (Exception e) {
            throw new EarthException(e);
        }
    }

    @Override
    public long insertUserAuthority(TemplateKey templateKey, List<UserAccessRight> userAccessRights)
        throws EarthException {
        try {
            QMgrTemplateU qMgrTemplateU = QMgrTemplateU.newInstance();
            EarthQueryFactory earthQueryFactory = ConnectionManager.getEarthQueryFactory(templateKey.getWorkspaceId());
            SQLInsertClause insert = earthQueryFactory.insert(qMgrTemplateU);
            for (UserAccessRight userAccessRight : userAccessRights) {
                insert.set(qMgrTemplateU.templateId, templateKey.getTemplateId())
                    .set(qMgrTemplateU.userId, userAccessRight.getUserId())
                    .set(qMgrTemplateU.accessAuthority, userAccessRight.getAccessRightValue())
                    .set(qMgrTemplateU.lastUpdateTime,
                        DateUtil.getCurrentDate(Constant.DatePattern.DATE_FORMAT_YYYYMMDDHHMMSSSSS))
                    .addBatch();
            }
            return insert.execute();
        } catch (Exception e) {
            throw new EarthException(e);
        }
    }

    @Override
    public long deleteAllProfileAuthority(TemplateKey templateKey) throws EarthException {
        try {
            QMgrTemplateP qMgrTemplateP = QMgrTemplateP.newInstance();
            EarthQueryFactory earthQueryFactory = ConnectionManager.getEarthQueryFactory(templateKey.getWorkspaceId());
            long deletedRecordNumber = earthQueryFactory.delete(qMgrTemplateP)
                .where(qMgrTemplateP.templateId.eq(templateKey.getTemplateId())).execute();
            return deletedRecordNumber;
        } catch (Exception e) {
            throw new EarthException(e);
        }
    }

    @Override
    public long insertProfileAuthority(TemplateKey templateKey, List<ProfileAccessRight> profileAccessRights)
        throws EarthException {
        try {
            QMgrTemplateP qMgrTemplateP = QMgrTemplateP.newInstance();
            EarthQueryFactory earthQueryFactory = ConnectionManager.getEarthQueryFactory(templateKey.getWorkspaceId());
            SQLInsertClause insert = earthQueryFactory.insert(qMgrTemplateP);
            for (ProfileAccessRight profileAccessRight : profileAccessRights) {
                insert.set(qMgrTemplateP.templateId, templateKey.getTemplateId())
                    .set(qMgrTemplateP.profileId, profileAccessRight.getProfileId())
                    .set(qMgrTemplateP.accessAuthority, profileAccessRight.getAccessRightValue())
                    .set(qMgrTemplateP.lastUpdateTime,
                        DateUtil.getCurrentDate(Constant.DatePattern.DATE_FORMAT_YYYYMMDDHHMMSSSSS))
                    .addBatch();
            }
            return insert.execute();
        } catch (Exception e) {
            throw new EarthException(e);
        }
    }

    @Override
    public long countMixAuthority(TemplateKey templateKey) throws EarthException {
        try {
            QCtlTemplate qCtlTemplate = QCtlTemplate.newInstance();
            QBean<CtlTemplate> selectList = Projections.bean(CtlTemplate.class, qCtlTemplate.all());
            EarthQueryFactory earthQueryFactory = ConnectionManager.getEarthQueryFactory(templateKey.getWorkspaceId());
            return earthQueryFactory.select(selectList).from(qCtlTemplate)
                .where(qCtlTemplate.templateId.eq(templateKey.getTemplateId())).fetch().size();
        } catch (Exception e) {
            throw new EarthException(e);
        }
    }

    @Override
    public long deleteMixAuthority(String workspaceId, List<String> templateIds) throws EarthException {
        try {
            QCtlTemplate qCtlTemplate = QCtlTemplate.newInstance();
            EarthQueryFactory earthQueryFactory = ConnectionManager.getEarthQueryFactory(workspaceId);
            return earthQueryFactory.delete(qCtlTemplate).where(qCtlTemplate.templateId.in(templateIds)).execute();
        } catch (Exception e) {
            throw new EarthException(e);
        }
    }

    @Override
    public long deleteUAuthority(String workspaceId, List<String> templateIds) throws EarthException {
        try {
            QMgrTemplateU qMgrTemplateU = QMgrTemplateU.newInstance();
            EarthQueryFactory earthQueryFactory = ConnectionManager.getEarthQueryFactory(workspaceId);
            long deletedRecordNumber = earthQueryFactory.delete(qMgrTemplateU)
                .where(qMgrTemplateU.templateId.in(templateIds)).execute();
            return deletedRecordNumber;
        } catch (Exception e) {
            throw new EarthException(e);
        }
    }

    @Override
    public long deletePAuthority(String workspaceId, List<String> templateIds) throws EarthException {
        try {
            QMgrTemplateP qMgrTemplateP = QMgrTemplateP.newInstance();
            EarthQueryFactory earthQueryFactory = ConnectionManager.getEarthQueryFactory(workspaceId);
            long deletedRecordNumber = earthQueryFactory.delete(qMgrTemplateP)
                .where(qMgrTemplateP.templateId.in(templateIds)).execute();
            return deletedRecordNumber;
        } catch (Exception e) {
            throw new EarthException(e);
        }
    }
}
