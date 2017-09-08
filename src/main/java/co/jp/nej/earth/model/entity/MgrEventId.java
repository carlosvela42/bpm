package co.jp.nej.earth.model.entity;

import co.jp.nej.earth.model.BaseModel;
import co.jp.nej.earth.model.sql.QMgrEventId;

public class MgrEventId extends BaseModel<MgrEventId> {
    /**
     * Serial version
     */
    private static final long serialVersionUID = 1L;
    private String issueDate;
    private Integer count;

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public MgrEventId() {
        this.setqObj(QMgrEventId.newInstance());
    }
}
