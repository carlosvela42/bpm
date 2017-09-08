package co.jp.nej.earth.service;

import co.jp.nej.earth.config.JdbcConfig;
import co.jp.nej.earth.dao.NativeDao;
import co.jp.nej.earth.dao.ProcessDao;
import co.jp.nej.earth.dao.StrageDbDao;
import co.jp.nej.earth.dao.StrageFileDao;
import co.jp.nej.earth.dao.TaskDao;
import co.jp.nej.earth.exception.CreateSchemaException;
import co.jp.nej.earth.exception.DeleteFailException;
import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.id.EAutoIncrease;
import co.jp.nej.earth.manager.connection.ConnectionManager;
import co.jp.nej.earth.manager.connection.EarthQueryFactory;
import co.jp.nej.earth.model.DatProcess;
import co.jp.nej.earth.model.Message;
import co.jp.nej.earth.model.MgrProcess;
import co.jp.nej.earth.model.StrageDb;
import co.jp.nej.earth.model.StrageFile;
import co.jp.nej.earth.model.constant.Constant;
import co.jp.nej.earth.model.constant.Constant.EarthId;
import co.jp.nej.earth.model.constant.Constant.ErrorCode;
import co.jp.nej.earth.model.enums.AccessRight;
import co.jp.nej.earth.model.enums.DatabaseType;
import co.jp.nej.earth.model.enums.DocumentDataSavePath;
import co.jp.nej.earth.model.form.DeleteProcessForm;
import co.jp.nej.earth.model.form.ProcessForm;
import co.jp.nej.earth.model.sql.QMgrProcess;
import co.jp.nej.earth.model.sql.QMgrTask;
import co.jp.nej.earth.model.sql.QStrageDb;
import co.jp.nej.earth.model.sql.QStrageFile;
import co.jp.nej.earth.util.ConversionUtil;
import co.jp.nej.earth.util.DateUtil;
import co.jp.nej.earth.util.EMessageResource;
import co.jp.nej.earth.util.EModelUtil;
import co.jp.nej.earth.util.EStringUtil;
import co.jp.nej.earth.util.SessionUtil;
import co.jp.nej.earth.util.TemplateUtil;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Path;
import com.querydsl.sql.SQLQuery;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static co.jp.nej.earth.dao.WorkspaceDaoImpl.ADD_ROLE_MEMBER;
import static co.jp.nej.earth.dao.WorkspaceDaoImpl.CREATE_TABLES_ERROR_CODE;

/** @author p-tvo-sonta */
@Service
public class ProcessServiceImpl extends BaseService implements ProcessService {

    public static final String STR_DATA_DB_ORACLE_SQL = "STR_DATA_DB_oracle.sql";
    public static final String STR_DATA_DB_SQLSERVER_SQL = "STR_DATA_DB_sqlserver.sql";
    public static final String STR_DATA_DB = "STR_DATA_DB";
    @Autowired
    private ProcessDao processDao;

    @Autowired
    private StrageFileDao strageFileDao;

    @Autowired
    private StrageDbDao strageDbDao;

    @Autowired
    private TaskDao taskDao;

    @Autowired
    private EAutoIncrease eAutoIncrease;

    @Autowired
    private EMessageResource eMessageResource;

    @Autowired
    private DatabaseType databaseType;

    @Autowired
    private NativeDao nativeDao;

    @Autowired
    private JdbcConfig jdbcConfig;

    private static final int NUM_255 = 255;
    private static final int NUM_260 = 260;

    public DatProcess getProcessSession(HttpSession session, String workspaceId, String workItemId, Integer processId)
            throws EarthException {
        // Get data process from session.
        DatProcess dataProcess = (DatProcess) getDataItemFromSession(session,
                SessionUtil.getOriginWorkItemDictionaryKey(workspaceId, workItemId),
                EModelUtil.getProcessIndex(String.valueOf(processId)));

        TemplateUtil.checkPermission(dataProcess, AccessRight.RO, messageSource.get(Constant.ErrorCode.E0025));
        return dataProcess;
    }

    public boolean updateProcessSession(HttpSession session, String workspaceId, DatProcess datProcess)
            throws EarthException {
        DatProcess dataProcessSession = getProcessSession(session, workspaceId, datProcess.getWorkitemId(),
                datProcess.getProcessId());

        TemplateUtil.checkPermission(dataProcessSession,
            AccessRight.RW, messageSource.get(Constant.ErrorCode.E0025));
        // Update process template data.
        dataProcessSession.setTemplateId(datProcess.getTemplateId());
        dataProcessSession.setMgrTemplate(datProcess.getMgrTemplate());
        dataProcessSession.setProcessData(datProcess.getProcessData());
        return true;
    }

