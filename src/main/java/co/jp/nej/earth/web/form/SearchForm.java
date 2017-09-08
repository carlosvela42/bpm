package co.jp.nej.earth.web.form;

import co.jp.nej.earth.model.Column;
import co.jp.nej.earth.model.Row;

import java.util.ArrayList;
import java.util.List;

public class SearchForm {

    private String templateId;

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    private String templateName;
    private String templateType;
    private String templateTableName;

    public String getTemplateTableName() {
        return templateTableName;
    }

    public void setTemplateTableName(String templateTableName) {
        this.templateTableName = templateTableName;
    }

    private List<Row> rows = new ArrayList<Row>();
    private List<Column> columns = new ArrayList<Column>();

    public List<Row> getRows() {
        return rows;
    }

    public void setRows(List<Row> rows) {
        this.rows = rows;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getTemplateType() {
        return templateType;
    }

    public void setTemplateType(String templateType) {
        this.templateType = templateType;
    }

}
