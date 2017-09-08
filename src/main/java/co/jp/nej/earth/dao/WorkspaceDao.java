package co.jp.nej.earth.dao;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.model.MgrWorkspace;
import co.jp.nej.earth.model.MgrWorkspaceConnect;

import java.util.List;

public interface WorkspaceDao {
    List<MgrWorkspaceConnect> getAllMgrConnections() throws EarthException;

    MgrWorkspaceConnect getMgrConnectionByWorkspaceId(String workspaceId) throws EarthException;

    List<MgrWorkspace> getAll() throws EarthException;

    boolean insertOne(MgrWorkspaceConnect mgrWorkspaceConnect) throws EarthException;

    boolean deleteList(List<Integer> workspaceIds) throws EarthException;

    MgrWorkspaceConnect getOne(String workspaceId) throws EarthException;

    boolean getById(String workspaceId) throws EarthException;

    boolean updateOne(MgrWorkspaceConnect mgrWorkspaceConnect) throws EarthException;
}
