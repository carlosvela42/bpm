package co.jp.nej.earth.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.Predicate;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.model.constant.Constant;
import co.jp.nej.earth.model.entity.StrCal;
import co.jp.nej.earth.model.sql.QStrCal;

@Repository
public class StrCalDaoImpl extends BaseDaoImpl<StrCal> implements StrCalDao {

    private static final QStrCal qStrCal = QStrCal.newInstance();

    public StrCalDaoImpl() throws Exception {
        super();
    }

    @Override
    public long deleteListByProfileIds(List<String> profileIds) throws EarthException {
        try {
            List<Map<Path<?>, Object>> conditions = new ArrayList<>();
            for (String profileId : profileIds) {
                Map<Path<?>, Object> condition = new HashMap<>();
                condition.put(qStrCal.profileId, profileId);
                conditions.add(condition);
            }
            return this.deleteList(Constant.EARTH_WORKSPACE_ID, conditions);
        } catch (Exception ex) {
            throw new EarthException(ex);
        }
    }

    @Override
    public List<StrCal> getListByProfileIds(List<String> profileIds) throws EarthException {
        try {
            BooleanBuilder condition = new BooleanBuilder();
            Predicate pre1 = qStrCal.profileId.in(profileIds);
            condition.and(pre1);
            return this.search(Constant.EARTH_WORKSPACE_ID, condition);
        } catch (Exception ex) {
            throw new EarthException(ex);
        }
    }
}
