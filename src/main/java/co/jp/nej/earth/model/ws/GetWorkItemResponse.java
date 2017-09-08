package co.jp.nej.earth.model.ws;

import java.util.ArrayList;
import java.util.List;

import co.jp.nej.earth.model.WorkItem;

public class GetWorkItemResponse extends Response {
    private List<WorkItem> workItemResult;

    public GetWorkItemResponse(boolean result) {
        super(result);
    }

    public GetWorkItemResponse(boolean result, WorkItem workItem) {
        super(result);
        if (workItemResult == null) {
            workItemResult = new ArrayList<>();
        }
        workItemResult.add(workItem);
    }

    /**
     * @param workItemResult
     */
    public GetWorkItemResponse(boolean result, List<WorkItem> workItemResult) {
        super(result);
        this.workItemResult = workItemResult;
    }

    /**
     * @return the workItemResult
     */
    public List<WorkItem> getWorkItemResult() {
        return workItemResult;
    }

    /**
     * @param workItemResult the workItemResult to set
     */
    public void setWorkItemResult(List<WorkItem> workItemResult) {
        this.workItemResult = workItemResult;
    }

}
