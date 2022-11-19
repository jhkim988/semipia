package data;

import java.util.*;
import java.util.stream.Collectors;

public class StockManage {
    private int stockInMonth;
    private int stockOutMonth;
    private final List<SaleInfo> stockOut;

    private int stock;

    public StockManage() {
        this.stockInMonth = 0;
        this.stockOutMonth = 0;
        this.stockOut = new ArrayList<>();
    }

    public int getStockOutMonth() {
        return stockOutMonth;
    }

    public int getStockInMonth() {
        return stockInMonth;
    }

    public void setStockInMonth(int quantity) {
        this.stockInMonth = quantity;
    }

    public void setStockOutMonth(int quantity) {
        this.stockOutMonth = quantity;
    }

    public void addSaleInfo(SaleInfo saleInfo) {
        stockOut.add(saleInfo);
    }

    public List<SaleInfo> primarySaleInfo(int num) {
        return stockOut.stream().sorted().limit(num).collect(Collectors.toList());
    }

    public List<SaleInfo> etcSaleInfo(int num) {
        return stockOut.stream().sorted().skip(num).collect(Collectors.toList());
    }

    public void setStock(int remain) {
        this.stock = remain;
    }

    public int getStock() {
        return this.stock;
    }
}
