package enums;

public enum Sort {
    RELEVATION("Релевантность"),
    NAME_FROM_A_TILL_YA("Названию: от А к Я"),
    NAME_FROM_YA_TILL_A("Названию: от Я к А"),
    PRICE_FROM_LOW_TILL_HIGH("Цене: от низкой к высокой"),
    PRICE_FROM_HIGH_TILL_LOW("Цене: от высокой к низкой");

    private String value;

    public String getValue() {
        return value;
    }

    Sort(String value) {
        this.value = value;
    }
}
