package co.jp.nej.earth.dao;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.manager.connection.ConnectionManager;
import co.jp.nej.earth.model.Document;
import co.jp.nej.earth.model.sql.QDocument;
import co.jp.nej.earth.util.DateUtil;
import com.querydsl.sql.dml.SQLInsertClause;
import com.querydsl.sql.dml.SQLUpdateClause;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DocumentDaoImpl extends BaseDaoImpl<Document> implements DocumentDao {

    public DocumentDaoImpl() throws Exception {
        super();
    }

    public List<Document> getAll(String workspaceId, String workitemId, String folderItemNo) throws EarthException {
        QDocument qDocument = QDocument.newInstance();
        List<Document> documents = ConnectionManager.getEarthQueryFactory(workspaceId).select(qDocument).from(qDocument)
                .where(qDocument.workitemId.eq(workitemId).and(qDocument.folderItemNo.eq(folderItemNo))).fetch();
        return documents;
    }

    /**
     * Add list folder item
     *
     * @return
     * @throws EarthException
     */
    @Override
    public void addBatch(Document document, SQLInsertClause sqlInsertClause) throws EarthException {
        if (sqlInsertClause == null) {
            throw new EarthException("Invalid parameter. sqlInsertClause is null");
        }
        if (document == null) {
            return;
        }
        sqlInsertClause.populate(document).addBatch();
    }

    @Override
    public void updateBatch(Document document, SQLUpdateClause sqlUpdateClause,
                            QDocument qDocument) throws EarthException {
        if (sqlUpdateClause == null) {
            throw new EarthException("Invalid parameter. sqlUpdateClause is null");
        }
        if (qDocument == null) {
            throw new EarthException("Invalid parameter. qDocument is null");
        }
        if (document == null) {
            return;
        }
        document.setLastUpdateTime(DateUtil.getCurrentDateString());
        sqlUpdateClause.populate(document)
            .where(qDocument.workitemId.eq(document.getWorkitemId()))
            .where(qDocument.folderItemNo.eq(document.getFolderItemNo()))
            .where(qDocument.documentNo.eq(document.getDocumentNo()))
            .addBatch();
    }

    @Override
    public Integer getMaxDocumentOrder(String workspaceId, String workItemId, String folderItemNo)
            throws EarthException {
        try {
            QDocument qDocument = QDocument.newInstance();
            Integer maxDocumentOrder = ConnectionManager.getEarthQueryFactory(workspaceId)
                    .select(qDocument.documentOder.max())
                    .from(qDocument)
                    .where(qDocument.workitemId.eq(workItemId))
                    .where(qDocument.folderItemNo.eq(folderItemNo))
                    .fetchOne();
            return maxDocumentOrder==null?0:maxDocumentOrder;
        } catch (Exception ex) {
            throw new EarthException(ex.getMessage());
        }
    }
}
