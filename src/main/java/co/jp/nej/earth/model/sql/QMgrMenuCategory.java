package co.jp.nej.earth.model.sql;

import co.jp.nej.earth.model.entity.MgrMenuCategory;
import co.jp.nej.earth.model.enums.ColumnNames;
import co.jp.nej.earth.model.enums.TableNames;
import com.querydsl.core.types.PathMetadataFactory;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.sql.ColumnMetadata;

public class QMgrMenuCategory extends QBase<MgrMenuCategory> {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public final StringPath functionCategoryId = createString(ColumnNames.FUNCTION_CATEGORY_ID.toString());
    public final StringPath functionCategoryName = createString(ColumnNames.FUNCTION_CATEGORY_NAME.toString());
    public final NumberPath<Integer> functionSortNo = createNumber(ColumnNames.FUNCTION_SORT_NO.toString(),
            Integer.class);

    public static QMgrMenuCategory newInstance() {
        return new QMgrMenuCategory(QMgrMenuCategory.class.getSimpleName(), null, TableNames.MGR_MENUCATEGORY.name());
    }

    public QMgrMenuCategory(String path, String schema, String table) {
        super(MgrMenuCategory.class, PathMetadataFactory.forVariable(path), schema, table);
        addMetadata();
    }

    protected void addMetadata() {
        addMetadata(functionCategoryId, ColumnMetadata.named(ColumnNames.FUNCTION_CATEGORY_ID.toString()));
        addMetadata(functionCategoryName, ColumnMetadata.named(ColumnNames.FUNCTION_CATEGORY_NAME.toString()));
        addMetadata(functionSortNo, ColumnMetadata.named(ColumnNames.FUNCTION_SORT_NO.toString()));
    }
}
