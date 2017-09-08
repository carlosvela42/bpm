package co.jp.nej.earth.model;

import java.util.List;

public class Row {
    private String workitemId;

    private List<Column> columns;

    public String getWorkitemId() {
        return workitemId;
    }

    public void setWorkitemId(String workitemId) {
        this.workitemId = workitemId;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }
}
