package co.jp.nej.earth.model.sql;

import co.jp.nej.earth.model.MgrProcess;
import co.jp.nej.earth.model.enums.ColumnNames;
import co.jp.nej.earth.model.enums.TableNames;
import com.querydsl.core.types.PathMetadataFactory;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.sql.ColumnMetadata;

/**
 * @author p-tvo-sonta
 */
public class QMgrProcess extends QBase<MgrProcess> {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public final NumberPath<Integer> processId = createNumber(ColumnNames.PROCESS_ID.toString(), Integer.class);
    public final StringPath processName = createString(ColumnNames.PROCESS_NAME.toString());
    public final NumberPath<Float> processVersion = createNumber(ColumnNames.PROCESS_VERSION.toString(), Float.class);
    public final StringPath description = createString(ColumnNames.DESCRIPTION.toString());
    public final StringPath processDefinition = createString(ColumnNames.PROCESS_DEFINITION.toString());
    public final StringPath documentDataSavePath = createString(ColumnNames.DOCUMENT_DATASAVE_PATH.toString());

    public final StringPath lastUpdateTime = createString(ColumnNames.LAST_UPDATE_TIME.toString());

    public static QMgrProcess newInstance() {
        return new QMgrProcess(QMgrProcess.class.getSimpleName(), null, TableNames.MGR_PROCESS.name());
    }

    public QMgrProcess(String path, String schema, String tableName) {
        super(MgrProcess.class, PathMetadataFactory.forVariable(path), schema, tableName);
        addMetadata();
    }

    protected void addMetadata() {
        addMetadata(processId, ColumnMetadata.named(ColumnNames.PROCESS_ID.toString()));
        addMetadata(processName, ColumnMetadata.named(ColumnNames.PROCESS_NAME.toString()));
        addMetadata(processVersion, ColumnMetadata.named(ColumnNames.PROCESS_VERSION.toString()));
        addMetadata(description, ColumnMetadata.named(ColumnNames.DESCRIPTION.toString()));
        addMetadata(processDefinition, ColumnMetadata.named(ColumnNames.PROCESS_DEFINITION.toString()));
        addMetadata(documentDataSavePath, ColumnMetadata.named(ColumnNames.DOCUMENT_DATASAVE_PATH.toString()));
        addMetadata(lastUpdateTime, ColumnMetadata.named(ColumnNames.LAST_UPDATE_TIME.toString()));
    }

}
