package co.jp.nej.earth.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.jp.nej.earth.dao.MstSystemDao;
import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.model.constant.Constant;
import co.jp.nej.earth.model.constant.Constant.AgentBatch;
import co.jp.nej.earth.model.constant.Constant.DatePattern;
import co.jp.nej.earth.model.entity.MstSystem;

/**
 * @author p-tvo-sonta
 */
@Service
public class SystemConfigurationServiceImpl extends BaseService implements SystemConfigurationService {

    @Autowired
    private MstSystemDao mstSystemDao;

    /**
     * {@inheritDoc}
     */
    @Override
    public int updateOperationDate() throws EarthException {
        return (int) this.executeTransaction(Constant.EARTH_WORKSPACE_ID, () -> {
            try {
                // get system config from db
                MstSystem mstSystem = mstSystemDao.getMstSystemBySectionAndVariable(AgentBatch.OPERATION_DATE,
                        AgentBatch.CURRENT_DATE);
                SimpleDateFormat formatter = new SimpleDateFormat(DatePattern.YYYYMMDD);
                Calendar configDate = Calendar.getInstance();
                if (mstSystem != null) {
                    try {
                        // set value of configDate by system date
                        configDate.setTime(formatter.parse(mstSystem.getConfigValue()));
                    } catch (ParseException e) {
                        throw new EarthException(e.getMessage());
                    }

                    // add systemdate to 1 day
                    configDate.add(Calendar.DATE, 1);
                    // update system date to system config.
                    return (int) mstSystemDao.updateMstSystem(AgentBatch.OPERATION_DATE, AgentBatch.CURRENT_DATE,
                            formatter.format(configDate.getTime()));
                } else {
                    // update system date to system config.
                    return (int) mstSystemDao.insertMstSystem(AgentBatch.OPERATION_DATE,
                            AgentBatch.CURRENT_DATE, formatter.format(configDate.getTime()));
                }

            } catch (Exception e) {
                throw new EarthException(e);
            }
        });
    }

    @Override
    public boolean updateSystemConfig(String inputDate) throws EarthException {
        return (boolean) this.executeTransaction(Constant.EARTH_WORKSPACE_ID, () -> {
            // Update system date to system configuration.
            return mstSystemDao.updateMstSystem(AgentBatch.OPERATION_DATE, AgentBatch.CURRENT_DATE, inputDate) > 0;
        });
    }
}