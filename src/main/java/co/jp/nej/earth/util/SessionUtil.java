package co.jp.nej.earth.util;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.model.Message;
import co.jp.nej.earth.model.MgrWorkspace;
import co.jp.nej.earth.model.constant.Constant;
import co.jp.nej.earth.service.WorkspaceService;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SessionUtil {

    /***
     * Key to store object item(Process, WorkItem, FolderItem, Document, Layer) on session
     */
    private static String keySessionItem = "ORIGIN%s&%s";

    private static String keySearchPrefix = "CSEARCH_";

    /**
     * Get Object from session by key
     *
     * @param session     HttpSession object
     * @param workspaceId Workspace ID
     * @param itemKey     key of Item to get data (processId, workItemId, folderItemId, documentId, layerId)
     * @return Object that store on session
     * @throws EarthException
     */
    public static Object getData(HttpSession session, String workspaceId, String itemKey) throws EarthException {
        if ((session == null) || (StringUtils.isEmpty(workspaceId)) || (StringUtils.isEmpty(itemKey))) {
            throw new EarthException("Invalid parameter: request, workspaceId, objectKey");
        }
        return session.getAttribute(String.format(keySessionItem, workspaceId, itemKey));
    }

    public static boolean loadWorkspaces(WorkspaceService service, Model model, HttpServletRequest request)
        throws EarthException {
        return loadWorkspacesWithMessage(service, model, request, null);
    }

    public static boolean loadWorkspacesWithMessage(WorkspaceService service, Model model, HttpServletRequest request,
                                         EMessageResource eMessageResource) throws
            EarthException {
        //Set workspaceId if request contains this parameter
        String workspaceId = (String) request.getParameter("workspaceId");
        if (workspaceId == null) {
            workspaceId = (String) request.getAttribute("workspaceId");
        }

        //reverse workspaceId if session contain
        if (workspaceId == null) {
            workspaceId = (String) SessionUtil.getSearchConditionWorkspaceId(request.getSession());
        }

        // get all work space
        List<MgrWorkspace> workspaces = service.getAll();

        workspaces = removedFailedConnectWorkspaces(workspaces, request);

        // Has no workspace
        if (CollectionUtils.isEmpty(workspaces)) {
            List<Message> messages = new ArrayList<>();
            messages.add(new Message(Constant.ErrorCode.I0002,
                eMessageResource.get(Constant.ErrorCode.I0002, null), Message.MessageTye.INFO));

            model.addAttribute("workspaceId", -1);
            model.addAttribute("workspaces", workspaces);
            model.addAttribute("noWorkspace", true);
            model.addAttribute("messages", messages);
            return false;
        }

        // if workspace id from parameter is null
        if (workspaceId == null || workspaceId.isEmpty()) {
            workspaceId = workspaces.get(0).getStringWorkspaceId();
        }

        String workspaceName = "";
        for (MgrWorkspace workspace : workspaces) {
            if (workspace.getStringWorkspaceId().equals(workspaceId)) {
                workspaceName = workspace.getWorkspaceName();
                break;
            }
        }

        model.addAttribute("workspaceId", Integer.parseInt(workspaceId));
        model.addAttribute("workspaceName", workspaceName);
        setSearchConditionWorkspace(request.getSession(), workspaceId);
        return true;
    }

    public static void setSearchConditionWorkspace(HttpSession session, String workspaceId) {
        Map<String, Object> searchCondition = getSearchCondition(session);
        searchCondition.put("workspaceId", workspaceId);
    }

    public static String getSearchConditionWorkspaceId(HttpSession session) {
        Map<String, Object> searchCondition = getSearchCondition(session);
        return (String) searchCondition.get("workspaceId");
    }

    public static String getSearchConditionWorkspace(HttpSession session) {
        Map<String, Object> searchCondition = getSearchCondition(session);
        return (String) searchCondition.get("workspaceId");
    }

    private static Map<String, Object> getSearchCondition(HttpSession session) {
        Map<String, Object> searchCondition =
                (Map<String, Object>) session.getAttribute(Constant.Session.LIST_SEARCH_CONDITION);
        if (searchCondition == null) {
            searchCondition = new HashMap<>();
            session.setAttribute(Constant.Session.LIST_SEARCH_CONDITION, searchCondition);
        }
        return searchCondition;
    }

    public static String getOriginWorkItemDictionaryKey(String workspaceId, String workItemId) {
        return "DICTIONARY_ORIGIN_WORKITEM_" + workspaceId + "_" + workItemId;
    }

    public static String getTempWorkItemDictionaryKey(String workspaceId, String workItemId) {
        return "DICTIONARY_TMP_WORKITEM_" + workspaceId + "_" + workItemId;
    }


    public static void clearAllOtherSearchCondition(HttpSession session, String screenKey) throws EarthException {

        if ((session == null) || (StringUtils.isEmpty(screenKey))) {
            throw new EarthException("Invalid parameter: session, key");
        }
        Enumeration<String> keys = session.getAttributeNames();
        while (keys.hasMoreElements()) {
            String key = (String)keys.nextElement();
            //System.out.print(keySearchPrefix+ screenKey);
            if(key.startsWith(keySearchPrefix) && (!key.equals(keySearchPrefix + screenKey))) {
                session.removeAttribute(key);
            }
        }

    }

    public static void deleteSessionSearchClient(HttpSession session, String nameScreen) {
        if (!(EStringUtil.equals(ConversionUtil.castObject(
            session.getAttribute(Constant.Session.SCREEN_NAME), String.class), nameScreen))) {
            session.removeAttribute(Constant.Session.SEARCH_CLIENT_FORM);
        }
    }

    public static void clearAllSearchCondition(HttpSession session, String screenName) throws EarthException {

        if ((session == null) || (StringUtils.isEmpty(screenName))) {
            throw new EarthException("Invalid parameter: session, key");
        }
        String screenNameOld=(String) session.getAttribute(Constant.Session.SCREEN_NAME);
        if(!EStringUtil.equals(screenName,screenNameOld)){
            session.removeAttribute(Constant.Session.SEARCH_BY_COLUMNS_FORM);
        }
    }

    public static void setSearchCondtionValue(HttpSession session, String screenKey,
        Object value) throws EarthException {

        if ((session == null) || (StringUtils.isEmpty(screenKey))) {
            throw new EarthException("Invalid parameter: session, screenKey");
        }
        session.setAttribute(keySearchPrefix + screenKey, value);

    }


    public static Object getSearchCondtionValue(HttpSession session, String screenKey) throws EarthException {
        if ((session == null) || (StringUtils.isEmpty(screenKey))) {
            throw new EarthException("Invalid parameter: session, screen");
        }
        Object value = session.getAttribute(keySearchPrefix + screenKey);
        return value;
    }

    private static List<MgrWorkspace> removedFailedConnectWorkspaces(List<MgrWorkspace> workspaces, HttpServletRequest
        request) {
        // removed failed workspaces
        List<MgrWorkspace> failedWorkspaces = (List<MgrWorkspace>) request.getSession()
            .getAttribute(Constant.Session.FAILED_WORKSPACES);
        if(!CollectionUtils.isEmpty(failedWorkspaces)) {
            final List<Integer> failedWorkspaceIds = failedWorkspaces.stream().map(MgrWorkspace::getWorkspaceId)
                .collect(Collectors.toList());
            workspaces = workspaces.stream().filter((w) -> {
                return !failedWorkspaceIds.contains(w.getWorkspaceId());
            }).collect(Collectors.toList());
        }
        return workspaces;
    }
}
