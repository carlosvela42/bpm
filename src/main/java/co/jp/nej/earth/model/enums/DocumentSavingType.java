package co.jp.nej.earth.model.enums;

public enum DocumentSavingType {
    FILE_UNTIL_FULL("1"), FILE_ROUND_ROBIN("2"), DATABASE("database");

    DocumentSavingType(String value) {
        this.value = value;
    }

    private String value;

    public String getValue() {
        return value;
    }

}
