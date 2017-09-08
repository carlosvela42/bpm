package co.jp.nej.earth.model.sql;

import co.jp.nej.earth.model.entity.MgrTask;
import co.jp.nej.earth.model.enums.ColumnNames;
import co.jp.nej.earth.model.enums.TableNames;
import com.querydsl.core.types.PathMetadataFactory;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.sql.ColumnMetadata;

public class QMgrTask extends QBase<MgrTask> {

    /**
     * serial number
     */
    private static final long serialVersionUID = 1L;

    public final StringPath taskId = createString(ColumnNames.TASK_ID.toString());
    public final StringPath taskName = createString(ColumnNames.TASK_NAME.toString());
    public final StringPath customTaskId = createString(ColumnNames.CUSTOM_TASK_ID.toString());
    public final StringPath processId = createString(ColumnNames.PROCESS_ID.toString());
    public final StringPath lastUpdateTime = createString(ColumnNames.LAST_UPDATE_TIME.toString());

    public static QMgrTask newInstance() {
        return new QMgrTask(QMgrTask.class.getSimpleName(), null, TableNames.MGR_TASK.name());
    }

    public QMgrTask(String path, String schema, String tableName) {
        super(MgrTask.class, PathMetadataFactory.forVariable(path), schema, tableName);
        addMetadata();
    }

    protected void addMetadata() {
        addMetadata(processId, ColumnMetadata.named(ColumnNames.PROCESS_ID.toString()));
        addMetadata(taskId, ColumnMetadata.named(ColumnNames.TASK_ID.toString()));
        addMetadata(customTaskId, ColumnMetadata.named(ColumnNames.CUSTOM_TASK_ID.toString()));
        addMetadata(taskName, ColumnMetadata.named(ColumnNames.TASK_NAME.toString()));
        addMetadata(lastUpdateTime, ColumnMetadata.named(ColumnNames.LAST_UPDATE_TIME.toString()));
    }
}
