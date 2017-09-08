package co.jp.nej.earth.service;

import co.jp.nej.earth.dao.CustomTaskDao;
import co.jp.nej.earth.dao.DatProcessDao;
import co.jp.nej.earth.dao.DocumentDao;
import co.jp.nej.earth.dao.EventDao;
import co.jp.nej.earth.dao.FolderItemDao;
import co.jp.nej.earth.dao.LayerDao;
import co.jp.nej.earth.dao.ProcessDao;
import co.jp.nej.earth.dao.StrLogAccessDao;
import co.jp.nej.earth.dao.TaskDao;
import co.jp.nej.earth.dao.TemplateDao;
import co.jp.nej.earth.dao.WorkItemDao;
import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.id.EEventId;
import co.jp.nej.earth.id.EWorkItemId;
import co.jp.nej.earth.manager.connection.ConnectionManager;
import co.jp.nej.earth.manager.connection.EarthQueryFactory;
import co.jp.nej.earth.model.Column;
import co.jp.nej.earth.model.DatProcess;
import co.jp.nej.earth.model.Document;
import co.jp.nej.earth.model.Field;
import co.jp.nej.earth.model.FolderItem;
import co.jp.nej.earth.model.Layer;
import co.jp.nej.earth.model.Message;
import co.jp.nej.earth.model.Row;
import co.jp.nej.earth.model.TemplateAccessRight;
import co.jp.nej.earth.model.TemplateData;
import co.jp.nej.earth.model.TemplateKey;
import co.jp.nej.earth.model.UserInfo;
import co.jp.nej.earth.model.WorkItem;
import co.jp.nej.earth.model.WorkItemDictionary;
import co.jp.nej.earth.model.WorkItemListDTO;
import co.jp.nej.earth.model.constant.Constant;
import co.jp.nej.earth.model.constant.Constant.ErrorCode;
import co.jp.nej.earth.model.constant.Constant.Session;
import co.jp.nej.earth.model.entity.CtlEvent;
import co.jp.nej.earth.model.entity.MgrTemplate;
import co.jp.nej.earth.model.entity.StrLogAccess;
import co.jp.nej.earth.model.enums.AccessRight;
import co.jp.nej.earth.model.enums.Action;
import co.jp.nej.earth.model.enums.ColumnNames;
import co.jp.nej.earth.model.enums.EventStatus;
import co.jp.nej.earth.model.enums.OpenProcessMode;
import co.jp.nej.earth.model.enums.TemplateType;
import co.jp.nej.earth.model.enums.Type;
import co.jp.nej.earth.model.sql.QCtlEvent;
import co.jp.nej.earth.model.sql.QDocument;
import co.jp.nej.earth.model.sql.QFolderItem;
import co.jp.nej.earth.model.sql.QLayer;
import co.jp.nej.earth.model.sql.QTemplateData;
import co.jp.nej.earth.util.ConversionUtil;
import co.jp.nej.earth.util.CryptUtil;
import co.jp.nej.earth.util.DateUtil;
import co.jp.nej.earth.util.EModelUtil;
import co.jp.nej.earth.util.EStringUtil;
import co.jp.nej.earth.util.SessionUtil;
import co.jp.nej.earth.util.TemplateUtil;
import co.jp.nej.earth.util.UniqueIdUtil;
import co.jp.nej.earth.web.form.SearchByColumnsForm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Path;
import com.querydsl.sql.dml.SQLDeleteClause;
import com.querydsl.sql.dml.SQLInsertClause;
import com.querydsl.sql.dml.SQLUpdateClause;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static co.jp.nej.earth.model.constant.Constant.ErrorCode.E0025;

/**
 * @author p-tvo-sonta
 */
@Service
public class WorkItemServiceImpl extends BaseService implements WorkItemService {

    @Autowired
    private WorkItemDao workItemDao;

    @Autowired
    private TemplateDao templateDao;

    @Autowired
    private DatProcessDao datProcessDao;

    @Autowired
    private ProcessDao processDao;

    @Autowired
    private FolderItemDao folderItemDao;

    @Autowired
    private DocumentDao documentDao;

    @Autowired
    private LayerDao layerDao;

    @Autowired
    private EventDao eventDao;

    @Autowired
    private StrLogAccessDao strLogAccessDao;

    @Autowired
    private EWorkItemId eWorkItemId;

    @Autowired
    private EEventId eEventId;

    @Autowired
    private CustomTaskDao customTaskDao;

    @Autowired
    private TaskDao taskDao;

    @Autowired
    private DocumentService documentService;

    private static final String EVENT_TASK = "DataUpdateProcess";
    private static final Logger LOG = LoggerFactory.getLogger(WorkItemServiceImpl.class);

    private static final int DEFAULT_PAGE_COUNT = 1;

    @Override
    public List<Message> openWorkItemFromScreen(HttpSession session, String workspaceId, String workItemId)
        throws EarthException {
        String templateId = (String) executeTransaction(workspaceId, () -> {
            return templateDao.getTemplateIdByItemId(workspaceId, workItemId, TemplateType.WORKITEM);
        });
        AccessRight accessRight = TemplateUtil.getAuthority(session, new TemplateKey(workspaceId, templateId));
        OpenProcessMode mode = accessRight.le(AccessRight.RW) ? OpenProcessMode.READ_ONLY : OpenProcessMode.EDIT;
        return openWorkItem(session, workspaceId, workItemId, mode.getMode());
    }

