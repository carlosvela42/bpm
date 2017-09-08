package co.jp.nej.earth.service;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.model.Layer;
import co.jp.nej.earth.model.constant.Constant;
import co.jp.nej.earth.model.enums.AccessRight;
import co.jp.nej.earth.model.enums.Action;
import co.jp.nej.earth.util.EModelUtil;
import co.jp.nej.earth.util.SessionUtil;
import co.jp.nej.earth.util.TemplateUtil;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class LayerServiceImpl extends BaseService implements LayerService {

    /**
     * Get Layer form session (From WorkItem in session)
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
    @Override
    public Layer getLayerSession(HttpSession session, String workspaceId, String workItemId, String folderItemNo,
                                 String documentNo, String layerNo) throws EarthException {
        // Get Layer data from session.
        Layer layerSession = (Layer) getDataItemFromSession(session,
                SessionUtil.getOriginWorkItemDictionaryKey(workspaceId, workItemId), EModelUtil.getLayerIndex(
                        workItemId, folderItemNo, documentNo, layerNo));
        TemplateUtil.checkPermission(layerSession, AccessRight.RO, messageSource.get(Constant.ErrorCode.E0025));

        return EModelUtil.clone(layerSession, Layer.class);
    }

    @Override
    public boolean updateLayerSession(HttpSession session, String workspaceId, String workItemId, String folderItemNo,
                                      String documentNo, Layer layer) throws EarthException {
        // Get Layer data from session.
        Layer layerSession = (Layer) getDataItemFromSession(session,
                SessionUtil.getOriginWorkItemDictionaryKey(workspaceId, workItemId),
                EModelUtil.getLayerIndex(workItemId, folderItemNo, documentNo, layer.getLayerNo()));

        TemplateUtil.checkPermission(layerSession, AccessRight.RW, messageSource.get(Constant.ErrorCode.E0025));
        layerSession.setTemplateId(layer.getTemplateId());
        layerSession.setLayerData(layer.getLayerData());
        layerSession.setMgrTemplate(layer.getMgrTemplate());
        layerSession.setAction(Action.UPDATE.getAction());
        return true;
    }
}
