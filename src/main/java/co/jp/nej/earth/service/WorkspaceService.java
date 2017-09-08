package co.jp.nej.earth.service;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.model.Message;
import co.jp.nej.earth.model.MgrWorkspace;
import co.jp.nej.earth.model.MgrWorkspaceConnect;

import java.util.List;

public interface WorkspaceService {
    List<MgrWorkspaceConnect> getAllWorkspaceConnections() throws EarthException;

    MgrWorkspaceConnect getMgrConnectionByWorkspaceId(String workspaceId) throws EarthException;

    List<MgrWorkspace> getAll() throws EarthException;

    boolean insertOne(MgrWorkspaceConnect mgrWorkspaceConnect) throws EarthException;

    MgrWorkspaceConnect getDetail(String workspaceId) throws EarthException;

    List<Message> deleteList(List<Integer> workspaceIds) throws EarthException;

    List<Message> validateInsert(MgrWorkspaceConnect mgrWorkspaceConnect,Boolean insert) throws EarthException;

    boolean updateOne(MgrWorkspaceConnect mgrWorkspaceConnect) throws EarthException;
}
