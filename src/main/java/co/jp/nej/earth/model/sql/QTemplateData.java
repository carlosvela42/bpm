package co.jp.nej.earth.model.sql;

import co.jp.nej.earth.model.Field;
import co.jp.nej.earth.model.TemplateData;
import co.jp.nej.earth.model.entity.MgrTemplate;
import co.jp.nej.earth.model.enums.ColumnNames;
import co.jp.nej.earth.model.enums.TemplateType;
import com.querydsl.core.types.PathMetadataFactory;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.sql.ColumnMetadata;

import java.util.List;

public class QTemplateData extends QBase<TemplateData> {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public final StringPath processId = createString(ColumnNames.PROCESS_ID.toString());
    public final StringPath workItemId = createString(ColumnNames.WORKITEM_ID.toString());
    public final StringPath folderItemNo = createString(ColumnNames.FOLDER_ITEM_NO.toString());
    public final StringPath documentNo = createString(ColumnNames.DOCUMENT_NO.toString());
    public final StringPath layerNo = createString(ColumnNames.LAYER_NO.toString());

    public final NumberPath<Integer> historyNo = createNumber(ColumnNames.HISTORY_NO.toString(), Integer.class);
    public final StringPath lastUpdateTime = createString(ColumnNames.LAST_UPDATE_TIME.toString());


    public BooleanExpression booleanExpression(String name) {
        return QTemplateData.newInstance(new MgrTemplate()).createBoolean(name);
    }

    public static QTemplateData newInstance(MgrTemplate template) {
        return new QTemplateData(QTemplateData.class.getSimpleName(), template);
    }

    public QTemplateData(String path, MgrTemplate template) {
        super(TemplateData.class, PathMetadataFactory.forVariable(path), null, template.getTemplateTableName());
        addMetadata(template.getTemplateFields(), template.getTemplateType());
    }

    protected void addMetadata(List<Field> fields, String templateType) {
        addMetadata(workItemId, ColumnMetadata.named(ColumnNames.WORKITEM_ID.toString()));

        if (TemplateType.isProcess(Integer.parseInt(templateType))) {
            addMetadata(processId, ColumnMetadata.named(ColumnNames.PROCESS_ID.toString()));
        }

        if (TemplateType.isFolderItem(Integer.parseInt(templateType))) {
            addMetadata(folderItemNo, ColumnMetadata.named(ColumnNames.FOLDER_ITEM_NO.toString()));
        }

        if (TemplateType.isDocument(Integer.parseInt(templateType))) {
            addMetadata(folderItemNo, ColumnMetadata.named(ColumnNames.FOLDER_ITEM_NO.toString()));
            addMetadata(documentNo, ColumnMetadata.named(ColumnNames.DOCUMENT_NO.toString()));
        }

        if (TemplateType.isLayer(Integer.parseInt(templateType))) {
            addMetadata(folderItemNo, ColumnMetadata.named(ColumnNames.FOLDER_ITEM_NO.toString()));
            addMetadata(documentNo, ColumnMetadata.named(ColumnNames.DOCUMENT_NO.toString()));
            addMetadata(layerNo, ColumnMetadata.named(ColumnNames.LAYER_NO.toString()));
        }
        addMetadata(historyNo, ColumnMetadata.named(ColumnNames.HISTORY_NO.toString()));

        for (Field field : fields) {
            StringPath cPath = createString(field.getName());
            addMetadata(cPath, ColumnMetadata.named(field.getName()).ofType(field.getType()));
        }
        addMetadata(lastUpdateTime, ColumnMetadata.named(ColumnNames.LAST_UPDATE_TIME.toString()));
    }
}