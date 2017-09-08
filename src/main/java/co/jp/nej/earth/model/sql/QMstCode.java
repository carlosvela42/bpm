package co.jp.nej.earth.model.sql;

import co.jp.nej.earth.model.entity.MstCode;
import co.jp.nej.earth.model.enums.ColumnNames;
import co.jp.nej.earth.model.enums.TableNames;
import com.querydsl.core.types.PathMetadataFactory;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.sql.ColumnMetadata;

public class QMstCode extends QBase<MstCode> {
    private static final long serialVersionUID = 1L;

    public final StringPath codeId = createString(ColumnNames.CODE_ID.toString());
    public final StringPath codeValue = createString(ColumnNames.CODE_VALUE.toString());
    public final StringPath section = createString(ColumnNames.SECTION.toString());
    public final StringPath sectionValue = createString(ColumnNames.SECTION_VALUE.toString());

    public static QMstCode newInstance() {
        return new QMstCode(QMstCode.class.getSimpleName(), null, TableNames.MST_CODE.name());
    }

    public QMstCode(String path, String schema, String tableName) {
        super(MstCode.class, PathMetadataFactory.forVariable(path), schema, tableName);
        addMetadata();
    }

    protected void addMetadata() {
        addMetadata(codeId, ColumnMetadata.named(ColumnNames.CODE_ID.toString()));
        addMetadata(codeValue, ColumnMetadata.named(ColumnNames.CODE_VALUE.toString()));
        addMetadata(section, ColumnMetadata.named(ColumnNames.SECTION.toString()));
        addMetadata(sectionValue, ColumnMetadata.named(ColumnNames.SECTION_VALUE.toString()));
    }
}
