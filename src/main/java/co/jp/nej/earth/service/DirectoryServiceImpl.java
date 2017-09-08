package co.jp.nej.earth.service;

import co.jp.nej.earth.dao.DirectoryDao;
import co.jp.nej.earth.dao.SiteDao;
import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.model.Directory;
import co.jp.nej.earth.model.Message;
import co.jp.nej.earth.model.constant.Constant;
import co.jp.nej.earth.model.constant.Constant.ErrorCode;
import co.jp.nej.earth.model.sql.QDirectory;
import co.jp.nej.earth.util.ConversionUtil;
import co.jp.nej.earth.util.EMessageResource;
import co.jp.nej.earth.util.EStringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DirectoryServiceImpl extends BaseService implements DirectoryService {
    @Autowired
    private DirectoryDao directoryDao;

    @Autowired
    private SiteDao siteDao;

    @Autowired
    private EMessageResource messageSource;

    @Override
    public List<Directory> getAllDirectories() throws EarthException {
        return ConversionUtil.castList(executeTransaction(Constant.EARTH_WORKSPACE_ID, () -> {
            return directoryDao.getAll(Constant.EARTH_WORKSPACE_ID, QDirectory.newInstance().dataDirectoryId.asc());
        }), Directory.class);
    }

    @Override
    public List<Integer> getAllDirectoryIds(String siteId, String workspaceId) throws EarthException {
        return ConversionUtil.castList(executeTransaction(workspaceId, () -> {
            return directoryDao.getDirectoryIds(Integer.valueOf(siteId), workspaceId);
        }), Integer.class);
    }

    @Override
    public List<Directory> getAllDirectoriesBySite(String siteId, String workspaceId) throws EarthException {
        return ConversionUtil.castList(executeTransaction(workspaceId, () -> {
            return directoryDao.getDirectoriesBySite(Integer.valueOf(siteId));
        }), Directory.class);
    }

    @Override
    public boolean deleteDirectories(List<Integer> directoryIds, String workspaceId) throws EarthException {
        return (boolean) executeTransaction(workspaceId, () -> {
            return directoryDao.deleteDirectorys(directoryIds, workspaceId) > 0L;
        });
    }

    @Override
    public List<Message> validate(Directory directory, String workspaceId) throws EarthException {
        List<Message> messages = new ArrayList<>();

        String reservedDiskVolSize = directory.getReservedDiskVolSize();

        if (!EStringUtil.isNumeric(reservedDiskVolSize)) {
            Message message = new Message(ErrorCode.E0008,
                messageSource.get(ErrorCode.E0008, new String[]{"reserved.disk.vol"}));
            messages.add(message);
            return messages;
        }

        String diskVolSize = directory.getDiskVolSize();
        if (!EStringUtil.isNumeric(diskVolSize)) {
            Message message = new Message(ErrorCode.E0008,
                messageSource.get(ErrorCode.E0008, new String[]{"disk.vol"}));
            messages.add(message);
            return messages;
        }

        if (EStringUtil.parseInt(diskVolSize) == 0) {
            Message message = new Message(ErrorCode.W0001,
                messageSource.get(ErrorCode.W0001, new String[]{"disk.vol"}), Message.MessageTye.WARNING);
            messages.add(message);
        }

        if (Integer.parseInt(directory.getReservedDiskVolSize()) > Integer
            .parseInt(directory.getDiskVolSize())) {
            Message message = new Message(ErrorCode.W0002,
                messageSource.get(ErrorCode.W0002, new String[]{"reserved.disk.vol", "disk.vol"}),
                Message.MessageTye.WARNING);
            messages.add(message);
            return messages;
        }

        return messages;
    }

    @Override
    public List<Message> validateDelete(List<Integer> directoryIds, String workspaceId) throws EarthException {
        return ConversionUtil.castList(executeTransaction(workspaceId, () -> {
            List<Message> messages = new ArrayList<Message>();
            if (siteDao.getSiteByDataDirectoryIds(directoryIds,workspaceId)>0) {
                Message message = new Message(ErrorCode.E0034,
                    messageSource.get(ErrorCode.E0034, new String[]{"directory"}));
                messages.add(message);
            }
            return messages;
        }), Message.class);
    }

    @Override
    public boolean insertOne(Directory directory, String workspaceId) throws EarthException {
        return (boolean) executeTransaction(workspaceId, () -> {
            return directoryDao.insertOne(directory, workspaceId) > 0;
        });
    }

    @Override
    public Directory getById(String dataDirectoryId, String workspaceId) throws EarthException {
        return ConversionUtil.castObject(executeTransaction(workspaceId, () -> {
            return directoryDao.getById(Integer.valueOf(dataDirectoryId));
        }), Directory.class);
    }

    @Override
    public boolean updateDirectory(Directory directory, String workspaceId) throws EarthException {
        return (boolean) executeTransaction(workspaceId, () -> {
            return directoryDao.updateOne(directory, workspaceId) > 0;
        });
    }
}
