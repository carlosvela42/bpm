package co.jp.nej.earth.service;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.model.Message;
import co.jp.nej.earth.model.WorkItem;
import co.jp.nej.earth.model.WorkItemListDTO;
import co.jp.nej.earth.web.form.SearchByColumnsForm;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author p-tvo-sonta
 */
public interface WorkItemService {
    List<Message> openWorkItemFromScreen(HttpSession session, String workspaceId, String
        workItemId) throws EarthException;

    List<Message> openWorkItem(HttpSession session, String workspaceId, String workItemId,
        Integer openMode) throws EarthException;

    /**
     * insert or update work item
     *
     * @param workspaceId
     * @param threadId
     * @param processServiceID
     * @return
     * @throws EarthException
     */
    boolean insertOrUpdateWorkItemToDbFromEvent(String workspaceId, Long threadId, String hostName,
                                                Integer processServiceID, String userId, String sessionId)
        throws EarthException, IOException;

    /**
     * get workitem data from session
     *
     * @param session
     * @param workspaceId
     * @param workItemId
     * @return
     * @throws EarthException
     */
    WorkItem getWorkItemSession(HttpSession session, String workspaceId, String workItemId) throws EarthException;

    /**
     * update workitem data to session
     *
     * @param session
     * @param workspaceId
     * @param workItem
     * @return
     * @throws EarthException
     */
    boolean updateWorkItemSession(HttpSession session, String workspaceId, WorkItem workItem) throws EarthException;

    /**
     * Update status to workitem on session
     *
     * @param session
     * @param workspaceId
     * @param workItem
     * @return
     * @throws EarthException
     */
    WorkItem updateTaskToWorkItemSession(HttpSession session, String workspaceId, WorkItem workItem)
        throws EarthException;

    /**
     * get workitem structure from session (remove template data from workitem)
     *
     * @param session
     * @param workspaceId
     * @param workitemId
     * @return
     * @throws EarthException
     */
    WorkItem getWorkItemStructureSession(HttpSession session, String workspaceId, String workitemId)
        throws EarthException;

    List<WorkItemListDTO> getWorkItemsByWorkspace(String workspaceId, List<String> workItemIds, String userId
        , String templateId) throws EarthException;

    boolean unlock(List<String> workItemId, String workspaceId) throws EarthException;

    Map<String, Object> searchWorkItems(HttpSession se, SearchByColumnsForm searchByColumnsForm, String workspaceId)
        throws EarthException;

    Integer getProcessIdByWorkItem(String workspaceId, String workitemId) throws EarthException;

    List<Message> closeWorkItem(HttpSession session, String workspaceId, String workItemId) throws EarthException;

    boolean closeAndSaveWorkItem(HttpSession session, String workItemId, String workspaceId)
        throws EarthException;
}
