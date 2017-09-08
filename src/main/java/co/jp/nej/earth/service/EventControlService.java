package co.jp.nej.earth.service;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.model.entity.CtlEvent;

import java.util.List;

/**
 * @author p-tvo-sonta
 */
public interface EventControlService {

    /**
     * insert event
     *
     * @param event
     * @return
     * @throws EarthException
     */
    boolean insertEvent(String workspaceId, CtlEvent event) throws EarthException;

    /**
     * insert list of event
     *
     * @param events
     * @return
     * @throws EarthException
     */
    boolean insertEvents(String workspaceId, List<CtlEvent> events) throws EarthException;

    /**
     * get list event by status
     *
     * @param status
     * @param workSpaceId
     * @return
     * @throws EarthException
     */
    List<String> getListEventIdByStatus(String status, String workSpaceId) throws EarthException;

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
     * get event by eventId
     *
     * @param eventId
     * @param workSpaceId
     * @return
     * @throws EarthException
     */
    CtlEvent getEventByEventId(String eventId, String workSpaceId) throws EarthException;

    /**
     * delete event
     *
     * @param event
     * @param workSpaceId
     * @return
     */
    boolean deleteEvent(CtlEvent event, String workSpaceId) throws EarthException;

    /**
     * unlock event control
     *
     * @param workspaceId
     * @param eventId
     * @return
     * @throws EarthException
     */
    boolean unlockEventControl(String workspaceId, String eventId) throws EarthException;


    /**
     * unlock event control
     *
     * @param workspaceId
     * @param eventIds
     * @return
     * @throws EarthException
     */
    boolean unlockEventControls(String workspaceId, List<String> eventIds) throws EarthException;

    /**
     * Get one event by status for update
     *
     * @param status
     * @param workSpaceId
     * @return
     * @throws EarthException
     */
    CtlEvent getOneEventByStatusForUpdate(String status, String workSpaceId, String userId) throws EarthException;

    String getEventIdByWorkItemId(String workspaceId, String workItemId) throws EarthException;
}