    /*
     * Open a Process, Including 2 modes: Edit and ReadOnly.
     *
     */
    public List<Message> openWorkItem(HttpSession session, String workspaceId, String workItemId, Integer openMode)
        throws EarthException {
        return ConversionUtil.castList(this.executeTransaction(workspaceId, () -> {
            List<Message> messages = new ArrayList<>();
            if (!workItemDao.checkExistWorkItem(workspaceId, workItemId)) {
                messages.add(new Message(ErrorCode.E0029,
                    messageSource.get(ErrorCode.E0029, new String[]{"workItem"})));
                return messages;
            }

            if (eventDao.checkExist(workspaceId, workItemId)) {
                messages.add(new Message(ErrorCode.E1010,
                    messageSource.get(ErrorCode.E1010, new String[]{"workItem"})));
                return messages;
            }

            String workItemTemplateId = templateDao.getTemplateIdByItemId(workspaceId, workItemId,
                TemplateType.WORKITEM);

            TemplateAccessRight templateAccessRight = TemplateUtil.getAuthorityFromSession(session);
            AccessRight accessRight = templateAccessRight.get(new TemplateKey(workspaceId, workItemTemplateId));

            // In case Template Access Right < RO.
            if (accessRight.le(AccessRight.RO)) {
                messages.add(new Message(E0025, messageSource.get(E0025)));
                return messages;
            }

            if (OpenProcessMode.isEdit(openMode)) {
                if (eventDao.getEventByWorkItemId(workspaceId, workItemId) != null) {
                    messages.add(new Message(ErrorCode.E1010,
                        messageSource.get(ErrorCode.E1010, new String[]{"workItem"})));
                    return messages;
                }

                return openWorkItemWithEditMode(session, workspaceId, workItemId);
            }

            return openWorkItemWithReadOnlyMode(session, workspaceId, workItemId);
        }), Message.class);
    }

    public List<Message> closeWorkItem(HttpSession session, String workspaceId, String workItemId)
        throws EarthException {
        List<Message> messages = new ArrayList<>();
        String originWorkItemKey = SessionUtil.getOriginWorkItemDictionaryKey(workspaceId, workItemId);
        // Get all WorkItem informations from session.
        WorkItem workItemSession = (WorkItem) getDataItemFromSession(session, originWorkItemKey,
            EModelUtil.getWorkItemIndex(workItemId));

        TemplateUtil.checkPermission(workItemSession, AccessRight.RO, messageSource.get(E0025));

        Integer openProcessMode = null;
        try {
            openProcessMode = (Integer) getDataItemFromSession(session, originWorkItemKey, Session.OPEN_PROCESS_MODE);
        } catch (EarthException e) {
            messages.add(new Message(null, e.getMessage()));
            return messages;
        }

        if (OpenProcessMode.isEdit(openProcessMode.intValue())) {
            Integer status = EventStatus.OPEN.getValue();
            this.executeTransaction(workspaceId, () -> {
                QCtlEvent qCtlEvent = QCtlEvent.newInstance();
                Map<Path<?>, Object> condition = new HashMap<>();
                condition.put(qCtlEvent.workitemId, workItemId);
                condition.put(qCtlEvent.status, status.toString());
                eventDao.delete(workspaceId, condition);

                return true;
            });
        }

        // Clear Session.
        session.removeAttribute(originWorkItemKey);
        return messages;
    }

    /*
     * Open Process with Edit Mode.
     *
     * 1. Create new Event and insert into EventControl with status is
     * Open(WorkItem is locked).</br> 2. Get all WorkItem informations from data
     * tables: </br> WorkItems, FolderItems,Documents, Layers, Annotations,
     * Templates,...</br> 3. Save them into session of current user.</br> 4.
     * Convert tree object to JSON format and update into
     * EventControl(WorkItemData column).</br>
     *
     */
    private List<Message> openWorkItemWithEditMode(HttpSession session, String workspaceId,
        String workItemId) throws EarthException {
        List<Message> messages = new ArrayList<>();

        // Get all WorkItem informations from data tables.
        TemplateAccessRight templateAccessRight = TemplateUtil.getAuthorityFromSession(session);
        WorkItemDictionary workItemDics = this.getWorkItemDataStructure(workItemId, workspaceId, templateAccessRight);
        if (workItemDics.size() == 0) {
            messages.add(new Message(ErrorCode.E1013, messageSource.get(ErrorCode.E1013, new String[]{"workItem"})));
            return messages;
        }

        workItemDics.put(Session.OPEN_PROCESS_MODE, OpenProcessMode.EDIT.getMode());

        try {
            // Create new event and insert into EventControl with status is Open. WorkItem is locked.
            CtlEvent ctlEvent = new CtlEvent();
            ctlEvent.setStatus(String.valueOf(EventStatus.OPEN.getValue()));
            ctlEvent.setWorkitemId(workItemId);
            ctlEvent.setTaskId(templateDao.getTaskIdByItemId(workspaceId, workItemId));

            UserInfo userInfo = (UserInfo) session.getAttribute(Constant.Session.USER_INFO);
            ctlEvent.setUserId(userInfo.getUserId());
            ctlEvent.setEventId(eEventId.getAutoId(session.getId()));
            ctlEvent.setWorkitemData(
                new ObjectMapper().writeValueAsString(workItemDics.get(EModelUtil.getWorkItemIndex(workItemId))));

            eventDao.add(workspaceId, ctlEvent);

            // Save them into session of current user.
            session.setAttribute(SessionUtil.getOriginWorkItemDictionaryKey(workspaceId, workItemId), workItemDics);
        } catch (JsonProcessingException e) {
            messages.add(new Message(null, e.getMessage()));
        }

        return messages;
    }

    /*
     * Open Process with ReadOnly Mode.
     *
     */
    private List<Message> openWorkItemWithReadOnlyMode(HttpSession session, String workspaceId,
                                                       String workItemId) throws EarthException {

        List<Message> messages = new ArrayList<>();
        // Get all WorkItem information from data tables.
        TemplateAccessRight templateAccessRight = TemplateUtil.getAuthorityFromSession(session);
        WorkItemDictionary workItemDics = this.getWorkItemDataStructure(workItemId, workspaceId, templateAccessRight);
        if (workItemDics.size() == 0) {
            messages.add(new Message(ErrorCode.E1013, messageSource.get(ErrorCode.E1013, new String[]{"workItem"})));
            return messages;
        }

        workItemDics.put(Session.OPEN_PROCESS_MODE, OpenProcessMode.EDIT.getMode());

        // Save them into session of current user
        session.setAttribute(SessionUtil.getOriginWorkItemDictionaryKey(workspaceId, workItemId), workItemDics);

        return messages;
    }

