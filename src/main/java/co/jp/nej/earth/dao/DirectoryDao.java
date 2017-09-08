package co.jp.nej.earth.dao;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.model.Directory;
import com.querydsl.core.types.OrderSpecifier;

import java.util.List;

public interface DirectoryDao extends BaseDao<Directory> {
    List<Directory> getDirectoriesBySite(int siteId) throws EarthException;

    List<Directory> getAll(String workspaceId, OrderSpecifier<?> orderBy) throws EarthException;

    List<Integer> getDirectoryIds(int siteId, String workspaceId) throws EarthException;

    long deleteDirectorys(List<Integer> directoryIds, String workspaceId) throws EarthException;

    Directory getById(int directoryId) throws EarthException;

    long insertOne(Directory directory, String workspaceId) throws EarthException;

    long updateOne(Directory directory, String workspaceId) throws EarthException;


}
