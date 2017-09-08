package co.jp.nej.earth.model.enums;

public enum OpenProcessMode {
    READ_ONLY(0), EDIT(1);

    private Integer mode;

    /**
     * @param mode
     */
    OpenProcessMode(Integer mode) {
        this.mode = mode;
    }

    public Integer getMode() {
        return mode;
    }

    public static boolean isEdit(int mode) {
        return OpenProcessMode.EDIT.getMode() == mode;
    }

    public static boolean isReadOnly(int mode) {
        return OpenProcessMode.READ_ONLY.getMode() == mode;
    }
}
