package co.jp.nej.earth.model.sql;

import co.jp.nej.earth.model.entity.StrDataDb;
import co.jp.nej.earth.model.enums.ColumnNames;
import co.jp.nej.earth.model.enums.TableNames;
import com.querydsl.core.types.PathMetadataFactory;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.sql.ColumnMetadata;

public class QStrDataDb extends QBase<StrDataDb> {

    private static final long serialVersionUID = 1L;

    public final StringPath workitemId = createString(ColumnNames.WORKITEM_ID.toString());
    public final StringPath folderItemNo = createString(ColumnNames.FOLDER_ITEM_NO.toString());
    public final StringPath documentNo = createString(ColumnNames.DOCUMENT_NO.toString());
    public final StringPath documentData = createString(ColumnNames.DOCUMENT_DATA.toString());

    public final StringPath lastUpdateTime = createString(ColumnNames.LAST_UPDATE_TIME.toString());

    public static QStrDataDb newInstance() {
        return new QStrDataDb(QStrDataDb.class.getSimpleName(), null, TableNames.STR_DATA_DB.name());
    }

    public QStrDataDb(String path, String schema, String table) {
        super(StrDataDb.class, PathMetadataFactory.forVariable(path), schema, table);
        addMetadata();
    }

    protected void addMetadata() {
        addMetadata(workitemId, ColumnMetadata.named(ColumnNames.WORKITEM_ID.toString()));
        addMetadata(folderItemNo, ColumnMetadata.named(ColumnNames.FOLDER_ITEM_NO.toString()));
        addMetadata(documentNo, ColumnMetadata.named(ColumnNames.DOCUMENT_NO.toString()));
        addMetadata(documentData, ColumnMetadata.named(ColumnNames.DOCUMENT_DATA.toString()));
        addMetadata(lastUpdateTime, ColumnMetadata.named(ColumnNames.LAST_UPDATE_TIME.toString()));
    }

}
