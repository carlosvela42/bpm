package co.jp.nej.earth.model.sql;

import co.jp.nej.earth.model.entity.MgrMenu;
import co.jp.nej.earth.model.enums.ColumnNames;
import co.jp.nej.earth.model.enums.TableNames;
import com.querydsl.core.types.PathMetadataFactory;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.sql.ColumnMetadata;

public class QMgrMenu extends QBase<MgrMenu> {

    /**
     * @author p-tvo-thuynd
     */
    private static final long serialVersionUID = 1L;

    public final StringPath functionId = createString(ColumnNames.FUNCTION_ID.toString());
    public final StringPath functionName = createString(ColumnNames.FUNCTION_NAME.toString());
    public final StringPath functionCategoryId = createString(ColumnNames.FUNCTION_CATEGORY_ID.toString());
    public final NumberPath<Integer> functionSortNo = createNumber(ColumnNames.FUNCTION_SORT_NO.toString(),
            Integer.class);
    public final StringPath functionInformation = createString(ColumnNames.FUNCTION_INFORMATION.toString());

    public static QMgrMenu newInstance() {
        return new QMgrMenu(QMgrMenu.class.getSimpleName(), null, TableNames.MGR_MENU.name());
    }

    public QMgrMenu(String path, String schema, String table) {
        super(MgrMenu.class, PathMetadataFactory.forVariable(path), schema, table);
        addMetadata();
    }

    protected void addMetadata() {
        addMetadata(functionId, ColumnMetadata.named(ColumnNames.FUNCTION_ID.toString()));
        addMetadata(functionName, ColumnMetadata.named(ColumnNames.FUNCTION_NAME.toString()));
        addMetadata(functionCategoryId, ColumnMetadata.named(ColumnNames.FUNCTION_CATEGORY_ID.toString()));
        addMetadata(functionSortNo, ColumnMetadata.named(ColumnNames.FUNCTION_SORT_NO.toString()));
        addMetadata(functionInformation, ColumnMetadata.named(ColumnNames.FUNCTION_INFORMATION.toString()));
    }
}
