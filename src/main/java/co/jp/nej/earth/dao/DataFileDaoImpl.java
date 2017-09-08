package co.jp.nej.earth.dao;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.manager.connection.ConnectionManager;
import co.jp.nej.earth.model.Document;
import co.jp.nej.earth.model.entity.StrDataFile;
import co.jp.nej.earth.model.sql.QStrDataFile;
import org.springframework.stereotype.Repository;

@Repository
public class DataFileDaoImpl extends BaseDaoImpl<StrDataFile> implements DataFileDao {

    public DataFileDaoImpl() throws Exception {
        super();
    }

    @Override
    public boolean isExistDocumentFile(String workspaceId, Document document) throws EarthException {
        QStrDataFile qStrDataFile = QStrDataFile.newInstance();
        return ConnectionManager.getEarthQueryFactory(workspaceId)
            .select(qStrDataFile.documentNo)
            .from(qStrDataFile)
            .where(qStrDataFile.workitemId.eq(document.getWorkitemId()))
            .where(qStrDataFile.folderItemNo.eq(document.getFolderItemNo()))
            .where(qStrDataFile.documentNo.eq(document.getDocumentNo()))
            .fetchCount() > 0;
    }
}
