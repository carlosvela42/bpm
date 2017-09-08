package co.jp.nej.earth.dao;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.manager.connection.ConnectionManager;
import co.jp.nej.earth.model.UsersProfile;
import co.jp.nej.earth.model.constant.Constant;
import co.jp.nej.earth.model.entity.MgrUser;
import co.jp.nej.earth.model.enums.ColumnNames;
import co.jp.nej.earth.model.sql.QMgrUser;
import co.jp.nej.earth.model.sql.QMgrUserProfile;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserDaoImpl extends BaseDaoImpl<MgrUser> implements UserDao {

    private static final QMgrUser qMgrUser = QMgrUser.newInstance();

    private static final Logger LOG = LoggerFactory.getLogger(UserDaoImpl.class);
    public static final String USERIDCHOOSE = "USERIDCHOOSE";

    public UserDaoImpl() throws Exception {
        super();
    }

    public MgrUser getById(String userId) throws EarthException {
        try {
            Map<Path<?>, Object> condition = new HashMap<>();
            condition.put(qMgrUser.userId, userId);
            return this.findOne(Constant.EARTH_WORKSPACE_ID, condition);
        } catch (Exception ex) {
            throw new EarthException(ex);
        }
    }

    public MgrUser getUserByIdAndPassword(String userId, String password) throws EarthException {
        try {
            Map<Path<?>, Object> condition = new HashMap<>();
            condition.put(qMgrUser.userId, userId);
            condition.put(qMgrUser.password, password);
            return this.findOne(Constant.EARTH_WORKSPACE_ID, condition);
        } catch (Exception ex) {
            throw new EarthException(ex);
        }
    }

    public List<MgrUser> getAll() throws EarthException {
        return this.findAll(Constant.EARTH_WORKSPACE_ID, null, null, null, null);
    }

    public long updateOne(MgrUser mgrUser) throws EarthException {
        Map<Path<?>, Object> condition = new HashMap<>();
        condition.put(qMgrUser.userId, mgrUser.getUserId());
        Map<Path<?>, Object> valueMap = new HashMap<>();
        if (mgrUser.isChangePassword()) {
            valueMap.put(qMgrUser.name, mgrUser.getName());
            valueMap.put(qMgrUser.password, mgrUser.getPassword());

        } else {
            valueMap.put(qMgrUser.name, mgrUser.getName());
        }
        return this.update(Constant.EARTH_WORKSPACE_ID, condition, valueMap);
    }

    public long deleteList(List<String> userIds) throws EarthException {
        List<Map<Path<?>, Object>> conditions = new ArrayList<>();
        for (String userId : userIds) {
            Map<Path<?>, Object> condition = new HashMap<>();
            condition.put(qMgrUser.userId, userId);
            conditions.add(condition);
        }
        return this.deleteList(Constant.EARTH_WORKSPACE_ID, conditions);
    }

    public List<UsersProfile> getUsersByProfileId(String profileId) throws EarthException {
        try {
            List<UsersProfile> usersProfiles=new ArrayList<UsersProfile>();
            QBean<MgrUser> selectList = Projections.bean(MgrUser.class, qMgrUser.all());
            QMgrUserProfile qMgrUserProfile = QMgrUserProfile.newInstance();
            ResultSet resultSet= ConnectionManager.getEarthQueryFactory(Constant.EARTH_WORKSPACE_ID)
                .select(selectList,qMgrUserProfile.userId.as(USERIDCHOOSE))
                .from(qMgrUser)
                .leftJoin(qMgrUserProfile)
                .on(qMgrUser.userId.eq(qMgrUserProfile.userId).and(qMgrUserProfile.profileId.eq(profileId)))
                .orderBy(qMgrUser.userId.toUpperCase().asc())
                .getResults();
            while (resultSet.next()){
                UsersProfile usersProfile = new UsersProfile();
                usersProfile.setUserId(resultSet.getString(ColumnNames.USER_ID.toString()));
                usersProfile.setName(resultSet.getString(ColumnNames.NAME.toString()));
                usersProfile.setUserIdChoose(resultSet.getString(USERIDCHOOSE));
                usersProfiles.add(usersProfile);
            }
            return usersProfiles;
        } catch (Exception ex) {
            LOG.error(ex.getMessage());
            throw new EarthException(ex);
        }
    }

    public List<String> getUserIdsByProfileId(String profileId) throws EarthException {
        try {
            QMgrUserProfile qMgrUserProfile = QMgrUserProfile.newInstance();
            List<String> userIds = (List<String>) ConnectionManager.getEarthQueryFactory(Constant.EARTH_WORKSPACE_ID)
                    .select(qMgrUser.userId).from(qMgrUser).innerJoin(qMgrUserProfile).on(qMgrUser.userId.eq(
                            qMgrUserProfile.userId)).where(qMgrUserProfile.profileId.eq(profileId)).fetch();
            return userIds;
        } catch (Exception ex) {
            LOG.error(ex.getMessage());
            throw new EarthException(ex);
        }
    }
}
