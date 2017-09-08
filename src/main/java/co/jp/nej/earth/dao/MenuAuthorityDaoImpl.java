package co.jp.nej.earth.dao;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.manager.connection.ConnectionManager;
import co.jp.nej.earth.manager.connection.EarthQueryFactory;
import co.jp.nej.earth.model.MenuAccessRight;
import co.jp.nej.earth.model.MenuLink;
import co.jp.nej.earth.model.ProfileAccessRight;
import co.jp.nej.earth.model.UserAccessRight;
import co.jp.nej.earth.model.constant.Constant;
import co.jp.nej.earth.model.entity.CtlMenu;
import co.jp.nej.earth.model.entity.MgrMenu;
import co.jp.nej.earth.model.entity.MgrMenuP;
import co.jp.nej.earth.model.entity.MgrMenuU;
import co.jp.nej.earth.model.enums.AccessRight;
import co.jp.nej.earth.model.enums.ColumnNames;
import co.jp.nej.earth.model.sql.QCtlMenu;
import co.jp.nej.earth.model.sql.QMgrMenu;
import co.jp.nej.earth.model.sql.QMgrMenuCategory;
import co.jp.nej.earth.model.sql.QMgrMenuP;
import co.jp.nej.earth.model.sql.QMgrMenuU;
import co.jp.nej.earth.model.sql.QMgrUserProfile;
import co.jp.nej.earth.util.DateUtil;
import co.jp.nej.earth.util.UserAccessRightUtil;
import com.google.gson.Gson;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import com.querydsl.sql.dml.SQLInsertClause;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author p-tvo-thuynd
 */

@Repository
public class MenuAuthorityDaoImpl extends BaseDaoImpl<CtlMenu> implements MenuAuthorityDao {

    private static final Logger LOG = LoggerFactory.getLogger(MenuAuthorityDaoImpl.class);

    public MenuAuthorityDaoImpl() throws Exception {
        super();
    }

    public Map<String, MenuAccessRight> getMixAuthority(String userId) throws EarthException {
        Map<String, MenuAccessRight> menuAccessRightMap = new LinkedHashMap<>();
        try {
            QMgrMenu qMgrMenu = QMgrMenu.newInstance();
            QMgrMenuCategory qMgrMenuCategory = QMgrMenuCategory.newInstance();
            QCtlMenu qCtlMenu = QCtlMenu.newInstance();

            ResultSet resultSet = ConnectionManager.getEarthQueryFactory(Constant.EARTH_WORKSPACE_ID)
                .select(qMgrMenu.functionId, qMgrMenu.functionName, qMgrMenu.functionCategoryId,
                    qMgrMenuCategory.functionCategoryName, qMgrMenu.functionInformation, qCtlMenu.accessAuthority)
                .from(qMgrMenu).join(qMgrMenuCategory)
                .on(qMgrMenu.functionCategoryId.eq(qMgrMenuCategory.functionCategoryId)).join(qCtlMenu)
                .on(qMgrMenu.functionId.eq(qCtlMenu.functionId)).where(qCtlMenu.userId.eq(userId))
                .orderBy(
                    qMgrMenuCategory.functionCategoryId.castToNum(Integer.class).asc(),
                    qMgrMenu.functionId.castToNum(Integer.class).asc()
                ).getResults();
            MgrMenu mgrMenu = null;
            MenuAccessRight menuAccessRight = null;

            while (resultSet.next()) {
                mgrMenu = new MgrMenu();
                String functionId = resultSet.getString(ColumnNames.FUNCTION_ID.toString());
                mgrMenu.setFunctionId(functionId);
                mgrMenu.setFunctionName(resultSet.getString(ColumnNames.FUNCTION_NAME.toString()));
                mgrMenu.setFunctionCategoryId(resultSet.getString(ColumnNames.FUNCTION_CATEGORY_ID.toString()));
                mgrMenu.setFunctionCategoryName(resultSet.getString(ColumnNames.FUNCTION_CATEGORY_NAME.toString()));
                String functionInformation = resultSet.getString(ColumnNames.FUNCTION_INFORMATION.toString());
                if (!StringUtils.isEmpty(functionInformation)) {
                    mgrMenu.setFunctionInformation(functionInformation);
                    Gson gson = new Gson();
                    MenuLink menuLink = gson.fromJson(functionInformation, MenuLink.class);
                    mgrMenu.setMenuInformation(menuLink);
                }
                menuAccessRight = new MenuAccessRight();
                menuAccessRight.setMgrMenu(mgrMenu);
                menuAccessRight.setAccessRight(
                    AccessRight.values()[resultSet.getInt(ColumnNames.ACCESS_AUTHORITY.toString())]);
                menuAccessRightMap.put(functionId, menuAccessRight);
            }
        } catch (Exception e) {
            throw new EarthException(e);
        }
        return menuAccessRightMap;
    }

