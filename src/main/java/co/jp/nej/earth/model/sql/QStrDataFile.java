package co.jp.nej.earth.model.sql;

import co.jp.nej.earth.model.entity.StrDataFile;
import co.jp.nej.earth.model.enums.ColumnNames;
import co.jp.nej.earth.model.enums.TableNames;
import com.querydsl.core.types.PathMetadataFactory;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.sql.ColumnMetadata;

public class QStrDataFile extends QBase<StrDataFile> {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    public final StringPath workitemId = createString(ColumnNames.WORKITEM_ID.toString());
    public final StringPath folderItemNo = createString(ColumnNames.FOLDER_ITEM_NO.toString());
    public final StringPath documentNo = createString(ColumnNames.DOCUMENT_NO.toString());
    public final StringPath documentDataPath = createString(ColumnNames.DOCUMENT_DATA_PATH.toString());

    public final StringPath lastUpdateTime = createString(ColumnNames.LAST_UPDATE_TIME.toString());

    public static QStrDataFile newInstance() {
        return new QStrDataFile(QStrDataFile.class.getSimpleName(), null, TableNames.STR_DATA_FILE.name());
    }

    public QStrDataFile(String path, String schema, String table) {
        super(StrDataFile.class, PathMetadataFactory.forVariable(path), schema, table);
        addMetadata();
    }

    protected void addMetadata() {
        addMetadata(workitemId, ColumnMetadata.named(ColumnNames.WORKITEM_ID.toString()));
        addMetadata(folderItemNo, ColumnMetadata.named(ColumnNames.FOLDER_ITEM_NO.toString()));
        addMetadata(documentNo, ColumnMetadata.named(ColumnNames.DOCUMENT_NO.toString()));
        addMetadata(documentDataPath, ColumnMetadata.named(ColumnNames.DOCUMENT_DATA_PATH.toString()));
        addMetadata(lastUpdateTime, ColumnMetadata.named(ColumnNames.LAST_UPDATE_TIME.toString()));
    }

}
