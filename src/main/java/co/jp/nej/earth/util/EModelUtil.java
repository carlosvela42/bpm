package co.jp.nej.earth.util;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.sql.RelationalPathBase;
import com.querydsl.sql.SQLBindings;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.model.Field;
import co.jp.nej.earth.model.TemplateDataKey;
import co.jp.nej.earth.model.entity.MgrTemplate;
import co.jp.nej.earth.model.enums.DatabaseType;
import co.jp.nej.earth.model.enums.TemplateType;

public class EModelUtil {
    private static final Logger LOG = LoggerFactory.getLogger(EModelUtil.class);

    @SuppressWarnings("rawtypes")
    public static <T> Predicate buildCondition(Map<Path<?>, Object> keyMap, RelationalPathBase<T> qObj,
                                               boolean withTable) {

        if (keyMap == null || keyMap.size() == 0) {
            return null;
        }

        Class<?> entityClass = qObj.getClass();

        String objName = withTable ? qObj.getTableName() : entityClass.getSimpleName();

        @SuppressWarnings("unchecked")
        PathBuilder<T> entityPath = new PathBuilder(entityClass, objName);

        Iterator<Entry<Path<?>, Object>> it = keyMap.entrySet().iterator();

        BooleanBuilder condition = new BooleanBuilder();
        while (it.hasNext()) {

            Map.Entry<Path<?>, Object> pair = (Map.Entry<Path<?>, Object>) it.next();

            Path<?> colName = pair.getKey();
            Object colValue = pair.getValue();

            Predicate predicate = null;
            if (colValue == null) {
                predicate = entityPath.get(colName).isNull();
            } else if (colValue instanceof String) {
                colValue = ((String) colValue).trim();
                StringExpression express = entityPath.get((StringPath) colName).trim();
                predicate = express.eq(Expressions.asSimple(colValue));
            } else {
                predicate = entityPath.get(colName).eq(Expressions.asSimple(colValue));
            }

            condition.and(predicate);
        }

        return condition.getValue();
    }

    public static String cleanQuery(SQLBindings bindings) {
        String query = bindings.getSQL();
        String tmp = StringUtils.EMPTY;
        for (Object binding : bindings.getBindings()) {

            if (binding instanceof String) {
                tmp = "'" + binding.toString() + "'";
            } else {
                tmp = binding.toString();
            }
            query = query.replaceFirst("\\?", tmp);
        }
        return query;
    }

    public static String createTemplateDataSql(
            DatabaseType type, String workItemId, Map<String, MgrTemplate> templates, int maxHistoryNo) {
        LOG.info("Create SQL statement.");
        // Get max number of fields in all templates.
        int maxColumns = getMaxColumnsTemplate(templates);

        // Build sql statement to select data of all the templates
        StringBuilder builder = new StringBuilder();
        Iterator<Entry<String, MgrTemplate>> it = templates.entrySet().iterator();
        int index = 0;
        while (it.hasNext()) {
            Entry<String, MgrTemplate> entry = it.next();
            String templateId = entry.getKey();
            MgrTemplate temp = entry.getValue();

            if (temp != null) {
                List<Field> fields = temp.getTemplateFields();
                StringBuilder tempBuild = new StringBuilder(" SELECT ");
                tempBuild.append(templateId + " AS TEMPLATE_KEY,");
                appendColumn(tempBuild, "WORKITEMID");
                if (TemplateType.isProcess(Integer.parseInt(temp.getTemplateType()))) {
                    appendColumn(tempBuild, "PROCESSID");
                }

                if (TemplateType.isFolderItem(Integer.parseInt(temp.getTemplateType()))) {
                    appendColumn(tempBuild, "FOLDERITEMNO");
                }

                if (TemplateType.isDocument(Integer.parseInt(temp.getTemplateType()))) {
                    appendColumn(tempBuild, "FOLDERITEMNO");
                    appendColumn(tempBuild, "DOCUMENTNO");
                }

                if (TemplateType.isLayer(Integer.parseInt(temp.getTemplateType()))) {
                    appendColumn(tempBuild, "FOLDERITEMNO");
                    appendColumn(tempBuild, "DOCUMENTNO");
                    appendColumn(tempBuild, "LAYERNO");
                }

                appendColumn(tempBuild, "HISTORYNO");
                appendColumn(tempBuild, "LASTUPDATETIME");

                int notYetUsedColumns = maxColumns - TemplateDataKey.getNumTemplateKeys(temp.getTemplateType());
                for (int i = 0; i < notYetUsedColumns; i++) {
                    if (i < fields.size()) {
                        Field field = fields.get(i);
                        tempBuild.append(toVarchar(field.getName())).append(",");
                    } else {
                        tempBuild.append("null,");
                    }
                }

                builder.append(tempBuild.substring(0, tempBuild.length() - 1))
                       .append(" FROM ").append(temp.getTemplateTableName())
                       .append(" WHERE ").append("HISTORYNO='" + maxHistoryNo + "'")
                       .append(" AND ").append("WORKITEMID='" + workItemId + "'");

                if (index < templates.size() - 1) {
                    builder.append(" UNION ALL ");
                }
            }
            index++;
        }

        if (builder.length() > 0) {
            builder.append(" ORDER BY ").append("TEMPLATE_KEY ASC ");
        }

        LOG.info("SQL statement:" + builder.toString());
        return builder.toString();
    }

    private static StringBuilder appendColumn(StringBuilder builder, String columnName) {
        return builder.append(toVarchar(columnName)).append(" AS " + columnName).append(" ,");
    }

    private static int getMaxColumnsTemplate(Map<String, MgrTemplate> templates) {
        Iterator<Entry<String, MgrTemplate>> it = templates.entrySet().iterator();
        int maxColumns = 0;
        while (it.hasNext()) {
            MgrTemplate temp = it.next().getValue();
            int numberTemplateFields = temp.getTemplateFields().size();
            int numColumns = TemplateDataKey.getNumTemplateKeys(temp.getTemplateType()) + numberTemplateFields;
            if (maxColumns < numColumns) {
                maxColumns = numColumns;
            }
        }
        return maxColumns;
    }

    private static String toVarchar(String name) {
        return "CAST (" + name + " AS VARCHAR(255))";
    }


    public static String getWorkItemIndex(String workItemId) {
        return "WORKITEM_" + workItemId;
    }

    public static String getProcessIndex(String processId) {
        return "PROCESS_" + processId;
    }

    public static String getFolderItemIndex(String workItemId, String folderItemNo) {
        return "FOLDERITEM_" + workItemId + "_" + folderItemNo;
    }

    public static String getDocumentIndex(String workItemId, String folderItemNo, String documentNo) {
        return "DOCUMENT_" + workItemId + "_" + folderItemNo + "_" + documentNo;
    }

    public static String getLayerIndex(String workItemId, String folderItemNo, String documentNo, String layerNo) {
        return "LAYER_" + workItemId + "_" + folderItemNo + "_" + documentNo + "_" + layerNo;
    }

    public static String getMrgTemplateIndex(String templateId) {
        return "MGR_TEMPLATE_" + templateId;
    }

    public static <T> T clone(Object o, Class<T> classes) throws EarthException {
        try {
            String json = new ObjectMapper().writeValueAsString(o);
            return new ObjectMapper().readValue(json, classes);
        } catch (IOException e) {
            throw new EarthException(e);
        }
    }
}
