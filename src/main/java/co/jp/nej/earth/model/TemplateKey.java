package co.jp.nej.earth.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class TemplateKey implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String workspaceId;
    private String templateId;

    public TemplateKey() {
    }

    public TemplateKey(String workspaceId, String templateId) {
        this.workspaceId = workspaceId;
        this.templateId = templateId;
    }

    public String getWorkspaceId() {
        return workspaceId;
    }

    public void setWorkspaceId(String workspaceId) {
        this.workspaceId = workspaceId;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((templateId == null) ? 0 : templateId.hashCode());
        result = prime * result + ((workspaceId == null) ? 0 : workspaceId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        TemplateKey other = (TemplateKey) obj;
        if (templateId == null) {
            if (other.templateId != null) {
                return false;
            }
        } else if (!templateId.equals(other.templateId)) {
            return false;
        }
        if (workspaceId == null) {
            if (other.workspaceId != null) {
                return false;
            }
        } else if (!workspaceId.equals(other.workspaceId)) {
            return false;
        }
        return true;
    }

    @Override public String toString() {
        Map map = new HashMap<String, String>();
        map.put("workspaceId", workspaceId);
        map.put("templateId", templateId);
        return map.toString();
    }
}
