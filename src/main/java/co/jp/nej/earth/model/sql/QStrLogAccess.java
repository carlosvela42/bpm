package co.jp.nej.earth.model.sql;

import co.jp.nej.earth.model.entity.StrLogAccess;
import co.jp.nej.earth.model.enums.ColumnNames;
import co.jp.nej.earth.model.enums.TableNames;
import com.querydsl.core.types.PathMetadataFactory;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.sql.ColumnMetadata;

/**
 * Created by minhtv on 3/29/2017.
 */
public class QStrLogAccess extends QBase<StrLogAccess> {

    private static final long serialVersionUID = 1L;

    public final StringPath eventId = createString(ColumnNames.EVENT_ID.toString());
    public final StringPath processTime = createString(ColumnNames.PROCESS_TIME.toString());
    public final StringPath userId = createString(ColumnNames.USER_ID.toString());
    public final StringPath workitemId = createString(ColumnNames.WORKITEM_ID.toString());
    public final NumberPath<Integer> historyNo = createNumber(ColumnNames.HISTORY_NO.toString(), Integer.class);
    public final StringPath taskId = createString(ColumnNames.TASK_ID.toString());
    public final StringPath lastUpdateTime = createString(ColumnNames.LAST_UPDATE_TIME.toString());

    public static QStrLogAccess newInstance() {
        return new QStrLogAccess(QStrLogAccess.class.getSimpleName(), null, TableNames.STR_LOG_ACCESS.name());
    }

    public QStrLogAccess(String path, String schema, String table) {
        super(StrLogAccess.class, PathMetadataFactory.forVariable(path), schema, table);
        addMetadata();
    }

    protected void addMetadata() {
        addMetadata(eventId, ColumnMetadata.named(ColumnNames.EVENT_ID.toString()));
        addMetadata(processTime, ColumnMetadata.named(ColumnNames.PROCESS_TIME.toString()));
        addMetadata(userId, ColumnMetadata.named(ColumnNames.USER_ID.toString()));
        addMetadata(workitemId, ColumnMetadata.named(ColumnNames.WORKITEM_ID.toString()));
        addMetadata(historyNo, ColumnMetadata.named(ColumnNames.HISTORY_NO.toString()));
        addMetadata(taskId, ColumnMetadata.named(ColumnNames.TASK_ID.toString()));
        addMetadata(lastUpdateTime, ColumnMetadata.named(ColumnNames.LAST_UPDATE_TIME.toString()));
    }
}
