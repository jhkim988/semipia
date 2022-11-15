package data;

import java.time.LocalDate;

public class SaleInfo {
    private String goodsName;
    private LocalDate date;
    private String partner;
    private int quantity;

    public SaleInfo(String goodsName, LocalDate date, String partner, int quantity) {
        this.goodsName = goodsName;
        this.date = date;
        this.partner = partner;
        this.quantity = quantity;
    }
}
