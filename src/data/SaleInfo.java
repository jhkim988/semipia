package data;

import java.time.LocalDate;

public class SaleInfo implements Comparable<SaleInfo> {
    private final String partner;
    private final int quantity;

    public SaleInfo(String partner, int quantity) {
        this.partner = partner;
        this.quantity = quantity;
    }

    @Override
    public int compareTo(SaleInfo o) {
        return Integer.compare(o.quantity, this.quantity);
    }

    @Override
    public String toString() {
        return "SaleInfo{" +
                "partner='" + partner + '\'' +
                ", quantity=" + quantity +
                '}';
    }

    public String getPartner() {
        return partner;
    }

    public int getQuantity() {
        return quantity;
    }

}
