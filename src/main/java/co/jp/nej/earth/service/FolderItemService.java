package co.jp.nej.earth.service;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.model.FolderItem;

import javax.servlet.http.HttpSession;

public interface FolderItemService {

    /**
     * get FolderItem from session
     *
     * @param session
     * @param workspaceId
     * @param workItemId
     * @param folderItemNo
     * @return
     * @throws EarthException
     */
    FolderItem getFolderItemSession(HttpSession session, String workspaceId, String workItemId, String folderItemNo)
        throws EarthException;

    /**
     * update folder item data in session
     *
     * @param session
     * @param workspaceId
     * @param workitemId
     * @param folderItem
     * @return
     * @throws EarthException
     */
    boolean updateFolderItemSession(HttpSession session, String workspaceId, String workitemId, FolderItem folderItem)
        throws EarthException;
}
