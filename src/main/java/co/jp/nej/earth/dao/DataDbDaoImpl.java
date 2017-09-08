package co.jp.nej.earth.dao;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.manager.connection.ConnectionManager;
import co.jp.nej.earth.model.Document;
import co.jp.nej.earth.model.entity.StrDataDb;
import co.jp.nej.earth.model.sql.QStrDataDb;
import org.springframework.stereotype.Repository;

@Repository
public class DataDbDaoImpl extends BaseDaoImpl<StrDataDb> implements DataDbDao {

    public DataDbDaoImpl() throws Exception {
        super();
    }

    @Override
    public boolean isExistDocumentDb(String workspaceId, Document document) throws EarthException {
        QStrDataDb qStrDataFile = QStrDataDb.newInstance();
        return ConnectionManager.getEarthQueryFactory(workspaceId)
            .select(qStrDataFile.documentNo)
            .from(qStrDataFile)
            .where(qStrDataFile.workitemId.eq(document.getWorkitemId()))
            .where(qStrDataFile.folderItemNo.eq(document.getFolderItemNo()))
            .where(qStrDataFile.documentNo.eq(document.getDocumentNo()))
            .fetchCount() > 0;
    }
}
