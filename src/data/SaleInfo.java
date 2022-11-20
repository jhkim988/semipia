package data;

public class SaleInfo implements Comparable<SaleInfo> {
    private final String partner;
    private final Double quantity;

    public SaleInfo(String partner, Double quantity) {
        this.partner = partner;
        this.quantity = quantity;
    }

    @Override
    public int compareTo(SaleInfo o) {
        return Double.compare(o.quantity, this.quantity);
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

    public Double getQuantity() {
        return quantity;
    }

}