    @Override
    public long deleteListByUserIds(List<String> userIds) throws EarthException {
        try {
            QCtlMenu qCtlMenu = QCtlMenu.newInstance();
            QMgrMenuU qMgrMenuU = QMgrMenuU.newInstance();
            EarthQueryFactory earthQueryFactory = ConnectionManager.getEarthQueryFactory(Constant.EARTH_WORKSPACE_ID);
            earthQueryFactory.delete(qCtlMenu).where(qCtlMenu.userId.in(userIds)).execute();
            return earthQueryFactory.delete(qMgrMenuU).where(qMgrMenuU.userId.in(userIds)).execute();
        } catch (Exception ex) {
            throw new EarthException(ex);
        }
    }

    @Override
    public long deleteListByUserIds(String workspaceId, List<String> userIds) throws EarthException {
        try {
            QCtlMenu qCtlMenu = QCtlMenu.newInstance();
            QMgrMenuU qMgrMenuU = QMgrMenuU.newInstance();
            EarthQueryFactory earthQueryFactory = ConnectionManager.getEarthQueryFactory(workspaceId);
            earthQueryFactory.delete(qMgrMenuU).where(qMgrMenuU.userId.in(userIds)).execute();
            return earthQueryFactory.delete(qCtlMenu).where(qCtlMenu.userId.in(userIds)).execute();
        } catch (Exception ex) {
            throw new EarthException(ex);
        }
    }

    @Override
    public long deleteListByProfileIds(List<String> profileIds) throws EarthException {
        try {
            QMgrMenuP qMgrMenuP = QMgrMenuP.newInstance();
            EarthQueryFactory earthQueryFactory = ConnectionManager.getEarthQueryFactory(Constant.EARTH_WORKSPACE_ID);
            return earthQueryFactory.delete(qMgrMenuP).where(qMgrMenuP.profileId.in(profileIds)).execute();
        } catch (Exception ex) {
            throw new EarthException(ex);
        }
    }

    @Override
    public long insertMixAuthority(String menuId, List<UserAccessRight> userAccessRights) throws EarthException {
        try {
            if(userAccessRights.size()>0) {
                QCtlMenu qCtlMenu = QCtlMenu.newInstance();
                EarthQueryFactory earthQueryFactory =
                    ConnectionManager.getEarthQueryFactory(Constant.EARTH_WORKSPACE_ID);
                SQLInsertClause insert = earthQueryFactory.insert(qCtlMenu);
                for (UserAccessRight userAccessRight : userAccessRights) {
                    insert.set(qCtlMenu.functionId, menuId)
                        .set(qCtlMenu.userId, userAccessRight.getUserId())
                        .set(qCtlMenu.accessAuthority, userAccessRight.getAccessRightValue())
                        .set(qCtlMenu.lastUpdateTime, DateUtil.getCurrentDate(
                            Constant.DatePattern.DATE_FORMAT_YYYYMMDDHHMMSSSSS))
                        .addBatch();
                }
                return insert.execute();
            }
            return 0L;
        } catch (Exception ex) {
            throw new EarthException(ex);
        }
    }

