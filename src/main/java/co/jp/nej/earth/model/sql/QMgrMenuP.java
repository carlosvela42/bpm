package co.jp.nej.earth.model.sql;

import co.jp.nej.earth.model.entity.MgrMenuP;
import co.jp.nej.earth.model.enums.ColumnNames;
import co.jp.nej.earth.model.enums.TableNames;
import com.querydsl.core.types.PathMetadataFactory;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.sql.ColumnMetadata;

public class QMgrMenuP extends QBase<MgrMenuP> {

    /**
     * @author p-tvo-thuynd
     */
    private static final long serialVersionUID = 1L;

    public final StringPath functionId = createString(ColumnNames.FUNCTION_ID.toString());
    public final StringPath profileId = createString(ColumnNames.PROFILE_ID.toString());
    public final NumberPath<Integer> accessAuthority = createNumber(ColumnNames.ACCESS_AUTHORITY.toString(),
            Integer.class);
    public final StringPath lastUpdateTime = createString(ColumnNames.LAST_UPDATE_TIME.toString());

    public static QMgrMenuP newInstance() {
        return new QMgrMenuP(QMgrMenuP.class.getSimpleName(), null, TableNames.MGR_MENU_P.name());
    }

    public QMgrMenuP(String path, String schema, String table) {
        super(MgrMenuP.class, PathMetadataFactory.forVariable(path), schema, table);
        addMetadata();
    }

    protected void addMetadata() {
        addMetadata(functionId, ColumnMetadata.named(ColumnNames.FUNCTION_ID.toString()));
        addMetadata(profileId, ColumnMetadata.named(ColumnNames.PROFILE_ID.toString()));
        addMetadata(accessAuthority, ColumnMetadata.named(ColumnNames.ACCESS_AUTHORITY.toString()));
        addMetadata(lastUpdateTime, ColumnMetadata.named(ColumnNames.LAST_UPDATE_TIME.toString()));
    }
}
