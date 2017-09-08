package co.jp.nej.earth.dao;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.manager.connection.ConnectionManager;
import co.jp.nej.earth.manager.connection.EarthQueryFactory;
import co.jp.nej.earth.model.constant.Constant;
import co.jp.nej.earth.model.entity.MgrMenu;
import co.jp.nej.earth.model.enums.ColumnNames;
import co.jp.nej.earth.model.sql.QMgrMenu;
import co.jp.nej.earth.model.sql.QMgrMenuCategory;
import co.jp.nej.earth.model.sql.QMgrMenuP;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MenuDaoImpl extends BaseDaoImpl<MgrMenu> implements MenuDao {

    private static final Logger LOG = LoggerFactory.getLogger(MenuDaoImpl.class);

    public MenuDaoImpl() throws Exception {
        super();
    }

    @Override
    public List<MgrMenu> getMenuByProfileId(List<String> profileIds) throws EarthException {
        try {
            QMgrMenu qMgrMenu = QMgrMenu.newInstance();
            QMgrMenuP qMgrMenuP = QMgrMenuP.newInstance();
            QBean<MgrMenu> selectList = Projections.bean(MgrMenu.class, qMgrMenu.all());
            EarthQueryFactory earthQueryFactory = ConnectionManager.getEarthQueryFactory(Constant.EARTH_WORKSPACE_ID);
            List<MgrMenu> mgrMenus = earthQueryFactory.select(selectList).distinct().from(qMgrMenu).innerJoin(qMgrMenuP)
                    .on(qMgrMenu.functionId.eq(qMgrMenuP.functionId)).where(qMgrMenuP.profileId.in(profileIds)).fetch();
            return mgrMenus;
        } catch (Exception ex) {
            LOG.error(ex.getMessage());
            throw new EarthException(ex);
        }
    }

    @Override
    public List<MgrMenu> getAll() throws EarthException {
        try {
            QMgrMenu qMgrMenu = QMgrMenu.newInstance();
            QMgrMenuCategory qMgrMenuCategory = QMgrMenuCategory.newInstance();
            EarthQueryFactory earthQueryFactory = ConnectionManager.getEarthQueryFactory(Constant.EARTH_WORKSPACE_ID);
            List<MgrMenu> mgrMenus = new ArrayList<>();
            ResultSet resultSet = earthQueryFactory.select(qMgrMenu.functionId, qMgrMenu.functionInformation,
                qMgrMenu.functionName, qMgrMenuCategory.functionCategoryName, qMgrMenu.functionCategoryId)
                .from(qMgrMenu).join(qMgrMenuCategory)
                .on(qMgrMenu.functionCategoryId.eq(qMgrMenuCategory.functionCategoryId))
                .orderBy(qMgrMenuCategory.functionSortNo.asc(), qMgrMenu.functionSortNo.asc()).getResults();
            while (resultSet.next()) {
                MgrMenu mgrMenu = new MgrMenu();
                mgrMenu.setFunctionId(resultSet.getString(ColumnNames.FUNCTION_ID.toString()));
                mgrMenu.setFunctionName(resultSet.getString(ColumnNames.FUNCTION_NAME.toString()));
                mgrMenu.setFunctionCategoryId(resultSet.getString(ColumnNames.FUNCTION_CATEGORY_ID.toString()));
                mgrMenu.setFunctionCategoryName(resultSet.getString(ColumnNames.FUNCTION_CATEGORY_NAME.toString()));
                mgrMenus.add(mgrMenu);
            }
            return mgrMenus;
        } catch (Exception ex) {
            LOG.error(ex.getMessage());
            throw new EarthException(ex);
        }
    }

    @Override
    public MgrMenu showDetail(String functionId) throws EarthException {
        try {
            QMgrMenu qMgrMenu = QMgrMenu.newInstance();
            QMgrMenuCategory qMgrMenuCategory = QMgrMenuCategory.newInstance();
            QBean<MgrMenu> selectList = Projections.bean(MgrMenu.class, qMgrMenu);
            EarthQueryFactory earthQueryFactory = ConnectionManager.getEarthQueryFactory(Constant.EARTH_WORKSPACE_ID);
            MgrMenu mgrMenu = null;
            ResultSet resultSet = earthQueryFactory.select(selectList, qMgrMenuCategory.functionCategoryName)
                .from(qMgrMenu).join(qMgrMenuCategory)
                .on(qMgrMenu.functionCategoryId.eq(qMgrMenuCategory.functionCategoryId))
                .where(qMgrMenu.functionId.eq(functionId))
                .getResults();
            while (resultSet.next()) {
                mgrMenu = new MgrMenu();
                mgrMenu.setFunctionId(resultSet.getString(ColumnNames.FUNCTION_ID.toString()));
                mgrMenu.setFunctionName(resultSet.getString(ColumnNames.FUNCTION_NAME.toString()));
                mgrMenu.setFunctionCategoryId(resultSet.getString(ColumnNames.FUNCTION_CATEGORY_ID.toString()));
                mgrMenu.setFunctionCategoryName(resultSet.getString(ColumnNames.FUNCTION_CATEGORY_NAME.toString()));
            }
            return mgrMenu;
        } catch (Exception ex) {
            LOG.error(ex.getMessage());
            throw new EarthException(ex);
        }
    }
}
