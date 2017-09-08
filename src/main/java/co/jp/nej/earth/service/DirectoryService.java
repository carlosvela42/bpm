package co.jp.nej.earth.service;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.model.Directory;
import co.jp.nej.earth.model.Message;

import java.util.List;

public interface DirectoryService {

    List<Directory> getAllDirectories() throws EarthException;

    List<Integer> getAllDirectoryIds(String siteId, String workspaceId) throws EarthException;

    List<Directory> getAllDirectoriesBySite(String siteId, String workspaceId) throws EarthException;

    boolean deleteDirectories(List<Integer> directoryIds, String workspaceId) throws EarthException;

    List<Message> validate(Directory directory, String workspaceId) throws EarthException;

    List<Message> validateDelete(List<Integer> directoryIds, String workspaceId) throws EarthException;

    boolean insertOne(Directory directory, String workspaceId) throws EarthException;

    Directory getById(String dataDirectoryId, String workspaceId) throws EarthException;

    boolean updateDirectory(Directory directory, String workspaceId) throws EarthException;

}
