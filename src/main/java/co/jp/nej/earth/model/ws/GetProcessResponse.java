package co.jp.nej.earth.model.ws;

import co.jp.nej.earth.model.DatProcess;

public class GetProcessResponse extends Response {
    public GetProcessResponse(boolean result) {
        super(result);
    }

    private DatProcess processResult;

    /**
     * @return the processResult
     */
    public DatProcess getProcessResult() {
        return processResult;
    }

    /**
     * @param processResult the processResult to set
     */
    public void setProcessResult(DatProcess processResult) {
        this.processResult = processResult;
    }
}