    @Override
    public long deleteAllMixAuthority(List<String> menuIds) throws EarthException {
        try {
            QCtlMenu qCtlMenu = QCtlMenu.newInstance();
            List<Map<Path<?>, Object>> conditions = new ArrayList<>();
            for (String menuId : menuIds) {
                Map<Path<?>, Object> condition = new HashMap<>();
                condition.put(qCtlMenu.functionId, menuId);
                conditions.add(condition);
            }
            return this.deleteList(Constant.EARTH_WORKSPACE_ID, conditions);
        } catch (Exception ex) {
            throw new EarthException(ex);
        }
    }

    @Override
    public List<UserAccessRight> getUserAuthority(String menuId) throws EarthException {
        try {
            QMgrMenuU qMgrMenuU = QMgrMenuU.newInstance();
            QBean<MgrMenuU> selectList = Projections.bean(MgrMenuU.class, qMgrMenuU.all());
            EarthQueryFactory earthQueryFactory = ConnectionManager.getEarthQueryFactory(Constant.EARTH_WORKSPACE_ID);
            List<MgrMenuU> mgrMenuUs = earthQueryFactory
                .select(selectList).from(qMgrMenuU).where(qMgrMenuU.functionId.eq(menuId))
                .fetch();
            List<UserAccessRight> userAccessRights = new ArrayList<UserAccessRight>();
            for (MgrMenuU mgrMenuU : mgrMenuUs) {
                UserAccessRight userAccessRight = new UserAccessRight();
                userAccessRight.setUserId(mgrMenuU.getUserId());
                userAccessRight.setAccessRight(AccessRight.values()[mgrMenuU.getAccessAuthority()]);
                userAccessRight.setAccessRightValue(mgrMenuU.getAccessAuthority());
                userAccessRights.add(userAccessRight);
            }
            return userAccessRights;
        } catch (Exception ex) {
            throw new EarthException(ex);
        }
    }

    @Override
    public List<UserAccessRight> getUserAuthorityByProfiles(String menuId) throws EarthException {
        try {
            QMgrMenuP qMgrMenuP = QMgrMenuP.newInstance();
            QMgrUserProfile qMgrUserProfile = QMgrUserProfile.newInstance();
            EarthQueryFactory earthQueryFactory = ConnectionManager.getEarthQueryFactory(Constant.EARTH_WORKSPACE_ID);
            ResultSet resultSet = earthQueryFactory.select(qMgrMenuP.functionId, qMgrUserProfile
                .userId, qMgrMenuP.accessAuthority, qMgrMenuP.lastUpdateTime).from(qMgrMenuP)
                .innerJoin(qMgrUserProfile)
                .on(qMgrMenuP.profileId.eq(qMgrUserProfile.profileId))
                .where(qMgrMenuP.functionId.eq(menuId)).getResults();

            List<UserAccessRight> userAccessRights = UserAccessRightUtil.getUserAccessRightsFromResult(resultSet);
            return userAccessRights;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new EarthException(ex);
        }
    }

