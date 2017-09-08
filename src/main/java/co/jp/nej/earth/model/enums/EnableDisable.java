package co.jp.nej.earth.model.enums;

/**
 * Enable 1
 * Disable 2
 */
public enum EnableDisable {
    ENABLE("1"), DISABLE("2");
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    EnableDisable(String id) {
        this.id = id;
    }

    public boolean equals(EnableDisable enableDisable) {
        return this.id == enableDisable.id;
    }
}
