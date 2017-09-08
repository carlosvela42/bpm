package co.jp.nej.earth.model.enums;

public enum SearchOperator {
    Equal("="),
    NotEqual("!="),
    Over(">"),
    EqualOver(">="),
    Under("<"),
    EqualUnder("<="),
    Like("like"),
    NotLike("not like"),
    IsNull("is null"),
    IsNotNull("is not null"),
    IsEmpty("is empty"),
    IsNotEmpty("is not empty");

    private String value;

    SearchOperator(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}