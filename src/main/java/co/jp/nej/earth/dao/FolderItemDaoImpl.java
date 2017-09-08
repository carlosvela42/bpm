package co.jp.nej.earth.dao;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.manager.connection.ConnectionManager;
import co.jp.nej.earth.model.FolderItem;
import co.jp.nej.earth.model.sql.QFolderItem;
import co.jp.nej.earth.util.DateUtil;
import com.querydsl.sql.dml.SQLInsertClause;
import com.querydsl.sql.dml.SQLUpdateClause;
import org.springframework.stereotype.Repository;

@Repository
public class FolderItemDaoImpl extends BaseDaoImpl<FolderItem> implements FolderItemDao {

    public FolderItemDaoImpl() throws Exception {
        super();
    }

    /**
     * Add list folder item
     *
     * @return
     * @throws EarthException
     */
    @Override
    public void addBatchFolderItem(FolderItem folderItem, SQLInsertClause sqlInsertClause) throws EarthException {
        if (sqlInsertClause == null) {
            throw new EarthException("Invalid parameter. sqlInsertClause is null");
        }
        if (folderItem == null) {
            return;
        }
        sqlInsertClause.populate(folderItem).addBatch();
    }

    @Override
    public void updateBatchFolderItem(FolderItem folderItem, SQLUpdateClause sqlUpdateClause,
                                      QFolderItem qFolderItem) throws EarthException {
        if (sqlUpdateClause == null) {
            throw new EarthException("Invalid parameter. sqlUpdateClause is null");
        }
        if (qFolderItem == null) {
            throw new EarthException("Invalid parameter. qFolderItem is null");
        }
        if (folderItem == null) {
            return;
        }
        folderItem.setLastUpdateTime(DateUtil.getCurrentDateString());
        sqlUpdateClause.populate(folderItem)
            .where(qFolderItem.workitemId.eq(folderItem.getWorkitemId()))
            .where(qFolderItem.folderItemNo.eq(folderItem.getFolderItemNo()))
            .addBatch();
    }

    @Override
    public Integer getMaxFolderItemOrder(String workspaceId, String workItemId)
            throws EarthException {
        try {
            QFolderItem qFolderItem = QFolderItem.newInstance();
            Integer maxFolderItemOrder = ConnectionManager.getEarthQueryFactory(workspaceId)
                    .select(qFolderItem.folderItemOrder.max())
                    .from(qFolderItem)
                    .where(qFolderItem.workitemId.eq(workItemId))
                    .fetchOne();
            return maxFolderItemOrder==null?0:maxFolderItemOrder;
        } catch (Exception ex) {
            throw new EarthException(ex.getMessage());
        }
    }
}
