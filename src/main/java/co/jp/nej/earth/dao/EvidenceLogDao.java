package co.jp.nej.earth.dao;

import co.jp.nej.earth.exception.EarthException;

import java.util.List;

/**
 * Created by minhtv on 3/29/2017.
 */
public interface EvidenceLogDao {
    boolean deleteListByUserIds(List<String> userIds) throws EarthException;
}
