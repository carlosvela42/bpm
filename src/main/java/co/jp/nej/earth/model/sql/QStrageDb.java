package co.jp.nej.earth.model.sql;

import co.jp.nej.earth.model.StrageDb;
import co.jp.nej.earth.model.enums.ColumnNames;
import co.jp.nej.earth.model.enums.TableNames;
import com.querydsl.core.types.PathMetadataFactory;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.sql.ColumnMetadata;

/**
 * @author p-tvo-sonta
 */
public class QStrageDb extends QBase<StrageDb> {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public final NumberPath<Integer> processId = createNumber(ColumnNames.PROCESS_ID.toString(), Integer.class);
    public final StringPath schemaName = createString(ColumnNames.SCHEMA_NAME.toString());
    public final StringPath dbUser = createString(ColumnNames.DB_USER.toString());
    public final StringPath dbPassword = createString(ColumnNames.DB_PASSWORD.toString());
    public final StringPath owner = createString(ColumnNames.OWNER.toString());
    public final StringPath dbServer = createString(ColumnNames.DB_SERVER.toString());
    public final StringPath dbType = createString(ColumnNames.DB_TYPE.toString());
    public final StringPath dbPort = createString(ColumnNames.DB_PORT.toString());
    public final StringPath lastUpdateTime = createString(ColumnNames.LAST_UPDATE_TIME.toString());

    public static QStrageDb newInstance() {
        return new QStrageDb(QStrageDb.class.getSimpleName(), null, TableNames.MGR_STRAGE_DB.name());
    }

    public QStrageDb(String path, String schema, String table) {
        super(StrageDb.class, PathMetadataFactory.forVariable(path), schema, table);
        addMetadata();
    }

    protected void addMetadata() {
        addMetadata(processId, ColumnMetadata.named(ColumnNames.PROCESS_ID.toString()));
        addMetadata(schemaName, ColumnMetadata.named(ColumnNames.SCHEMA_NAME.toString()));
        addMetadata(dbUser, ColumnMetadata.named(ColumnNames.DB_USER.toString()));
        addMetadata(dbPassword, ColumnMetadata.named(ColumnNames.DB_PASSWORD.toString()));
        addMetadata(owner, ColumnMetadata.named(ColumnNames.OWNER.toString()));
        addMetadata(dbServer, ColumnMetadata.named(ColumnNames.DB_SERVER.toString()));
        addMetadata(lastUpdateTime, ColumnMetadata.named(ColumnNames.LAST_UPDATE_TIME.toString()));
    }

}
