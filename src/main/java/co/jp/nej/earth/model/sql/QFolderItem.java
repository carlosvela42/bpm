package co.jp.nej.earth.model.sql;

import co.jp.nej.earth.model.FolderItem;
import co.jp.nej.earth.model.enums.ColumnNames;
import co.jp.nej.earth.model.enums.TableNames;
import com.querydsl.core.types.PathMetadataFactory;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.sql.ColumnMetadata;

public class QFolderItem extends QBase<FolderItem> {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public final StringPath workitemId = createString(ColumnNames.WORKITEM_ID.toString());
    public final StringPath folderItemNo = createString(ColumnNames.FOLDER_ITEM_NO.toString());
    public final StringPath templateId = createString(ColumnNames.TEMPLATE_ID.toString());
    public final NumberPath<Integer> folderItemOrder
                                        = createNumber(ColumnNames.FOLDER_ITEM_ORDER.toString(), Integer.class);
    public final StringPath lastUpdateTime = createString(ColumnNames.LAST_UPDATE_TIME.toString());

    public static QFolderItem newInstance() {
        return new QFolderItem(QFolderItem.class.getSimpleName(), null, TableNames.DAT_FOLDERITEM.name());
    }

    public QFolderItem(String path, String schema, String tableName) {
        super(FolderItem.class, PathMetadataFactory.forVariable(path), schema, tableName);
        addMetadata();
    }

    protected void addMetadata() {
        addMetadata(workitemId, ColumnMetadata.named(ColumnNames.WORKITEM_ID.toString()));
        addMetadata(folderItemNo, ColumnMetadata.named(ColumnNames.FOLDER_ITEM_NO.toString()));
        addMetadata(templateId, ColumnMetadata.named(ColumnNames.TEMPLATE_ID.toString()));
        addMetadata(folderItemOrder, ColumnMetadata.named(ColumnNames.FOLDER_ITEM_ORDER.toString()));
        addMetadata(lastUpdateTime, ColumnMetadata.named(ColumnNames.LAST_UPDATE_TIME.toString()));
    }
}
