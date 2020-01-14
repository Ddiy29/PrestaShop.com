package models;

import java.util.Objects;

public class ObjectPrice {

    private double priceWithDiscount;

    public ObjectPrice(double priceWithDiscount) {
        this.priceWithDiscount = priceWithDiscount;
    }

    public double getPriceWithDiscount() {
        return priceWithDiscount;
    }

    public void setPriceWithDiscount(double priceWithDiscount) {
        this.priceWithDiscount = priceWithDiscount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ObjectPrice that = (ObjectPrice) o;
        return Double.compare(that.priceWithDiscount, priceWithDiscount) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(priceWithDiscount);
    }
}
