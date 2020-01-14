package enums;

public enum Currency {
    UAH("₴"),
    USD("$"),
    EUR("€");

    private String currency;

    public String getCurrency() {
        return currency;
    }

    Currency(String currency) {
        this.currency = currency;
    }
}
