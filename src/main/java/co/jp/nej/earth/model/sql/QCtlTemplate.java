package co.jp.nej.earth.model.sql;

import com.querydsl.core.types.PathMetadataFactory;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.sql.ColumnMetadata;

import co.jp.nej.earth.model.entity.CtlTemplate;
import co.jp.nej.earth.model.enums.ColumnNames;
import co.jp.nej.earth.model.enums.TableNames;

public class QCtlTemplate extends QBase<CtlTemplate> {

    /**
     * @author p-tvo-thuynd
     */
    private static final long serialVersionUID = 1L;

    public final StringPath userId = createString(ColumnNames.USER_ID.toString());
    public final StringPath templateId = createString(ColumnNames.TEMPLATE_ID.toString());
    public final StringPath accessAuthority = createString(ColumnNames.ACCESS_AUTHORITY.toString());
    public final StringPath lastUpdateTime = createString(ColumnNames.LAST_UPDATE_TIME.toString());

    public static QCtlTemplate newInstance() {
        return new QCtlTemplate(QCtlTemplate.class.getSimpleName(), null, TableNames.CTL_TEMPLATE.name());
    }

    public QCtlTemplate(String path, String schema, String table) {
        super(CtlTemplate.class, PathMetadataFactory.forVariable(path), schema, table);
        addMetadata();
    }

    protected void addMetadata() {
        addMetadata(userId, ColumnMetadata.named(ColumnNames.USER_ID.toString()));
        addMetadata(templateId, ColumnMetadata.named(ColumnNames.TEMPLATE_ID.toString()));
        addMetadata(accessAuthority, ColumnMetadata.named(ColumnNames.ACCESS_AUTHORITY.toString()));
        addMetadata(lastUpdateTime, ColumnMetadata.named(ColumnNames.LAST_UPDATE_TIME.toString()));
    }
}
