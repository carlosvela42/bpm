package co.jp.nej.earth.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.jp.nej.earth.dao.MstCodeDao;
import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.model.constant.Constant;

@Service
public class MstCodeServiceImpl extends BaseService implements MstCodeService {

    @Autowired
    private MstCodeDao mstCodeDao;

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, String> getMstCodesBySection(String section) throws EarthException {
        return (Map<String, String>) executeTransaction(Constant.EARTH_WORKSPACE_ID, () -> {
            return mstCodeDao.getMstCodesBySection(section);
        });
    }
}
