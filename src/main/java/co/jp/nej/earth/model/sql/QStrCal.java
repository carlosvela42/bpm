package co.jp.nej.earth.model.sql;

import co.jp.nej.earth.model.entity.StrCal;
import co.jp.nej.earth.model.enums.ColumnNames;
import co.jp.nej.earth.model.enums.TableNames;
import com.querydsl.core.types.PathMetadataFactory;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.sql.ColumnMetadata;

public class QStrCal extends QBase<StrCal> {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public final StringPath division = createString(ColumnNames.DIVISION.toString());
    public final StringPath processTime = createString(ColumnNames.PROCESS_TIME.toString());
    public final StringPath profileId = createString(ColumnNames.PROFILE_ID.toString());
    public final StringPath lastUpdatetime = createString(ColumnNames.LAST_UPDATE_TIME.toString());
    public final NumberPath<Integer> availableLicenseCount = createNumber(
            ColumnNames.AVAILABLE_LICENSE_COUNT.toString(), Integer.class);
    public final NumberPath<Integer> useLicenseCount = createNumber(ColumnNames.USE_LICENSE_COUNT.toString(),
            Integer.class);

    public static QStrCal newInstance() {
        return new QStrCal(QStrCal.class.getSimpleName(), null, TableNames.STR_CAL.name());
    }

    public QStrCal(String path, String schema, String table) {
        super(StrCal.class, PathMetadataFactory.forVariable(path), schema, table);
        addMetadata();
    }

    protected void addMetadata() {
        addMetadata(division, ColumnMetadata.named(ColumnNames.DIVISION.toString()));
        addMetadata(processTime, ColumnMetadata.named(ColumnNames.PROCESS_TIME.toString()));
        addMetadata(profileId, ColumnMetadata.named(ColumnNames.PROFILE_ID.toString()));
        addMetadata(availableLicenseCount, ColumnMetadata.named(ColumnNames.AVAILABLE_LICENSE_COUNT.toString()));
        addMetadata(useLicenseCount, ColumnMetadata.named(ColumnNames.USE_LICENSE_COUNT.toString()));
        addMetadata(lastUpdatetime, ColumnMetadata.named(ColumnNames.LAST_UPDATE_TIME.toString()));
    }
}
