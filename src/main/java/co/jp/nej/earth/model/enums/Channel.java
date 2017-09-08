package co.jp.nej.earth.model.enums;

/**
 * Access Right 0: None 1: can hide masking 2: only write 3: only read 4: write
 * and read 5: Full
 */
public enum Channel {
    INTERNAL(0), WEB_SERVICE(1), BATCH(2);
    private int value;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    Channel(int value) {
        this.value = value;
    }

    public boolean equals(Channel channel) {
        return this.value == channel.value;
    }
}
