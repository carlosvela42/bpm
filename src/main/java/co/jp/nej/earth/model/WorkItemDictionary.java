package co.jp.nej.earth.model;

import java.util.HashMap;
import java.util.Map;

public class WorkItemDictionary {
    private Map<String, Object> workItemDictionary = new HashMap<>();

    /**
     * @return the workItemDictionary
     */
    public Map<String, Object> getWorkItemDictionary() {
        return workItemDictionary;
    }

    /**
     * @param workItemDictionary the workItemDictionary to set
     */
    public void setWorkItemDictionary(Map<String, Object> workItemDictionary) {
        this.workItemDictionary = workItemDictionary;
    }

    public Object get(String key) {
        return workItemDictionary.get(key);
    }

    public void put(String key, Object value) {
        workItemDictionary.put(key, value);
    }

    public int size() {
        return workItemDictionary.size();
    }

    public boolean exists(String id) {
        return workItemDictionary.containsKey(id);
    }
}
