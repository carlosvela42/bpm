package co.jp.nej.earth.model.form;

/**
 * Created by p-dcv-minhtv on 5/31/2017.
 */
public class SearchForm {
    private Long limit;
    private Long skip;

    public Long getLimit() {
        return limit;
    }

    public void setLimit(Long limit) {
        this.limit = limit;
    }

    public Long getSkip() {
        return skip;
    }

    public void setSkip(Long skip) {
        this.skip = skip;
    }
}
