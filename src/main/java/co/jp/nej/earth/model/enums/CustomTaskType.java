package co.jp.nej.earth.model.enums;

/**
 * For Schedule 1
 */
public enum CustomTaskType {
    SCHEDULE("1"),
    AUTO("2");
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    CustomTaskType(String id) {
        this.id = id;
    }

    public boolean equals(CustomTaskType type) {
        return this.id == type.id;
    }
}
