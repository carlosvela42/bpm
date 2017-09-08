package co.jp.nej.earth.dao;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.manager.connection.ConnectionManager;
import co.jp.nej.earth.manager.connection.EarthQueryFactory;
import co.jp.nej.earth.model.Site;
import co.jp.nej.earth.model.StrageFile;
import co.jp.nej.earth.model.constant.Constant;
import co.jp.nej.earth.model.sql.QSite;
import co.jp.nej.earth.model.sql.QStrageFile;
import co.jp.nej.earth.util.DateUtil;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import com.querydsl.sql.dml.SQLInsertClause;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author p-tvo-sonta
 */
@Repository
public class SiteDaoImpl extends BaseDaoImpl<Site> implements SiteDao {

    private static final Logger LOG = LoggerFactory.getLogger(SiteDaoImpl.class);

    public SiteDaoImpl() throws Exception {
        super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Integer> getAllSiteIds() throws EarthException {
        // find all site and find list site id
        try {
            QSite qSite = QSite.newInstance();
            return ConnectionManager.getEarthQueryFactory()
                .select(qSite.siteId)
                .from(qSite)
                .orderBy(qSite.siteId.asc())
                .groupBy(qSite.siteId).fetch();

        } catch (Exception e) {
            LOG.error(e.getMessage());
            throw new EarthException(e);
        }
    }

    @Override
    public long deleteSites(List<Integer> siteIds, String workspaceId) throws EarthException {
        List<Map<Path<?>, Object>> conditions = new ArrayList<>();
        QSite qSite = QSite.newInstance();
        for (Integer siteId : siteIds) {
            Map<Path<?>, Object> condition = new HashMap<>();
            condition.put(qSite.siteId, siteId);
            conditions.add(condition);
        }
        return deleteList(workspaceId, conditions);
    }

    @Override
    public long insertOne(String siteId, List<String> directoryIds, String workspaceId) throws EarthException {
        QSite qSite = QSite.newInstance();
        try {
            EarthQueryFactory earthQueryFactory = ConnectionManager.getEarthQueryFactory(workspaceId);
            SQLInsertClause insert = earthQueryFactory.insert(qSite);
            for (String directoryId : directoryIds) {
                insert.set(qSite.siteId, Integer.valueOf(siteId))
                    .set(qSite.dataDirectoryId, Integer.valueOf(directoryId))
                    .set(qSite.lastUpdateTime, DateUtil.getCurrentDate(
                        Constant.DatePattern.DATE_FORMAT_YYYYMMDDHHMMSSSSS))
                    .addBatch();
            }
            return insert.execute();
        } catch (Exception ex) {
            throw new EarthException(ex);
        }
    }

    @Override
    public long deleteSite(String siteId, String workspaceId) throws EarthException {
        List<Map<Path<?>, Object>> conditions = new ArrayList<>();
        QSite qSite = QSite.newInstance();
        Map<Path<?>, Object> condition = new HashMap<>();
        condition.put(qSite.siteId, Integer.valueOf(siteId));
        conditions.add(condition);
        return deleteList(workspaceId, conditions);
    }

    @Override
    public long getSiteByDataDirectoryIds(List<Integer> directoryIds, String workspaceId) throws EarthException {
        long records = 0L;
        try {
            List<Map<Path<?>, Object>> conditions = new ArrayList<>();
            EarthQueryFactory earthQueryFactory = ConnectionManager.getEarthQueryFactory(workspaceId);
            QSite qSite = QSite.newInstance();
            QBean<Site> selectList = Projections.bean(Site.class, qSite.all());
            List<Site> sites = earthQueryFactory.select(selectList)
                .from(qSite)
                .where(qSite.dataDirectoryId.in(directoryIds)).fetch();
            if (sites != null) {
                records = sites.size();
            }
        } catch (Exception ex) {
            throw new EarthException(ex);
        }
        return records;
    }

    @Override
    public boolean checkExistSiteId(List<Integer> siteIds, String workspaceId) throws EarthException {
        long records = 0L;
        try {
            EarthQueryFactory earthQueryFactory = ConnectionManager.getEarthQueryFactory(workspaceId);
            QStrageFile qStrageFile = QStrageFile.newInstance();
            QBean<StrageFile> selectList = Projections.bean(StrageFile.class, qStrageFile.all());

            List<StrageFile> strageFiles = earthQueryFactory.select(selectList)
                .from(qStrageFile)
                .where(qStrageFile.siteId.in(siteIds)).fetch();
            if (strageFiles != null) {
                records = strageFiles.size();
            }
        } catch (Exception ex) {
            throw new EarthException(ex);
        }
        return records > 0L;
    }
}
