package data;

public class Goods {
    private final String code;
    private final String groupName;
    private final String goodsName;

    private Double salePrice = 0.0;
    private Double productionPrice = 0.0;
    private Double currentQuantity = 0.0;

    public Goods(String code, String groupName, String goodsName) {
        this.code = code;
        this.groupName =groupName;
        this.goodsName = goodsName;
    }

    public Double calculateMargin() {
        return salePrice - productionPrice;
    }
    public Double calculateMarginRate() {
        return (salePrice - productionPrice)/productionPrice;
    }
public Double calculateCurrentTotalStockProductionPrice() {
        return currentQuantity * productionPrice;
}

    public String getCode() {
        return code;
    }
    public String getGroupName() {
        return groupName;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public Double getSalePrice() {
        return salePrice;
    }

    public Double getProductionPrice() {
        return productionPrice;
    }

    public void setSalePrice(Double salePrice) {
        this.salePrice = salePrice;
    }

    public void setProductionPrice(Double productionPrice) {
        this.productionPrice = productionPrice;
    }
    public Double getCurrentQuantity() {
        return currentQuantity;
    }

    public void setCurrentQuantity(Double currentQuantity) {
        this.currentQuantity = currentQuantity;
    }
}
