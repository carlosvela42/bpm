package co.jp.nej.earth.dao;

import co.jp.nej.earth.ddl.CreateTableClause;
import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.manager.connection.ConnectionManager;
import co.jp.nej.earth.manager.connection.EarthQueryFactory;
import co.jp.nej.earth.model.DatProcess;
import co.jp.nej.earth.model.Document;
import co.jp.nej.earth.model.Field;
import co.jp.nej.earth.model.FolderItem;
import co.jp.nej.earth.model.Layer;
import co.jp.nej.earth.model.TemplateData;
import co.jp.nej.earth.model.TemplateDataKey;
import co.jp.nej.earth.model.TemplateKey;
import co.jp.nej.earth.model.WorkItem;
import co.jp.nej.earth.model.constant.Constant;
import co.jp.nej.earth.model.entity.MgrTemplate;
import co.jp.nej.earth.model.enums.ColumnNames;
import co.jp.nej.earth.model.enums.DatabaseType;
import co.jp.nej.earth.model.enums.TemplateType;
import co.jp.nej.earth.model.enums.Type;
import co.jp.nej.earth.model.sql.QBase;
import co.jp.nej.earth.model.sql.QCtlTemplate;
import co.jp.nej.earth.model.sql.QDatProcess;
import co.jp.nej.earth.model.sql.QDocument;
import co.jp.nej.earth.model.sql.QFolderItem;
import co.jp.nej.earth.model.sql.QLayer;
import co.jp.nej.earth.model.sql.QMgrTemplate;
import co.jp.nej.earth.model.sql.QWorkItem;
import co.jp.nej.earth.util.ConversionUtil;
import co.jp.nej.earth.util.CryptUtil;
import co.jp.nej.earth.util.EModelUtil;
import co.jp.nej.earth.util.EStringUtil;
import co.jp.nej.earth.web.form.SearchForm;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.sql.Configuration;
import com.querydsl.sql.SQLQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TemplateDaoImpl extends BaseDaoImpl<MgrTemplate> implements TemplateDao {
    private static final Logger LOG = LoggerFactory.getLogger(UserDaoImpl.class);
    private static final int ORDER_THREE = 3;
    private static final int ORDER_FIVE = 5;
    private static final int ORDER_FOUR = 4;
    private static final int SIZE = 20;
    private static final int SIZEDATE = 17;
    private static final String TYPENVARCHAR = "NVARCHAR2";

    @Autowired
    private DatabaseType databaseType;

    @Autowired
    private MstCodeDao mstCodeDao;

    public TemplateDaoImpl() throws Exception {
        super();
    }

    @Override
    public MgrTemplate getTemplate(String workspaceId, String templateId) throws EarthException {
        try {
            QMgrTemplate qMgrTemplate = QMgrTemplate.newInstance();
            QBean<MgrTemplate> selectList = Projections.bean(MgrTemplate.class, qMgrTemplate.all());
            MgrTemplate template = ConnectionManager.getEarthQueryFactory(workspaceId)
                .select(selectList).from(qMgrTemplate)
                .where(qMgrTemplate.templateId.eq(templateId)).fetchOne();

            if (template != null) {
                template.addTemplateFields(template.getTemplateField());
            }

            return template;
        } catch (Exception e) {
            throw new EarthException(e);
        }
    }

    @Override
    public Map<String, MgrTemplate> getTemplates(String workspaceId, List<String> templateIds) throws EarthException {
        try {
            QMgrTemplate qMgrTemplate = QMgrTemplate.newInstance();
            QBean<MgrTemplate> selectList = Projections.bean(MgrTemplate.class, qMgrTemplate.all());

            SQLQuery<MgrTemplate> query = ConnectionManager.getEarthQueryFactory(workspaceId).select(selectList)
                .from(qMgrTemplate).where(qMgrTemplate.templateId.in(templateIds));

            LOG.info(query.getSQL().getSQL());
            List<MgrTemplate> templates = query.fetch();

            Map<String, MgrTemplate> tempMap = new LinkedHashMap<>();
            for (MgrTemplate temp : templates) {
                temp.addTemplateFields(temp.getTemplateField());
                tempMap.put(temp.getTemplateId(), temp);
            }

            return tempMap;
        } catch (Exception ex) {
            throw new EarthException(ex);
        }
    }

    @Override
    public TemplateData getProcessTemplateData(String workspaceId, String processId
        , String workItemId, String templateId, int maxVersion) throws EarthException {
        try {
            return ConnectionManager.getEarthQueryFactory(workspaceId)
                .getTemplateData(getTemplate(workspaceId, templateId), processId, workItemId
                    , null, null, null, maxVersion);
        } catch (Exception ex) {
            throw new EarthException(ex);
        }
    }

    @Override
    public List<TemplateData> getTemplateDataList(String workspaceId, MgrTemplate mgrTemplate, BooleanBuilder condition,
                                                  Long offset, Long limit, List<OrderSpecifier<?>> orderBys)
        throws EarthException {
        try {
            return ConnectionManager.getEarthQueryFactory(workspaceId)
                .getTemplateDataList(mgrTemplate, condition, offset, limit, orderBys);
        } catch (Exception ex) {
            throw new EarthException(ex);
        }
    }


    @Override
    public TemplateData getWorkItemTemplateData(String workspaceId
        , String workItemId, String templateId, int maxVersion) throws EarthException {
        try {
            return ConnectionManager.getEarthQueryFactory(workspaceId)
                .getTemplateData(getTemplate(workspaceId, templateId),
                    null, workItemId, null, null, null, maxVersion);
        } catch (Exception ex) {
            throw new EarthException(ex);
        }
    }

    @Override
    public TemplateData getFolderItemTemplateData(String workspaceId, String workItemId, String folderItemNo
        , String templateId, int maxVersion) throws EarthException {
        try {
            return ConnectionManager.getEarthQueryFactory(workspaceId)
                .getTemplateData(getTemplate(workspaceId, templateId),
                    null, workItemId, folderItemNo, null, null, maxVersion);
        } catch (Exception ex) {
            throw new EarthException(ex);
        }
    }

    @Override
    public TemplateData getDocumentTemplateData(String workspaceId, String workItemId, String folderItemNo
        , String docNo, String templateId, int maxVersion) throws EarthException {
        try {
            return ConnectionManager.getEarthQueryFactory(workspaceId)
                .getTemplateData(getTemplate(workspaceId, templateId),
                    null, workItemId, folderItemNo, docNo, null, maxVersion);
        } catch (Exception ex) {
            throw new EarthException(ex);
        }
    }

    @Override
    public TemplateData getLayerTemplateData(String workspaceId, String workItemId, String folderItemNo, String docNo
        , String layerNo, String templateId, int maxVersion) throws EarthException {
        try {
            return ConnectionManager.getEarthQueryFactory(workspaceId)
                .getTemplateData(getTemplate(workspaceId, templateId)
                    , null, workItemId, folderItemNo, docNo, layerNo, maxVersion);
        } catch (Exception ex) {
            throw new EarthException(ex);
        }
    }

    @Override
    public long insertProcessTemplateData(String workspaceId, DatProcess process, int historyNo) throws EarthException {
        try {
            if ((process == null) || (EStringUtil.isEmpty(workspaceId))) {
                throw new EarthException("Invalid parameter workspaceId or process");
            }
            TemplateData templateData = process.getProcessData();
            if (templateData != null) {
                templateData.setHistoryNo(historyNo);
            }
            MgrTemplate mgrTemplate = process.getMgrTemplate();
            if ((mgrTemplate == null) && (EStringUtil.isNotEmpty(process.getTemplateId()))) {
                mgrTemplate =  this.getById(new TemplateKey(workspaceId, process.getTemplateId()));
            }
            return ConnectionManager.getEarthQueryFactory(workspaceId)
                .insertTemplateData(mgrTemplate, process.getProcessData()
                    , Integer.toString(process.getProcessId()), process.getWorkitemId()
                    , null, null, null);
        } catch (Exception ex) {
            throw new EarthException(ex);
        }
    }

    @Override
    public long insertWorkItemTemplateData(WorkItem workItem) throws EarthException {
        try {
            if ((workItem == null) || (EStringUtil.isEmpty(workItem.getWorkspaceId()))) {
                throw new EarthException("Invalid parameter workItem");
            }
            TemplateData templateData = workItem.getWorkItemData();
            if (templateData != null) {
                templateData.setHistoryNo(workItem.getLastHistoryNo());
            }
            MgrTemplate mgrTemplate = workItem.getMgrTemplate();
            if ((mgrTemplate == null) && (EStringUtil.isNotEmpty(workItem.getTemplateId()))) {
                mgrTemplate =  this.getById(new TemplateKey(workItem.getWorkspaceId(), workItem.getTemplateId()));
            }
            return ConnectionManager.getEarthQueryFactory(workItem.getWorkspaceId())
                .insertTemplateData(mgrTemplate, workItem.getWorkItemData(), null, workItem.getWorkitemId()
                    , null, null, null);
        } catch (Exception ex) {
            throw new EarthException(ex);
        }
    }

    @Override
    public long insertFolderItemTemplateData(String workspaceId, FolderItem folderItem, int historyNo)
        throws EarthException {
        try {
            return ConnectionManager.getEarthQueryFactory(workspaceId).insertTemplateData(folderItem.getMgrTemplate()
                , folderItem.getFolderItemData(), null, folderItem.getWorkitemId()
                , folderItem.getFolderItemNo(), null, null);
        } catch (Exception ex) {
            throw new EarthException(ex);
        }
    }

    @Override
    public long insertDocumentTemplateData(String workspaceId, Document document, int historyNo) throws EarthException {
        try {
            return ConnectionManager.getEarthQueryFactory(workspaceId).insertTemplateData(document.getMgrTemplate(),
                document.getDocumentData(), null, document.getWorkitemId(), document.getFolderItemNo(),
                document.getDocumentNo(), null);
        } catch (Exception ex) {
            throw new EarthException(ex);
        }
    }

    @Override
    public long insertLayerTemplateData(String workspaceId, Layer layer, int historyNo) throws EarthException {
        try {
            return ConnectionManager.getEarthQueryFactory(workspaceId).insertTemplateData(layer.getMgrTemplate()
                , layer.getLayerData(), null, layer.getWorkitemId()
                , layer.getFolderItemNo(), layer.getDocumentNo(), layer.getLayerNo());
        } catch (Exception ex) {
            throw new EarthException(ex);
        }
    }

    @Override
    public List<MgrTemplate> getAllByWorkspace(String workspaceId, String userId) throws EarthException {
        try {
            Map<String, String> templateType = mstCodeDao.getMstCodesBySection(Constant.MstCode.TEMPLATE_TYPE);
            QMgrTemplate qMgrTemplate = QMgrTemplate.newInstance();
            QCtlTemplate qCtlTemplate = QCtlTemplate.newInstance();
            QBean<MgrTemplate> selectList = Projections.bean(MgrTemplate.class, qMgrTemplate.all());
            List<MgrTemplate> mgrTemplates = ConnectionManager.getEarthQueryFactory(workspaceId)
                .select(selectList)
                .from(qMgrTemplate)
                .innerJoin(qCtlTemplate).on(qCtlTemplate.templateId.eq(qMgrTemplate.templateId))
                .orderBy(qMgrTemplate.templateId.asc())
                .where(qCtlTemplate.userId.eq(userId))
                .fetch();

            if (mgrTemplates != null && mgrTemplates.size() > 0) {
                for (MgrTemplate template : mgrTemplates) {
                    template.setTemplateType(templateType.get(template.getTemplateType()));
                    template.addTemplateFields(template.getTemplateField());
                }
            }

            return mgrTemplates;
        } catch (Exception ex) {
            throw new EarthException(ex);
        }
    }

    @Override
    public List<MgrTemplate> getAllByWorkspace(String workspaceId) throws EarthException {
        try {
            Map<String, String> templateType = mstCodeDao.getMstCodesBySection(Constant.MstCode.TEMPLATE_TYPE);
            QMgrTemplate qMgrTemplate = QMgrTemplate.newInstance();
            QBean<MgrTemplate> selectList = Projections.bean(MgrTemplate.class, qMgrTemplate.all());
            List<MgrTemplate> mgrTemplates = ConnectionManager.getEarthQueryFactory(workspaceId)
                .select(selectList)
                .from(qMgrTemplate)
                .orderBy(qMgrTemplate.templateId.asc())
                .fetch();
            if (mgrTemplates != null && mgrTemplates.size() > 0) {
                for (MgrTemplate template : mgrTemplates) {
                    template.setTemplateType(templateType.get(template.getTemplateType()));
                    template.addTemplateFields(template.getTemplateField());
                }
            }
            return mgrTemplates;
        } catch (Exception ex) {
            throw new EarthException(ex);
        }
    }

    @Override
    public MgrTemplate getById(TemplateKey templateKey) throws EarthException {
        try {
            QMgrTemplate qMgrTemplate = QMgrTemplate.newInstance();
            QBean<MgrTemplate> selectList = Projections.bean(MgrTemplate.class, qMgrTemplate.all());
            return ConnectionManager.getEarthQueryFactory(templateKey.getWorkspaceId())
                .select(selectList).from(qMgrTemplate)
                .where(qMgrTemplate.templateId.eq(templateKey.getTemplateId())).fetchOne();
        } catch (Exception ex) {
            throw new EarthException(ex);
        }
    }

    /**
     * Get List MgrTemplate by list id
     *
     * @param ids         List template id
     * @param workspaceId Workspace ID
     * @param type        Template type
     * @return List MgrTemplate if OK, otherwise throw EarthException
     */
    @Override
    public List<MgrTemplate> getByIdsAndType(String workspaceId, List<String> ids, TemplateType type) throws
        EarthException {
        try {
            QMgrTemplate qMgrTemplate = QMgrTemplate.newInstance();
            BooleanBuilder condition = new BooleanBuilder();
            Predicate predicate1 = qMgrTemplate.templateId.in(ids);
            condition.and(predicate1);
            Predicate predicate2 = qMgrTemplate.templateType.eq(String.valueOf(type.getValue()));
            condition.and(predicate2);

            List<OrderSpecifier<?>> orderList = new ArrayList<>();
            orderList.add(qMgrTemplate.templateId.asc());
            return this.search(workspaceId, condition, null, null, orderList, null);
        } catch (Exception ex) {
            throw new EarthException(ex);
        }
    }

    @Override
    public List<MgrTemplate> getTemplateByType(String workspaceId, String templateType) throws EarthException {
        try {
            QMgrTemplate qMgrTemplate = QMgrTemplate.newInstance();
            QBean<MgrTemplate> selectList = Projections.bean(MgrTemplate.class, qMgrTemplate.all());
            return ConversionUtil.castList(ConnectionManager.getEarthQueryFactory(workspaceId)
                .select(selectList)
                .from(qMgrTemplate)
                .where(qMgrTemplate.templateType.eq(templateType))
                .orderBy(qMgrTemplate.templateId.asc())
                .fetch(), MgrTemplate.class);
        } catch (Exception ex) {
            throw new EarthException(ex);
        }
    }

    @Override
    public List<MgrTemplate> getTemplateByType(String workspaceId, String templateType, String userId)
        throws EarthException {
        try {
            QMgrTemplate qMgrTemplate = QMgrTemplate.newInstance();
            QBean<MgrTemplate> selectList = Projections.bean(MgrTemplate.class, qMgrTemplate.all());
            QCtlTemplate qCtlTemplate = QCtlTemplate.newInstance();
            return ConversionUtil.castList(ConnectionManager.getEarthQueryFactory(workspaceId)
                .select(selectList)
                .from(qMgrTemplate)
                .innerJoin(qCtlTemplate)
                .on(qCtlTemplate.templateId.eq(qMgrTemplate.templateId).and(qCtlTemplate.userId.eq(userId)))
                .where(qMgrTemplate.templateType.eq(templateType))
                .orderBy(qMgrTemplate.templateTableName.asc())
                .fetch(), MgrTemplate.class);
        } catch (Exception ex) {
            throw new EarthException(ex);
        }
    }

    @Override
    public TemplateData getProcessTemplateData(String workspaceId, String processId, String templateId, int maxVersion)
        throws EarthException {
        return ConnectionManager.getEarthQueryFactory(workspaceId)
            .getProcessTemplateData(getTemplate(workspaceId, templateId), processId, maxVersion);
    }

    @Override
    public long deleteTemplates(List<String> templateIds, String workspaceId) throws EarthException {
        List<Map<Path<?>, Object>> conditions = new ArrayList<>();
        QMgrTemplate qMgrTemplate = QMgrTemplate.newInstance();
        for (String templateId : templateIds) {
            Map<Path<?>, Object> condition = new HashMap<>();
            condition.put(qMgrTemplate.templateId, templateId);
            conditions.add(condition);
        }
        return deleteList(workspaceId, conditions);
    }

    @Override
    public long insertOne(String workspaceId, MgrTemplate mgrTemplate) throws EarthException {
        try {
            return add(workspaceId, mgrTemplate);
        } catch (Exception ex) {
            throw new EarthException(ex);
        }
    }

    @Override
    public long updateOne(String workspaceId, MgrTemplate mgrTemplate) throws EarthException {
        try {
            QMgrTemplate qMgrTemplate = QMgrTemplate.newInstance();
            Map<Path<?>, Object> condition = new HashMap<>();
            Map<Path<?>, Object> value = new HashMap<>();
            condition.put(qMgrTemplate.templateId, mgrTemplate.getTemplateId());
            value.put(qMgrTemplate.templateName, mgrTemplate.getTemplateName());
            value.put(qMgrTemplate.templateField, mgrTemplate.getTemplateField());
            return update(workspaceId, condition, value);
        } catch (Exception ex) {
            throw new EarthException(ex);
        }
    }

    @Override
    public long resetNodeItemUsingTemplateId(String workspaceId, List<String> templateIds) throws EarthException {
        EarthQueryFactory factory = ConnectionManager.getEarthQueryFactory(workspaceId);

        QWorkItem qWorkItem = QWorkItem.newInstance();
        QDatProcess qDatProcess = QDatProcess.newInstance();
        QFolderItem qFolderItem = QFolderItem.newInstance();
        QDocument qDocument = QDocument.newInstance();
        QLayer qLayer = QLayer.newInstance();

        List<QBase> qBeens = new ArrayList<>();
        qBeens.add(qWorkItem);
        qBeens.add(qDatProcess);
        qBeens.add(qFolderItem);
        qBeens.add(qDocument);
        qBeens.add(qLayer);

        long count = 0;
        for (QBase qBase : qBeens) {
            StringPath templateIdPath = qBase.getFieldString(ColumnNames.TEMPLATE_ID.toString());
            count = factory.update(qBase)
                .setNull(templateIdPath)
                .where(templateIdPath.in(templateIds))
                .execute();
        }

        return count;
    }


    @Override
    public long createTemplateData(String workspaceId, MgrTemplate mgrTemplate) throws EarthException {
        try {
            String tableName = mgrTemplate.getTemplateTableName();
            if (databaseType.isSqlServer()) {
                tableName = "dbo." + mgrTemplate.getTemplateTableName();
            }
            String primaryKey = "PK_LANGUAGE_" + mgrTemplate.getTemplateTableName();
            String columnPrimaryKey = "WORKITEMID,HISTORYNO";
            EarthQueryFactory earthQueryFactory = ConnectionManager.getEarthQueryFactory(workspaceId);
            Connection connection = ConnectionManager.getEarthQueryFactory(workspaceId).getConnection();
            Configuration configuration = earthQueryFactory.getConfiguration();
            CreateTableClause createTableClause = new CreateTableClause(
                connection, configuration,
                tableName, databaseType)
                .column("WORKITEMID", TYPENVARCHAR, databaseType).size(SIZE).notNull();
            if (TemplateType.isProcess(Integer.parseInt(mgrTemplate.getTemplateType()))) {
                createTableClause.column("PROCESSID", Integer.class).notNull();
                columnPrimaryKey = "WORKITEMID,PROCESSID,HISTORYNO";
            }
            if (TemplateType.isFolderItem(Integer.parseInt(mgrTemplate.getTemplateType()))) {
                createTableClause.column("FOLDERITEMNO", TYPENVARCHAR, databaseType).size(SIZE).notNull();
                columnPrimaryKey = "WORKITEMID,FOLDERITEMNO,HISTORYNO";
            }
            if (TemplateType.isDocument(Integer.parseInt(mgrTemplate.getTemplateType()))) {
                createTableClause.column("FOLDERITEMNO", TYPENVARCHAR, databaseType).size(SIZE).notNull()
                    .column("DOCUMENTNO", TYPENVARCHAR, databaseType).size(SIZE).notNull();
                columnPrimaryKey = "WORKITEMID,FOLDERITEMNO,DOCUMENTNO,HISTORYNO";
            }
            if (TemplateType.isLayer(Integer.parseInt(mgrTemplate.getTemplateType()))) {
                createTableClause.column("FOLDERITEMNO", TYPENVARCHAR, databaseType).size(SIZE).notNull()
                    .column("DOCUMENTNO", TYPENVARCHAR, databaseType).size(SIZE).notNull()
                    .column("LAYERNO", TYPENVARCHAR, databaseType).size(SIZE).notNull();
                columnPrimaryKey = "WORKITEMID,FOLDERITEMNO,DOCUMENTNO,LAYERNO,HISTORYNO";
            }
            createTableClause.column("HISTORYNO", Integer.class).notNull();
            List<Field> fields = mgrTemplate.getTemplateFields();

            for (Field field : fields) {
                Integer size = field.getSize();
//                if (field.getEncrypted()) {
//                    size= NumberUtil.sizeField(size);
//                }
                if (field.getType().equals(Constant.Template.FIELD_TYPTE_1)) {
                    createTableClause.column(field.getName(), Integer.class);
                } else if (field.getType().equals(Constant.Template.FIELD_TYPTE_2)) {
                    createTableClause.column(field.getName(), Long.class);
                } else if (field.getType().equals(Constant.Template.FIELD_TYPTE_3)) {
                    createTableClause.column(field.getName(), Type.getLabel(Constant.Template.FIELD_TYPTE_3),
                        databaseType).size(size);
                } else {
                    createTableClause.column(field.getName(), Type.getLabel(Constant.Template.FIELD_TYPTE_4),
                        databaseType).size(size);
                }
                if (field.getRequired()) {
                    createTableClause.notNull();
                }
            }
            String[] columns = columnPrimaryKey.split(",");
            createTableClause.column("LASTUPDATETIME", TYPENVARCHAR, databaseType).size(SIZEDATE).notNull()
                .primaryKey(primaryKey, columns);
            createTableClause.execute();
            return 0L;

        } catch (Exception ex) {
            throw new EarthException(ex);
        }
    }

    @Override
    public long isExistsTableData(String workspaceId, String templateTableName, String dbUser) throws EarthException {
        try {
            EarthQueryFactory earthQueryFactory = ConnectionManager.getEarthQueryFactory(workspaceId);
            DatabaseMetaData meta = earthQueryFactory.getConnection().getMetaData();
            ResultSet res = meta.getTables(null, null, templateTableName, null);
            while (res.next()) {
                return 1;
            }
            return 0L;
        } catch (Exception ex) {
            throw new EarthException(ex.getMessage());
        }
    }

    @Override
    public String getFieldJson(String workspaceId, SearchForm searchForm) throws EarthException {
        try {
            QMgrTemplate qMgrTemplate = QMgrTemplate.newInstance();
            String fieldJson = ConnectionManager.getEarthQueryFactory(workspaceId).select(qMgrTemplate.templateField)
                .from(qMgrTemplate)
                .where(qMgrTemplate.templateId.eq(searchForm.getTemplateId()).and(qMgrTemplate.templateTableName
                    .eq(searchForm.getTemplateTableName())
                    .and(qMgrTemplate.templateType.eq(searchForm.getTemplateType()))))
                .fetchOne();
            return fieldJson;
        } catch (Exception ex) {
            throw new EarthException(ex);
        }
    }

    @Override
    public Map<String, TemplateData> getTemplateDataMap(String workspaceId, String workItemId,
                                                        Map<String, MgrTemplate> templates, int maxHistoryNo)
        throws EarthException {
        Connection conn = null;
        Map<String, TemplateData> dataMap = new LinkedHashMap<>();
        try {
            String sql = EModelUtil.createTemplateDataSql(databaseType, workItemId, templates, maxHistoryNo);
            if (EStringUtil.isEmpty(sql)) {
                return dataMap;
            }

            conn = ConnectionManager.getEarthQueryFactory(workspaceId).getConnection();
            PreparedStatement pre = conn.prepareStatement(sql);
            ResultSet result = pre.executeQuery();
            while (result.next()) {
                String templateId = result.getString(1);
                MgrTemplate temp = templates.get(templateId);

                String dataKey = EStringUtil.EMPTY;

                TemplateData templateData = new TemplateData();

                Map<String, Object> fieldDataMap = new HashMap<>();

                int columnIndex = TemplateDataKey.getNumTemplateKeys(temp.getTemplateType());
                templateData.setHistoryNo(Integer.parseInt(result.getString(columnIndex - 1)));
                templateData.setLastUpdateTime(result.getString(columnIndex));

                if (TemplateType.isWorkItem(Integer.parseInt(temp.getTemplateType()))) {
                    dataKey = EModelUtil.getWorkItemIndex(workItemId);
                }

                if (TemplateType.isProcess(Integer.parseInt(temp.getTemplateType()))) {
                    String processId = result.getString(ORDER_THREE);
                    dataKey = EModelUtil.getProcessIndex(processId);
                }

                if (TemplateType.isFolderItem(Integer.parseInt(temp.getTemplateType()))) {
                    String folderItemNo = result.getString(ORDER_THREE);
                    dataKey = EModelUtil.getFolderItemIndex(workItemId, folderItemNo);
                }

                if (TemplateType.isDocument(Integer.parseInt(temp.getTemplateType()))) {
                    String folderItemNo = result.getString(ORDER_THREE);
                    String documentNo = result.getString(ORDER_FOUR);
                    dataKey = EModelUtil.getDocumentIndex(workItemId, folderItemNo, documentNo);
                }

                if (TemplateType.isLayer(Integer.parseInt(temp.getTemplateType()))) {
                    String folderItemNo = result.getString(ORDER_THREE);
                    String documentNo = result.getString(ORDER_FOUR);
                    String layerNo = result.getString(ORDER_FIVE);
                    dataKey = EModelUtil.getLayerIndex(workItemId, folderItemNo, documentNo, layerNo);
                }

                List<Field> fields = temp.getTemplateFields();
                for (Field field : fields) {
                    String fieldValue = result.getString(++columnIndex);
                    if (field.isEncrypted() && (!EStringUtil.isEmpty(fieldValue))) {
                        fieldDataMap.put(field.getName(), CryptUtil.decryptData(fieldValue));
                    } else {
                        fieldDataMap.put(field.getName(), fieldValue);
                    }
                }

                templateData.setDataMap(fieldDataMap);
                dataMap.put(dataKey, templateData);
            }
            ConnectionManager.commitConnection(conn);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            ConnectionManager.rollbackConnection(conn);
            throw new EarthException(e);
        }
        return dataMap;
    }

    @Override
    public String getTemplateIdByItemId(String workspaceId, String id, TemplateType type) throws EarthException {
        try {
            EarthQueryFactory query = ConnectionManager.getEarthQueryFactory(workspaceId);
            String templateId = EStringUtil.EMPTY;
            if (TemplateType.WORKITEM.equal(type)) {
                QWorkItem qWorkItem = QWorkItem.newInstance();
                templateId = query
                    .select(qWorkItem.templateId).from(qWorkItem)
                    .where(qWorkItem.workitemId.eq(id)).fetchOne();
            } else if (TemplateType.PROCESS.equal(type)) {
                QDatProcess qProcess = QDatProcess.newInstance();
                templateId = query
                    .select(qProcess.templateId).from(qProcess)
                    .where(qProcess.processId.eq(Integer.parseInt(id))).fetchOne();
            } else if (TemplateType.FOLDERITEM.equal(type)) {
                QFolderItem qFolderItem = QFolderItem.newInstance();
                templateId = query
                    .select(qFolderItem.templateId).from(qFolderItem)
                    .where(qFolderItem.folderItemNo.eq(id)).fetchOne();
            } else if (TemplateType.DOCUMENT.equal(type)) {
                QDocument qDoc = QDocument.newInstance();
                templateId = query
                    .select(qDoc.templateId).from(qDoc)
                    .where(qDoc.documentNo.eq(id)).fetchOne();
            } else if (TemplateType.LAYER.equal(type)) {
                QLayer qLayer = QLayer.newInstance();
                templateId = query
                    .select(qLayer.templateId).from(qLayer)
                    .where(qLayer.layerNo.eq(id)).fetchOne();
            }

            return templateId;
        } catch (Exception ex) {
            throw new EarthException(ex);
        }
    }

    @Override
    public String getTaskIdByItemId(String workspaceId, String id) throws EarthException {
        try {
            EarthQueryFactory query = ConnectionManager.getEarthQueryFactory(workspaceId);
            QWorkItem qWorkItem = QWorkItem.newInstance();
            return query
                .select(qWorkItem.taskId).from(qWorkItem)
                .where(qWorkItem.workitemId.eq(id)).fetchOne();
        } catch (Exception ex) {
            throw new EarthException(ex);
        }
    }

}