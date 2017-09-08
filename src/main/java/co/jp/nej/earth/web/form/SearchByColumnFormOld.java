package co.jp.nej.earth.web.form;

import java.util.List;

public class SearchByColumnFormOld {

    private String templateId;
    private String templateType;
    private String valid;

    private List<ColumnSearch> columnSearchs;

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getTemplateType() {
        return templateType;
    }

    public void setTemplateType(String templateType) {
        this.templateType = templateType;
    }

    public String getValid() {
        return valid;
    }

    public void setValid(String valid) {
        this.valid = valid;
    }

    public List<ColumnSearch> getColumnSearchs() {
        return columnSearchs;
    }

    public void setColumnSearchs(List<ColumnSearch> columnSearchs) {
        this.columnSearchs = columnSearchs;
    }


}
