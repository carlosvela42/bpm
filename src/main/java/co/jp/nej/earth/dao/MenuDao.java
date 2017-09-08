package co.jp.nej.earth.dao;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.model.entity.MgrMenu;

import java.util.List;

public interface MenuDao extends BaseDao<MgrMenu> {

    List<MgrMenu> getMenuByProfileId(List<String> profileIds) throws EarthException;

    List<MgrMenu> getAll() throws EarthException;

    MgrMenu showDetail(String functionId) throws EarthException;

}
