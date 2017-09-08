package co.jp.nej.earth.dao;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.model.FolderItem;
import co.jp.nej.earth.model.sql.QFolderItem;
import com.querydsl.sql.dml.SQLInsertClause;
import com.querydsl.sql.dml.SQLUpdateClause;

public interface FolderItemDao extends BaseDao<FolderItem> {
    void addBatchFolderItem(FolderItem folderItem, SQLInsertClause sqlInsertClause) throws EarthException;
    void updateBatchFolderItem(FolderItem folderItem, SQLUpdateClause sqlUpdateClause,
                            QFolderItem qFolderItem) throws EarthException;
    Integer getMaxFolderItemOrder(String workspaceId, String workItemId) throws EarthException;
}
