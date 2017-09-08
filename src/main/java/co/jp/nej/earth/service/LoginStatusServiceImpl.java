package co.jp.nej.earth.service;

import co.jp.nej.earth.dao.LoginControlDao;
import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.model.constant.Constant;
import co.jp.nej.earth.model.entity.CtlLogin;
import co.jp.nej.earth.model.sql.QCtlLogin;
import co.jp.nej.earth.util.ConversionUtil;
import co.jp.nej.earth.util.DateUtil;
import co.jp.nej.earth.web.form.SearchByColumnsForm;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by p-dcv-minhtv on 5/8/2017.
 */
@Service
public class LoginStatusServiceImpl extends BaseService implements LoginStatusService {

    private static final Logger LOG = LoggerFactory.getLogger(LoginStatusServiceImpl.class);

    @Autowired
    private LoginControlDao loginControlDao;

    @Override
    public List<CtlLogin> getAll(Long offset, Long limit, OrderSpecifier<String> orderByColumn)
        throws EarthException {
        return ConversionUtil.castList(executeTransaction(() -> {
            List<CtlLogin> ctlLogins = new ArrayList<CtlLogin>();
            QCtlLogin qCtlLogin = QCtlLogin.newInstance();
            try {
                OrderSpecifier<String> stringOrderSpecifier =
                    new OrderSpecifier<String>(Order.DESC, qCtlLogin.loginTime);
                List<OrderSpecifier<?>> orderBys = new ArrayList<>();
                orderBys.add(stringOrderSpecifier);

                ctlLogins = loginControlDao.findAll(Constant.EARTH_WORKSPACE_ID, offset, limit, orderBys, null);

                for (CtlLogin ctlLogin : ctlLogins) {
                    ctlLogin.setLoginTime(DateUtil.convertStringToDateFormat(ctlLogin.getLoginTime()));
                    ctlLogin.setLogoutTime(DateUtil.convertStringToDateFormat(ctlLogin.getLogoutTime()));
                }
                return ctlLogins;
            } catch (Exception ex) {
                LOG.error(ex.getMessage());
                return ctlLogins;
            }
        }), CtlLogin.class);
    }

    @Override
    public List<CtlLogin> getAllByColumn(SearchByColumnsForm searchByColumnsForm, OrderSpecifier<String> orderByColumn)
        throws EarthException {

        return ConversionUtil.castList(executeTransaction(() -> {
            QCtlLogin qCtlLogin = QCtlLogin.newInstance();
            SearchColumn searchColumn = new SearchColumn();
            BooleanBuilder condition =
                searchColumn.searchColumns(qCtlLogin, searchByColumnsForm.getValid(),
                    searchByColumnsForm.getSearchByColumnForms());
            List<CtlLogin> ctlLogins = new ArrayList<CtlLogin>();
            OrderSpecifier<String> stringOrderSpecifier = new OrderSpecifier<String>(Order.ASC, qCtlLogin.userId);
            List<OrderSpecifier<?>> orderBys = new ArrayList<>();
            orderBys.add(stringOrderSpecifier);

            ctlLogins = loginControlDao.search(Constant.EARTH_WORKSPACE_ID, condition,
                searchByColumnsForm.getSkip(), searchByColumnsForm.getLimit(), orderBys, null);
            for (CtlLogin ctlLogin : ctlLogins) {
                ctlLogin.setLoginTime(DateUtil.convertStringToDateFormat(ctlLogin.getLoginTime()));
                ctlLogin.setLogoutTime(DateUtil.convertStringToDateFormat(ctlLogin.getLogoutTime()));
            }

            return ctlLogins;
        }), CtlLogin.class);
    }

    @Override
    public boolean deleteList(List<String> sessionIds) throws EarthException {
        return (boolean) executeTransaction(() -> {
            QCtlLogin qCtlLogin = QCtlLogin.newInstance();
            long del = 0L;
            List<Map<Path<?>, Object>> conditionLogAccesses = new ArrayList<>();
            for (String sessionId : sessionIds) {
                Map<Path<?>, Object> condition = new HashMap<>();
                condition.put(qCtlLogin.sessionId, sessionId);
                conditionLogAccesses.add(condition);
            }
            del = loginControlDao.deleteList(Constant.EARTH_WORKSPACE_ID, conditionLogAccesses);
            return del == sessionIds.size();
        });
    }
}
