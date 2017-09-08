package co.jp.nej.earth.web.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SearchClientForm implements Serializable {
    private static final long serialVersionUID = 1708800705518475391L;
    private List<String> searchColumns = new ArrayList<>();

    public List<String> getSearchColumns() {
        return searchColumns;
    }

    public void setSearchColumns(List<String> searchColumns) {
        this.searchColumns = searchColumns;
    }
}
