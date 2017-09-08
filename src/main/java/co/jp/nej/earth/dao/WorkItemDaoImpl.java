package co.jp.nej.earth.dao;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.manager.connection.ConnectionManager;
import co.jp.nej.earth.manager.connection.EarthQueryFactory;
import co.jp.nej.earth.model.DatProcess;
import co.jp.nej.earth.model.Document;
import co.jp.nej.earth.model.FolderItem;
import co.jp.nej.earth.model.Layer;
import co.jp.nej.earth.model.WorkItem;
import co.jp.nej.earth.model.WorkItemListDTO;
import co.jp.nej.earth.model.constant.Constant;
import co.jp.nej.earth.model.enums.ColumnNames;
import co.jp.nej.earth.model.sql.QCtlEvent;
import co.jp.nej.earth.model.sql.QCtlTemplate;
import co.jp.nej.earth.model.sql.QDatProcess;
import co.jp.nej.earth.model.sql.QDocument;
import co.jp.nej.earth.model.sql.QFolderItem;
import co.jp.nej.earth.model.sql.QLayer;
import co.jp.nej.earth.model.sql.QMgrTask;
import co.jp.nej.earth.model.sql.QMgrTemplate;
import co.jp.nej.earth.model.sql.QStrDataFile;
import co.jp.nej.earth.model.sql.QWorkItem;
import co.jp.nej.earth.util.ConversionUtil;
import co.jp.nej.earth.util.DateUtil;
import co.jp.nej.earth.util.EStringUtil;
import com.querydsl.core.Tuple;
import com.querydsl.sql.SQLQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author p-tvo-khanhnv
 */
@Repository
public class WorkItemDaoImpl extends BaseDaoImpl<WorkItem> implements WorkItemDao {
    private static final String WORKITEM_TEMPLATE_ID = "workitem_template_id";
    private static final String PROCESS_TEMPLATE_ID = "process_template_id";
    private static final String FOLDERITEM_TEMPLATE_ID = "folderitem_template_id";
    private static final String DOCUMENT_TEMPLATE_ID = "document_template_id";
    private static final String LAYER_TEMPLATE_ID = "layer_template_id";

    private static final String WORKITEM_LAST_UPDATE_TIME = "workitem_last_update_time";
    private static final String PROCESS_LAST_UPDATE_TIME = "process_last_update_time";
    private static final String FOLDERITEM_LAST_UPDATE_TIME = "folderitem_last_update_time";
    private static final String DOCUMENT_LAST_UPDATE_TIME = "document_last_update_time";
    private static final String LAYER_LAST_UPDATE_TIME = "layer_last_update_time";
    private static final String LAYER_INSERT_DATE_TIME = "layer_insert_date_time";

    public WorkItemDaoImpl() throws Exception {
        super();
    }

    @Autowired
    private MstCodeDao mstCodeDao;

    @Override
    public Map<WorkItem, List<String>> getWorkItemStructure(String workspaceId, String workitemId)
        throws EarthException {

        List<String> templateIds = new ArrayList<>();

        // Get WorkItem result.
        ResultSet workItemResult = getWorkItemResult(workspaceId, workitemId);

        // Build WorkItem tree.
        WorkItem workItem = buildWorkItemTree(workitemId, workItemResult, templateIds);
        workItem.setWorkspaceId(workspaceId);

        Map<WorkItem, List<String>> workItemStructrue = new HashMap<>();
        workItemStructrue.put(workItem, templateIds);
        return workItemStructrue;
    }

