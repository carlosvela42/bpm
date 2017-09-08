package co.jp.nej.earth.model.enums;

/**
 * Event status.
 */
public enum EventStatus {

    OPEN(1), EDIT(2), EDITTING(3);

    private int value;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    EventStatus(int value) {
        this.value = value;
    }

    public boolean equals(EventStatus eventStatus) {
        return this.value == eventStatus.value;
    }
}
