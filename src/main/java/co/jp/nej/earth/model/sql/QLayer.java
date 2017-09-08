package co.jp.nej.earth.model.sql;

import co.jp.nej.earth.model.Layer;
import co.jp.nej.earth.model.enums.ColumnNames;
import co.jp.nej.earth.model.enums.TableNames;
import com.querydsl.core.types.PathMetadataFactory;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.sql.ColumnMetadata;

public class QLayer extends QBase<Layer> {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public final StringPath workitemId = createString(ColumnNames.WORKITEM_ID.toString());
    public final StringPath folderItemNo = createString(ColumnNames.FOLDER_ITEM_NO.toString());
    public final StringPath documentNo = createString(ColumnNames.DOCUMENT_NO.toString());
    public final StringPath layerNo = createString(ColumnNames.LAYER_NO.toString());
    public final NumberPath<Integer> layerOder = createNumber(ColumnNames.LAYER_ORDER.toString(), Integer.class);
    public final StringPath layerName = createString(ColumnNames.LAYER_NAME.toString());
    public final StringPath templateId = createString(ColumnNames.TEMPLATE_ID.toString());
    public final StringPath ownerId = createString(ColumnNames.OWNER_ID.toString());
    public final StringPath annotations = createString(ColumnNames.ANNOTATIONS.toString());
    public final StringPath insertDateTime = createString(ColumnNames.INSERT_DATE_TIME.toString());
    public final StringPath lastUpdateTime = createString(ColumnNames.LAST_UPDATE_TIME.toString());

    public static QLayer newInstance() {
        return new QLayer(QLayer.class.getSimpleName(), null, TableNames.DAT_LAYER.name());
    }

    public QLayer(String path, String schema, String tableName) {
        super(Layer.class, PathMetadataFactory.forVariable(path), schema, tableName);
        addMetadata();
    }

    protected void addMetadata() {
        addMetadata(workitemId, ColumnMetadata.named(ColumnNames.WORKITEM_ID.toString()));
        addMetadata(folderItemNo, ColumnMetadata.named(ColumnNames.FOLDER_ITEM_NO.toString()));
        addMetadata(documentNo, ColumnMetadata.named(ColumnNames.DOCUMENT_NO.toString()));
        addMetadata(layerOder, ColumnMetadata.named(ColumnNames.LAYER_ORDER.toString()));
        addMetadata(layerNo, ColumnMetadata.named(ColumnNames.LAYER_NO.toString()));
        addMetadata(layerName, ColumnMetadata.named(ColumnNames.LAYER_NAME.toString()));
        addMetadata(templateId, ColumnMetadata.named(ColumnNames.TEMPLATE_ID.toString()));
        addMetadata(ownerId, ColumnMetadata.named(ColumnNames.OWNER_ID.toString()));
        addMetadata(annotations, ColumnMetadata.named(ColumnNames.ANNOTATIONS.toString()));
        addMetadata(insertDateTime, ColumnMetadata.named(ColumnNames.INSERT_DATE_TIME.toString()));
        addMetadata(lastUpdateTime, ColumnMetadata.named(ColumnNames.LAST_UPDATE_TIME.toString()));
    }
}
