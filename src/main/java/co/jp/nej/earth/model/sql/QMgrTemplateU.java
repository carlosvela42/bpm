package co.jp.nej.earth.model.sql;

import co.jp.nej.earth.model.entity.MgrTemplateU;
import co.jp.nej.earth.model.enums.ColumnNames;
import co.jp.nej.earth.model.enums.TableNames;
import com.querydsl.core.types.PathMetadataFactory;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.sql.ColumnMetadata;

public class QMgrTemplateU extends QBase<MgrTemplateU> {

    /**
     * @author p-tvo-thuynd
     */
    private static final long serialVersionUID = 1L;

    public final StringPath userId = createString(ColumnNames.USER_ID.toString());
    public final StringPath templateId = createString(ColumnNames.TEMPLATE_ID.toString());
    public final NumberPath<Integer> accessAuthority = createNumber(ColumnNames.ACCESS_AUTHORITY.toString(),
            Integer.class);
    public final StringPath lastUpdateTime = createString(ColumnNames.LAST_UPDATE_TIME.toString());

    public static QMgrTemplateU newInstance() {
        return new QMgrTemplateU(MgrTemplateU.class.getSimpleName(), null, TableNames.MGR_TEMPLATE_U.name());
    }

    public QMgrTemplateU(String path, String schema, String table) {
        super(MgrTemplateU.class, PathMetadataFactory.forVariable(path), schema, table);
        addMetadata();
    }

    protected void addMetadata() {
        addMetadata(userId, ColumnMetadata.named(ColumnNames.USER_ID.toString()));
        addMetadata(templateId, ColumnMetadata.named(ColumnNames.TEMPLATE_ID.toString()));
        addMetadata(accessAuthority, ColumnMetadata.named(ColumnNames.ACCESS_AUTHORITY.toString()));
        addMetadata(lastUpdateTime, ColumnMetadata.named(ColumnNames.LAST_UPDATE_TIME.toString()));
    }
}
