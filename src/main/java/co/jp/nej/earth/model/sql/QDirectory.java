package co.jp.nej.earth.model.sql;

import co.jp.nej.earth.model.Directory;
import co.jp.nej.earth.model.enums.ColumnNames;
import co.jp.nej.earth.model.enums.TableNames;
import com.querydsl.core.types.PathMetadataFactory;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.sql.ColumnMetadata;

public class QDirectory extends QBase<Directory> {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public final NumberPath<Integer> dataDirectoryId = createNumber(ColumnNames.DATA_DIRECTORY_ID.toString(),
            Integer.class);
    public final StringPath folderPath = createString(ColumnNames.FOLDER_PATH.toString());
    public final NumberPath<Integer> newCreateFile = createNumber(ColumnNames.NEW_CREATE_FILE.toString(),
            Integer.class);
    public final StringPath reservedDiskVolSize = createString(ColumnNames.RESERVED_DISK_VOL_SIZE.toString());
    public final StringPath diskVolSize = createString(ColumnNames.DISK_VOL_SIZE.toString());
    public final StringPath lastUpdateTime = createString(ColumnNames.LAST_UPDATE_TIME.toString());

    public static QDirectory newInstance() {
        return new QDirectory(QDirectory.class.getSimpleName(), null, TableNames.MGR_DIRECTORY.name());
    }

    public QDirectory(String path, String schema, String tableName) {
        super(Directory.class, PathMetadataFactory.forVariable(path), schema, tableName);
        addMetadata();
    }

    protected void addMetadata() {
        addMetadata(dataDirectoryId, ColumnMetadata.named(ColumnNames.DATA_DIRECTORY_ID.toString()));
        addMetadata(folderPath, ColumnMetadata.named(ColumnNames.FOLDER_PATH.toString()));
        addMetadata(newCreateFile, ColumnMetadata.named(ColumnNames.NEW_CREATE_FILE.toString()));
        addMetadata(reservedDiskVolSize, ColumnMetadata.named(ColumnNames.RESERVED_DISK_VOL_SIZE.toString()));
        addMetadata(diskVolSize, ColumnMetadata.named(ColumnNames.DISK_VOL_SIZE.toString()));
        addMetadata(lastUpdateTime, ColumnMetadata.named(ColumnNames.LAST_UPDATE_TIME.toString()));
    }

}