    /**
     * {@inheritDoc}}
     *
     * @throws EarthException
     */
    @Override
    public boolean insertOrUpdateWorkItemToDbFromEvent(String workspaceId, Long threadId, String hostName,
                                                       Integer processServiceID, String userId, String sessionId)
        throws EarthException, IOException {
        if (EStringUtil.isEmpty(workspaceId)) {
            return false;
        }

        return (boolean) this.executeTransaction(workspaceId, () -> {
            try {
                String transactionToken = this.generateTransactionToken(processServiceID, hostName, threadId);
                boolean isUpdateEventSuccess = eventDao.updateStatusAndTokenEvent(transactionToken, workspaceId);
                if (!isUpdateEventSuccess) {
                    return false;
                }
                CtlEvent event = eventDao.getEventIsEditing(transactionToken, workspaceId, userId);
                if (event == null) {
                    return false;
                }

                // Get className and get next task
                // TODO Get className and get next task
                /*String className = customTaskDao.getClassName(workspaceId, event.getEventId());
                Class<?> clazz = Class.forName(className);
                Constructor<?> ctor = clazz.getConstructor(String.class);
                Object object = ctor.newInstance(new Object[]{});
                String nextTaskId = object.run();
                */

                WorkItem workItem = new ObjectMapper().readValue(event.getWorkitemData(), WorkItem.class);
                workItem.setWorkspaceId(workspaceId);
                if (EStringUtil.isEmpty(workItem.getWorkitemId())) {
                    insertWorkItemToDb(workItem, sessionId);
                } else {
                    updateWorkItemToDb(workItem);
                }
                Integer historyNo = workItem.getLastHistoryNo();

                // Insert | update process data
                DatProcess datProcess = workItem.getDataProcess();
                if (datProcess == null) {
                    throw new EarthException("Invalid Process");
                }

                Integer processId = taskDao.getProcessByTaskId(workspaceId, event.getTaskId());
                datProcess.setProcessId(processId);
                datProcess.setWorkitemId(workItem.getWorkitemId());
                insertOrUpdateProcessToDb(workspaceId, datProcess, historyNo);

                // Get list folder and update data
                List<FolderItem> folderItems = workItem.getFolderItems();
                if (folderItems != null) {

                    // Insert FolderItem data and Document data
                    boolean isUpdateFolderItemOk =
                        insertOrUpdateBatchFolderToDb(workspaceId, workItem.getWorkitemId(), folderItems, historyNo);

                    // Save document file
                    if (isUpdateFolderItemOk) {
                        List<String> tempFolder = new ArrayList<>();
                        Map<String, String> documentPathMap;
                        for (FolderItem folderItem : folderItems) {
                            List<Document> documents = folderItem.getDocuments();
                            if (CollectionUtils.isEmpty(documents)) {
                                continue;
                            }
                            documentPathMap = new HashMap<>();
                            for (Document document : documents) {
                                tempFolder.add(document.getDocumentPath());
                                if (!documentService.saveDocumentDataFile(workspaceId, document, processId,
                                        documentPathMap)) {
                                    throw new EarthException("Save file fail");
                                }
                            }
                        }

                        // Delete file
                        deleteFileAndDirectory(tempFolder);
                    }
                }

                // Delete event
                eventDao.deleteEvent(workspaceId, event);

                // Write log process
                writeLog(workspaceId, processServiceID, event, workItem.getWorkitemId());
            } catch (IOException e) {
                throw new EarthException(e);
            }
            return true;
        });
    }

    /**
     * writeLog
     *
     * @param workspaceId
     * @param processId
     * @param event
     * @param workItemId
     * @throws EarthException
     */
    private void writeLog(String workspaceId, int processId, CtlEvent event, String workItemId) throws EarthException {
        StrLogAccess logAccess = new StrLogAccess();
        logAccess.setEventId(event.getEventId());
        logAccess.setProcessTime(DateUtil.getCurrentDateString());
        logAccess.setUserId(event.getUserId());
        logAccess.setWorkitemId(workItemId);
        Integer maxHistoryNoLog = strLogAccessDao.getMaxHistoryNo(workspaceId, event.getEventId());
        logAccess.setHistoryNo(++maxHistoryNoLog);
        logAccess.setTaskId(event.getTaskId());
        logAccess.setLastUpdateTime(DateUtil.getCurrentDateString());

        strLogAccessDao.add(workspaceId, logAccess);
    }

    /**
     * {@inheritDoc}}
     *
     * @throws EarthException
     */
    private void insertOrUpdateProcessToDb(String workspaceId, DatProcess datProcess, Integer historyNo)
        throws EarthException {
        if (EStringUtil.isEmpty(workspaceId)) {
            throw new EarthException("Invalid parameter workspaceId is null");
        }

        String workItemId = datProcess.getWorkitemId();
        if (EStringUtil.isNotEmpty(datProcess.getProcessId())
            && (!processDao.isExistProcess(workspaceId, datProcess.getProcessId()))) {

            // Process ID is not existence im MGR_PROCESS
            throw new EarthException("Invalid data process");
        } else if (EStringUtil.isEmpty(datProcess.getProcessId())
            || (!datProcessDao.isExistWorkItemInProcess(workspaceId, datProcess.getProcessId(), workItemId))) {

            // Insert process
            if (EStringUtil.isEmpty(datProcess.getProcessId())) {
                Integer processId = datProcessDao.getMaxId(workspaceId, workItemId);
                datProcess.setProcessId(++processId);
            }
            datProcess.setLastUpdateTime(DateUtil.getCurrentDateString());
            datProcessDao.add(workspaceId, datProcess);
        } else {

            // Update process
            datProcess.setLastUpdateTime(DateUtil.getCurrentDateString());
            datProcessDao.updateProcess(workspaceId, datProcess);
        }

        // Insert workItem template data
        templateDao.insertProcessTemplateData(workspaceId, datProcess, historyNo);
    }

