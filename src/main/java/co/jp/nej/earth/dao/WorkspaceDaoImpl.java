package co.jp.nej.earth.dao;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.manager.connection.ConnectionManager;
import co.jp.nej.earth.manager.connection.EarthQueryFactory;
import co.jp.nej.earth.model.MgrWorkspace;
import co.jp.nej.earth.model.MgrWorkspaceConnect;
import co.jp.nej.earth.model.constant.Constant;
import co.jp.nej.earth.model.enums.DatabaseType;
import co.jp.nej.earth.model.sql.QMgrProcessService;
import co.jp.nej.earth.model.sql.QMgrWorkspace;
import co.jp.nej.earth.model.sql.QMgrWorkspaceConnect;
import co.jp.nej.earth.util.CryptUtil;
import co.jp.nej.earth.util.DateUtil;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author longlt
 */

@Repository
public class WorkspaceDaoImpl implements WorkspaceDao {
    public static final String CREATE_SCHEMA_ERROR_CODE = "create_schema_error";
    public static final String ADD_ROLE_MEMBER = "add_role_member";
    public static final String CREATE_TABLES_ERROR_CODE = "create_table_error";
    public static final String INSERT_WORKSPACE_INFO_ERROR_CODE = "insert_workspace_info_error";

    @Autowired
    private DatabaseType databaseType;

    public List<MgrWorkspaceConnect> getAllMgrConnections() throws EarthException {
        try {
            QMgrWorkspaceConnect qMgrWorkspaceConnect = QMgrWorkspaceConnect.newInstance();
            QBean<MgrWorkspaceConnect> selectList = Projections.bean(MgrWorkspaceConnect.class,
                    qMgrWorkspaceConnect.all());
            return ConnectionManager.getEarthQueryFactory(Constant.EARTH_WORKSPACE_ID).select(selectList)
                                        .from(qMgrWorkspaceConnect)
                                        .orderBy(qMgrWorkspaceConnect.workspaceId.asc())
                                        .fetch();
        } catch (Exception ex) {
            throw new EarthException(ex);
        }
    }

    public MgrWorkspaceConnect getMgrConnectionByWorkspaceId(String workspaceId) throws EarthException {
        try {
            QMgrWorkspaceConnect qMgrWorkspaceConnect = QMgrWorkspaceConnect.newInstance();
            QBean<MgrWorkspaceConnect> selectList = Projections.bean(MgrWorkspaceConnect.class,
                    qMgrWorkspaceConnect.all());
            return ConnectionManager
                    .getEarthQueryFactory(Constant.EARTH_WORKSPACE_ID).select(selectList).from(qMgrWorkspaceConnect)
                    .where(qMgrWorkspaceConnect.workspaceId.eq(Integer.parseInt(workspaceId))).fetchOne();
        } catch (Exception ex) {
            throw new EarthException(ex);
        }
    }

    public List<MgrWorkspace> getAll() throws EarthException {
        try {
            QMgrWorkspace qMgrWorkspace = QMgrWorkspace.newInstance();
            QBean<MgrWorkspace> selectList = Projections.bean(MgrWorkspace.class, qMgrWorkspace.all());
            return ConnectionManager.getEarthQueryFactory(Constant.EARTH_WORKSPACE_ID)
                    .select(selectList).from(qMgrWorkspace).orderBy(qMgrWorkspace.workspaceId.asc())
                .fetch();
        } catch (Exception ex) {
            throw new EarthException(ex);
        }
    }

