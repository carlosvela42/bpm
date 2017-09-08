package co.jp.nej.earth.model.sql;

import co.jp.nej.earth.model.Document;
import co.jp.nej.earth.model.enums.ColumnNames;
import co.jp.nej.earth.model.enums.TableNames;
import com.querydsl.core.types.PathMetadataFactory;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.sql.ColumnMetadata;

public class QDocument extends QBase<Document> {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public final StringPath workitemId = createString(ColumnNames.WORKITEM_ID.toString());
    public final StringPath folderItemNo = createString(ColumnNames.FOLDER_ITEM_NO.toString());
    public final StringPath documentNo = createString(ColumnNames.DOCUMENT_NO.toString());
    public final NumberPath<Integer> documentOder = createNumber(ColumnNames.DOCUMENT_ORDER.toString(), Integer.class);
    public final StringPath templateId = createString(ColumnNames.TEMPLATE_ID.toString());
    public final StringPath viewInformation = createString(ColumnNames.VIEW_INFORMATION.toString());
    public final NumberPath<Integer> pageCount = createNumber(ColumnNames.PAGE_COUNT.toString(), Integer.class);
    public final StringPath documentType = createString(ColumnNames.DOCUMENT_TYPE.toString());
    public final StringPath lastUpdateTime = createString(ColumnNames.LAST_UPDATE_TIME.toString());

    public static QDocument newInstance() {
        return new QDocument(QDocument.class.getSimpleName(), null, TableNames.DAT_DOCUMENT.name());
    }

    public QDocument(String path, String schema, String tableName) {
        super(Document.class, PathMetadataFactory.forVariable(path), schema, tableName);
        addMetadata();
    }

    protected void addMetadata() {
        addMetadata(workitemId, ColumnMetadata.named(ColumnNames.WORKITEM_ID.toString()));
        addMetadata(folderItemNo, ColumnMetadata.named(ColumnNames.FOLDER_ITEM_NO.toString()));
        addMetadata(documentNo, ColumnMetadata.named(ColumnNames.DOCUMENT_NO.toString()));
        addMetadata(documentOder, ColumnMetadata.named(ColumnNames.DOCUMENT_ORDER.toString()));
        addMetadata(templateId, ColumnMetadata.named(ColumnNames.TEMPLATE_ID.toString()));
        addMetadata(pageCount, ColumnMetadata.named(ColumnNames.PAGE_COUNT.toString()));
        addMetadata(viewInformation, ColumnMetadata.named(ColumnNames.VIEW_INFORMATION.toString()));
        addMetadata(documentType, ColumnMetadata.named(ColumnNames.DOCUMENT_TYPE.toString()));
        addMetadata(lastUpdateTime, ColumnMetadata.named(ColumnNames.LAST_UPDATE_TIME.toString()));
    }
}