    private WorkItem buildWorkItemTree(String workitemId, ResultSet result, List<String> templateIds)
        throws EarthException {
        WorkItem workItem = new WorkItem();
        try {
            // Folder folder = new Folder();
            FolderItem folderItem = new FolderItem();
            Document document = new Document();
            List<Layer> layers = new ArrayList<>();
            while (result.next()) {
                addTemplateIds(templateIds, result.getString(WORKITEM_TEMPLATE_ID));
                addTemplateIds(templateIds, result.getString(PROCESS_TEMPLATE_ID));
                addTemplateIds(templateIds, result.getString(DOCUMENT_TEMPLATE_ID));
                addTemplateIds(templateIds, result.getString(FOLDERITEM_TEMPLATE_ID));
                addTemplateIds(templateIds, result.getString(LAYER_TEMPLATE_ID));
                if (result.isFirst()) {
                    // Set WorkItem info.
                    workItem = new WorkItem(workitemId, result.getString(ColumnNames.TASK_ID.toString()),
                        result.getString(WORKITEM_TEMPLATE_ID),
                        result.getInt(ColumnNames.LAST_HISTORY_NO.toString()),
                        result.getString(WORKITEM_LAST_UPDATE_TIME));

                    DatProcess datProcess = new DatProcess(result.getInt(ColumnNames.PROCESS_ID.toString()), workitemId,
                        result.getString(PROCESS_TEMPLATE_ID), result.getString(PROCESS_LAST_UPDATE_TIME));
                    workItem.setDataProcess(datProcess);
                }

                String folderItemNo = result.getString(ColumnNames.FOLDER_ITEM_NO.toString());
                String documentNo = result.getString(ColumnNames.DOCUMENT_NO.toString());
                String folderItemTemplateId = result.getString(FOLDERITEM_TEMPLATE_ID);
                String docTemplateId = result.getString(DOCUMENT_TEMPLATE_ID);
                if ((!StringUtils.isEmpty(folderItem.getFolderItemNo()))
                    && (!folderItemNo.equals(folderItem.getFolderItemNo()))) {
                    folderItem.addDocument(document);
                    workItem.addFolderItem(folderItem);
                    folderItem = new FolderItem();
                    document = new Document();
                } else if ((!StringUtils.isEmpty(document.getDocumentNo()))
                    && (!documentNo.equals(document.getDocumentNo()))) {
                    folderItem.addDocument(document);
                    document = new Document();
                }

                if (!StringUtils.isEmpty(documentNo)) {
                    document.setFolderItemNo(folderItemNo);
                    document.setDocumentNo(documentNo);
                    document.setTemplateId(docTemplateId);
                    document.setWorkitemId(workitemId);
                    document.setDocumentPath(result.getString(ColumnNames.DOCUMENT_DATA_PATH.toString()));
                    document.setDocumentType(result.getString(ColumnNames.DOCUMENT_TYPE.toString()));
                    document.setViewInformation(result.getString(ColumnNames.VIEW_INFORMATION.toString()));
                    document.setPageCount(result.getInt(ColumnNames.PAGE_COUNT.toString()));
                    document.setLastUpdateTime(result.getString(DOCUMENT_LAST_UPDATE_TIME));
                    document.setDocumentOrder(
                        EStringUtil.parseInt(result.getString(ColumnNames.DOCUMENT_ORDER.toString())));
                }

                if (!StringUtils.isEmpty(folderItemNo)) {
                    folderItem.setFolderItemNo(folderItemNo);
                    folderItem.setWorkitemId(workitemId);
                    folderItem.setTemplateId(folderItemTemplateId);
                    folderItem.setLastUpdateTime(result.getString(FOLDERITEM_LAST_UPDATE_TIME));
                    folderItem.setFolderItemOrder(
                        EStringUtil.parseInt(result.getString(ColumnNames.FOLDER_ITEM_ORDER.toString())));
                }

                // Get Layer info.
                String layerNo = result.getString(ColumnNames.LAYER_NO.toString());
                if (!StringUtils.isEmpty(layerNo)) {
                    Layer layer = new Layer();
                    layer.setDocumentNo(documentNo);
                    layer.setLayerNo(layerNo);
                    layer.setWorkitemId(workitemId);
                    layer.setFolderItemNo(folderItemNo);
                    layer.setTemplateId(result.getString(LAYER_TEMPLATE_ID));
                    layer.setOwnerId(result.getString(ColumnNames.OWNER_ID.toString()));
                    layer.setAnnotations(result.getString(ColumnNames.ANNOTATIONS.toString()));
                    layer.setInsertDateTime(result.getString(LAYER_INSERT_DATE_TIME));
                    layer.setLastUpdateTime(result.getString(LAYER_LAST_UPDATE_TIME));
                    layer.setLayerName(result.getString(ColumnNames.LAYER_NAME.toString()));
                    layer.setLayerOrder(
                        EStringUtil.parseInt(result.getString(ColumnNames.LAYER_ORDER.toString())));
                    layers.add(layer);
                    document.addLayer(layer);
                }
            }

            folderItem.addDocument(document);
            workItem.addFolderItem(folderItem);
        } catch (SQLException e) {
            throw new EarthException(e);
        }

        return workItem;
    }

