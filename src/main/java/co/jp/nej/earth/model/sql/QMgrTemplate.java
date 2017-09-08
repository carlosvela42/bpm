package co.jp.nej.earth.model.sql;

import co.jp.nej.earth.model.entity.MgrTemplate;
import co.jp.nej.earth.model.enums.ColumnNames;
import co.jp.nej.earth.model.enums.TableNames;
import com.querydsl.core.types.PathMetadataFactory;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.sql.ColumnMetadata;

public class QMgrTemplate extends QBase<MgrTemplate> {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public final StringPath templateId = createString(ColumnNames.TEMPLATE_ID.toString());
    public final StringPath templateName = createString(ColumnNames.TEMPLATE_NAME.toString());
    public final StringPath templateTableName = createString(ColumnNames.TEMPLATE_TABLE_NAME.toString());
    public final StringPath templateField = createString(ColumnNames.TEMPLATE_FIELD.toString());
    public final StringPath templateType = createString(ColumnNames.TEMPLATE_TYPE.toString());
    public final StringPath lastUpdateTime = createString(ColumnNames.LAST_UPDATE_TIME.toString());

    public static QMgrTemplate newInstance() {
        return new QMgrTemplate(QMgrTemplate.class.getSimpleName(), null, TableNames.MGR_TEMPLATE.name());
    }

    public QMgrTemplate(String path, String schema, String tableName) {
        super(MgrTemplate.class, PathMetadataFactory.forVariable(path), schema, tableName);
        addMetadata();
    }

    protected void addMetadata() {
        addMetadata(templateId, ColumnMetadata.named(ColumnNames.TEMPLATE_ID.toString()));
        addMetadata(templateName, ColumnMetadata.named(ColumnNames.TEMPLATE_NAME.toString()));
        addMetadata(templateTableName, ColumnMetadata.named(ColumnNames.TEMPLATE_TABLE_NAME.toString()));
        addMetadata(templateField, ColumnMetadata.named(ColumnNames.TEMPLATE_FIELD.toString()));
        addMetadata(templateType, ColumnMetadata.named(ColumnNames.TEMPLATE_TYPE.toString()));
        addMetadata(lastUpdateTime, ColumnMetadata.named(ColumnNames.LAST_UPDATE_TIME.toString()));
    }
}
