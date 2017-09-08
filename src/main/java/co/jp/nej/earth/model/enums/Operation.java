package co.jp.nej.earth.model.enums;

/**
 * Access Right 0: None 1: can hide masking 2: only write 3: only read 4: write
 * and read 5: Full
 */
public enum Operation {
    SEARCH_INFO(0), GET_DATA(1), UPDATE_DATA(2), DELETE_DATA(3);
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

    Operation(int value) {
        this.action = value;
    }

    public boolean equal(Operation operation) {
        return this.getAction() == operation.getAction();
    }
}
