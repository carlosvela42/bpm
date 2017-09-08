package co.jp.nej.earth.model.ws;

import co.jp.nej.earth.model.DatProcess;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;

public class UpdateProcessRequest extends Request {
    @NotEmpty(message = "E0001,workspaceId")
    private String workspaceId;
    @Valid
    private DatProcess datProcess;

    /**
     * @return the workspaceId
     */
    public String getWorkspaceId() {
        return workspaceId;
    }

    /**
     * @param workspaceId the workspaceId to set
     */
    public void setWorkspaceId(String workspaceId) {
        this.workspaceId = workspaceId;
    }

    /**
     * @return the datProcess
     */
    public DatProcess getDatProcess() {
        return datProcess;
    }

    /**
     * @param datProcess the datProcess to set
     */
    public void setDatProcess(DatProcess datProcess) {
        this.datProcess = datProcess;
    }

}
