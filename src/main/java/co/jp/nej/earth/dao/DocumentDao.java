package co.jp.nej.earth.dao;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.model.Document;
import co.jp.nej.earth.model.sql.QDocument;
import com.querydsl.sql.dml.SQLInsertClause;
import com.querydsl.sql.dml.SQLUpdateClause;

import java.util.List;

public interface DocumentDao extends BaseDao<Document> {
    List<Document> getAll(String workspaceId, String workitemId, String folderItemNo)
            throws EarthException;

    void addBatch(Document document, SQLInsertClause sqlInsertClause) throws EarthException;
    void updateBatch(Document document, SQLUpdateClause sqlUpdateClause, QDocument qDocument) throws EarthException ;
    Integer getMaxDocumentOrder(String workspaceId, String workItemId, String folderItemNo) throws EarthException;
}
