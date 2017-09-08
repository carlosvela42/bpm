/**
 *
 */
package co.jp.nej.earth.manager.connection;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.model.Field;
import co.jp.nej.earth.model.TemplateData;
import co.jp.nej.earth.model.entity.MgrTemplate;
import co.jp.nej.earth.model.enums.ColumnNames;
import co.jp.nej.earth.model.enums.TemplateType;
import co.jp.nej.earth.model.sql.QDatProcess;
import co.jp.nej.earth.model.sql.QDocument;
import co.jp.nej.earth.model.sql.QFolderItem;
import co.jp.nej.earth.model.sql.QLayer;
import co.jp.nej.earth.model.sql.QTemplateData;
import co.jp.nej.earth.model.sql.QWorkItem;
import co.jp.nej.earth.util.ConversionUtil;
import co.jp.nej.earth.util.CryptUtil;
import co.jp.nej.earth.util.DateUtil;
import co.jp.nej.earth.util.EStringUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import com.querydsl.sql.Configuration;
import com.querydsl.sql.SQLQuery;
import com.querydsl.sql.SQLQueryFactory;
import com.querydsl.sql.dml.SQLInsertClause;
import org.springframework.util.CollectionUtils;

import javax.inject.Provider;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author p-tvo-khanhnv
 */
public class EarthQueryFactory extends SQLQueryFactory {

    public static final int INSERT_ERROR = -1;
    public static final long SEARCH_LIMIT_DEFAULT = 100L;

    /**
     * @param configuration
     */
    public EarthQueryFactory(Configuration configuration, Provider<Connection> provider) {
        super(configuration, provider);
    }

    @Override
    public SQLQuery<Tuple> select(Expression<?>... exprs) {
        return super.select(exprs);
    }

    public SQLQuery<Tuple> selectAll(Expression<?>... exprs) {

        return super.select(exprs);
    }

    /**
     * Get Template data information.
     *
     * @param template
     * @param processId
     * @param workItemId
     * @param folderItemNo
     * @param documentNo
     * @param layerNo
     * @param maxVersion
     */
    public TemplateData getTemplateData(MgrTemplate template, String processId, String workItemId, String folderItemNo,
                                        String documentNo, String layerNo, int maxVersion) throws EarthException {
        if (template == null || EStringUtil.isEmpty(template.getTemplateId())) {
            return null;
        }

        QTemplateData qTemplateData = QTemplateData.newInstance(template);
        QBean<TemplateData> selectList = Projections.bean(TemplateData.class, qTemplateData.all());

        Predicate whereClauses = null;
        if (TemplateType.isProcess(Integer.parseInt(template.getTemplateType()))) {
            whereClauses = qTemplateData.processId.eq(processId).and(qTemplateData.workItemId.eq(workItemId))
                .and(qTemplateData.historyNo.eq(maxVersion));
        }

        if (TemplateType.isWorkItem(Integer.parseInt(template.getTemplateType()))) {
            whereClauses = qTemplateData.workItemId.eq(workItemId).and(qTemplateData.historyNo.eq(maxVersion));
        }

        if (TemplateType.isFolderItem(Integer.parseInt(template.getTemplateType()))) {
            whereClauses = qTemplateData.workItemId.eq(workItemId).and(qTemplateData.folderItemNo.eq(folderItemNo))
                .and(qTemplateData.historyNo.eq(maxVersion));
        }

        if (TemplateType.isDocument(Integer.parseInt(template.getTemplateType()))) {
            whereClauses = qTemplateData.workItemId.eq(workItemId).and(qTemplateData.folderItemNo.eq(folderItemNo))
                .and(qTemplateData.documentNo.eq(documentNo)).and(qTemplateData.historyNo.eq(maxVersion));
        }

        if (TemplateType.isLayer(Integer.parseInt(template.getTemplateType()))) {
            whereClauses = qTemplateData.workItemId.eq(workItemId).and(qTemplateData.folderItemNo.eq(folderItemNo))
                .and(qTemplateData.documentNo.eq(documentNo)).and(qTemplateData.layerNo.eq(layerNo))
                .and(qTemplateData.historyNo.eq(maxVersion));
        }

        TemplateData templateData = null;
        try {
            ResultSet resultSet = select(selectList).from(qTemplateData).where(whereClauses).getResults();

            if (resultSet.next()) {
                Map<String, Object> dataMap = new HashMap<String, Object>();
                for (Field field : template.getTemplateFields()) {
                    Object columnValue = resultSet.getObject(field.getName());
                    dataMap.put(field.getName(), columnValue);
                }

                String lastUpdateTime = resultSet.getString(ColumnNames.LAST_UPDATE_TIME.toString());
                Integer historyNo = resultSet.getInt(ColumnNames.HISTORY_NO.toString());

                templateData = new TemplateData(historyNo, lastUpdateTime, dataMap);
            }
        } catch (SQLException e) {
            throw new EarthException(e);
        }

        return templateData;
    }


