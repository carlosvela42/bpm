package co.jp.nej.earth.model.ws;

import java.util.HashMap;
import java.util.Map;

public class SearchWorkItemResponse extends Response {
    private Map<String, Object> workItemResult;

    public SearchWorkItemResponse(boolean result, Map<String, Object> workItems) {
        super(result);
        if (workItemResult == null) {
            workItemResult = new HashMap<>();
        }
        workItemResult.putAll(workItems);
    }

    public Map<String, Object> getWorkItemResult() {
        return workItemResult;
    }

    public void setWorkItemResult(Map<String, Object> workItemResult) {
        this.workItemResult = workItemResult;
    }
}
