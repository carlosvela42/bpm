package co.jp.nej.earth.model.enums;

public enum LogCategory {
    COMMON_API("Common API"),
    SCREEN("Screen"),
    PROCESS_SERVICE("Process Service"),
    WEB_SERVICE("Web Service");

    private String propertyName;

    LogCategory(String name) {
        propertyName = name;
    }

    @Override
    public String toString() {
        return propertyName;
    }
}
