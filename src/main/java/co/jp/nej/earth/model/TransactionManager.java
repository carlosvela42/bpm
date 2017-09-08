package co.jp.nej.earth.model;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.manager.connection.ConnectionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * Created by minhtv on 4/24/2017.
 */
public class TransactionManager {
    private PlatformTransactionManager manager;
    private TransactionDefinition txDef;
    private TransactionStatus txStatus;

    public TransactionManager(String workspaceId) throws EarthException {
        this.manager = ConnectionManager.getTransactionManager(workspaceId);
        this.txDef = new DefaultTransactionDefinition();
        this.txStatus = manager.getTransaction(txDef);
    }

    /**
     * Function to rollback transaction, only rollback when commit is not completed
     * @param transactionMgr
     */
    public static void rollbackWithCheck(TransactionManager transactionMgr) {
        if (transactionMgr != null && !transactionMgr.getTxStatus().isCompleted()) {
            transactionMgr.getManager().rollback(transactionMgr.getTxStatus());
        }
    }

    public PlatformTransactionManager getManager() {
        return manager;
    }

    public void setManager(PlatformTransactionManager manager) {
        this.manager = manager;
    }

    public TransactionDefinition getTxDef() {
        return txDef;
    }

    public void setTxDef(TransactionDefinition txDef) {
        this.txDef = txDef;
    }

    public TransactionStatus getTxStatus() {
        return txStatus;
    }

    public void setTxStatus(TransactionStatus txStatus) {
        this.txStatus = txStatus;
    }

    public void rollback() {
        this.manager.rollback(this.txStatus);
    }

    public void commit() {
        this.manager.commit(this.txStatus);
    }
}
