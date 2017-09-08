package co.jp.nej.earth.model.entity;

import co.jp.nej.earth.model.BaseModel;
import co.jp.nej.earth.model.sql.QMgrProcessService;

public class MgrProcessService extends BaseModel<MgrProcessService> {

    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = 1L;

    private int processIServiceId;
    private int workspaceId;
    private String processIServiceName;

    public MgrProcessService() {
        this.setqObj(QMgrProcessService.newInstance());
    }

    public MgrProcessService(int processIServiceId, int workspaceId, String processIServiceName) {
        this();
        this.processIServiceId = processIServiceId;
        this.workspaceId = workspaceId;
        this.processIServiceName = processIServiceName;
    }

    public int getProcessIServiceId() {
        return processIServiceId;
    }

    public void setProcessIServiceId(int processIServiceId) {
        this.processIServiceId = processIServiceId;
    }

    public int getWorkspaceId() {
        return workspaceId;
    }

    public void setWorkspaceId(int workspaceId) {
        this.workspaceId = workspaceId;
    }

    public String getProcessIServiceName() {
        return processIServiceName;
    }

    public void setProcessIServiceName(String processIServiceName) {
        this.processIServiceName = processIServiceName;
    }
}
