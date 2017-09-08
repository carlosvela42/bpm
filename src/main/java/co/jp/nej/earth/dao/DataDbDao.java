package co.jp.nej.earth.dao;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.model.Document;
import co.jp.nej.earth.model.entity.StrDataDb;

public interface DataDbDao extends BaseDao<StrDataDb> {
    boolean isExistDocumentDb(String workspaceId, Document document) throws EarthException;
}
