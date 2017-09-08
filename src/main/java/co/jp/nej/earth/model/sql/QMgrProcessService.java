package co.jp.nej.earth.model.sql;

import co.jp.nej.earth.model.entity.MgrProcessService;
import co.jp.nej.earth.model.enums.ColumnNames;
import co.jp.nej.earth.model.enums.TableNames;
import com.querydsl.core.types.PathMetadataFactory;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.sql.ColumnMetadata;

public class QMgrProcessService extends QBase<MgrProcessService> {

    private static final long serialVersionUID = 1L;

    public final NumberPath<Integer> processIServiceId = createNumber(
            ColumnNames.PROCESS_ISERVICEID.toString(), Integer.class);
    public final NumberPath<Integer> workspaceId = createNumber(
            ColumnNames.WORKSPACE_ID.toString(), Integer.class);
    public final StringPath processIServiceName = createString(ColumnNames.PROCESS_ISERVICENAME.toString());
    public final StringPath lastUpdateTime = createString(ColumnNames.LAST_UPDATE_TIME.toString());

    public static QMgrProcessService newInstance() {
        return new QMgrProcessService(QMgrProcessService.class.getSimpleName(), null,
                TableNames.MGR_PROCESS_SERVICE.name());
    }

    public QMgrProcessService(String path, String schema, String tableName) {
        super(MgrProcessService.class, PathMetadataFactory.forVariable(path), schema, tableName);
        addMetadata();
    }

    protected void addMetadata() {
        addMetadata(processIServiceId, ColumnMetadata.named(ColumnNames.PROCESS_ISERVICEID.toString()));
        addMetadata(processIServiceName, ColumnMetadata.named(ColumnNames.PROCESS_ISERVICENAME.toString()));
        addMetadata(workspaceId, ColumnMetadata.named(ColumnNames.WORKSPACE_ID.toString()));
        addMetadata(lastUpdateTime, ColumnMetadata.named(ColumnNames.LAST_UPDATE_TIME.toString()));
    }
}
