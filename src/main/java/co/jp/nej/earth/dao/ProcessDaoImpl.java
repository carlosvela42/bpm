package co.jp.nej.earth.dao;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.manager.connection.ConnectionManager;
import co.jp.nej.earth.model.MgrProcess;
import co.jp.nej.earth.model.sql.QMgrProcess;
import org.springframework.stereotype.Repository;

/**
 * @author p-tvo-sonta
 */
@Repository
public class ProcessDaoImpl extends BaseDaoImpl<MgrProcess> implements ProcessDao {

    public ProcessDaoImpl() throws Exception {
        super();
    }

    @Override
    public boolean isExistProcess(String workspaceId, Integer processId) throws EarthException {
        return (boolean) executeWithException(() -> {
            QMgrProcess qMgrProcess = QMgrProcess.newInstance();
            return ConnectionManager.getEarthQueryFactory(workspaceId)
                .select(qMgrProcess.processId)
                .from(qMgrProcess)
                .where(qMgrProcess.processId.eq(processId)).fetchCount() > 0;
        });
    }
}
