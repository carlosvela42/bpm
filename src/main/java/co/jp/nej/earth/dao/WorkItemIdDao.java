package co.jp.nej.earth.dao;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.model.entity.MgrWorkItemId;

public interface WorkItemIdDao extends BaseDao<MgrWorkItemId> {
    MgrWorkItemId getByIssueToday() throws EarthException;
    MgrWorkItemId getByIssueDate(String issueDate) throws EarthException;
    Long update(MgrWorkItemId workItemId) throws EarthException;
}
