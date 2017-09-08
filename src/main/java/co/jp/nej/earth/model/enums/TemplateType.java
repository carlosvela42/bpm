package co.jp.nej.earth.model.enums;

import co.jp.nej.earth.util.EStringUtil;

public enum TemplateType {
    PROCESS(1, "PROCESS"), WORKITEM(2, "WORKITEM"), FOLDERITEM(3, "FOLDERITEM"),
    DOCUMENT(4, "DOCUMENT"), LAYER(5, "LAYER");

    private Integer value;
    private String title;

    TemplateType(Integer value, String title) {
        this.value = value;
        this.title = title;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public static boolean isProcess(Integer type) {

        return TemplateType.PROCESS.value.equals(type);
    }

    public static boolean isWorkItem(Integer type) {
        return TemplateType.WORKITEM.value.equals(type);
    }

    public static boolean isFolderItem(Integer type) {
        return TemplateType.FOLDERITEM.value.equals(type);
    }

    public static boolean isDocument(Integer type) {
        return TemplateType.DOCUMENT.value.equals(type);
    }

    public static boolean isLayer(Integer type) {
        return TemplateType.LAYER.value.equals(type);
    }

    @Override
    public String toString() {
        return super.toString().toUpperCase();
    }

    public static String getTemplateTypeName(Integer type) {
        if (isProcess(type)) {
            return TemplateType.PROCESS.toString();
        }

        if (isFolderItem(type)) {
            return TemplateType.FOLDERITEM.toString();
        }

        if (isDocument(type)) {
            return TemplateType.DOCUMENT.toString();
        }

        if (isLayer(type)) {
            return TemplateType.LAYER.toString();
        }

        return TemplateType.WORKITEM.toString();
    }

    public boolean equal(TemplateType type) {
        return EStringUtil.equals(this.toString(), type.toString());
    }

    public static TemplateType getByValue(Integer value) {
        for(TemplateType e : values()) {
            if(e.value.equals(value)) {
                return e;
            }
        }
        return null;
    }
}
