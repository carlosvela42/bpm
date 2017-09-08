package co.jp.nej.earth.service;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.model.Layer;

import javax.servlet.http.HttpSession;

public interface LayerService {
    /**
     * get layer from session
     *
     * @param session
     * @param workspaceId
     * @param workItemId
     * @param folderItemNo
     * @param documentNo
     * @param layerNo
     * @return
     * @throws EarthException
     */
    Layer getLayerSession(HttpSession session, String workspaceId, String workItemId, String folderItemNo,
                          String documentNo, String layerNo) throws EarthException;

    /**
     * update layer data in session
     *
     * @param session
     * @param workspaceId
     * @param workItemId
     * @param folderItemNo
     * @param documentNo
     * @return
     * @throws EarthException
     */
    boolean updateLayerSession(HttpSession session, String workspaceId, String workItemId, String folderItemNo,
                               String documentNo, Layer layer) throws EarthException;
}
