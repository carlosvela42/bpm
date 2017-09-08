package co.jp.nej.earth.model.enums;

import java.util.ArrayList;
import java.util.List;

public enum Type {
    INTEGER(1), NUMBER(2), NVARCHAR2(3), NCHAR(4);

    private Integer value;

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    Type(int value) {
        this.value = value;
    }

    public static List<Type> getFieldTypes() {
        List<Type> templateTypes = new ArrayList<>();
        // Pendng viec tao kieu number cho truong ma hoa
//        templateTypes.add(Type.INTEGER);
//        templateTypes.add(Type.NUMBER);
        templateTypes.add(Type.NVARCHAR2);
//        templateTypes.add(Type.NCHAR);
        return templateTypes;
    }

    public static boolean isNumber(Integer type) {
        return type <= 2;
    }

    public static boolean isString(Integer type) {
        return type > 2;
    }

    @Override
    public String toString() {
        return super.toString().toUpperCase();
    }


    public static String getLabel(Integer value) {
        String label = Type.NUMBER.toString();
        for (Type type : Type.values()) {
            if (type.getValue().equals(value)) {
                label = type.toString();
                break;
            }
        }
        return label;
    }


}
