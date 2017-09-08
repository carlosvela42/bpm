package co.jp.nej.earth.model.enums;

public enum Action {
    INSERT(0), UPDATE(1), DELETE(2);
    private int action;

    /**
     * @return the action
     */
    public int getAction() {
        return action;
    }

    /**
     * @param action the action to set
     */
    public void setAction(int action) {
        this.action = action;
    }

    Action(int value) {
        this.action = value;
    }
}
