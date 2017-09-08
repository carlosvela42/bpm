package co.jp.nej.earth.model.enums;

public enum EventType {
    BUTTON_CLICK(0),
    LINK_CLICK(1);
    private int value;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    EventType(int value) {
        this.value = value;
    }

    public static boolean isButtonClick(String event) {
        return String.valueOf(EventType.BUTTON_CLICK.getValue()).equals(event);
    }

    public static boolean isLinkClick(String event) {
        return String.valueOf(EventType.LINK_CLICK.getValue()).equals(event);
    }
}