    /**
     * Insert list folder to database
     *
     * @param workspaceId
     * @param folderItems
     * @return
     * @throws EarthException
     */
    private boolean insertOrUpdateBatchFolderToDb(String workspaceId, String workItemId, List<FolderItem> folderItems,
                                                  Integer historyNo) throws EarthException {
        if (EStringUtil.isEmpty(workspaceId)) {
            throw new EarthException("Invalid parameter workspaceId");
        }
        if (CollectionUtils.isEmpty(folderItems)) {
            return false;
        }
        QFolderItem qFolderItem = QFolderItem.newInstance();
        EarthQueryFactory earthQueryFactory = ConnectionManager.getEarthQueryFactory(workspaceId);
        SQLInsertClause sqlInsertFolderItemClause = null;
        SQLUpdateClause sqlUpdateFolderItemClause = null;
        List<SQLInsertClause> batchSQLTemplateData = new ArrayList<>();
        List<SQLInsertClause> batchInsertWorkItemTree = new ArrayList<>();
        List<SQLUpdateClause> batchUpdateWorkItemTree = new ArrayList<>();
        List<SQLDeleteClause> batchDeleteWorkItemTree = new ArrayList<>();
        MgrTemplate mgrTemplate = null;
        SQLInsertClause sqlInsertTemplateDataClause = null;
        for (FolderItem folderItem : folderItems) {
            folderItem.setLastUpdateTime(DateUtil.getCurrentDateString());

            // Save DAT_FOLDER_ITEM data
            if (EStringUtil.isEmpty(folderItem.getFolderItemNo())) {

                // Insert FolderItem
                folderItem.setFolderItemNo(String.valueOf(new UniqueIdUtil().createId()));
                folderItem.setWorkitemId(workItemId);
                int maxFolderOrder = folderItemDao.getMaxFolderItemOrder(workspaceId, folderItem.getWorkitemId());
                folderItem.setFolderItemOrder(++maxFolderOrder);
                sqlInsertFolderItemClause = earthQueryFactory.insert(qFolderItem);
                folderItemDao.addBatchFolderItem(folderItem, sqlInsertFolderItemClause);
                batchInsertWorkItemTree.add(sqlInsertFolderItemClause);
            } else {

                // Update folder item
                sqlUpdateFolderItemClause = earthQueryFactory.update(qFolderItem);
                folderItemDao.updateBatchFolderItem(folderItem, sqlUpdateFolderItemClause, qFolderItem);
                batchUpdateWorkItemTree.add(sqlUpdateFolderItemClause);
            }

            // Save Folder Item template data
            mgrTemplate = getInstanceMgrTemplate(folderItem.getMgrTemplate(), folderItem.getTemplateId(), workspaceId);
            TemplateData templateData = folderItem.getFolderItemData();
            QTemplateData qTemplateData = null;
            if ((mgrTemplate != null) && (templateData != null)) {
                qTemplateData = QTemplateData.newInstance(mgrTemplate);
                sqlInsertTemplateDataClause = earthQueryFactory.insert(qTemplateData).columns(qTemplateData.all());
                templateData.setHistoryNo(historyNo);
                earthQueryFactory.insertBatchTemplateData(sqlInsertTemplateDataClause, mgrTemplate
                    , templateData, null, folderItem.getWorkitemId()
                    , folderItem.getFolderItemNo(), null, null);
                batchSQLTemplateData.add(sqlInsertTemplateDataClause);
            }

            // Insert or update document
            insertOrUpdateBatchDocumentToDb(workspaceId, folderItem, historyNo,
                batchSQLTemplateData, batchInsertWorkItemTree, batchUpdateWorkItemTree, batchDeleteWorkItemTree);
        }
        // Insert folderItem - document- layer
        for (SQLInsertClause sqlInsert : batchInsertWorkItemTree) {
            if (sqlInsert != null && (sqlInsert.getBatchCount() > 0)) {
                sqlInsert.execute();
            }
        }

        // update folderItem - document- layer
        for (SQLUpdateClause sqlUpdate : batchUpdateWorkItemTree) {
            if (sqlUpdate != null && (sqlUpdate.getBatchCount() > 0)) {
                sqlUpdate.execute();
            }
        }

        // update folderItem - document- layer
        for (SQLDeleteClause sqlDelete : batchDeleteWorkItemTree) {
            if (sqlDelete != null && (sqlDelete.getBatchCount() > 0)) {
                sqlDelete.execute();
            }
        }

        // Insert template data
        for (SQLInsertClause sqlInsert : batchSQLTemplateData) {
            if (sqlInsert != null && (sqlInsert.getBatchCount() > 0)) {
                sqlInsert.execute();
            }
        }

        return true;
    }

