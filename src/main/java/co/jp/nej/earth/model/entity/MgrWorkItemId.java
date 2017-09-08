package co.jp.nej.earth.model.entity;

import co.jp.nej.earth.model.BaseModel;
import co.jp.nej.earth.model.sql.QMgrWorkItemId;

public class MgrWorkItemId extends BaseModel<MgrWorkItemId> {

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

    public MgrWorkItemId() {
        this.setqObj(QMgrWorkItemId.newInstance());
    }
}
