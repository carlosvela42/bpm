package co.jp.nej.earth.model.sql;

import co.jp.nej.earth.model.entity.MgrTemplateP;
import co.jp.nej.earth.model.enums.ColumnNames;
import co.jp.nej.earth.model.enums.TableNames;
import com.querydsl.core.types.PathMetadataFactory;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.sql.ColumnMetadata;

public class QMgrTemplateP extends QBase<MgrTemplateP> {

    /**
     * @author p-tvo-thuynd
     */
    private static final long serialVersionUID = 1L;

    public final StringPath profileId = createString(ColumnNames.PROFILE_ID.toString());
    public final StringPath templateId = createString(ColumnNames.TEMPLATE_ID.toString());
    public final NumberPath<Integer> accessAuthority = createNumber(ColumnNames.ACCESS_AUTHORITY.toString(),
            Integer.class);
    public final StringPath lastUpdateTime = createString(ColumnNames.LAST_UPDATE_TIME.toString());

    public static QMgrTemplateP newInstance() {
        return new QMgrTemplateP(MgrTemplateP.class.getSimpleName(), null, TableNames.MGR_TEMPLATE_P.name());
    }

    public QMgrTemplateP(String path, String schema, String table) {
        super(MgrTemplateP.class, PathMetadataFactory.forVariable(path), schema, table);
        addMetadata();
    }

    protected void addMetadata() {
        addMetadata(profileId, ColumnMetadata.named(ColumnNames.PROFILE_ID.toString()));
        addMetadata(templateId, ColumnMetadata.named(ColumnNames.TEMPLATE_ID.toString()));
        addMetadata(accessAuthority, ColumnMetadata.named(ColumnNames.ACCESS_AUTHORITY.toString()));
        addMetadata(lastUpdateTime, ColumnMetadata.named(ColumnNames.LAST_UPDATE_TIME.toString()));
    }
}
