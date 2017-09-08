package co.jp.nej.earth.service;

import co.jp.nej.earth.dao.MstSystemDao;
import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.model.constant.Constant;
import co.jp.nej.earth.model.entity.MstSystem;
import co.jp.nej.earth.util.ConversionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MstSystemServiceImpl extends BaseService implements MstSystemService {

    @Autowired
    private MstSystemDao mstSystemDao;

    public List<MstSystem> getMstSystem() throws EarthException {
        return ConversionUtil.castList(executeTransaction(Constant.EARTH_WORKSPACE_ID, () -> {
            return mstSystemDao.getMstSystem();
        }), MstSystem.class);
    }

    @Override
    public List<MstSystem> getMstSystemBySection(String section) throws EarthException {
        return ConversionUtil.castList(executeTransaction(Constant.EARTH_WORKSPACE_ID, () -> {
            return mstSystemDao.getMstSystemBySection(section);
        }), MstSystem.class);
    }
}