    private void addTemplateIds(List<String> templateIds, String id) {
        if (!EStringUtil.isEmpty(id) && !templateIds.contains(id)) {
            templateIds.add(id);
        }
    }

    private ResultSet getWorkItemResult(String workspaceId, String workitemId) throws EarthException {
        QWorkItem qWorkItem = QWorkItem.newInstance();
        QDatProcess qProcessMap = QDatProcess.newInstance();
        QFolderItem qFolderItem = QFolderItem.newInstance();
        QDocument qDocument = QDocument.newInstance();
        QLayer qLayer = QLayer.newInstance();
        QStrDataFile qStrDataFile = QStrDataFile.newInstance();

        EarthQueryFactory queryFactory = ConnectionManager.getEarthQueryFactory(workspaceId);
        SQLQuery<Tuple> sqlClause = queryFactory
            .select(
                // WorkItem info.
                qWorkItem.workitemId
                , qWorkItem.templateId.as(WORKITEM_TEMPLATE_ID)
                , qWorkItem.taskId
                , qWorkItem.lastHistoryNo
                , qWorkItem.lastUpdateTime.as(WORKITEM_LAST_UPDATE_TIME)

                // Process info.
                , qProcessMap.workItemId
                , qProcessMap.processId
                , qProcessMap.templateId.as(PROCESS_TEMPLATE_ID)
                , qProcessMap.lastUpdateTime.as(PROCESS_LAST_UPDATE_TIME)

                // FolderItem info.
                , qFolderItem.workitemId
                , qFolderItem.folderItemNo
                , qFolderItem.templateId.as(FOLDERITEM_TEMPLATE_ID)
                , qFolderItem.lastUpdateTime.as(FOLDERITEM_LAST_UPDATE_TIME)
                , qFolderItem.folderItemOrder

                // Document info.
                , qDocument.workitemId
                , qDocument.folderItemNo
                , qDocument.documentNo
                , qDocument.templateId.as(DOCUMENT_TEMPLATE_ID)
                , qDocument.pageCount
                , qDocument.viewInformation
                , qDocument.documentType
                , qDocument.lastUpdateTime.as(DOCUMENT_LAST_UPDATE_TIME)
                , qDocument.documentOder

                // Document path
                , qStrDataFile.documentDataPath

                // Layer info.
                , qLayer.workitemId
                , qLayer.folderItemNo
                , qLayer.documentNo
                , qLayer.layerNo
                , qLayer.layerName
                , qLayer.templateId.as(LAYER_TEMPLATE_ID)
                , qLayer.ownerId
                , qLayer.annotations
                , qLayer.lastUpdateTime.as(LAYER_LAST_UPDATE_TIME)
                , qLayer.insertDateTime.as(LAYER_INSERT_DATE_TIME)
                , qLayer.annotations
                , qLayer.layerOder
            )
            .from(qWorkItem)
            .leftJoin(qProcessMap)
            .on(qWorkItem.workitemId.eq(qProcessMap.workItemId))
            .leftJoin(qFolderItem)
            .on(qWorkItem.workitemId.eq(qFolderItem.workitemId))
            .leftJoin(qDocument)
            .on(qFolderItem.workitemId.eq(qDocument.workitemId)
                .and(qFolderItem.folderItemNo.eq(qDocument.folderItemNo)))
            .leftJoin(qStrDataFile)
            .on(qStrDataFile.workitemId.eq(qDocument.workitemId)
                .and(qStrDataFile.folderItemNo.eq(qDocument.folderItemNo))
                .and(qStrDataFile.documentNo.eq(qDocument.documentNo)))
            .leftJoin(qLayer)
            .on(qDocument.workitemId.eq(qLayer.workitemId)
                .and(qDocument.folderItemNo.eq(qLayer.folderItemNo))
                .and(qDocument.documentNo.eq(qLayer.documentNo)))
            .where(
                qWorkItem.workitemId.eq(workitemId))
            .orderBy(
                qWorkItem.workitemId.asc()
                , qFolderItem.folderItemOrder.asc()
                , qFolderItem.folderItemNo.asc()
                , qDocument.documentOder.asc()
                , qDocument.documentNo.asc()
                , qLayer.layerOder.asc()
                , qLayer.layerNo.asc());

        LOG.info(sqlClause.getSQL().getSQL());
        return sqlClause.getResults();
    }

