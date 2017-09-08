package co.jp.nej.earth.model.sql;

import co.jp.nej.earth.model.DatProcess;
import co.jp.nej.earth.model.enums.ColumnNames;
import co.jp.nej.earth.model.enums.TableNames;
import com.querydsl.core.types.PathMetadataFactory;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.sql.ColumnMetadata;

public class QDatProcess extends QBase<DatProcess> {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public final NumberPath<Integer> processId = createNumber(ColumnNames.PROCESS_ID.toString(), Integer.class);
    public final StringPath workItemId = createString(ColumnNames.WORKITEM_ID.toString());
    public final StringPath templateId = createString(ColumnNames.TEMPLATE_ID.toString());
    public final StringPath lastUpdateTime = createString(ColumnNames.LAST_UPDATE_TIME.toString());

    public static QDatProcess newInstance() {
        return new QDatProcess(QDatProcess.class.getSimpleName(), null, TableNames.DAT_PROCESS.name());
    }

    public QDatProcess(String path, String schema, String tableName) {
        super(DatProcess.class, PathMetadataFactory.forVariable(path), schema, tableName);
        addMetadata();
    }

    protected void addMetadata() {
        addMetadata(processId, ColumnMetadata.named(ColumnNames.PROCESS_ID.toString()));
        addMetadata(workItemId, ColumnMetadata.named(ColumnNames.WORKITEM_ID.toString()));
        addMetadata(templateId, ColumnMetadata.named(ColumnNames.TEMPLATE_ID.toString()));
        addMetadata(lastUpdateTime, ColumnMetadata.named(ColumnNames.LAST_UPDATE_TIME.toString()));
    }
}