    @Override
    public List<ProfileAccessRight> getProfileAuthority(String menuId) throws EarthException {
        List<ProfileAccessRight> profileAccessRights = new ArrayList<>();
        try {
            QMgrMenuP qMgrMenuP = QMgrMenuP.newInstance();
            ResultSet resultSet = ConnectionManager.getEarthQueryFactory(Constant.EARTH_WORKSPACE_ID)
                .select(qMgrMenuP.profileId, qMgrMenuP.accessAuthority).from(qMgrMenuP)
                .where(qMgrMenuP.functionId.eq(menuId)).getResults();
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
    public long deleteAllUserAuthority(String functionId) throws EarthException {
        try {
            QMgrMenuU qMgrMenuU = QMgrMenuU.newInstance();
            EarthQueryFactory earthQueryFactory = ConnectionManager.getEarthQueryFactory(Constant.EARTH_WORKSPACE_ID);
            long deletedRecordNumber = earthQueryFactory.delete(qMgrMenuU)
                .where(qMgrMenuU.functionId.eq(functionId)).execute();
            return deletedRecordNumber;
        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
            throw new EarthException(ex);
        }
    }

    @Override
    public long deleteAllProfileAuthority(String functionId) throws EarthException {
        try {
            QMgrMenuP qMgrMenuP = QMgrMenuP.newInstance();
            EarthQueryFactory earthQueryFactory = ConnectionManager.getEarthQueryFactory(Constant.EARTH_WORKSPACE_ID);
            long deletedRecordNumber = earthQueryFactory.delete(qMgrMenuP)
                .where(qMgrMenuP.functionId.eq(functionId)).execute();
            return deletedRecordNumber;
        } catch (Exception ex) {
            LOG.error(ex.getMessage());
            throw new EarthException(ex);
        }
    }

    @Override
    public long insertUserAuthority(String functionId, List<UserAccessRight> userAccessRights) throws EarthException {
        try {
            QMgrMenuU qMgrMenuU = QMgrMenuU.newInstance();
            EarthQueryFactory earthQueryFactory = ConnectionManager.getEarthQueryFactory(Constant.EARTH_WORKSPACE_ID);
            SQLInsertClause insert = earthQueryFactory.insert(qMgrMenuU);
            for (UserAccessRight userAccessRight : userAccessRights) {
                insert.set(qMgrMenuU.functionId, functionId)
                    .set(qMgrMenuU.userId, userAccessRight.getUserId())
                    .set(qMgrMenuU.accessAuthority, userAccessRight.getAccessRightValue())
                    .set(qMgrMenuU.lastUpdateTime,
                        DateUtil.getCurrentDate(Constant.DatePattern.DATE_FORMAT_YYYYMMDDHHMMSSSSS))
                    .addBatch();
            }
            return insert.execute();
        } catch (Exception e) {
            throw new EarthException(e);
        }
    }

    @Override
    public long insertMenuU(MgrMenuU mgrMenuU) throws EarthException {
        try {
            QMgrMenuU qMgrMenuU = QMgrMenuU.newInstance();
            EarthQueryFactory earthQueryFactory = ConnectionManager.getEarthQueryFactory(Constant.EARTH_WORKSPACE_ID);
            mgrMenuU.setLastUpdateTime(DateUtil.getCurrentDate(Constant.DatePattern.DATE_FORMAT_YYYYMMDDHHMMSSSSS));
            return earthQueryFactory.insert(qMgrMenuU).populate(mgrMenuU).execute();
        } catch (Exception e) {
            throw new EarthException(e);
        }
    }

    @Override
    public long insertProfileAuthority(String functionId, List<ProfileAccessRight> profileAccessRights)
        throws EarthException {
        try {
            QMgrMenuP qMgrMenuP = QMgrMenuP.newInstance();
            EarthQueryFactory earthQueryFactory = ConnectionManager.getEarthQueryFactory(Constant.EARTH_WORKSPACE_ID);
            SQLInsertClause insert = earthQueryFactory.insert(qMgrMenuP);
            for (ProfileAccessRight profileAccessRight : profileAccessRights) {
                insert.set(qMgrMenuP.functionId, functionId)
                    .set(qMgrMenuP.profileId, profileAccessRight.getProfileId())
                    .set(qMgrMenuP.accessAuthority, profileAccessRight.getAccessRightValue())
                    .set(qMgrMenuP.lastUpdateTime,
                        DateUtil.getCurrentDate(Constant.DatePattern.DATE_FORMAT_YYYYMMDDHHMMSSSSS))
                    .addBatch();
            }
            return insert.execute();
        } catch (Exception e) {
            throw new EarthException(e);
        }
    }

    @Override
    public long insertMenuP(MgrMenuP mgrMenuP) throws EarthException {
        try {
            QMgrMenuP qMgrMenuP = QMgrMenuP.newInstance();
            EarthQueryFactory earthQueryFactory = ConnectionManager.getEarthQueryFactory(Constant.EARTH_WORKSPACE_ID);
            mgrMenuP.setLastUpdateTime(DateUtil.getCurrentDate(Constant.DatePattern.DATE_FORMAT_YYYYMMDDHHMMSSSSS));
            return earthQueryFactory.insert(qMgrMenuP).populate(mgrMenuP).execute();
        } catch (Exception e) {
            throw new EarthException(e);
        }
    }
}