    /**
     * Insert Or Update Batch DocumentToDb
     *
     * @param workspaceId
     * @param folderItem
     * @return
     * @throws EarthException
     */
    private boolean insertOrUpdateBatchDocumentToDb(String workspaceId, FolderItem folderItem, Integer historyNo,
                                                    List<SQLInsertClause> batchSQLTemplateData,
                                                    List<SQLInsertClause> batSQLInsertDocument,
                                                    List<SQLUpdateClause> batSQLUpdateDocument,
                                                    List<SQLDeleteClause> batchDeleteDocument)
        throws EarthException {
        if (EStringUtil.isEmpty(workspaceId) || (batchSQLTemplateData == null)
            || (batSQLInsertDocument == null) || (batSQLUpdateDocument == null)) {
            throw new EarthException("Invalid parameter_crudBatchDocumentToDb");
        }
        if (folderItem == null) {
            return false;
        }
        QDocument qDocument = QDocument.newInstance();
        EarthQueryFactory earthQueryFactory = ConnectionManager.getEarthQueryFactory(workspaceId);
        SQLInsertClause sqlInsertClause = null;
        SQLUpdateClause sqlUpdateClause = null;
        MgrTemplate mgrTemplate = null;
        SQLInsertClause sqlInsertTemplateDataClause = null;
        List<Document> documents = folderItem.getDocuments();
        if (CollectionUtils.isEmpty(documents)) {
            return true;
        }

        // Insert document
        for (Document document : documents) {
            document.setLastUpdateTime(DateUtil.getCurrentDateString());
            if (EStringUtil.isEmpty(document.getDocumentNo())) {

                // Insert document
                document.setWorkitemId(folderItem.getWorkitemId());
                document.setFolderItemNo(folderItem.getFolderItemNo());
                document.setDocumentNo(String.valueOf(new UniqueIdUtil().createId()));
                int maxDocumentOrder = documentDao.getMaxDocumentOrder(workspaceId, document.getWorkitemId(),
                    document.getFolderItemNo());
                document.setDocumentOrder(++maxDocumentOrder);

                // TODO upgrade in phase 02
                if (document.getPageCount() == null) {
                    document.setPageCount(DEFAULT_PAGE_COUNT);
                }

                sqlInsertClause = earthQueryFactory.insert(qDocument);
                documentDao.addBatch(document, sqlInsertClause);
                batSQLInsertDocument.add(sqlInsertClause);
            } else {

                // Update document
                sqlUpdateClause = earthQueryFactory.update(qDocument);
                documentDao.updateBatch(document, sqlUpdateClause, qDocument);
                batSQLUpdateDocument.add(sqlUpdateClause);
            }

            // Insert data template
            mgrTemplate = getInstanceMgrTemplate(document.getMgrTemplate(), document.getTemplateId(), workspaceId);
            TemplateData templateData = document.getDocumentData();
            QTemplateData qTemplateData = null;
            if ((mgrTemplate != null) && (templateData != null)) {
                qTemplateData = QTemplateData.newInstance(mgrTemplate);
                templateData.setHistoryNo(historyNo);
                sqlInsertTemplateDataClause = earthQueryFactory.insert(qTemplateData).columns(qTemplateData.all());
                earthQueryFactory.insertBatchTemplateData(sqlInsertTemplateDataClause, mgrTemplate,
                    templateData, null, document.getWorkitemId(),
                    document.getFolderItemNo(), document.getDocumentNo(), null);
                batchSQLTemplateData.add(sqlInsertTemplateDataClause);
            }

            // Insert or update layer
            insertOrUpdateBatchLayerToDb(workspaceId, document, historyNo, batchSQLTemplateData, batSQLInsertDocument,
                batSQLUpdateDocument, batchDeleteDocument);
        }

        return true;
    }

    /**
     * Insert or update batch layer
     *
     * @param workspaceId
     * @param document
     * @return
     * @throws EarthException
     */
    private boolean insertOrUpdateBatchLayerToDb(String workspaceId, Document document, Integer historyNo,
                                                 List<SQLInsertClause> batSQLTemplateData,
                                                 List<SQLInsertClause> batSQLInsertLayer,
                                                 List<SQLUpdateClause> batSQLUpdateLayer,
                                                 List<SQLDeleteClause> batchDeleteLayer)
        throws EarthException {
        if (EStringUtil.isEmpty(workspaceId) || (batSQLTemplateData == null)
            || (batSQLInsertLayer == null) || (batSQLUpdateLayer == null) || (batchDeleteLayer == null)) {
            throw new EarthException("Invalid parameter: Function update batch layer");
        }
        if (document == null) {
            return false;
        }
        QLayer qLayer = QLayer.newInstance();
        EarthQueryFactory earthQueryFactory = ConnectionManager.getEarthQueryFactory(workspaceId);

        // Prepare SQL clause
        SQLInsertClause sqlInsertClause = null;
        SQLUpdateClause sqlUpdateClause = null;
        SQLDeleteClause sqlDeleteClause = null;
        MgrTemplate mgrTemplate = null;
        SQLInsertClause sqlInsertTemplateDataClause = null;
        QTemplateData qTemplateData = null;

        // Process with layer
        for (Layer layer : document.getLayers()) {
            String currentTime = DateUtil.getCurrentDateString();
            layer.setLastUpdateTime(currentTime);
            layer.setInsertDateTime(currentTime);
            if (EStringUtil.isEmpty(layer.getLayerNo())) {

                // Insert layer
                layer.setLayerNo(String.valueOf(new UniqueIdUtil().createId()));
                layer.setWorkitemId(document.getWorkitemId());
                layer.setFolderItemNo(document.getFolderItemNo());
                layer.setDocumentNo(document.getDocumentNo());
                int maxLayerOrder = layerDao.getMaxLayerOrder(workspaceId, document.getWorkitemId(),
                    document.getFolderItemNo(), document.getDocumentNo());
                layer.setLayerOrder(++maxLayerOrder);
                sqlInsertClause = earthQueryFactory.insert(qLayer);
                layerDao.addBatch(layer, sqlInsertClause);
                batSQLInsertLayer.add(sqlInsertClause);
            } else if (Integer.valueOf(Action.UPDATE.getAction()).equals(layer.getAction())) {

                // Update layer
                sqlUpdateClause = earthQueryFactory.update(qLayer);
                layerDao.updateBatch(layer, sqlUpdateClause, qLayer);
                batSQLUpdateLayer.add(sqlUpdateClause);
            } else if (Integer.valueOf(Action.DELETE.getAction()).equals(layer.getAction())) {

                // Delete layer
                sqlDeleteClause = earthQueryFactory.delete(qLayer);
                layerDao.deleteBatch(layer, sqlDeleteClause, qLayer);
                batchDeleteLayer.add(sqlDeleteClause);
            }

            mgrTemplate = getInstanceMgrTemplate(layer.getMgrTemplate(), layer.getTemplateId(), workspaceId);
            TemplateData templateData = layer.getLayerData();
            if ((mgrTemplate == null) || (templateData == null)) {
                continue;
            }

            qTemplateData = QTemplateData.newInstance(mgrTemplate);
            sqlInsertTemplateDataClause = earthQueryFactory.insert(qTemplateData).columns(qTemplateData.all());
            templateData.setHistoryNo(historyNo);

            // Insert template data
            earthQueryFactory.insertBatchTemplateData(sqlInsertTemplateDataClause, mgrTemplate,
                templateData, null, layer.getWorkitemId(),
                layer.getFolderItemNo(), layer.getDocumentNo(), layer.getLayerNo());
            batSQLTemplateData.add(sqlInsertTemplateDataClause);
        }

        return true;
    }

