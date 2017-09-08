package co.jp.nej.earth.dao;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.model.Site;

import java.util.List;

/**
 * @author p-tvo-sonta
 */
public interface SiteDao extends BaseDao<Site> {

    /**
     * get all file id
     *
     * @return
     * @throws EarthException
     */
    List<Integer> getAllSiteIds() throws EarthException;

    long deleteSites(List<Integer> siteIds, String workspaceId) throws EarthException;

    long deleteSite(String siteId, String workspaceId) throws EarthException;

    long insertOne(String siteId, List<String> directoryIds, String workspaceId) throws EarthException;

    long getSiteByDataDirectoryIds(List<Integer>  directoryIds, String workspaceId) throws EarthException;

    boolean checkExistSiteId(List<Integer> siteIds,String workspaceId) throws EarthException;

}
