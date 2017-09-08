package co.jp.nej.earth.service;

import co.jp.nej.earth.dao.FolderItemDao;
import co.jp.nej.earth.dao.TemplateDao;
import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.model.Document;
import co.jp.nej.earth.model.FolderItem;
import co.jp.nej.earth.model.constant.Constant;
import co.jp.nej.earth.model.enums.AccessRight;
import co.jp.nej.earth.util.EModelUtil;
import co.jp.nej.earth.util.SessionUtil;
import co.jp.nej.earth.util.TemplateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Service
public class FolderItemServiceImpl extends BaseService implements FolderItemService {

    @Autowired
    private FolderItemDao folderItemDao;

    @Autowired
    private TemplateDao templateDao;

    /**
     * {@inheritDoc}
     */
    @Override
    public FolderItem getFolderItemSession(HttpSession session, String workspaceId, String workItemId,
                                           String folderItemNo) throws EarthException {
        // Get FolderItem data from session
        FolderItem folderItemSession = (FolderItem) getDataItemFromSession(session,
            SessionUtil.getOriginWorkItemDictionaryKey(workspaceId, workItemId),
            EModelUtil.getFolderItemIndex(workItemId, folderItemNo));
        TemplateUtil.checkPermission(folderItemSession, AccessRight.RO, messageSource.get(Constant.ErrorCode.E0025));

        FolderItem folderItem = EModelUtil.clone(folderItemSession, FolderItem.class);
        folderItem.setDocuments(new ArrayList<Document>());
        return folderItem;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean updateFolderItemSession(HttpSession session, String workspaceId, String workItemId,
                                           FolderItem folderItem) throws EarthException {
        // Get FolderItem data from session
        FolderItem folderItemSession = (FolderItem) getDataItemFromSession(session,
            SessionUtil.getOriginWorkItemDictionaryKey(workspaceId, workItemId),
            EModelUtil.getFolderItemIndex(workItemId, String.valueOf(folderItem.getFolderItemNo())));
        TemplateUtil.checkPermission(folderItemSession, AccessRight.RW, messageSource.get(Constant.ErrorCode.E0025));


        folderItemSession.setFolderItemData(folderItem.getFolderItemData());
        folderItemSession.setMgrTemplate(folderItem.getMgrTemplate());
        folderItemSession.setLastUpdateTime(folderItem.getLastUpdateTime());
        folderItemSession.setTemplateId(folderItem.getTemplateId());
        folderItemSession.setAction(folderItem.getAction());
        return true;
    }
}
