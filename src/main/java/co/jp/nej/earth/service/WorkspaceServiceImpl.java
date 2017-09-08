package co.jp.nej.earth.service;

import co.jp.nej.earth.dao.NativeDao;
import co.jp.nej.earth.dao.WorkspaceDao;
import static co.jp.nej.earth.dao.WorkspaceDaoImpl.CREATE_SCHEMA_ERROR_CODE;
import static co.jp.nej.earth.dao.WorkspaceDaoImpl.CREATE_TABLES_ERROR_CODE;
import static co.jp.nej.earth.dao.WorkspaceDaoImpl.INSERT_WORKSPACE_INFO_ERROR_CODE;
import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.manager.connection.ConnectionManager;
import co.jp.nej.earth.model.Message;
import co.jp.nej.earth.model.MgrWorkspace;
import co.jp.nej.earth.model.MgrWorkspaceConnect;
import co.jp.nej.earth.model.TransactionManager;
import co.jp.nej.earth.model.constant.Constant;
import co.jp.nej.earth.model.constant.Constant.WorkSpace;
import static co.jp.nej.earth.model.constant.Constant.WorkSpace.CHARACTER_COMMON;
import co.jp.nej.earth.model.enums.DatabaseType;
import co.jp.nej.earth.util.ConversionUtil;
import co.jp.nej.earth.util.DateUtil;
import co.jp.nej.earth.util.EMessageResource;
import co.jp.nej.earth.util.EStringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * @author longlt
 */
@Service
public class WorkspaceServiceImpl extends BaseService implements WorkspaceService {
    private static final Logger LOG = LoggerFactory.getLogger(WorkspaceService.class);
    @Autowired
    private WorkspaceDao workspaceDao;

    @Autowired
    private EMessageResource messageSource;

    @Autowired
    private DatabaseType databaseType;

    @Autowired
    private NativeDao nativeDao;

    public List<MgrWorkspaceConnect> getAllWorkspaceConnections() throws EarthException {
        return ConversionUtil.castList(executeTransaction(Constant.EARTH_WORKSPACE_ID, () -> {
            return workspaceDao.getAllMgrConnections();
        }), MgrWorkspaceConnect.class);
    }

    public MgrWorkspaceConnect getMgrConnectionByWorkspaceId(String workspaceId) throws EarthException {
        return ConversionUtil.castObject(executeTransaction(Constant.EARTH_WORKSPACE_ID, () -> {
            return workspaceDao.getMgrConnectionByWorkspaceId(workspaceId);
        }), MgrWorkspaceConnect.class);
    }

    public List<MgrWorkspace> getAll() throws EarthException {
        return ConversionUtil.castList(executeTransaction(Constant.EARTH_WORKSPACE_ID, () -> {
            return workspaceDao.getAll();
        }), MgrWorkspace.class);
    }

    public boolean insertOne(MgrWorkspaceConnect mgrWorkspaceConnect) throws EarthException {
        TransactionManager transactionManager = null;
        Connection sysConnection = null;
        try {
            transactionManager = new TransactionManager(Constant.EARTH_WORKSPACE_ID);
            mgrWorkspaceConnect.setLastUpdateTime(DateUtil.getCurrentDateString());
            mgrWorkspaceConnect.setPort(WorkSpace.SQL_PORT);
            if (mgrWorkspaceConnect.getDbType().equals(DatabaseType.ORACLE)) {
                mgrWorkspaceConnect
                    .setSchemaName(CHARACTER_COMMON + mgrWorkspaceConnect.getSchemaName());
                mgrWorkspaceConnect.setDbUser(CHARACTER_COMMON + mgrWorkspaceConnect.getDbUser());
                mgrWorkspaceConnect.setPort(WorkSpace.ORACLE_PORT);
            }

            sysConnection = ConnectionManager.getEarthQueryFactory(Constant.SYSTEM_WORKSPACE_ID).getConnection();
            // Create new schema.
            nativeDao.createSchema(
                mgrWorkspaceConnect.getSchemaName(), mgrWorkspaceConnect.getDbPassword(), sysConnection);

            // Create new table list.
            nativeDao.createTables(sysConnection, mgrWorkspaceConnect.getSchemaName(), databaseType.getSourceFile());

            // Add role member.
            nativeDao.addRoleMember(mgrWorkspaceConnect.getDbUser(), mgrWorkspaceConnect.getSchemaName(),
                    mgrWorkspaceConnect.getDbPassword(), sysConnection);

            ConnectionManager.commitConnection(sysConnection);

            workspaceDao.insertOne(mgrWorkspaceConnect);
            transactionManager.commit();
        } catch (EarthException e) {
            if (CREATE_TABLES_ERROR_CODE.equals(e.getErrorCode())
                || CREATE_SCHEMA_ERROR_CODE.equals(e.getErrorCode())) {
                ConnectionManager.rollbackConnection(sysConnection);
            }

            if (transactionManager != null) {
                transactionManager.rollback();
            }

            if (CREATE_TABLES_ERROR_CODE.equals(e.getErrorCode())
                || INSERT_WORKSPACE_INFO_ERROR_CODE.equals(e.getErrorCode())) {
                dropSchema(mgrWorkspaceConnect.getSchemaName());
            }

            throw new EarthException(e);
        }
        return true;
    }