    public boolean insertOne(MgrWorkspaceConnect mgrWorkspaceConnect) throws EarthException {
        try {
            QMgrWorkspaceConnect qMgrWorkspaceConnect = QMgrWorkspaceConnect.newInstance();
            QMgrWorkspace qMgrWorkspace = QMgrWorkspace.newInstance();

            if (databaseType.isOracle()) {
                mgrWorkspaceConnect.setDbType(Constant.WorkSpace.ORACLE);
            } else {
                mgrWorkspaceConnect.setDbType(Constant.WorkSpace.SQL);
            }
            mgrWorkspaceConnect.setDbPassword(CryptUtil.encryptData(mgrWorkspaceConnect.getDbPassword()));
            mgrWorkspaceConnect.setLastUpdateTime(DateUtil.getCurrentDateString());

            MgrWorkspace mgrWorkspace = mgrWorkspaceConnect.getMgrWorkspace();
            mgrWorkspace.setLastUpdateTime(DateUtil.getCurrentDateString());

            EarthQueryFactory queryFactory = ConnectionManager.getEarthQueryFactory(Constant.EARTH_WORKSPACE_ID);
            queryFactory.insert(qMgrWorkspaceConnect)
                    .populate(mgrWorkspaceConnect).execute();
            queryFactory.insert(qMgrWorkspace)
                    .populate(mgrWorkspace).execute();
        } catch (Exception e) {
            throw new EarthException(INSERT_WORKSPACE_INFO_ERROR_CODE, e);
        }
        return true;
    }

    public boolean deleteList(List<Integer> workspaceIds) throws EarthException {
        QMgrWorkspaceConnect qMgrWorkspaceConnect = QMgrWorkspaceConnect.newInstance();
        QMgrWorkspace qMgrWorkspace = QMgrWorkspace.newInstance();
        QMgrProcessService qMgrProcessService = QMgrProcessService.newInstance();
        try {
            EarthQueryFactory factory = ConnectionManager.getEarthQueryFactory(Constant.EARTH_WORKSPACE_ID);
            factory.delete(qMgrWorkspaceConnect)
                    .where(qMgrWorkspaceConnect.workspaceId.in(workspaceIds)).execute();
            factory.delete(qMgrWorkspace)
                    .where(qMgrWorkspace.workspaceId.in(workspaceIds)).execute();
            List<Integer> integerWorkspaceIds = workspaceIds.stream().map(Integer::valueOf)
                .collect(Collectors.toList());
            factory.delete(qMgrProcessService)
                    .where(qMgrProcessService.workspaceId.in(integerWorkspaceIds)).execute();
        } catch (Exception e) {
            throw new EarthException(e);
        }
        return true;
    }

    public MgrWorkspaceConnect getOne(String workspaceId) throws EarthException {
        QMgrWorkspaceConnect qMgrWorkspaceConnect = QMgrWorkspaceConnect.newInstance();
        QMgrWorkspace qMgrWorkspace = QMgrWorkspace.newInstance();
        QBean<MgrWorkspaceConnect> selectList = Projections.bean(MgrWorkspaceConnect.class, qMgrWorkspaceConnect.all());
        MgrWorkspaceConnect mgrWorkspaceConnect = ConnectionManager.getEarthQueryFactory(Constant.EARTH_WORKSPACE_ID)
                .select(selectList)
                .from(qMgrWorkspaceConnect)
                .where(qMgrWorkspaceConnect.workspaceId.eq(Integer.parseInt(workspaceId)))
                .fetchOne();
        if (mgrWorkspaceConnect != null) {
            mgrWorkspaceConnect.setDbPassword(CryptUtil.decryptData(mgrWorkspaceConnect.getDbPassword()));
        } else {
            return null;
        }

        QBean<MgrWorkspace> mgrWSSelectedColumns = Projections.bean(MgrWorkspace.class, qMgrWorkspace.all());
        MgrWorkspace mgrWorkspace = ConnectionManager.getEarthQueryFactory(Constant.EARTH_WORKSPACE_ID)
            .select(mgrWSSelectedColumns)
            .from(qMgrWorkspace)
            .where(qMgrWorkspace.workspaceId.eq(Integer.parseInt(workspaceId)))
            .fetchOne();

        if (mgrWorkspace == null) {
            mgrWorkspace = new MgrWorkspace();
        }

        mgrWorkspaceConnect.setMgrWorkspace(mgrWorkspace);
        return mgrWorkspaceConnect;
    }

