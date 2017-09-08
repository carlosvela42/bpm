package co.jp.nej.earth.dao;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.manager.connection.ConnectionManager;
import co.jp.nej.earth.manager.connection.EarthQueryFactory;
import co.jp.nej.earth.model.constant.Constant;
import static co.jp.nej.earth.model.constant.Constant.EARTH_WORKSPACE_ID;
import co.jp.nej.earth.model.entity.CtlLogin;
import co.jp.nej.earth.model.entity.StrCal;
import co.jp.nej.earth.model.enums.ColumnNames;
import co.jp.nej.earth.model.sql.QCtlLogin;
import co.jp.nej.earth.model.sql.QMgrUserProfile;
import co.jp.nej.earth.model.sql.QMstSystem;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Path;
import com.querydsl.sql.SQLQuery;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class LoginControlDaoImpl extends BaseDaoImpl<CtlLogin> implements LoginControlDao {

    private static final QCtlLogin qcCtlLogin = QCtlLogin.newInstance();

    public LoginControlDaoImpl() throws Exception {
        super();
    }

    public List<StrCal> getNumberOnlineUserByProfile(String userId) throws EarthException {
        QCtlLogin qCtlLogin = QCtlLogin.newInstance();
        QMgrUserProfile qMgrUserProfile = QMgrUserProfile.newInstance();
        QMstSystem qMstSystem = QMstSystem.newInstance();
        EarthQueryFactory earthQueryFactory = ConnectionManager.getEarthQueryFactory(EARTH_WORKSPACE_ID);
        Map<String, StrCal> strCalMap = new HashMap<>();
        try {
//            update all profile
            SQLQuery<Tuple> query2 = earthQueryFactory
                .select(qMgrUserProfile.profileId, qMstSystem.configValue.as("availableLicenceCount"))
                .from(qMgrUserProfile)
                .where(qMgrUserProfile.userId.eq(userId))
                .leftJoin(qMstSystem)
                .on(qMgrUserProfile.profileId.eq(qMstSystem.variableName)
                    .and(qMstSystem.section.eq(Constant.AVAILABLELICENCECOUNT)));

            ResultSet resultSet2 = query2.getResults();
            while (resultSet2.next()) {
                StrCal strCal = new StrCal();
                strCal.setDivision(Constant.PROFILE_DIVISION);
                strCal.setProfileId(resultSet2.getString(ColumnNames.PROFILE_ID.toString()));
                strCal.setAvailableLicenseCount(resultSet2.getInt("availableLicenceCount"));
                strCalMap.put(strCal.getProfileId(), strCal);
            }

            //            count all profile if exits
            SQLQuery<Tuple> query = earthQueryFactory
                .select(qMgrUserProfile.profileId, qCtlLogin.userId.count().as("useLicenseCount"))
                .from(qMgrUserProfile)
                .innerJoin(qCtlLogin)
                .on(qMgrUserProfile.userId.eq(qCtlLogin.userId))
                .where(qMgrUserProfile.profileId
                    .in(earthQueryFactory.select(qMgrUserProfile.profileId).from(qMgrUserProfile)
                        .where(qMgrUserProfile.userId.eq(userId.trim())))
                    .and(qCtlLogin.logoutTime.isNull()))
                .groupBy(qMgrUserProfile.profileId);

            ResultSet resultSet = query.getResults();

            while (resultSet.next()) {
                String profileId = resultSet.getString(ColumnNames.PROFILE_ID.toString());
                StrCal strCal = strCalMap.get(profileId);
                strCal.setUseLicenseCount(resultSet.getInt("useLicenseCount"));
            }

        } catch (Exception e) {
            throw new EarthException(e);
        }
        return new ArrayList<>(strCalMap.values());
    }

    public List<StrCal> getLicenceOfEntireSystem() throws EarthException {
        QCtlLogin qCtlLogin = QCtlLogin.newInstance();
        QMstSystem qMstSystem = QMstSystem.newInstance();
        EarthQueryFactory earthQueryFactory = ConnectionManager.getEarthQueryFactory(EARTH_WORKSPACE_ID);
        List<StrCal> strCals = new ArrayList<StrCal>();
        try {
            StrCal strCal = new StrCal();
            strCal.setDivision(Constant.SYSTEM_DIVISION);
            strCal.setProfileId(null);
            strCals.add(strCal);

            ResultSet resultSet = null;
            resultSet = earthQueryFactory.select(qMstSystem.configValue.as("availableLicenceCount"))
                .from(qMstSystem).where(qMstSystem.section.eq(Constant.AVAILABLELICENCECOUNT)
                .and(qMstSystem.variableName.eq(Constant.ENTIRE_SYSTEM))).getResults();
            while (resultSet.next()) {
                strCal.setAvailableLicenseCount(resultSet.getInt("availableLicenceCount"));
            }

            resultSet = earthQueryFactory
                    .select((earthQueryFactory.select(qCtlLogin.userId.countDistinct()).from(qCtlLogin)
                            .where(qCtlLogin.logoutTime.isNull()).as("useLicenseCount")))
                    .getResults();

            while (resultSet.next()) {
                strCal.setUseLicenseCount(resultSet.getInt("useLicenseCount"));
            }
        } catch (Exception e) {
            throw new EarthException(e);
        }
        return strCals;
    }

    public long updateOutTime(String sessionId, String outTime) throws EarthException {
        QCtlLogin qcCtlLogin = QCtlLogin.newInstance();
        EarthQueryFactory query = ConnectionManager.getEarthQueryFactory(EARTH_WORKSPACE_ID);

        return (long) executeWithException(() -> {
            return query.update(qcCtlLogin).set(qcCtlLogin.logoutTime, outTime)
                    .where(qcCtlLogin.sessionId.eq(sessionId)).execute();
        });
    }

    public long deleteListByUserIds(List<String> userIds) throws EarthException {
        List<Map<Path<?>, Object>> conditions = new ArrayList<>();
        for (String userId : userIds) {
            Map<Path<?>, Object> condition = new HashMap<>();
            condition.put(qcCtlLogin.userId, userId);
            conditions.add(condition);
        }
        return this.deleteList(Constant.EARTH_WORKSPACE_ID, conditions);

    }
}
