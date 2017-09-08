package co.jp.nej.earth.dao;

import java.util.Map;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.model.entity.MstCode;

public interface MstCodeDao extends BaseDao<MstCode> {
    Map<String, String> getMstCodesBySection(String section) throws EarthException;
    String getMstCodesByCodeId(String section, String codeId) throws EarthException;
}