    private void dropSchema(String schemaName) throws EarthException {
        TransactionManager transactionManager = null;
        Connection connection = null;
        try {
            transactionManager = new TransactionManager(Constant.EARTH_WORKSPACE_ID);
            connection = ConnectionManager.getEarthQueryFactory(Constant.SYSTEM_WORKSPACE_ID).getConnection();
            nativeDao.dropSchema(schemaName, connection);
            ConnectionManager.commitConnection(connection);
            transactionManager.commit();
        } catch (EarthException e) {
            LOG.error(e.getMessage(), e);
            ConnectionManager.rollbackConnection(connection);
            if (transactionManager != null) {
                transactionManager.rollback();
            }
        }
    }

    public MgrWorkspaceConnect getDetail(String workspaceId) throws EarthException {
        return ConversionUtil.castObject(executeTransaction(Constant.EARTH_WORKSPACE_ID, () -> {
            MgrWorkspaceConnect mgrWorkspaceconnect = workspaceDao.getOne(workspaceId);
            if(mgrWorkspaceconnect != null) {
                mgrWorkspaceconnect.setSchemaName(mgrWorkspaceconnect.getSchemaName().replace(Constant.WorkSpace
                    .CHARACTER_COMMON, ""));
                mgrWorkspaceconnect.setDbUser(mgrWorkspaceconnect.getDbUser().replace(Constant.WorkSpace
                    .CHARACTER_COMMON, ""));
            }
            return mgrWorkspaceconnect;
        }), MgrWorkspaceConnect.class);
    }

    public List<Message> deleteList(List<Integer> workspaceIds) throws EarthException {
        List<Message> messages = new ArrayList<>();
        boolean result = (boolean) executeTransaction(Constant.EARTH_WORKSPACE_ID, () -> {
            return workspaceDao.deleteList(workspaceIds);
        });
        return result ? success() : updateFailed(Constant.ScreenItem.WORKSPACE);
    }

    private List<Message> validateByDatabaseType(MgrWorkspaceConnect mgrWorkspaceConnect) throws EarthException {
        List<Message> messages = new ArrayList<Message>();
        if(databaseType.isOracle()){
            if(!mgrWorkspaceConnect.getSchemaName().equals(mgrWorkspaceConnect.getDbUser())) {
                messages.add(new Message(Constant.ErrorCode.E0037,
                    messageSource.get(Constant.ErrorCode.E0037, new String[] { "workspace.schemaName",
                        "workspace.dbUser"})));
            }
        }
        return messages;
    }

    public List<Message> validateInsert(MgrWorkspaceConnect mgrWorkspaceConnect, Boolean insert) throws EarthException {
        List<Message> messages = new ArrayList<Message>();
        if (insert) {
            if (EStringUtil.isEmpty(mgrWorkspaceConnect.getDbPassword())) {
                Message message = new Message(
                    Constant.ErrorCode.E0001,
                    messageSource.get(Constant.ErrorCode.E0001, new String[]{"workspace.dbPassword"}));
                messages.add(message);
                return messages;
            }
            //no need to validate anything now
            executeTransaction(() -> {
                if (nativeDao.iSchemaExits(mgrWorkspaceConnect.getSchemaName())) {
                    Message message = new Message(Constant.ErrorCode.E0003,
                        messageSource.get(Constant.ErrorCode.E0003, new String[]{"workspace.schemaName"}));
                    messages.add(message);
                }
                return null;
            });
            messages.addAll(validateByDatabaseType(mgrWorkspaceConnect));
        } else {
            if (mgrWorkspaceConnect.isChangePassword()) {
                if (EStringUtil.isEmpty(mgrWorkspaceConnect.getDbPassword())) {
                    Message message = new Message(Constant.ErrorCode.E0001,
                        messageSource.get(Constant.ErrorCode.E0001, new String[]{"workspace.dbPassword"}));
                    messages.add(message);
                }
            }
        }
        return messages;
    }

    public boolean updateOne(MgrWorkspaceConnect mgrWorkspaceConnect) throws EarthException {
        return (boolean) executeTransaction(Constant.EARTH_WORKSPACE_ID, () -> {
            mgrWorkspaceConnect.setPort(WorkSpace.SQL_PORT);
            if (mgrWorkspaceConnect.getDbType().equals(DatabaseType.ORACLE)) {
                mgrWorkspaceConnect
                        .setSchemaName(CHARACTER_COMMON + mgrWorkspaceConnect.getSchemaName());
                mgrWorkspaceConnect.setDbUser(CHARACTER_COMMON + mgrWorkspaceConnect.getDbUser());
                mgrWorkspaceConnect.setPort(WorkSpace.ORACLE_PORT);
            }

            boolean isSuccess = workspaceDao.updateOne(mgrWorkspaceConnect);
            if (isSuccess) {
                ConnectionManager.remove(mgrWorkspaceConnect.getStringWorkspaceId());
            }

            return  isSuccess;
        });
    }

}