    /**
     * @param template
     * @return
     * @throws EarthException
     */
    public List<TemplateData> getTemplateDataList(MgrTemplate template, BooleanBuilder condition,
                                                  Long offset, Long limit,
                                                  List<OrderSpecifier<?>> orderBys) throws
        EarthException {
        if (template == null || EStringUtil.isEmpty(template.getTemplateId())) {
            return null;
        }

        QTemplateData qTemplateData = QTemplateData.newInstance(template);
        QBean<TemplateData> selectList = Projections.bean(TemplateData.class, qTemplateData.all());

        QWorkItem qWorkItem = QWorkItem.newInstance();
        QDatProcess qDatProcess = QDatProcess.newInstance();
        QDocument qDocument = QDocument.newInstance();
        QFolderItem qFolderItem = QFolderItem.newInstance();
        QLayer qLayer = QLayer.newInstance();

        String templateId = template.getTemplateId();
        List<TemplateData> templateDataList = new ArrayList<>();

        long off = (offset == null || offset < 0) ? 0L : offset.longValue();
        long lim = (limit == null || limit <= 0) ? SEARCH_LIMIT_DEFAULT : limit.longValue();
        try {
            ResultSet resultSet = select(selectList).from(qTemplateData)
                .innerJoin(qWorkItem)
                .on(qTemplateData.workItemId.eq(qWorkItem.workitemId),
                    qTemplateData.historyNo.eq(qWorkItem.lastHistoryNo))
                .offset(off)
                .orderBy(qTemplateData.workItemId.asc())
                .where(condition.getValue())
                .limit(lim)
                .getResults();


            while (resultSet.next()) {
                Map<String, Object> dataMap = new HashMap<String, Object>();
                for (Field field : template.getTemplateFields()) {
                    Object columnValue = resultSet.getObject(field.getName());
                    dataMap.put(field.getName(), columnValue);
                }
                dataMap.put(ColumnNames.WORKITEM_ID.toString(),
                    resultSet.getObject(ColumnNames.WORKITEM_ID.toString()));
                String lastUpdateTime = resultSet.getString(ColumnNames.LAST_UPDATE_TIME.toString());
                Integer historyNo = resultSet.getInt(ColumnNames.HISTORY_NO.toString());

                templateDataList.add(new TemplateData(historyNo, lastUpdateTime, dataMap));

            }
        } catch (SQLException e) {
            throw new EarthException(e);
        }
        return templateDataList;
    }


    /**
     * Get Template data information.
     *
     * @param template
     * @param processId
     * @param maxVersion
     */
    public TemplateData getProcessTemplateData(MgrTemplate template, String processId, int maxVersion)
        throws EarthException {
        if (template == null || EStringUtil.isEmpty(template.getTemplateId())) {
            return null;
        }

        QTemplateData qTemplateData = QTemplateData.newInstance(template);
        QBean<TemplateData> selectList = Projections.bean(TemplateData.class, qTemplateData.all());

        Predicate whereClauses = qTemplateData.processId.eq(processId).and(qTemplateData.historyNo.eq(maxVersion));
        TemplateData templateData = null;
        try {
            ResultSet resultSet = select(selectList).from(qTemplateData).where(whereClauses).getResults();

            if (resultSet.next()) {
                Map<String, Object> dataMap = new HashMap<String, Object>();
                for (Field field : template.getTemplateFields()) {
                    Object columnValue = resultSet.getObject(field.getName());
                    dataMap.put(field.getName(), columnValue);
                }

                String lastUpdateTime = resultSet.getString(ColumnNames.LAST_UPDATE_TIME.toString());
                Integer historyNo = resultSet.getInt(ColumnNames.HISTORY_NO.toString());

                templateData = new TemplateData(historyNo, lastUpdateTime, dataMap);
            }
        } catch (SQLException e) {
            throw new EarthException(e);
        }

        return templateData;
    }