    /**
     * insert work item
     *
     * @param workItem
     * @return
     * @throws EarthException
     */
    private boolean insertWorkItemToDb(WorkItem workItem, String sessionId) throws EarthException {
        if (workItem == null) {
            return false;
        }
        String workspaceId = workItem.getWorkspaceId();
        if (EStringUtil.isEmpty(workspaceId)) {
            throw new EarthException("Invalid workspaceId is null or empty ");
        }
        workItem.setWorkitemId(eWorkItemId.getAutoId(sessionId));

        Integer maxHistoryNoDb = workItemDao.getMaxHistoryNo(workItem.getWorkspaceId(), workItem.getWorkitemId());
        workItem.setLastHistoryNo(++maxHistoryNoDb);
        workItemDao.add(workspaceId, workItem);

        // Insert workItem template data
        templateDao.insertWorkItemTemplateData(workItem);
        return true;
    }

    /**
     * update work item
     *
     * @param workItem
     * @return
     * @throws EarthException
     */
    private boolean updateWorkItemToDb(WorkItem workItem) throws EarthException {
        if (workItem == null) {
            return false;
        }
        Integer maxHistoryNoDb = workItemDao.getMaxHistoryNo(workItem.getWorkspaceId(), workItem.getWorkitemId());
        workItem.setLastHistoryNo(++maxHistoryNoDb);
        workItemDao.updateWorkItem(workItem);

        // Insert workItem template data
        templateDao.insertWorkItemTemplateData(workItem);
        return true;
    }

    /**
     * Get WorkItem from session
     *
     * @param session     HttpSession object
     * @param workspaceId Id of Workspace
     * @param workItemId  Id of workItem
     * @throws EarthException
     */
    @Override
    public WorkItem getWorkItemSession(HttpSession session, String workspaceId, String workItemId)
        throws EarthException {
        // Get workItem data from session.
        WorkItem workItemSession = (WorkItem) getDataItemFromSession(session,
            SessionUtil.getOriginWorkItemDictionaryKey(workspaceId, workItemId),
            EModelUtil.getWorkItemIndex(workItemId));

        TemplateUtil.checkPermission(workItemSession, AccessRight.RO, messageSource.get(E0025));

        WorkItem workItem = EModelUtil.clone(workItemSession, WorkItem.class);
        workItem.setFolderItems(new ArrayList<>());
        return workItem;
    }

    @Override
    public boolean updateWorkItemSession(HttpSession session, String workspaceId, WorkItem workItem)
        throws EarthException {
        WorkItem workItemSession = updateTaskToWorkItemSession(session, workspaceId, workItem);
        TemplateUtil.checkPermission(workItemSession, AccessRight.RW, messageSource.get(E0025));
        // Update work item data.
        if (workItemSession != null) {
            workItemSession.setWorkItemData(workItem.getWorkItemData());
            workItemSession.setMgrTemplate(workItem.getMgrTemplate());
            workItemSession.setTemplateId(workItem.getTemplateId());
        }

        return true;
    }

    @Override
    public WorkItem updateTaskToWorkItemSession(HttpSession session, String workspaceId, WorkItem workItem)
        throws EarthException {
        // Get WorkItem data from session.
        WorkItem workItemSession = (WorkItem) getDataItemFromSession(
            session,
            SessionUtil.getOriginWorkItemDictionaryKey(workspaceId, workItem.getWorkitemId()),
            EModelUtil.getWorkItemIndex(workItem.getWorkitemId()));

        TemplateUtil.checkPermission(workItemSession, AccessRight.RW, messageSource.get(E0025));

        // Update work item data.
        workItemSession.setTaskId(workItem.getTaskId());

        return workItemSession;
    }

    @Override
    public WorkItem getWorkItemStructureSession(HttpSession session, String workspaceId, String workItemId)
        throws EarthException {
        // Get WorkItem data from session.
        WorkItem workItemSession = (WorkItem) getDataItemFromSession(
            session,
            SessionUtil.getOriginWorkItemDictionaryKey(workspaceId, workItemId),
            EModelUtil.getWorkItemIndex(workItemId));

        TemplateUtil.checkPermission(workItemSession, AccessRight.SO, messageSource.get(E0025));

        WorkItem workItem = EModelUtil.clone(workItemSession, WorkItem.class);

        // Set workItem data is null.
        workItem.setWorkItemData(null);

        // set folder items, documents, layers data in workItem is null.
        List<FolderItem> folderItems = workItem.getFolderItems();
        List<FolderItem> noneDisplayfolderItems = new ArrayList<>();
        for (FolderItem folderItem : folderItems) {
            if (AccessRight.NONE.goe(folderItem.getAccessRight())) {
                noneDisplayfolderItems.add(folderItem);
            } else {
                folderItem.setFolderItemData(null);
                List<Document> documents = folderItem.getDocuments();
                List<Document> noneDisplayDocuments = new ArrayList<>();
                for (Document document : documents) {
                    if (AccessRight.NONE.goe(document.getAccessRight())) {
                        noneDisplayDocuments.add(document);
                    } else {
                        document.setDocumentData(null);
                        List<Layer> layers = document.getLayers();
                        List<Layer> noneDisplayLayers = new ArrayList<>();
                        for (Layer layer : layers) {
                            if (AccessRight.NONE.goe(layer.getAccessRight())) {
                                noneDisplayLayers.add(layer);
                            } else {
                                layer.setLayerData(null);
                            }
                        }

                        // Remove all layers have access right is None.
                        layers.removeAll(noneDisplayLayers);
                    }
                }

                // Remove all documents have access right is None.
                documents.removeAll(noneDisplayDocuments);
            }
        }

        // Remove all Folder Items have access right is None.
        folderItems.removeAll(noneDisplayfolderItems);

        return workItem;
    }

