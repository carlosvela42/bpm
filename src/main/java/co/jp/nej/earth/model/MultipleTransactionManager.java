package co.jp.nej.earth.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by minhtv on 4/24/2017.
 */
public class MultipleTransactionManager {
    private List<TransactionManager> transactionManagers = new ArrayList<>();

    public List<TransactionManager> getTransactionManagers() {
        return transactionManagers;
    }

    public void setTransactionManagers(List<TransactionManager> transactionManagers) {
        this.transactionManagers = transactionManagers;
    }

    public void rollback() {
        for (int i = transactionManagers.size() - 1; i >= 0; i--) {
            this.transactionManagers.get(i).rollback();
        }

    }

    public void commit() {
        for (int i = transactionManagers.size() - 1; i >= 0; i--) {
            this.transactionManagers.get(i).commit();
        }
    }

    public void add(TransactionManager transactionManager) {
        transactionManagers.add(transactionManager);
    }
}


