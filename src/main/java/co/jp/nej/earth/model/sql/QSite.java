package co.jp.nej.earth.model.sql;

import co.jp.nej.earth.model.Site;
import co.jp.nej.earth.model.enums.ColumnNames;
import co.jp.nej.earth.model.enums.TableNames;
import com.querydsl.core.types.PathMetadataFactory;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.sql.ColumnMetadata;

/**
 * @author p-tvo-sonta
 */
public class QSite extends QBase<Site> {

    /**
     * serial number
     */
    private static final long serialVersionUID = 1L;

    public final NumberPath<Integer> siteId = createNumber(ColumnNames.SITE_ID.toString(), Integer.class);
    public final NumberPath<Integer> dataDirectoryId = createNumber(ColumnNames.DATA_DIRECTORY_ID.toString(),
            Integer.class);
    public final StringPath lastUpdateTime = createString(ColumnNames.LAST_UPDATE_TIME.toString());

    public static QSite newInstance() {
        return new QSite(QSite.class.getSimpleName(), null, TableNames.MGR_SITE.name());
    }

    public QSite(String path, String schema, String tableName) {
        super(Site.class, PathMetadataFactory.forVariable(path), schema, tableName);
        addMetadata();
    }

    protected void addMetadata() {
        addMetadata(siteId, ColumnMetadata.named(ColumnNames.SITE_ID.toString()));
        addMetadata(dataDirectoryId, ColumnMetadata.named(ColumnNames.DATA_DIRECTORY_ID.toString()));
        addMetadata(lastUpdateTime, ColumnMetadata.named(ColumnNames.LAST_UPDATE_TIME.toString()));
    }

}