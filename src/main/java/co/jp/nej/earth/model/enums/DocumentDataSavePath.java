package co.jp.nej.earth.model.enums;

public enum DocumentDataSavePath {

    DATABASE(1, "Database"), FILE(2, "File");

    private int id;
    private String title;

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    DocumentDataSavePath(int id, String title) {
        this.id = id;
        this.title = title;
    }

    DocumentDataSavePath(int id) {
        this.id = id;
    }

    public boolean eq(DocumentDataSavePath accessRight) {
        return this.getId() == accessRight.getId();
    }

    public boolean goe(DocumentDataSavePath accessRight) {
        return this.getId() >= accessRight.getId();
    }

    public boolean le(DocumentDataSavePath accessRight) {
        return this.getId() < accessRight.getId();
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    public static boolean isDatabase(String type) {
        return String.valueOf(DocumentDataSavePath.DATABASE.getId()).equals(type);
    }
}
