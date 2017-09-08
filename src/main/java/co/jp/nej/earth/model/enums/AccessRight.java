package co.jp.nej.earth.model.enums;


/**
 * Access Right
 * 0: None
 * 1: can hide masking
 * 2: only write
 * 3: only read
 * 4: write and read
 * 5: Full
 */
public enum AccessRight {

    NONE(0, "NONE"), SO(1, "SO"), RO(2, "RO"), RW(3, "RW"), FULL(4, "FULL");

    private int value;
    private String title;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    AccessRight(int value, String title) {
        this.value = value;
        this.title = title;
    }

    AccessRight(int value) {
        this.value = value;
    }

    public boolean eq(AccessRight accessRight) {
        return this.getValue() == accessRight.getValue();
    }

    public boolean goe(AccessRight accessRight) {
        return this.getValue() >= accessRight.getValue();
    }

    public boolean le(AccessRight accessRight) {
        return this.getValue() < accessRight.getValue();
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

    /**
     * Get enum from value
     *
     * @param value value of enum
     * @return AccessRight object
     * @throws IllegalArgumentException
     */
    public static AccessRight fromValue(int value)
        throws IllegalArgumentException {
        try {
            return AccessRight.values()[value];
        } catch(ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException("Unknown enum value :"+ value);
        }
    }

    /**
     * Get enum from value
     *
     * @param title title of enum
     * @return AccessRight object
     * @throws IllegalArgumentException
     */
    public static AccessRight fromTitle(String title)
        throws IllegalArgumentException {
        try {
            return AccessRight.valueOf(title);
        } catch(ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException("Unknown enum title :"+ title);
        }
    }
}
