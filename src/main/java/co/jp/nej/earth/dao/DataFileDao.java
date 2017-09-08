package co.jp.nej.earth.dao;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.model.Document;
import co.jp.nej.earth.model.entity.StrDataFile;

public interface DataFileDao extends BaseDao<StrDataFile> {
    boolean isExistDocumentFile(String workspaceId, Document document) throws EarthException;
}
