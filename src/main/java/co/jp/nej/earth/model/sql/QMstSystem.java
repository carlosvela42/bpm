package co.jp.nej.earth.model.sql;

import co.jp.nej.earth.model.entity.MstSystem;
import co.jp.nej.earth.model.enums.ColumnNames;
import co.jp.nej.earth.model.enums.TableNames;
import com.querydsl.core.types.PathMetadataFactory;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.sql.ColumnMetadata;

public class QMstSystem extends QBase<MstSystem> {
    private static final long serialVersionUID = 1L;

    //    public final NumberPath<Integer> id = createNumber("id", Integer.class);
    public final StringPath section = createString(ColumnNames.SECTION.toString());
    public final StringPath variableName = createString(ColumnNames.VARIABLE_NAME.toString());
    public final StringPath configValue = createString(ColumnNames.CONFIG_VALUE.toString());
    public final StringPath lastUpdateTime = createString(ColumnNames.LAST_UPDATE_TIME.toString());

    public static QMstSystem newInstance() {
        return new QMstSystem(QMstSystem.class.getSimpleName(), null, TableNames.MST_SYSTEM.name());
    }

    public QMstSystem(String path, String schema, String tableName) {
        super(MstSystem.class, PathMetadataFactory.forVariable(path), schema, tableName);
        addMetadata();
    }

    protected void addMetadata() {
        // do khách chưa chốt db nên tạm thời trường id trong bảng này được giả
        // định do đó ở trong Enum ColumnNames chưa được thêm vào
//        addMetadata(id, ColumnMetadata.named("id"));
        addMetadata(section, ColumnMetadata.named(ColumnNames.SECTION.toString()));
        addMetadata(variableName, ColumnMetadata.named(ColumnNames.VARIABLE_NAME.toString()));
        addMetadata(configValue, ColumnMetadata.named(ColumnNames.CONFIG_VALUE.toString()));
        addMetadata(lastUpdateTime, ColumnMetadata.named(ColumnNames.LAST_UPDATE_TIME.toString()));
    }
}
