package co.jp.nej.earth.dao;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.model.entity.MgrEventId;

public interface EventIdDao extends BaseDao<MgrEventId> {

    MgrEventId getByIssueToday() throws EarthException;

    MgrEventId getByIssueDate(String issueDate) throws EarthException;

}