    /**
     * Insert Template data information into Database.
     *
     * @param template
     * @param templateData
     * @param processId
     * @param workItemId
     * @param folderItemNo
     * @param documentNo
     * @param layerNo
     */
    public long insertTemplateData(MgrTemplate template, TemplateData templateData, String processId, String workItemId,
                                   String folderItemNo, String documentNo, String layerNo) throws EarthException {
        if (template == null || templateData == null) {
            return INSERT_ERROR;
        }

        String templateField = template.getTemplateField();
        if (CollectionUtils.isEmpty(template.getTemplateFields()) && (EStringUtil.isNotEmpty(templateField))) {
            try {
                template.setTemplateFields(new ObjectMapper().readValue(templateField,
                    new TypeReference<List<Field>>() {}));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (CollectionUtils.isEmpty(template.getTemplateFields())) {
            return INSERT_ERROR;
        }

        // Create Q object base on template
        QTemplateData qTemplateData = QTemplateData.newInstance(template);

        String templateType = template.getTemplateType();
        List<Object> values = new ArrayList<Object>();

        values.add(workItemId);
        if (TemplateType.isProcess(Integer.parseInt(templateType))) {
            values.add(processId);
        }

        if (TemplateType.isFolderItem(Integer.parseInt(templateType))) {
            values.add(folderItemNo);
        }

        if (TemplateType.isDocument(Integer.parseInt(templateType))) {
            values.add(folderItemNo);
            values.add(documentNo);
        }

        if (TemplateType.isLayer(Integer.parseInt(templateType))) {
            values.add(folderItemNo);
            values.add(documentNo);
            values.add(layerNo);
        }
        values.add(templateData.getHistoryNo());

        Map<String, Object> dataMap = templateData.getDataMap();

        for (Field field : template.getTemplateFields()) {
            String templateValue = ConversionUtil.castObject(dataMap.get(field.getName()), String.class);
            if (EStringUtil.isNotEmpty(templateValue) && (field.getEncrypted())) {
                try {
                    templateValue = CryptUtil.encryptData(templateValue);
                } catch (Exception e) {
                    throw new EarthException(e);
                }
            }
            values.add(templateValue);
        }
        values.add(DateUtil.getCurrentDateString());

        long count = insert(qTemplateData).columns(qTemplateData.all()).values(values.toArray()).execute();
        return count;
    }

    /**
     * Insert Template data information into Database.
     *
     * @param template
     * @param templateData
     * @param processId
     * @param workItemId
     * @param folderItemNo
     * @param documentNo
     * @param layerNo
     */
    public void insertBatchTemplateData(SQLInsertClause sqlInsertClause, MgrTemplate template,
                                        TemplateData templateData,
                                        String processId,
                                        String workItemId,
                                        String folderItemNo, String documentNo, String layerNo) throws EarthException {
        if (template == null || templateData == null) {
            throw new EarthException("Invalid parameter: template or template Data is null");
        }

        String templateType = template.getTemplateType();
        List<Object> values = new ArrayList<Object>();

        // Add WORKITEMID
        values.add(workItemId);

        // ===========================================//
        // Add FIELDDEFINITION1 -> FIELDDEFINITIONN
        // ===========================================//

        // Template data for process
        if (TemplateType.isProcess(Integer.parseInt(templateType))) {
            if (EStringUtil.isEmpty(processId) || (EStringUtil.isEmpty(workItemId))) {
                throw new EarthException("Invalid parameter: processId or workItemId is null");
            }
            values.add(processId);
        } else if (TemplateType.isFolderItem(Integer.parseInt(templateType))) {
            // Template data for Folder Item
            if (EStringUtil.isEmpty(folderItemNo) || (EStringUtil.isEmpty(workItemId))) {
                throw new EarthException("Invalid parameter: folderItemNo or workItemId is null");
            }
            values.add(folderItemNo);
        } else if (TemplateType.isDocument(Integer.parseInt(templateType))) {

            // Template data for Folder Document
            if ((EStringUtil.isEmpty(folderItemNo)) || (EStringUtil.isEmpty(workItemId))
                || (EStringUtil.isEmpty(folderItemNo))) {
                throw new EarthException("Invalid parameter: folderItemNo or workItemId is null");
            }
            values.add(folderItemNo);
            values.add(documentNo);
        }

        // Template data for Folder Layer
        if (TemplateType.isLayer(Integer.parseInt(templateType))) {
            if ((EStringUtil.isEmpty(folderItemNo)) || (EStringUtil.isEmpty(workItemId))
                || (EStringUtil.isEmpty(folderItemNo)) || (EStringUtil.isEmpty(layerNo))) {
                throw new EarthException("Invalid parameter: folderItemNo or workItemId is null");
            }
            values.add(folderItemNo);
            values.add(documentNo);
            values.add(layerNo);
        }

        // Set HISTORYNO
        values.add(templateData.getHistoryNo());

        Map<String, Object> dataMap = templateData.getDataMap();
        String templateField = template.getTemplateField();
        if (CollectionUtils.isEmpty(template.getTemplateFields()) && (EStringUtil.isNotEmpty(templateField))) {
            try {
                template.setTemplateFields(new ObjectMapper().readValue(templateField,
                    new TypeReference<List<Field>>() { }));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        for (Field field : template.getTemplateFields()) {
            String templateValue = ConversionUtil.castObject(dataMap.get(field.getName()), String.class);
            if (field.getEncrypted()) {
                try {
                    templateValue = CryptUtil.encryptData(templateValue);
                } catch (Exception e) {
                    throw new EarthException(e);
                }
            }
            values.add(templateValue);
        }

        // Add LAST UPDATE TIME
        values.add(DateUtil.getCurrentDateString());

        sqlInsertClause.values(values.toArray()).addBatch();
    }
}