    /** {@inheritDoc} */
    @Override
    public List<MgrProcess> getAllByWorkspace(String workspaceId) throws EarthException {
        return ConversionUtil.castList(this.executeTransaction(workspaceId, () -> {
            try {
                return processDao.findAll(workspaceId, QMgrProcess.newInstance().processId.asc());
            } catch (Exception e) {
                throw new EarthException(e);
            }
        }), MgrProcess.class);
    }

    /** {@inheritDoc} */
    @Override
    public boolean deleteList(DeleteProcessForm form) throws EarthException {
        try {
            return (boolean) this.executeTransaction(form.getWorkspaceId(), () -> {
                List<Map<Path<?>, Object>> conditions = new ArrayList<>();
                QMgrProcess qMgrProcess = QMgrProcess.newInstance();
                QStrageFile qStrageFile = QStrageFile.newInstance();
                QStrageDb qStrageDb = QStrageDb.newInstance();
                QMgrTask qMgrTask = QMgrTask.newInstance();

                // loop list process id
                for (Integer processId : form.getProcessIds()) {

                    // Delete the data in process
                    Map<Path<?>, Object> processCondition = new HashMap<>();
                    processCondition.put(qMgrProcess.processId, processId);
                    conditions.add(processCondition);
                    if(processDao.delete(form.getWorkspaceId(), processCondition) <= 0L) {
                        throw new DeleteFailException();
                    }

                    // delete data in strage file
                    Map<Path<?>, Object> fileCondition = new HashMap<>();
                    fileCondition.put(qStrageFile.processId, processId);

                    strageFileDao.delete(form.getWorkspaceId(), fileCondition) ;

                    // delete data in strage db
                    Map<Path<?>, Object> dbCondition = new HashMap<>();
                    dbCondition.put(qStrageDb.processId, processId);

                    strageDbDao.delete(form.getWorkspaceId(), dbCondition);
                    // delete data in the tasks
                    Map<Path<?>, Object> taskCondition = new HashMap<>();
                    taskCondition.put(qMgrTask.processId, processId);
                    taskDao.delete(form.getWorkspaceId(), taskCondition);
                }
                return true;
            });
        } catch (EarthException ex) {
            return false;
        }
    }


    /** {@inheritDoc} */
    @Override
    public boolean insertOne(ProcessForm form, String sessionId) throws EarthException {
        return (boolean) this.executeTransaction(form.getWorkspaceId(), () -> {
            MgrProcess process = form.getProcess();
            Integer newId = Integer.parseInt(eAutoIncrease.getAutoId(EarthId.PROCESS, sessionId));
            if (newId == 0) {
                return false;
            }
            process.setProcessId(newId);

            // parse data to JSON.
            if (!EStringUtil.isEmpty(process.getProcessDefinition())) {
                process.setProcessDefinition(XML.toJSONObject(process.getProcessDefinition()).toString());
            }

            processDao.add(form.getWorkspaceId(), process);
            if (String.valueOf(DocumentDataSavePath.FILE.getId()).equals(process.getDocumentDataSavePath())) {
                if (form.getStrageFile() == null) {
                    return false;
                }
                form.getStrageFile().setProcessId(process.getProcessId());
                strageFileDao.add(form.getWorkspaceId(), form.getStrageFile());
                return true;
            }

            if (EStringUtil.isEmpty(process.getDocumentDataSavePath())
                || process.getDocumentDataSavePath().length() > NUM_260) {
                return true;
            }

            form.getStrageDb().setProcessId(process.getProcessId());
            strageDbDao.add(form.getWorkspaceId(), form.getStrageDb());

            if (DocumentDataSavePath.isDatabase(form.getProcess().getDocumentDataSavePath())) {
                // Create a place to save document data.
                createDocumentDatabasePlace(form);
            }

            return true;
        });
    }

