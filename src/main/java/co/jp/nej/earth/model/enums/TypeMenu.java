package co.jp.nej.earth.model.enums;

/**
 * Type of MgrMenu 1: Link 2: Button
 */
public enum TypeMenu {
    LINK(1), BUTTON(2);

    private int value;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    TypeMenu(int value) {
        this.value = value;
    }
}
