package co.jp.nej.earth.dao;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.model.Layer;
import co.jp.nej.earth.model.sql.QLayer;
import com.querydsl.sql.dml.SQLDeleteClause;
import com.querydsl.sql.dml.SQLInsertClause;
import com.querydsl.sql.dml.SQLUpdateClause;

import java.util.List;

public interface LayerDao extends BaseDao<Layer> {
    List<Layer> getAll(String workspaceId, String workitemId, String folderItemNo, String documentNo)
            throws EarthException;
    void addBatch(Layer layer, SQLInsertClause sqlInsertClause) throws EarthException;
    void updateBatch(Layer layer, SQLUpdateClause sqlUpdateClause, QLayer qLayer) throws EarthException;
    void deleteBatch(Layer layer, SQLDeleteClause sqlDeleteClause, QLayer qLayer) throws EarthException;
    Integer getMaxLayerOrder(String workspaceId, String workItemId, String folderItemNo, String documentNo)
            throws EarthException;
}