    @Override
    public List<WorkItemListDTO> getWorkItemsByWorkspace(String workspaceId, List<String> workItemIds, String userId)
        throws EarthException {
        Map<String, String> statusEvent = mstCodeDao.getMstCodesBySection(Constant.MstCode.STATUS_EVENT);
        QWorkItem qWorkItem = QWorkItem.newInstance();
        QMgrTemplate qMgrTemplate = QMgrTemplate.newInstance();
        QCtlEvent qCtlEvent = QCtlEvent.newInstance();
        QCtlTemplate qCtlTemplate=QCtlTemplate.newInstance();
        QMgrTask qMgrTask = QMgrTask.newInstance();
        List<WorkItemListDTO> workItems = new ArrayList<>();
        try {
            EarthQueryFactory earthQueryFactory = ConnectionManager.getEarthQueryFactory(workspaceId);
            ResultSet resultSet = earthQueryFactory
                .select(qWorkItem.workitemId, qCtlEvent.status, qMgrTask.taskName, qMgrTemplate.templateId,
                    qMgrTemplate.templateName,qCtlEvent.eventId)
                .from(qWorkItem).innerJoin(qMgrTemplate).on(qWorkItem.templateId.eq(qMgrTemplate.templateId))
                .innerJoin(qCtlTemplate)
                .on(qCtlTemplate.templateId.eq(qWorkItem.templateId).and(qCtlTemplate.userId.eq(userId)))
                .innerJoin(qMgrTask)
                .on(qMgrTask.taskId.eq(qWorkItem.taskId))
                .leftJoin(qCtlEvent).on(qWorkItem.workitemId.eq(qCtlEvent.workitemId))
                .where(qWorkItem.workitemId.in(workItemIds)).orderBy(qWorkItem.workitemId.asc()).getResults();
            while (resultSet.next()) {
                WorkItemListDTO workItem = new WorkItemListDTO();
                workItem.setStatusLock(statusEvent.get(resultSet.getString(ColumnNames.STATUS.toString())));
                workItem.setWorkitemId(resultSet.getString(ColumnNames.WORKITEM_ID.toString()));
                workItem.setTaskName(resultSet.getString(ColumnNames.TASK_NAME.toString()));
                workItem.setTemplateId(resultSet.getString(ColumnNames.TEMPLATE_ID.toString()));
                workItem.setTemplateName(resultSet.getString(ColumnNames.TEMPLATE_NAME.toString()));
                workItem.setEventId(resultSet.getString(ColumnNames.EVENT_ID.toString()));
                workItems.add(workItem);
            }
            return workItems;
        } catch (Exception ex) {
            throw new EarthException(ex);
        }
    }

    public List<WorkItemListDTO> getWorkItemsWithoutTemplates(String workspaceId, List<String> workItemIds)
        throws EarthException {
        Map<String, String> statusEvent = mstCodeDao.getMstCodesBySection(Constant.MstCode.STATUS_EVENT);
        QWorkItem qWorkItem = QWorkItem.newInstance();
        QCtlEvent qCtlEvent = QCtlEvent.newInstance();
        QMgrTask qMgrTask = QMgrTask.newInstance();
        List<WorkItemListDTO> workItems = new ArrayList<>();
        try {
            EarthQueryFactory earthQueryFactory = ConnectionManager.getEarthQueryFactory(workspaceId);
            ResultSet resultSet = earthQueryFactory
                .select(qWorkItem.workitemId, qCtlEvent.status, qMgrTask.taskName, qCtlEvent.eventId)
                .from(qWorkItem).leftJoin(qMgrTask)
                .on(qMgrTask.taskId.eq(qWorkItem.taskId))
                .leftJoin(qCtlEvent).on(qWorkItem.workitemId.eq(qCtlEvent.workitemId))
                .where(qWorkItem.workitemId.in(workItemIds)).orderBy(qWorkItem.workitemId.asc()).getResults();
            while (resultSet.next()) {
                WorkItemListDTO workItem = new WorkItemListDTO();
                workItem.setStatusLock(statusEvent.get(resultSet.getString(ColumnNames.STATUS.toString())));
                workItem.setWorkitemId(resultSet.getString(ColumnNames.WORKITEM_ID.toString()));
                workItem.setTaskName(resultSet.getString(ColumnNames.TASK_NAME.toString()));
                workItem.setEventId(resultSet.getString(ColumnNames.EVENT_ID.toString()));
                workItems.add(workItem);
            }
            return workItems;
        } catch (Exception ex) {
            throw new EarthException(ex);
        }
    }

