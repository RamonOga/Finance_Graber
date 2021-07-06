package model;

import java.util.Objects;

public class Price implements Comparable<Price> {
    private double highPrice;
    private double lowPrice;
    private long timeStamp;

    public Price(double highPrice, double lowPrice, long timeStamp) {
        this.highPrice = highPrice;
        this.lowPrice = lowPrice;
        this.timeStamp = timeStamp;
    }


    public double getHighPrice() {
        return highPrice;
    }

    public double getLowPrice() {
        return lowPrice;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    @Override
    public String toString() {
        return "Price{" +
                ", highPrice=" + highPrice +
                ", lowPrice=" + lowPrice +
                ", timeStamp=" + timeStamp +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Price price = (Price) o;
        return Double.compare(price.highPrice, highPrice) == 0 &&
                Double.compare(price.lowPrice, lowPrice) == 0 &&
                timeStamp == price.timeStamp;
    }

    @Override
    public int hashCode() {
        return Objects.hash(highPrice, lowPrice, timeStamp);
    }

    @Override
    public int compareTo(Price p) {
        return Long.compare(p.timeStamp, this.timeStamp);
    }
}