    public boolean getById(String schemaName) throws EarthException {
        boolean isExit = false;
        List<MgrWorkspace> mgrWorkspaces = new ArrayList<>();
        try {
            QMgrWorkspace qMgrWorkspace = QMgrWorkspace.newInstance();
            QBean<MgrWorkspace> selectList = Projections.bean(MgrWorkspace.class, qMgrWorkspace.all());
            if (databaseType.isOracle()) {
                mgrWorkspaces = ConnectionManager.getEarthQueryFactory(Constant.EARTH_WORKSPACE_ID).select(selectList)
                        .from(qMgrWorkspace)
                        .where(qMgrWorkspace.workspaceName.eq(Constant.WorkSpace.CHARACTER_COMMON + schemaName))
                        .fetch();
            } else {
                mgrWorkspaces = ConnectionManager.getEarthQueryFactory(Constant.EARTH_WORKSPACE_ID).select(selectList)
                        .from(qMgrWorkspace).where(qMgrWorkspace.workspaceName.eq(schemaName)).fetch();
            }
            if (!mgrWorkspaces.isEmpty()) {
                isExit = true;
            }
        } catch (Exception ex) {
            throw new EarthException(ex);
        }
        return isExit;
    }

    public boolean updateOne(MgrWorkspaceConnect mgrWorkspaceConnect) throws EarthException {
        try {
            QMgrWorkspaceConnect qMgrWorkspaceConnect = QMgrWorkspaceConnect.newInstance();
            QMgrWorkspace qMgrWorkspace = QMgrWorkspace.newInstance();
            EarthQueryFactory factory = ConnectionManager.getEarthQueryFactory(Constant.EARTH_WORKSPACE_ID);
            long count = 0L;
            if (mgrWorkspaceConnect.isChangePassword()) {
                count = factory.update(qMgrWorkspaceConnect)
                    .set(qMgrWorkspaceConnect.schemaName, mgrWorkspaceConnect.getSchemaName())
                    .set(qMgrWorkspaceConnect.dbUser, mgrWorkspaceConnect.getDbUser())
                    .set(qMgrWorkspaceConnect.dbPassword, CryptUtil.encryptData(mgrWorkspaceConnect.getDbPassword()))
                    .set(qMgrWorkspaceConnect.owner, mgrWorkspaceConnect.getOwner())
                    .set(qMgrWorkspaceConnect.dbServer, mgrWorkspaceConnect.getDbServer())
                    .set(qMgrWorkspaceConnect.lastUpdateTime, DateUtil.getCurrentDateString())
                    .where(qMgrWorkspaceConnect.workspaceId.eq(mgrWorkspaceConnect.getWorkspaceId())).execute();
            } else {
                count = factory.update(qMgrWorkspaceConnect)
                    .set(qMgrWorkspaceConnect.schemaName, mgrWorkspaceConnect.getSchemaName())
                    .set(qMgrWorkspaceConnect.dbUser, mgrWorkspaceConnect.getDbUser())
                    .set(qMgrWorkspaceConnect.owner, mgrWorkspaceConnect.getOwner())
                    .set(qMgrWorkspaceConnect.dbServer, mgrWorkspaceConnect.getDbServer())
                    .set(qMgrWorkspaceConnect.lastUpdateTime, DateUtil.getCurrentDateString())
                    .where(qMgrWorkspaceConnect.workspaceId.eq(mgrWorkspaceConnect.getWorkspaceId())).execute();
            }

            if (count <= 0) {
                return false;
            }

            return (factory.update(qMgrWorkspace)
                .set(qMgrWorkspace.workspaceName, mgrWorkspaceConnect.getMgrWorkspace().getWorkspaceName())
                .set(qMgrWorkspace.lastUpdateTime, DateUtil.getCurrentDateString())
                .where(qMgrWorkspace.workspaceId.eq(mgrWorkspaceConnect.getMgrWorkspace().getWorkspaceId()))
                .execute()) > 0;



        } catch (Exception ex) {
            throw new EarthException(ex);
        }
    }

}
