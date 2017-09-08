package co.jp.nej.earth.model.sql;

import co.jp.nej.earth.model.entity.MgrCustomTask;
import co.jp.nej.earth.model.enums.ColumnNames;
import co.jp.nej.earth.model.enums.TableNames;
import com.querydsl.core.types.PathMetadataFactory;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.sql.ColumnMetadata;

public class QMgrCustomTask extends QBase<MgrCustomTask> {

    /**
     * serial number
     */
    private static final long serialVersionUID = 1L;

    public final StringPath customTaskId = createString(ColumnNames.CUSTOM_TASK_ID.toString());
    public final StringPath customTaskname = createString(ColumnNames.CUSTOM_TASK_NAME.toString());
    public final StringPath customTaskType = createString(ColumnNames.CUSTOM_TASK_TYPE.toString());
    public final StringPath className = createString(ColumnNames.CLASS_NAME.toString());
    public final StringPath lastUpdateTime = createString(ColumnNames.LAST_UPDATE_TIME.toString());

    public static QMgrCustomTask newInstance() {
        return new QMgrCustomTask(QMgrCustomTask.class.getSimpleName(), null, TableNames.MGR_CUSTOM_TASK.name());
    }

    public QMgrCustomTask(String path, String schema, String tableName) {
        super(MgrCustomTask.class, PathMetadataFactory.forVariable(path), schema, tableName);
        addMetadata();
    }

    protected void addMetadata() {
        addMetadata(customTaskId, ColumnMetadata.named(ColumnNames.CUSTOM_TASK_ID.toString()));
        addMetadata(customTaskname, ColumnMetadata.named(ColumnNames.CUSTOM_TASK_NAME.toString()));
        addMetadata(customTaskType, ColumnMetadata.named(ColumnNames.CUSTOM_TASK_TYPE.toString()));
        addMetadata(className, ColumnMetadata.named(ColumnNames.CLASS_NAME.toString()));
        addMetadata(lastUpdateTime, ColumnMetadata.named(ColumnNames.LAST_UPDATE_TIME.toString()));
    }
}
