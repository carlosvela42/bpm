package co.jp.nej.earth.dao;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Path;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.model.entity.MgrEventId;
import co.jp.nej.earth.model.sql.QMgrEventId;
import co.jp.nej.earth.util.DateUtil;

@Repository
public class EventIdDaoImpl extends BaseDaoImpl<MgrEventId> implements EventIdDao {

    private static final QMgrEventId qMgrEventId = QMgrEventId.newInstance();

    private static final Logger LOG = LoggerFactory.getLogger(EventIdDaoImpl.class);

    public EventIdDaoImpl() throws Exception {
        super();
    }

    public MgrEventId getByIssueToday() throws EarthException {
        return getByIssueDate(DateUtil.getCurrentShortDateString());
    }

    public MgrEventId getByIssueDate(String issueDate) throws EarthException {
        Map<Path<?>, Object> condition = new HashMap<>();
        condition.put(qMgrEventId.issueDate, issueDate);
        return this.findOne(condition);
    }
}