    @Override
    public List<WorkItemListDTO> getWorkItemsByWorkspace(String workspaceId, List<String> workItemIds, String userId,
                                                         String templateId)
        throws EarthException {
        return ConversionUtil.castList(executeTransaction(Constant.SYSTEM_WORKSPACE_ID, () -> {
            if (EStringUtil.isEmpty(templateId)) {
                return workItemDao.getWorkItemsWithoutTemplates(workspaceId, workItemIds);
            }

            return workItemDao.getWorkItemsByWorkspace(workspaceId, workItemIds, userId);
        }), WorkItemListDTO.class);
    }

    @Override
    public boolean unlock(List<String> workItemId, String workspaceId) throws EarthException {
        return (boolean) executeTransaction(workspaceId, () -> {
            return workItemDao.unlock(workItemId, workspaceId) > 0;
        });
    }

    @Override
    public Map<String, Object> searchWorkItems(
        HttpSession session, SearchByColumnsForm searchByColumnsForm, String workspaceId) throws EarthException {

        Map<String, Object> map = new HashMap<>();

        if (EStringUtil.isEmpty(searchByColumnsForm.getTemplateId())) {
            return searchWorkItemsWithoutTemplates(workspaceId, searchByColumnsForm);
        }

        TemplateAccessRight templateAccessRight = TemplateUtil.getAuthorityFromSession(session);
        AccessRight accessRight = templateAccessRight
            .get(new TemplateKey(workspaceId, searchByColumnsForm.getTemplateId()));
        if (accessRight.le(AccessRight.SO)) {
            map.put("columns", new ArrayList<Column>());
            map.put("rows", new ArrayList<Row>());
            map.put("errors", messageSource.get(E0025, new String[]{}));
            return map;
        }

        MgrTemplate mgrTemplate = (MgrTemplate) executeTransaction(workspaceId, () -> {
            return templateDao.getTemplate(workspaceId, searchByColumnsForm.getTemplateId());
        });

        if (mgrTemplate == null) {
            return searchWorkItemsWithoutTemplates(workspaceId, searchByColumnsForm);
        }

        return searchWorkItemsIncludingTemplates(workspaceId, mgrTemplate, searchByColumnsForm);
    }

    private Map<String, Object> searchWorkItemsWithoutTemplates(String workspaceId,
            SearchByColumnsForm searchByColumnsForm) throws EarthException {
        Map<String, Object> resultMap = new HashMap<>();
        List<Row> rowList = new ArrayList<>();

        List<String> workItemIds = ConversionUtil.castList(executeTransaction(workspaceId, () -> {
            return workItemDao.getWorkItemIdsNotHaveTemplate(workspaceId, searchByColumnsForm.getSkip(),
                searchByColumnsForm.getLimit());
        }), String.class);

        for (String workItemId : workItemIds) {
            Row row = new Row();
            row.setWorkitemId(workItemId);
            row.setColumns(new ArrayList<>());
            rowList.add(row);
        }

        resultMap.put("columns", new ArrayList<Column>());
        resultMap.put("rows", rowList);

        return resultMap;
    }

    private Map<String, Object> searchWorkItemsIncludingTemplates(
                String workspaceId, MgrTemplate mgrTemplate, SearchByColumnsForm searchByColumnsForm)
        throws EarthException {
        Map<String, Object> map = new HashMap<>();
        List<Row> rowList = new ArrayList<>();

        List<Column> columns = new ArrayList<>();

        QTemplateData qTemplateData = QTemplateData.newInstance(mgrTemplate);
        SearchColumn searchColumn = new SearchColumn();
        BooleanBuilder condition =
            searchColumn.searchColumns(qTemplateData, searchByColumnsForm.getValid(),
                searchByColumnsForm.getSearchByColumnForms());

        // Get field list.
        List<Field> fields = mgrTemplate.getTemplateFields();
        for (Field field : fields) {
            Column column = new Column();
            column.setName(field.getName());
            column.setType(ConversionUtil.castObject(Type.getLabel(field.getType()), String.class));
            column.setDescription(field.getDescription());
            column.setEncrypted(field.isEncrypted());
            columns.add(column);
        }

        List<TemplateData> templateDataList =
            ConversionUtil.castList(executeTransaction(workspaceId, () -> {
                return templateDao.getTemplateDataList(workspaceId, mgrTemplate, condition,
                    searchByColumnsForm.getSkip(), searchByColumnsForm.getLimit(), null);
            }), TemplateData.class);

        // Get template data list.
        for (TemplateData templateData : templateDataList) {
            List<Column> listCol = new ArrayList<>();
            Map<String, Object> mapTemplate = templateData.getDataMap();
            for (Column column : columns) {
                String name = column.getName();
                boolean encrypted = column.getEncrypted();
                Column col = new Column();
                col.setName(name);
                String value = "";
                if (mapTemplate.get(name) != null) {
                    value = ConversionUtil.castObject(mapTemplate.get(name).toString(), String.class);
                    if (encrypted) {
                        try {
                            value = CryptUtil.decryptData(value);
                        } catch (Exception ex) {
                            LOG.error(ex.getMessage());
                        }
                    }
                    col.setValue(value);
                    listCol.add(col);
                } else {
                    col.setValue(value);
                    listCol.add(col);
                }
            }
            Row row = new Row();
            row.setWorkitemId((String) mapTemplate.get(ColumnNames.WORKITEM_ID.toString()));
            row.setColumns(listCol);
            rowList.add(row);
        }
        map.put("columns", columns);
        map.put("rows", rowList);
        return map;
    }

