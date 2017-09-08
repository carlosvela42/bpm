package co.jp.nej.earth.dao;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.model.constant.Constant;
import co.jp.nej.earth.model.entity.MgrUserProfile;
import co.jp.nej.earth.model.sql.QMgrUserProfile;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.Predicate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository
public class UserProfileDaoImpl extends BaseDaoImpl<MgrUserProfile> implements UserProfileDao {

    private static final QMgrUserProfile qMgrUserProfile = QMgrUserProfile.newInstance();

    public UserProfileDaoImpl() throws Exception {
        super();
    }

    public long deleteListByUserIds(List<String> userIds) throws EarthException {
        try {
            List<Map<Path<?>, Object>> conditions = new ArrayList<>();
            for (String userId : userIds) {
                Map<Path<?>, Object> condition = new HashMap<>();
                condition.put(qMgrUserProfile.userId, userId);
                conditions.add(condition);
            }
            return this.deleteList(Constant.EARTH_WORKSPACE_ID, conditions);
        } catch (Exception ex) {
            throw new EarthException(ex);
        }
    }

    @Override
    public long deleteListByProfileIds(List<String> profileIds) throws EarthException {
        try {
            List<Map<Path<?>, Object>> conditions = new ArrayList<>();
            for (String profileId : profileIds) {
                Map<Path<?>, Object> condition = new HashMap<>();
                condition.put(qMgrUserProfile.profileId, profileId);
                conditions.add(condition);
            }
            return this.deleteList(Constant.EARTH_WORKSPACE_ID, conditions);
        } catch (Exception ex) {
            throw new EarthException(ex);
        }
    }

    @Override
    public List<MgrUserProfile> getListByProfileIds(List<String> profileIds) throws EarthException {
        try {
            BooleanBuilder condition = new BooleanBuilder();
            Predicate pre1 = qMgrUserProfile.profileId.in(profileIds);
            condition.and(pre1);
            return this.search(Constant.EARTH_WORKSPACE_ID, condition);
        } catch (Exception ex) {
            throw new EarthException(ex);
        }
    }
}
