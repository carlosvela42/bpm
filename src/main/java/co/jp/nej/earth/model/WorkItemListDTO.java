package co.jp.nej.earth.model;

public class WorkItemListDTO {
    private String workitemId;
    private String statusLock;
    private String taskName;
    private String templateId;
    private String templateName;
    private String eventId;

    public String getWorkitemId() {
        return workitemId;
    }

    public void setWorkitemId(String workitemId) {
        this.workitemId = workitemId;
    }

    public String getStatusLock() {
        return statusLock;
    }

    public void setStatusLock(String statusLock) {
        this.statusLock = statusLock;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }
}
