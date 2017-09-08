package co.jp.nej.earth.service;

import co.jp.nej.earth.dao.StrLogAccessDao;
import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.model.entity.StrLogAccess;
import co.jp.nej.earth.model.sql.QStrLogAccess;
import co.jp.nej.earth.util.ConversionUtil;
import co.jp.nej.earth.util.DateUtil;
import co.jp.nej.earth.web.form.SearchByColumnsForm;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EvidentLogServiceImpl extends BaseService implements EvidentLogService {

    @Autowired
    private StrLogAccessDao strLogAccessDao;

    @Override
    public List<StrLogAccess> getListByWorkspaceId(String workspaceId, Long offset, Long limit,
                                                   List<OrderSpecifier<?>> orderBys) throws EarthException {
        return ConversionUtil.castList(executeTransaction(workspaceId, () -> {
            List<StrLogAccess> strLogAccesses = new ArrayList<StrLogAccess>();
            strLogAccesses = strLogAccessDao.findAll(workspaceId, offset, limit, orderBys, null);
            for (StrLogAccess strLogAccess : strLogAccesses) {
                try {
                    strLogAccess.setProcessTime(DateUtil.convertStringToDateFormat(strLogAccess.getProcessTime()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return strLogAccesses;
        }), StrLogAccess.class);
    }

    @Override
    public List<StrLogAccess> getListByWorkspaceIdColumn(String workspaceId, SearchByColumnsForm searchByColumnsForm,
                                                         List<OrderSpecifier<?>> orderBys) throws EarthException {
        QStrLogAccess qStrLogAccess = QStrLogAccess.newInstance();
        SearchColumn searchColumn = new SearchColumn();
        BooleanBuilder condition =
            searchColumn.searchColumns(qStrLogAccess, searchByColumnsForm.getValid(),
                searchByColumnsForm.getSearchByColumnForms());
        return ConversionUtil.castList(executeTransaction(workspaceId, () -> {
            return strLogAccessDao.searchColumn(workspaceId, condition, searchByColumnsForm.getSkip(),
                searchByColumnsForm.getLimit(), orderBys, null);
        }), StrLogAccess.class);
    }
}