    @Override
    public Integer getProcessIdByWorkItem(String workspaceId, String workitemId) throws EarthException {
        return (Integer) executeTransaction(Constant.SYSTEM_WORKSPACE_ID, () -> {
            return workItemDao.getProcessIdByWorkItem(workspaceId, workitemId);
        });
    }

    @Override
    public boolean closeAndSaveWorkItem(HttpSession session, String workItemId, String workspaceId)
        throws EarthException {
        return (boolean) executeTransaction(workspaceId, () -> {
            try {
                String originWorkItemKey = SessionUtil.getOriginWorkItemDictionaryKey(workspaceId, workItemId);
                // Get all WorkItem informations from session.
                WorkItem workItemSession = (WorkItem) getDataItemFromSession(session, originWorkItemKey,
                    EModelUtil.getWorkItemIndex(workItemId));

                TemplateUtil.checkPermission(workItemSession, AccessRight.RW, messageSource.get(E0025));

                Integer openProcessMode = (Integer) getDataItemFromSession(session, originWorkItemKey,
                    Session.OPEN_PROCESS_MODE);
                if (OpenProcessMode.isReadOnly(openProcessMode)) {
                    throw new EarthException(messageSource.get(ErrorCode.E1011, new String[]{"workItem"}));
                }

                String jsonWorkItem = new ObjectMapper().writeValueAsString(workItemSession);

                // Update Event Control.
                Integer status = EventStatus.EDIT.getValue();
                QCtlEvent qCtlEvent = QCtlEvent.newInstance();
                Map<Path<?>, Object> condition = new HashMap<>();
                condition.put(qCtlEvent.workitemId, workItemId);
                Map<Path<?>, Object> updateMap = new HashMap<>();
                updateMap.put(qCtlEvent.workitemData, jsonWorkItem);
                updateMap.put(qCtlEvent.status, status.toString());
                updateMap.put(qCtlEvent.taskId, workItemSession.getTaskId());
                boolean result = (eventDao.update(workspaceId, condition, updateMap) > 0);

                // Clear session.
                session.removeAttribute(SessionUtil.getOriginWorkItemDictionaryKey(workspaceId, workItemId));
                return result;
            } catch (JsonProcessingException e) {
                throw new EarthException(e);
            }
        });
    }

    private WorkItemDictionary getWorkItemDataStructure(String workItemId, String workspaceId,
                                                        TemplateAccessRight templateAccessRight) throws EarthException {
        return (WorkItemDictionary) this.executeTransaction(workspaceId, () -> {
            // Get WorkItem structure without Template.
            Map<WorkItem, List<String>> workItemMap = workItemDao.getWorkItemStructure(workspaceId, workItemId);
            Map.Entry<WorkItem, List<String>> entry = workItemMap.entrySet().iterator().next();
            WorkItem workItem = entry.getKey();

            // Get list of templates in workItem.
            List<String> templateIds = entry.getValue();
            Map<String, MgrTemplate> templates = templateDao.getTemplates(workspaceId, templateIds);

            // Get max history no of WorkItem.
            int lastHistoryNo = workItemDao.getMaxHistoryNo(workspaceId, workItemId);

            // Get list of data on templates.
            Map<String, TemplateData> templateDataMap = templateDao.getTemplateDataMap(workspaceId, workItemId,
                templates, lastHistoryNo);

            // Creating WorkItem dictionary.
            WorkItemDictionary workItemDics = createWorkItemDictionaries(workItem, templates, templateDataMap,
                templateAccessRight);

            return workItemDics;
        });
    }

    /**
     * Generate transaction token
     *
     * @param processServiceID
     * @param hostName
     * @param threadId
     * @return
     */
    private String generateTransactionToken(Integer processServiceID, String hostName, Long threadId) {
        StringBuilder stringBuilder = new StringBuilder(processServiceID);
        stringBuilder.append(hostName);
        stringBuilder.append(threadId);
        stringBuilder.append(DateUtil.getCurrentDate(Constant.DatePattern.DATE_FORMAT_YYYYMMDDHHMMSSSSS));
        return stringBuilder.toString();
    }

    /**
     * Get instance Mgr Template object (If only pass templateId)
     *
     * @param mgrTemplate
     * @param templateId
     * @param workspaceId
     * @return
     * @throws EarthException
     */
    private MgrTemplate getInstanceMgrTemplate(MgrTemplate mgrTemplate, String templateId, String workspaceId)
        throws EarthException {
        if (mgrTemplate == null && (EStringUtil.isNotEmpty(templateId))) {
            mgrTemplate = templateDao.getById(new TemplateKey(workspaceId, templateId));
            String templateField = mgrTemplate.getTemplateField();
            if (CollectionUtils.isEmpty(mgrTemplate.getTemplateFields())
                && (EStringUtil.isNotEmpty(templateField))) {
                try {
                    mgrTemplate.setTemplateFields(new ObjectMapper().readValue(templateField,
                        new TypeReference<List<Field>>() {
                        }));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return mgrTemplate;
    }

    /**
     * delete File
     *
     * @param pathFiles
     * @return
     */
    private void deleteFileAndDirectory(List<String> pathFiles) {
        if (CollectionUtils.isEmpty(pathFiles)) {
            return;
        }
        File file;
        Set<String> parentPaths = new HashSet<String>();
        for (String pathFile : pathFiles) {
            file = new File(pathFile);
            if (file.exists()) {
                parentPaths.add(file.getParent());
                file.delete();
            }
        }
        File directory;
        for (String parentPath : parentPaths) {
            directory = new File(parentPath);
            if (directory.exists()) {
                directory.delete();
            }
        }
    }
}
