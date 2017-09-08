package co.jp.nej.earth.web.form;

import co.jp.nej.earth.contraints.NotEmptyAndValidSize;
import co.jp.nej.earth.model.Field;
import co.jp.nej.earth.model.constant.Constant;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

public class TemplateForm {

    private String templateId;

    @NotEmptyAndValidSize(notEmpty = true, max = Constant.Regexp.MAX_LENGTH,
        messageNotEmpty = "E0001,template.name",
        messageLengthMax = "E0026,template.name,255")
    private String templateName;

    private String lastUpdateTime;

    public String getWorkspaceId() {
        return workspaceId;
    }

    public void setWorkspaceId(String workspaceId) {
        this.workspaceId = workspaceId;
    }

    private String templateTableName;

    @NotEmpty(message = "E0001,template.definition")
    private List<Field> templateFields;

    private String workspaceId;

    public List<Field> getTemplateFields() {
        return templateFields;
    }

    public void setTemplateFields(List<Field> templateFields) {
        this.templateFields = templateFields;
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

    public String getTemplateTableName() {
        return templateTableName;
    }

    public void setTemplateTableName(String templateTableName) {
        this.templateTableName = templateTableName;
    }

    /**
     * @return the lastUpdateTime
     */
    public String getLastUpdateTime() {
        return lastUpdateTime;
    }

    /**
     * @param lastUpdateTime the lastUpdateTime to set
     */
    public void setLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

}
