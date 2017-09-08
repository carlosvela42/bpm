package co.jp.nej.earth.model.sql;

import co.jp.nej.earth.model.entity.CtlEvent;
import co.jp.nej.earth.model.enums.ColumnNames;
import co.jp.nej.earth.model.enums.TableNames;
import com.querydsl.core.types.PathMetadataFactory;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.sql.ColumnMetadata;

public class QCtlEvent extends QBase<CtlEvent> {

    private static final long serialVersionUID = 1L;

    public final StringPath eventId = createString(ColumnNames.EVENT_ID.toString());
    public final StringPath userId = createString(ColumnNames.USER_ID.toString());
    public final StringPath workitemId = createString(ColumnNames.WORKITEM_ID.toString());
    public final StringPath status = createString(ColumnNames.STATUS.toString());
    public final StringPath taskId = createString(ColumnNames.TASK_ID.toString());
    public final StringPath workitemData = createString(ColumnNames.WORKITEM_DATA.toString());
    public final StringPath transactionToken = createString(ColumnNames.TRANSACTION_TOKEN.toString());

    public final StringPath lastUpdateTime = createString(ColumnNames.LAST_UPDATE_TIME.toString());

    public static QCtlEvent newInstance() {
        return new QCtlEvent(QCtlEvent.class.getSimpleName(), null, TableNames.CTL_EVENT.name());
    }

    public QCtlEvent(String path, String schema, String table) {
        super(CtlEvent.class, PathMetadataFactory.forVariable(path), schema, table);
        addMetadata();
    }

    protected void addMetadata() {
        addMetadata(eventId, ColumnMetadata.named(ColumnNames.EVENT_ID.toString()));
        addMetadata(userId, ColumnMetadata.named(ColumnNames.USER_ID.toString()));
        addMetadata(workitemId, ColumnMetadata.named(ColumnNames.WORKITEM_ID.toString()));
        addMetadata(status, ColumnMetadata.named(ColumnNames.STATUS.toString()));
        addMetadata(taskId, ColumnMetadata.named(ColumnNames.TASK_ID.toString()));
        addMetadata(transactionToken, ColumnMetadata.named(ColumnNames.TRANSACTION_TOKEN.toString()));
        addMetadata(workitemData, ColumnMetadata.named(ColumnNames.WORKITEM_DATA.toString()));
        addMetadata(lastUpdateTime, ColumnMetadata.named(ColumnNames.LAST_UPDATE_TIME.toString()));
    }

}
