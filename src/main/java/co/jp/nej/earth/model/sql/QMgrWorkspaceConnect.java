package co.jp.nej.earth.model.sql;

import co.jp.nej.earth.model.MgrWorkspaceConnect;
import co.jp.nej.earth.model.enums.ColumnNames;
import co.jp.nej.earth.model.enums.TableNames;
import com.querydsl.core.types.PathMetadataFactory;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.sql.ColumnMetadata;

public class QMgrWorkspaceConnect extends QBase<MgrWorkspaceConnect> {

    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = 1L;

    public final NumberPath<Integer> workspaceId
                                            = createNumber(ColumnNames.WORKSPACE_ID.toString(), Integer.class);
    public final StringPath schemaName = createString(ColumnNames.SCHEMA_NAME.toString());
    public final StringPath dbUser = createString(ColumnNames.DB_USER.toString());
    public final StringPath dbPassword = createString(ColumnNames.DB_PASSWORD.toString());
    public final StringPath owner = createString(ColumnNames.OWNER.toString());
    public final StringPath dbServer = createString(ColumnNames.DB_SERVER.toString());
    public final NumberPath<Integer> port = createNumber(ColumnNames.PORT.toString(), Integer.class);
    public final StringPath lastUpdateTime = createString(ColumnNames.LAST_UPDATE_TIME.toString());

    public static QMgrWorkspaceConnect newInstance() {
        return new QMgrWorkspaceConnect(QMgrWorkspaceConnect.class.getSimpleName(), null,
                TableNames.MGR_WORKSPACE_CONNECT.name());
    }

    public QMgrWorkspaceConnect(String path, String schema, String tableName) {
        super(MgrWorkspaceConnect.class, PathMetadataFactory.forVariable(path), schema, tableName);
        addMetadata();
    }

    protected void addMetadata() {
        addMetadata(workspaceId, ColumnMetadata.named(ColumnNames.WORKSPACE_ID.toString()));
        addMetadata(schemaName, ColumnMetadata.named(ColumnNames.SCHEMA_NAME.toString()));
        addMetadata(dbUser, ColumnMetadata.named(ColumnNames.DB_USER.toString()));
        addMetadata(dbPassword, ColumnMetadata.named(ColumnNames.DB_PASSWORD.toString()));
        addMetadata(owner, ColumnMetadata.named(ColumnNames.OWNER.toString()));
        addMetadata(dbServer, ColumnMetadata.named(ColumnNames.DB_SERVER.toString()));
        addMetadata(port, ColumnMetadata.named(ColumnNames.PORT.toString()));
        addMetadata(lastUpdateTime, ColumnMetadata.named(ColumnNames.LAST_UPDATE_TIME.toString()));
    }
}
