package co.jp.nej.earth.dao;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.manager.connection.ConnectionManager;
import co.jp.nej.earth.manager.connection.EarthQueryFactory;
import co.jp.nej.earth.model.constant.Constant;
import co.jp.nej.earth.model.entity.MgrProfile;
import co.jp.nej.earth.model.sql.QMgrProfile;
import co.jp.nej.earth.model.sql.QMgrUserProfile;
import co.jp.nej.earth.util.DateUtil;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import com.querydsl.sql.dml.SQLInsertClause;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ProfileDaoImpl extends BaseDaoImpl<MgrProfile> implements ProfileDao {
    private static final QMgrProfile qMgrProfile = QMgrProfile.newInstance();
    private static final QMgrUserProfile qMgrUserProfile = QMgrUserProfile.newInstance();

    public ProfileDaoImpl() throws Exception {
        super();
    }

    public List<MgrProfile> getProfilesByUserId(String userId) throws EarthException {
        try {
            QBean<MgrProfile> selectList = Projections.bean(MgrProfile.class, qMgrProfile.all());
            List<MgrProfile> profiles = ConnectionManager.getEarthQueryFactory(Constant.EARTH_WORKSPACE_ID)
                .select(selectList)
                .from(qMgrProfile).innerJoin(qMgrUserProfile)
                .on(qMgrProfile.profileId.eq(qMgrUserProfile.profileId))
                .orderBy(qMgrProfile.profileId.asc())
                .where(qMgrUserProfile.userId.eq(userId))
                .fetch();
            return profiles;
        } catch (Exception ex) {
            throw new EarthException(ex);
        }
    }

    public MgrProfile getById(String profileId) throws EarthException {
        try {
            Map<Path<?>, Object> condition = new HashMap<>();
            condition.put(qMgrProfile.profileId, profileId);
            return this.findOne(Constant.EARTH_WORKSPACE_ID, condition);
        } catch (Exception ex) {
            throw new EarthException(ex);
        }
    }

    public List<MgrProfile> getAll() throws EarthException {
        try {
            return this.findAll(Constant.EARTH_WORKSPACE_ID, null, null, null, null);
        } catch (Exception ex) {
            throw new EarthException(ex);
        }
    }

    public long deleteList(List<String> profileIds) throws EarthException {
        long del = 0L;
        try {
            List<Map<Path<?>, Object>> conditions = new ArrayList<>();
            for (String profileId : profileIds) {
                Map<Path<?>, Object> condition = new HashMap<>();
                condition.put(qMgrProfile.profileId, profileId);
                conditions.add(condition);
            }
            del = this.deleteList(Constant.EARTH_WORKSPACE_ID, conditions);
        } catch (Exception ex) {
            throw new EarthException(ex);
        }
        return del;
    }

    @Override
    public long insertOne(MgrProfile mgrProfile) throws EarthException {
        return this.add(Constant.EARTH_WORKSPACE_ID, mgrProfile);
    }

    @Override
    public long assignUsers(String profileId, List<String> userIds) throws EarthException {
        long count = 0L;
        try {
            if(userIds.size() > 0) {
                EarthQueryFactory earthQueryFactory = ConnectionManager.
                    getEarthQueryFactory(Constant.EARTH_WORKSPACE_ID);
                SQLInsertClause insert = earthQueryFactory.insert(qMgrUserProfile);
                if (!userIds.contains("")) {
                    String lastUpdateTime = DateUtil.getCurrentDateString();
                    for (String userId : userIds) {
                        insert.set(qMgrUserProfile.profileId, profileId).set(qMgrUserProfile.userId, userId)
                            .set(qMgrUserProfile.lastUpdateTime, lastUpdateTime)
                            .addBatch();
                    }
                    count = insert.execute();
                }
            }
        } catch (Exception ex) {
            throw new EarthException(ex);
        }
        return count;
    }

    // @Override
    // public long unAssignAllUsers(String profileIds) throws EarthException {
    // long unAssign=0L;
    // try {
    //
    // unAssign=qMgrUserProfile;
    // } catch (Exception ex) {
    // throw new EarthException(ex);
    // }
    // return unAssign;
    // }

    @Override
    public long updateOne(MgrProfile mgrProfile) throws EarthException {
        Map<Path<?>, Object> condition = new HashMap<>();
        condition.put(qMgrProfile.profileId, mgrProfile.getProfileId());
        Map<Path<?>, Object> valueMap = new HashMap<>();
        valueMap.put(qMgrProfile.description, mgrProfile.getDescription());
        valueMap.put(qMgrProfile.ldapIdentifier, mgrProfile.getLdapIdentifier());
        valueMap.put(qMgrProfile.lastUpdateTime, mgrProfile.getLastUpdateTime());
        return this.update(Constant.EARTH_WORKSPACE_ID, condition, valueMap);
    }
}
