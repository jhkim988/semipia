package data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StockManage {
    private Double stockInMonth;
    private Double stockOutMonth;
    private final List<SaleInfo> stockOut = new ArrayList<>();

    private Double stock;

    public StockManage() {
        this.stockInMonth = 0.0;
        this.stockOutMonth = 0.0;
    }

    public Double getStockOutMonth() {
        return stockOutMonth;
    }

    public Double getStockInMonth() {
        return stockInMonth;
    }

    public void setStockInMonth(Double quantity) {
        this.stockInMonth = quantity;
    }

    public void setStockOutMonth(Double quantity) {
        this.stockOutMonth = quantity;
    }

    public void addSaleInfo(SaleInfo saleInfo) {
        stockOut.add(saleInfo);
    }

    public List<SaleInfo> primarySaleInfo(int num) {
        return stockOut.stream().sorted().limit(num).collect(Collectors.toList());
    }

//    public List<SaleInfo> etcSaleInfo(int num) {
//        return stockOut.stream().sorted().skip(num).collect(Collectors.toList());
//    }

    public Double etcSum(int num) {
        return stockOut.stream().sorted().skip(num).map(SaleInfo::getQuantity).reduce(Double::sum).orElse(0.0);
    }

    public void setStock(Double remain) {
        this.stock = remain;
    }

    public Double getStock() {
        return this.stock;
    }
}
