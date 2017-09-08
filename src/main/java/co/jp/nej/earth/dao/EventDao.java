package co.jp.nej.earth.dao;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.model.entity.CtlEvent;

import java.util.List;

/**
 * Created by minhtv on 3/29/2017.
 */
public interface EventDao extends BaseDao<CtlEvent> {
    boolean deleteListByUserIds(List<String> userIds) throws EarthException;

    /**
     * get list ctlEvent by status
     *
     * @param status
     * @param workSpaceId
     * @return
     * @throws EarthException
     */
    List<String> getListCtlEventIdByStatus(String status, String workSpaceId) throws EarthException;

    /**
     * update bulk event status
     *
     * @param eventIds
     * @param workSpaceId
     * @return
     * @throws EarthException
     */
    boolean updateBulkEventStatus(List<String> eventIds, String workSpaceId) throws EarthException;

    /**
     * Count clEvent by list User
     *
     * @param userIds
     * @param workspaceId
     * @return
     * @throws EarthException
     */
    long countEventByUserIds(List<String> userIds, String workspaceId) throws EarthException;

    /**
     * Update status and TransactionToken for event
     *
     * @param workSpaceId
     * @return
     */
    boolean updateStatusAndTokenEvent(String transactionToken, String workSpaceId)
        throws EarthException;

    /**
     * Get clEvent by workItem.
     *
     * @param workspaceId
     * @param workItemId
     * @return
     * @throws EarthException
     */
    CtlEvent getEventByWorkItemId(String workspaceId, String workItemId) throws EarthException;

    /**
     * Get one event for update by status
     *
     * @param transactionToken
     * @param workSpaceId
     * @return
     * @throws EarthException
     */
    CtlEvent getEventIsEditing(String transactionToken, String workSpaceId, String userId)
        throws EarthException;

    /**
     * Delete event
     *
     * @param workspaceId
     * @param event
     * @return
     * @throws EarthException
     */
    boolean deleteEvent(String workspaceId, CtlEvent event) throws EarthException;

    /**
     * Delete list of events
     *
     * @param workspaceId
     * @param eventIds
     * @return
     * @throws EarthException
     */
    long deleteBulkEvents(String workspaceId, List<String> eventIds, String status) throws EarthException;

    /**
     * Update Bulk events Status
     *
     * @param workspaceId
     * @param eventIds
     * @return
     * @throws EarthException
     */
    long updateBulkEventStatus(String workspaceId, List<String> eventIds, String oldStatus, String newStatus)
        throws EarthException;

    boolean checkExist(String workspaceId, String workItemId) throws EarthException;

}
