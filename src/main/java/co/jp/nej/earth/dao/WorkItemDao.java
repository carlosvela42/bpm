package co.jp.nej.earth.dao;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.model.WorkItem;
import co.jp.nej.earth.model.WorkItemListDTO;

import java.util.List;
import java.util.Map;

/**
 * @author p-dcv-khanhnv
 */
public interface WorkItemDao extends BaseDao<WorkItem> {
    Map<WorkItem, List<String>> getWorkItemStructure(String workspaceId, String workitemId) throws EarthException;

    List<WorkItemListDTO> getWorkItemsByWorkspace(String workspaceId, List<String> workItemIds, String userId)
        throws EarthException;

    List<String> getWorkItemIdsNotHaveTemplate(String workspaceId, Long offset, Long limit) throws EarthException;

    List<WorkItemListDTO> getWorkItemsWithoutTemplates(String workspaceId, List<String> workItemIds)
        throws EarthException;

    long unlock(List<String> workItemId, String workspaceId) throws EarthException;

    Integer getProcessIdByWorkItem(String workspaceId, String workitemId) throws EarthException;

    Integer getMaxHistoryNo(String workspaceId, String workitemId) throws EarthException;

    long updateWorkItem(WorkItem workItem) throws EarthException;

    boolean checkExistWorkItem(String workspaceId, String workItemId) throws EarthException;
}