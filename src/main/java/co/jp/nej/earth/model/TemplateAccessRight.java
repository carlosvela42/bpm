package co.jp.nej.earth.model;

import co.jp.nej.earth.model.enums.AccessRight;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class TemplateAccessRight implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private Map<TemplateKey, AccessRight> templatesAccessRights = new HashMap<>();

    public AccessRight get(TemplateKey key) {
        AccessRight accessRight = templatesAccessRights.get(key);
        if (accessRight == null) {
            return AccessRight.FULL;
        }
        return accessRight;
    }

    /**
     * @return the templatesAccessRights
     */
    public Map<TemplateKey, AccessRight> getTemplatesAccessRights() {
        return templatesAccessRights;
    }

    /**
     * @param templatesAccessRights the templatesAccessRights to set
     */
    public void setTemplatesAccessRights(Map<TemplateKey, AccessRight> templatesAccessRights) {
        this.templatesAccessRights = templatesAccessRights;
    }

    public void putAll(Map<TemplateKey, AccessRight> accessMap) {
        this.templatesAccessRights.putAll(accessMap);
    }

    public void put(TemplateKey key, AccessRight value) {
        templatesAccessRights.put(key, value);
    }

    public void putAll(TemplateKey key, AccessRight value) {
        templatesAccessRights.put(key, value);
    }

    public int size() {
        return templatesAccessRights.size();
    }

    public boolean exists(String id) {
        return templatesAccessRights.containsKey(id);
    }
}