    @Override
    public List<String> getWorkItemIdsNotHaveTemplate(String workspaceId, Long offset, Long limit)
        throws EarthException {
        return ConversionUtil.castList(executeWithException(() -> {
            QWorkItem qWorkItem = QWorkItem.newInstance();
            long off = (offset == null || offset < 0) ? 0L : offset;
            long lim = (limit == null || limit <= 0) ? SEARCH_LIMIT_DEFAULT : limit;
            return ConnectionManager.getEarthQueryFactory(workspaceId).select(qWorkItem.workitemId)
                .from(qWorkItem)
                .offset(off)
                .where(qWorkItem.templateId.isNull().or(qWorkItem.templateId.isEmpty()))
                .limit(lim)
                .fetch();
        }), String.class);
    }

    @Override
    public long unlock(List<String> workItemId, String workspaceId) throws EarthException {
        try {
            QCtlEvent qCtlEvent = QCtlEvent.newInstance();
            return ConnectionManager.getEarthQueryFactory(workspaceId).delete(qCtlEvent)
                .where(qCtlEvent.workitemId.in(workItemId)).execute();
        } catch (Exception ex) {
            throw new EarthException(ex.getMessage());
        }
    }

    @Override
    public Integer getProcessIdByWorkItem(String workspaceId, String workitemId) throws EarthException {
        try {
            QDatProcess qDatProcess = QDatProcess.newInstance();
            return ConnectionManager.getEarthQueryFactory(workspaceId).select(qDatProcess.processId)
                .from(qDatProcess)
                .where(qDatProcess.workItemId.eq(workitemId)).fetchOne();
        } catch (Exception ex) {
            throw new EarthException(ex.getMessage());
        }
    }

    @Override
    public Integer getMaxHistoryNo(String workspaceId, String workitemId) throws EarthException {
        try {
            QWorkItem qWorkItem = QWorkItem.newInstance();
            Integer maxLastHistoryNo = ConnectionManager.getEarthQueryFactory(workspaceId)
                .select(qWorkItem.lastHistoryNo.max())
                .from(qWorkItem).where(qWorkItem.workitemId.eq(workitemId)).fetchOne();
            return maxLastHistoryNo == null ? 0 : maxLastHistoryNo;
        } catch (Exception ex) {
            throw new EarthException(ex.getMessage());
        }
    }

    /**
     * Update WorkItem
     *
     * @param workItem
     * @return
     * @throws EarthException
     */
    @Override
    public long updateWorkItem(WorkItem workItem) throws EarthException {
        if (workItem == null) {
            return 0;
        }
        String workspaceId = workItem.getWorkspaceId();
        if (EStringUtil.isEmpty(workspaceId)) {
            throw new EarthException("Invalid parameter workspace");
        }

        // Set last history no
        Integer lastHistoryNoDB = getMaxHistoryNo(workspaceId, workItem.getWorkitemId());
        workItem.setLastHistoryNo(++lastHistoryNoDB);
        workItem.setLastUpdateTime(DateUtil.getCurrentDateString());
        QWorkItem qWorkItem = QWorkItem.newInstance();
        return ConnectionManager.getEarthQueryFactory(workspaceId)
            .update(qWorkItem)
            .populate(workItem)
            .where(qWorkItem.workitemId.eq(workItem.getWorkitemId()))
            .execute();
    }

    @Override
    public boolean checkExistWorkItem(String workspaceId, String workItemId) throws EarthException {
        try {
            EarthQueryFactory earthQueryFactory = ConnectionManager.getEarthQueryFactory(workspaceId);
            QWorkItem qWorkItem = QWorkItem.newInstance();

            String result = earthQueryFactory.select(qWorkItem.workitemId)
                                                .from(qWorkItem)
                                                .where(qWorkItem.workitemId.eq(workItemId)).fetchOne();

            return (!EStringUtil.isEmpty(result));
        } catch (Exception ex) {
            throw new EarthException(ex);
        }
    }
}
