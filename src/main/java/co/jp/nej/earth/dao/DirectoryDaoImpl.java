package co.jp.nej.earth.dao;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.manager.connection.ConnectionManager;
import co.jp.nej.earth.manager.connection.EarthQueryFactory;
import co.jp.nej.earth.model.Directory;
import co.jp.nej.earth.model.constant.Constant;
import co.jp.nej.earth.model.enums.ColumnNames;
import co.jp.nej.earth.model.sql.QDirectory;
import co.jp.nej.earth.model.sql.QSite;
import co.jp.nej.earth.util.DirectoryUtil;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class DirectoryDaoImpl extends BaseDaoImpl<Directory> implements DirectoryDao {

    public DirectoryDaoImpl() throws Exception {
        super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Directory> getDirectoriesBySite(int siteId) throws EarthException {
        QDirectory qDirectory = QDirectory.newInstance();
        QSite qSite = QSite.newInstance();
        EarthQueryFactory earthQueryFactory = ConnectionManager.getEarthQueryFactory(Constant.EARTH_WORKSPACE_ID);
        try {
            ResultSet resultSet = earthQueryFactory
                .select(qDirectory.dataDirectoryId, qDirectory.diskVolSize, qDirectory.folderPath,
                    qDirectory.reservedDiskVolSize, qDirectory.reservedDiskVolSize, qDirectory.lastUpdateTime)
                .from(qDirectory).innerJoin(qSite).on(qDirectory.dataDirectoryId.eq(qSite.dataDirectoryId))
                .where(qSite.siteId.eq(siteId))
                .orderBy(qDirectory.dataDirectoryId.asc()).getResults();
            List<Directory> directories = new ArrayList<>();

            while (resultSet.next()) {
                Directory directory = new Directory();
                directory.setDataDirectoryId(resultSet.getInt(ColumnNames.DATA_DIRECTORY_ID.toString()));
                directory.setDiskVolSize(resultSet.getString(ColumnNames.DISK_VOL_SIZE.toString()));
                directory.setFolderPath(resultSet.getString(ColumnNames.FOLDER_PATH.toString()));
                directory.setLastUpdateTime(resultSet.getString(ColumnNames.LAST_UPDATE_TIME.toString()));
                directory.setNewCreateFile(resultSet.getInt(ColumnNames.DATA_DIRECTORY_ID.toString()));
                directory.setReservedDiskVolSize(resultSet.getString(ColumnNames.RESERVED_DISK_VOL_SIZE.toString()));
                directories.add(directory);
            }
            return directories;
        } catch (Exception e) {
            throw new EarthException(e);
        }
    }

    @Override
    public List<Directory> getAll(String workspaceId, OrderSpecifier<?> orderBy) throws EarthException {
        List<Directory> directories = new ArrayList<>();
        try {
            directories = findAll(workspaceId, orderBy);
            for (Directory directory : directories) {
                directory.setDiskVolSize(String.valueOf(DirectoryUtil.getSizeFolder(directory.getFolderPath())));
            }
        } catch (Exception ex) {
            throw new EarthException(ex);
        }
        return directories;
    }

    @Override
    public List<Integer> getDirectoryIds(int siteId, String workspaceId) throws EarthException {
        List<Integer> directoryIds = new ArrayList<>();
        try {
            QSite qsite = QSite.newInstance();
            EarthQueryFactory earthQueryFactory = ConnectionManager.getEarthQueryFactory(workspaceId);
            directoryIds = earthQueryFactory
                .select(qsite.dataDirectoryId)
                .from(qsite)
                .where(qsite.siteId.eq(siteId))
                .orderBy(qsite.dataDirectoryId.asc())
                .fetch();
        } catch (Exception ex) {
            throw new EarthException(ex);
        }
        return directoryIds;
    }

    @Override
    public long deleteDirectorys(List<Integer> directoryIds, String workspaceId) throws EarthException {
        List<Map<Path<?>, Object>> conditions = new ArrayList<>();
        try {
            QDirectory qDirectory = QDirectory.newInstance();
            for (Integer directoryId : directoryIds) {
                Map<Path<?>, Object> condition = new HashMap<>();
                condition.put(qDirectory.dataDirectoryId, directoryId);
                conditions.add(condition);
            }
        } catch (Exception ex) {
            throw new EarthException(ex.getMessage());
        }
        return deleteList(workspaceId, conditions);
    }

    @Override
    public Directory getById(int directoryId) throws EarthException {
        Directory directory = new Directory();
        try {
            QDirectory qDirectory = QDirectory.newInstance();
            QBean<Directory> selectList = Projections.bean(Directory.class, qDirectory.all());
            EarthQueryFactory earthQueryFactory = ConnectionManager.getEarthQueryFactory(Constant.EARTH_WORKSPACE_ID);
            directory = earthQueryFactory.select(selectList).from(qDirectory)
                .where(qDirectory.dataDirectoryId.eq(directoryId)).fetchOne();
            if (directory != null) {
                directory.setDiskVolSize(String.valueOf(DirectoryUtil.getSizeFolder(directory.getFolderPath())));
            }
        } catch (Exception ex) {
            throw new EarthException(ex.getMessage());
        }
        return directory;
    }

    @Override
    public long insertOne(Directory directory, String workspaceId) throws EarthException {
        try {
            return add(workspaceId, directory);
        } catch (Exception ex) {
            throw new EarthException(ex);
        }
    }

    @Override
    public long updateOne(Directory directory, String workspaceId) throws EarthException {
        try {
            QDirectory qDirectory = QDirectory.newInstance();
            return ConnectionManager.getEarthQueryFactory(workspaceId).update(qDirectory)
                .set(qDirectory.newCreateFile, directory.getNewCreateFile())
                .set(qDirectory.reservedDiskVolSize, directory.getReservedDiskVolSize())
                .set(qDirectory.diskVolSize, directory.getDiskVolSize())
                .set(qDirectory.folderPath, directory.getFolderPath())
                .where(qDirectory.dataDirectoryId.eq(directory.getDataDirectoryId())).execute();
        } catch (Exception ex) {
            throw new EarthException(ex.getMessage());
        }
    }

}
