package data;

public class Goods {
    private String code;
    private String groupName;
    private String goodsName;

    public Goods(String code, String groupName, String goodsName) {
        this.code = code;
        this.groupName =groupName;
        this.goodsName = goodsName;
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
}
