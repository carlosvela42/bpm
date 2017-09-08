package co.jp.nej.earth.service;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.model.DatProcess;
import co.jp.nej.earth.model.MgrProcess;
import co.jp.nej.earth.model.form.DeleteProcessForm;
import co.jp.nej.earth.model.form.ProcessForm;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * @author p-tvo-sonta
 */
public interface ProcessService {

    DatProcess getProcessSession(HttpSession session, String workspaceId, String workItemId, Integer processId)
            throws EarthException;

    boolean updateProcessSession(HttpSession session, String workspaceId, DatProcess datProcess)
            throws EarthException;

//    boolean closeAndSaveProcess(HttpSession session, WorkItem workItem, String workspacedId)
//            throws EarthException;

    /**
     * get all process by workspace
     *
     * @param workspaceId
     * @return
     * @throws EarthException
     */
    List<MgrProcess> getAllByWorkspace(String workspaceId) throws EarthException;

    /**
     * delete list process
     *
     * @param form
     * @return
     * @throws EarthException
     */
    boolean deleteList(DeleteProcessForm form) throws EarthException;

    /**
     * insert process
     *
     * @param form
     * @return
     * @throws EarthException
     */
    boolean insertOne(ProcessForm form, String sessionID) throws EarthException;

    /**
     * get process detail
     *
     * @param workspaceId
     * @param processId
     * @return
     * @throws EarthException
     */
    Map<String, Object> getDetail(String workspaceId, String processId) throws EarthException;

    /**
     * update process
     *
     * @param form
     * @return
     * @throws EarthException
     */
    boolean updateOne(ProcessForm form) throws EarthException;
}
