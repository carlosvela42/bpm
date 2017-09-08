package co.jp.nej.earth.model.sql;

import co.jp.nej.earth.model.MgrSchedule;
import co.jp.nej.earth.model.enums.ColumnNames;
import co.jp.nej.earth.model.enums.TableNames;
import com.querydsl.core.types.PathMetadataFactory;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.sql.ColumnMetadata;

public class QMgrSchedule extends QBase<MgrSchedule> {

    private static final long serialVersionUID = 1L;

    public final StringPath scheduleId = createString(ColumnNames.SCHEDULE_ID.toString());
    public final StringPath hostName = createString(ColumnNames.HOST_NAME.toString());
    public final StringPath processIServiceId = createString(ColumnNames.PROCESS_ISERVICEID.toString());
    public final StringPath taskId = createString(ColumnNames.TASK_ID.toString());
    public final StringPath startTime = createString(ColumnNames.START_TIME.toString());
    public final StringPath endTime = createString(ColumnNames.END_TIME.toString());
    public final StringPath enableDisable = createString(ColumnNames.ENABLEDISABLE.toString());
    public final StringPath nextRunDate = createString(ColumnNames.NEXT_RUN_DATE.toString());
    public final StringPath runIntervalDay = createString(ColumnNames.RUN_INTERVAL_DAY.toString());
    public final StringPath runIntervalHour = createString(ColumnNames.RUN_INTERVAL_HOUR.toString());
    public final StringPath runIntervalMinute = createString(ColumnNames.RUN_INTERVAL_MINUTE.toString());
    public final StringPath runIntervalSecond = createString(ColumnNames.RUN_INTERVAL_SECOND.toString());
    public final StringPath lastUpdateTime = createString(ColumnNames.LAST_UPDATE_TIME.toString());

    public static QMgrSchedule newInstance() {
        return new QMgrSchedule(QMgrSchedule.class.getSimpleName(), null, TableNames.MGR_SCHEDULE.name());
    }

    public QMgrSchedule(String path, String schema, String tableName) {
        super(MgrSchedule.class, PathMetadataFactory.forVariable(path), schema, tableName);
        addMetadata();
    }

    protected void addMetadata() {
        addMetadata(scheduleId, ColumnMetadata.named(ColumnNames.SCHEDULE_ID.toString()));
        addMetadata(taskId, ColumnMetadata.named(ColumnNames.TASK_ID.toString()));
        addMetadata(hostName, ColumnMetadata.named(ColumnNames.HOST_NAME.toString()));
        addMetadata(processIServiceId, ColumnMetadata.named(ColumnNames.PROCESS_ISERVICEID.toString()));
        addMetadata(startTime, ColumnMetadata.named(ColumnNames.START_TIME.toString()));
        addMetadata(endTime, ColumnMetadata.named(ColumnNames.END_TIME.toString()));
        addMetadata(enableDisable, ColumnMetadata.named(ColumnNames.ENABLE_DISABLE.toString()));
        addMetadata(nextRunDate, ColumnMetadata.named(ColumnNames.NEXT_RUN_DATE.toString()));
        addMetadata(runIntervalDay, ColumnMetadata.named(ColumnNames.RUN_INTERVAL_DAY.toString()));
        addMetadata(runIntervalHour, ColumnMetadata.named(ColumnNames.RUN_INTERVAL_HOUR.toString()));
        addMetadata(runIntervalMinute, ColumnMetadata.named(ColumnNames.RUN_INTERVAL_MINUTE.toString()));
        addMetadata(runIntervalSecond, ColumnMetadata.named(ColumnNames.RUN_INTERVAL_SECOND.toString()));
        addMetadata(lastUpdateTime, ColumnMetadata.named(ColumnNames.LAST_UPDATE_TIME.toString()));
    }
}