    private void createDocumentDatabasePlace(ProcessForm form) throws EarthException {
        Connection sysConnection = null;
        Connection strDbConnection = null;
        try {
            String fileName = databaseType.isOracle() ? STR_DATA_DB_ORACLE_SQL : STR_DATA_DB_SQLSERVER_SQL;

            String user = form.getStrageDb().getDbUser();
            String schemaName = form.getStrageDb().getSchemaName();
            String password = form.getStrageDb().getDbPassword();

            boolean isExistedSchema = (boolean) executeTransaction(() -> {
                return nativeDao.iSchemaExits(schemaName);
            });


            sysConnection = ConnectionManager.getEarthQueryFactory(Constant.SYSTEM_WORKSPACE_ID).getConnection();
            if (!isExistedSchema) {
                nativeDao.createSchema(schemaName, password, sysConnection);

            }

            nativeDao.addRoleMember(user, schemaName, password, sysConnection);
            ConnectionManager.commitConnection(sysConnection);

            strDbConnection = ConnectionManager.createEarthQueryFactory(
                                                    jdbcConfig.strDbDataSource(form.getStrageDb())).getConnection();
            if (!nativeDao.checkExistsTable(strDbConnection, schemaName, STR_DATA_DB)) {
                nativeDao.createTables(strDbConnection, schemaName, fileName);
            }

            ConnectionManager.commitConnection(strDbConnection);
        } catch (EarthException e) {
            if (CREATE_TABLES_ERROR_CODE.equals(e.getErrorCode()) || ADD_ROLE_MEMBER.equals(e.getErrorCode())) {
                ConnectionManager.rollbackConnection(sysConnection);
            } else {
                ConnectionManager.rollbackConnection(strDbConnection);
            }

            throw new CreateSchemaException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean updateOne(ProcessForm form) throws EarthException {
        return (boolean) this.executeTransaction(form.getWorkspaceId(), () -> {
            QMgrProcess qMgrProcess = QMgrProcess.newInstance();
            MgrProcess process = form.getProcess();
            if (!EStringUtil.isEmpty(process.getProcessDefinition())) {
                process.setProcessDefinition(XML.toJSONObject(process.getProcessDefinition()).toString());
            }
            // update processCondition
            Map<Path<?>, Object> processCondition = new HashMap<>();
            processCondition.put(qMgrProcess.processId, process.getProcessId());
            // update map
            Map<Path<?>, Object> processUpdateMap = new HashMap<>();
            processUpdateMap.put(qMgrProcess.description, process.getDescription());
            processUpdateMap.put(qMgrProcess.documentDataSavePath, process.getDocumentDataSavePath());
            processUpdateMap.put(qMgrProcess.processDefinition, process.getProcessDefinition());
            processUpdateMap.put(qMgrProcess.processName, process.getProcessName());
            processUpdateMap.put(qMgrProcess.processVersion, process.getProcessVersion());
            processUpdateMap.put(qMgrProcess.lastUpdateTime, DateUtil.getCurrentDateString());
            // update process
            long resultNum = processDao.update(form.getWorkspaceId(), processCondition, processUpdateMap);
            if (resultNum <= 0) {
                return false;
            }
            // delete strageFile and strageDb from db if existed
            QStrageFile qStrageFile = QStrageFile.newInstance();
            QStrageDb qStrageDb = QStrageDb.newInstance();
            Map<Path<?>, Object> fileCondition = new HashMap<>();
            fileCondition.put(qStrageFile.processId, process.getProcessId());
            strageFileDao.delete(form.getWorkspaceId(), fileCondition);
            Map<Path<?>, Object> dbCondition = new HashMap<>();
            dbCondition.put(qStrageDb.processId, process.getProcessId());
            strageDbDao.delete(form.getWorkspaceId(), dbCondition);

            // insert strageFile strageDb
            if (String.valueOf(DocumentDataSavePath.FILE.getId()).equals(process.getDocumentDataSavePath())) {
                if (form.getStrageFile() == null) {
                    return false;
                }
                form.getStrageFile().setProcessId(process.getProcessId());
                strageFileDao.add(form.getWorkspaceId(), form.getStrageFile());
            } else {
                if (form.getStrageDb() == null) {
                    return false;
                }
                form.getStrageDb().setProcessId(process.getProcessId());
                strageDbDao.add(form.getWorkspaceId(), form.getStrageDb());
            }

            if (DocumentDataSavePath.isDatabase(form.getProcess().getDocumentDataSavePath())) {
                // Create a place to save document data.
                createDocumentDatabasePlace(form);
            }
            return true;
        });

    }

    /** {@inheritDoc} */
    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> getDetail(String workspaceId, String processId) throws EarthException {
        return (HashMap<String, Object>) this.executeTransaction(workspaceId, () -> {
            try {

                final int cpProcessId = 1;
                final int cpProcessName = 2;
                final int cpProcessVersion = 3;
                final int cpDescription = 4;
                final int cpProcessDefinition = 5;
                final int cpDocumentDataSavePath = 6;
                final int cpPLastUpdateTime = 7;
                final int cdbProcessId = 8;
                final int cdbSchemaName = 9;
                final int cdbDbUser = 10;
                final int cdbDbPassword = 11;
                final int cdbOwner = 12;
                final int cdbDbServer = 13;
                final int cdbLastUpdateTime = 14;
                final int fProcessId = 15;
                final int fSiteId = 16;
                final int fSiteManagementType = 17;
                final int fLastUpdateTime = 18;

                Map<String, Object> result = new HashMap<>();

                int intProcessId = Integer.parseInt(processId);
                QMgrProcess qMgrProcess = QMgrProcess.newInstance();
                QStrageFile qStrageFile = QStrageFile.newInstance();
                QStrageDb qStrageDb = QStrageDb.newInstance();
                EarthQueryFactory queryFactory = ConnectionManager.getEarthQueryFactory(workspaceId);
                SQLQuery<Tuple> sqlClause = queryFactory
                        .select(qMgrProcess.processId, qMgrProcess.processName, qMgrProcess.processVersion,
                                qMgrProcess.description, qMgrProcess.processDefinition,
                                qMgrProcess.documentDataSavePath, qMgrProcess.lastUpdateTime, qStrageDb.processId,
                                qStrageDb.schemaName, qStrageDb.dbUser, qStrageDb.dbPassword, qStrageDb.owner,
                                qStrageDb.dbServer, qStrageDb.lastUpdateTime, qStrageFile.processId, qStrageFile.siteId,
                                qStrageFile.siteManagementType, qStrageFile.lastUpdateTime)
                        .from(qMgrProcess).leftJoin(qStrageDb).on(qMgrProcess.processId.eq(qStrageDb.processId))
                        .leftJoin(qStrageFile).on(qMgrProcess.processId.eq(qStrageFile.processId))
                        .where(qMgrProcess.processId.eq(intProcessId));

                // LOG.info(sqlClause.getSQL().getSQL());

                ResultSet rs = sqlClause.getResults();
                if (rs.next()) {
                    MgrProcess process = new MgrProcess();
                    process.setProcessId(rs.getInt(cpProcessId));
                    process.setProcessName(rs.getString(cpProcessName));
                    process.setProcessVersion(rs.getFloat(cpProcessVersion));
                    process.setDescription(rs.getString(cpDescription));
                    process.setProcessDefinition(rs.getString(cpProcessDefinition));
                    process.setDocumentDataSavePath(rs.getString(cpDocumentDataSavePath));
                    process.setLastUpdateTime(rs.getString(cpPLastUpdateTime));

                    StrageDb strageDb = new StrageDb();
                    strageDb.setProcessId(rs.getInt(cdbProcessId));
                    strageDb.setSchemaName(rs.getString(cdbSchemaName));
                    strageDb.setDbUser(rs.getString(cdbDbUser));
                    strageDb.setDbPassword(rs.getString(cdbDbPassword));
                    strageDb.setOwner(rs.getString(cdbOwner));
                    strageDb.setDbServer(rs.getString(cdbDbServer));
                    strageDb.setLastUpdateTime(rs.getString(cdbLastUpdateTime));

                    StrageFile strageFile = new StrageFile();
                    strageFile.setProcessId(rs.getInt(fProcessId));
                    strageFile.setSiteId(rs.getInt(fSiteId));
                    strageFile.setSiteManagementType(rs.getString(fSiteManagementType));
                    strageFile.setLastUpdateTime(rs.getString(fLastUpdateTime));

                    result.put("process", process);
                    result.put("strageFile", strageFile);
                    result.put("strageDb", strageDb);
                }
                return result;
            } catch (Exception e) {
                throw new EarthException(e);
            }
        });
    }

    /** check validation process text field
     *
     * @param process
     * @return */
    private boolean validationProcessText(MgrProcess process) {
        if (EStringUtil.isEmpty(process.getProcessName()) || process.getProcessName().length() > NUM_255) {
            return true;
        }
        if (EStringUtil.isEmpty(process.getDescription()) || process.getDescription().length() > NUM_255) {
            return true;
        }
        if (EStringUtil.isEmpty(process.getDocumentDataSavePath())
                || process.getDocumentDataSavePath().length() > NUM_260) {
            return true;
        }
        return false;
    }

    private boolean checkTextEmptyAndLength(String text, String name, int maxLength, List<Message> messages) {
        boolean checkResult = true;
        if (EStringUtil.isEmpty(text)) {
            messages.add(new Message(ErrorCode.E0001,
                    eMessageResource.get(ErrorCode.E0001, new String[] { name })));
            checkResult = false;
        } else if (text.length() > maxLength) {
            messages.add(new Message(ErrorCode.E0026,
                    eMessageResource.get(ErrorCode.E0026, new String[] { name , String.valueOf(maxLength)})));
            checkResult = false;
        }
        return checkResult;
    }

    /** test conection
     *
     * @param strageDb
     * @return */
    private boolean testConnection(StrageDb strageDb) {
        if (EStringUtil.isEmpty(strageDb.getSchemaName())) {
            return false;
        } else if (strageDb.getSchemaName().length() > NUM_255) {
            return false;
        }
        if (strageDb.getDbUser() != null && strageDb.getDbUser().length() > NUM_255) {
            return false;
        }
        if (strageDb.getDbPassword() != null && strageDb.getDbPassword().length() > NUM_255) {
            return false;
        }
        if (strageDb.getOwner() != null && strageDb.getOwner().length() > NUM_255) {
            return false;
        }
        if (strageDb.getDbServer() != null && strageDb.getDbServer().length() > NUM_255) {
            return false;
        }
        return true;
    }
}
