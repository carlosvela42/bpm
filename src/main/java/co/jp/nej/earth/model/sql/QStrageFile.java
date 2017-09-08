package co.jp.nej.earth.model.sql;

import co.jp.nej.earth.model.StrageFile;
import co.jp.nej.earth.model.enums.ColumnNames;
import co.jp.nej.earth.model.enums.TableNames;
import com.querydsl.core.types.PathMetadataFactory;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.sql.ColumnMetadata;

/**
 * @author p-tvo-sonta
 */
public class QStrageFile extends QBase<StrageFile> {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public final NumberPath<Integer> processId = createNumber(ColumnNames.PROCESS_ID.toString(), Integer.class);
    public final NumberPath<Integer> siteId = createNumber(ColumnNames.SITE_ID.toString(), Integer.class);
    public final StringPath siteManagementType = createString(ColumnNames.SITE_MANAGEMENT_TYPE.toString());
    public final StringPath lastUpdateTime = createString(ColumnNames.LAST_UPDATE_TIME.toString());

    public static QStrageFile newInstance() {
        return new QStrageFile(QStrageFile.class.getSimpleName(), null, TableNames.MGR_STRAGE_FILE.name());
    }

    public QStrageFile(String path, String schema, String table) {
        super(StrageFile.class, PathMetadataFactory.forVariable(path), schema, table);
        addMetadata();
    }

    protected void addMetadata() {
        addMetadata(processId, ColumnMetadata.named(ColumnNames.PROCESS_ID.toString()));
        addMetadata(siteId, ColumnMetadata.named(ColumnNames.SITE_ID.toString()));
        addMetadata(siteManagementType, ColumnMetadata.named(ColumnNames.SITE_MANAGEMENT_TYPE.toString()));
        addMetadata(lastUpdateTime, ColumnMetadata.named(ColumnNames.LAST_UPDATE_TIME.toString()));
    }

}