package co.jp.nej.earth.dao;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.manager.connection.ConnectionManager;
import co.jp.nej.earth.model.Layer;
import co.jp.nej.earth.model.sql.QLayer;
import co.jp.nej.earth.util.DateUtil;
import com.querydsl.sql.dml.SQLDeleteClause;
import com.querydsl.sql.dml.SQLInsertClause;
import com.querydsl.sql.dml.SQLUpdateClause;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LayerDaoImpl extends BaseDaoImpl<Layer> implements LayerDao {

    public LayerDaoImpl() throws Exception {
        super();
    }

    public List<Layer> getAll(String workspaceId, String workitemId, String folderItemNo, String documentNo)
            throws EarthException {
        QLayer qLayer = QLayer.newInstance();
        List<Layer> layers = ConnectionManager.getEarthQueryFactory(workspaceId).select(qLayer).from(qLayer)
                .where(qLayer.workitemId.eq(workitemId)
                        .and(qLayer.folderItemNo.eq(folderItemNo).and(qLayer.documentNo.eq(documentNo))))
                .fetch();
        return layers;
    }

    /**
     * Add layer
     *
     * @return
     * @throws EarthException
     */
    @Override
    public void addBatch(Layer layer, SQLInsertClause sqlInsertClause) throws EarthException {
        if (sqlInsertClause == null) {
            throw new EarthException("Invalid parameter. sqlInsertClause is null");
        }
        if (layer == null) {
            return;
        }
        sqlInsertClause.populate(layer).addBatch();
    }

    @Override
    public void updateBatch(Layer layer, SQLUpdateClause sqlUpdateClause, QLayer qLayer) throws EarthException {
        if (sqlUpdateClause == null) {
            throw new EarthException("Invalid parameter. sqlUpdateClause is null");
        }
        if (qLayer == null) {
            throw new EarthException("Invalid parameter. qLayer is null");
        }
        if (layer == null) {
            return;
        }
        layer.setLastUpdateTime(DateUtil.getCurrentDateString());
        sqlUpdateClause.populate(layer)
            .where(qLayer.workitemId.eq(layer.getWorkitemId()))
            .where(qLayer.folderItemNo.eq(layer.getFolderItemNo()))
            .where(qLayer.documentNo.eq(layer.getDocumentNo()))
            .where(qLayer.layerNo.eq(layer.getLayerNo()))
            .addBatch();
    }

    @Override
    public void deleteBatch(Layer layer, SQLDeleteClause sqlDeleteClause, QLayer qLayer) throws EarthException {
        if (sqlDeleteClause == null) {
            throw new EarthException("Invalid parameter. sqlDeleteClause is null");
        }
        if (qLayer == null) {
            throw new EarthException("Invalid parameter. qLayer is null");
        }
        if (layer == null) {
            return;
        }
        layer.setLastUpdateTime(DateUtil.getCurrentDateString());
        sqlDeleteClause.where(qLayer.workitemId.eq(layer.getWorkitemId()))
            .where(qLayer.folderItemNo.eq(layer.getFolderItemNo()))
            .where(qLayer.documentNo.eq(layer.getDocumentNo()))
            .where(qLayer.layerNo.eq(layer.getLayerNo()))
            .addBatch();
    }

    @Override
    public Integer getMaxLayerOrder(String workspaceId, String workItemId,
                                    String folderItemNo, String documentNo) throws EarthException {
        try {
            QLayer qLayer = QLayer.newInstance();
            Integer maxLayerOrder = ConnectionManager.getEarthQueryFactory(workspaceId).select(qLayer.layerOder.max())
                    .from(qLayer)
                    .where(qLayer.workitemId.eq(workItemId))
                    .where(qLayer.folderItemNo.eq(folderItemNo))
                    .where(qLayer.documentNo.eq(documentNo))
                    .fetchOne();
            return maxLayerOrder==null?0:maxLayerOrder;
        } catch (Exception ex) {
            throw new EarthException(ex.getMessage());
        }
    }
}
