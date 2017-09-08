package co.jp.nej.earth.service;

import co.jp.nej.earth.dao.SiteDao;
import co.jp.nej.earth.dao.WorkspaceDao;
import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.model.Message;
import co.jp.nej.earth.model.MgrWorkspace;
import co.jp.nej.earth.model.MultipleTransactionManager;
import co.jp.nej.earth.model.Site;
import co.jp.nej.earth.model.TransactionManager;
import co.jp.nej.earth.model.constant.Constant;
import co.jp.nej.earth.model.sql.QSite;
import co.jp.nej.earth.util.ConversionUtil;
import co.jp.nej.earth.util.EMessageResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SiteServiceImpl extends BaseService implements SiteService {

    @Autowired
    private SiteDao siteDao;

    @Autowired
    private EMessageResource eMessageResource;

    @Autowired
    private WorkspaceDao workspaceDao;

    private static final Logger LOG = LoggerFactory.getLogger(SiteServiceImpl.class);

    @Override
    public List<Site> getAllSites() throws EarthException {
        return ConversionUtil.castList(executeTransaction(Constant.EARTH_WORKSPACE_ID, () -> {
            return siteDao.findAll(Constant.EARTH_WORKSPACE_ID, QSite.newInstance().siteId.asc());
        }), Site.class);
    }

    @Override
    public List<Integer> getAllSiteIds() throws EarthException {
        return ConversionUtil.castList(executeTransaction(() -> {
            return siteDao.getAllSiteIds();
        }), Integer.class);
    }

    @Override
    public boolean deleteSites(List<Integer> siteIds, String workspaceId) throws EarthException {
        return (boolean) executeTransaction(workspaceId, () -> {
            return siteDao.deleteSites(siteIds, workspaceId) > 0;
        });
    }

    @Override
    public boolean insertOne(String siteId, List<String> directoryIds, String workspaceId) throws EarthException {
        return (boolean) executeTransaction(workspaceId, () -> {
            return siteDao.insertOne(siteId, directoryIds, workspaceId) > 0;
        });
    }

    @Override
    public boolean updateSite(String siteId, List<String> directoryIds, String workspaceId) throws EarthException {
        return (boolean) executeTransaction(workspaceId, () -> {
            siteDao.deleteSite(siteId, workspaceId);
            siteDao.insertOne(siteId, directoryIds, workspaceId);
            return true;
        });
    }

    @Override
    public List<Message> validateDelete(List<Integer> siteIds) throws EarthException {
        MultipleTransactionManager multipleTransactionManager = new MultipleTransactionManager();
        multipleTransactionManager.add(new TransactionManager(Constant.EARTH_WORKSPACE_ID));
        boolean exist = false;
        try {
            List<MgrWorkspace> mgrWorkspaces = workspaceDao.getAll();
            if (mgrWorkspaces != null && mgrWorkspaces.size() > 0) {
                for (MgrWorkspace mgrWorkspace : mgrWorkspaces) {
                    String workspaceId = mgrWorkspace.getStringWorkspaceId();
                    multipleTransactionManager.add(new TransactionManager(workspaceId));
                    if (siteDao.checkExistSiteId(siteIds, workspaceId)) {
                        exist = true;
                        break;
                    }
                }
            }
            multipleTransactionManager.commit();
        } catch (EarthException ex) {
            // Rollback transactions.
            multipleTransactionManager.rollback();
            LOG.error(ex.getMessage());
            exist = true;
        }
        List<Message> messages = new ArrayList<>();
        if (exist) {
            Message message = new Message(Constant.ErrorCode.E0034, eMessageResource.get(Constant.ErrorCode.E0034,
                new String[]{Constant.ScreenItem.SITE}));
            messages.add(message);
        }
        return messages;
    }
}
