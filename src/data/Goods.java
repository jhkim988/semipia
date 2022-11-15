package data;

public class Goods {
    public static class Builder {
        private String code;
        private String groupName;
        private String goodsName;

        public Builder code(String code) {
            this.code = code;
            return this;
        }

        public Builder groupName(String groupName) {
            this.groupName = groupName;
            return this;
        }

        public Builder goodsName(String goodsName) {
            this.goodsName = goodsName;
            return this;
        }

        public Goods build() {
            return new Goods(this);
        }
    }
    private String code;



    private String groupName;
    private String goodsName;


    public Goods() {

    }
    public Goods(String code, String groupName, String goodsName) {
        this.code = code;
        this.groupName =groupName;
        this.goodsName = goodsName;
    }

    public Goods(Builder builder) {
        this.code = builder.code;
        this.groupName = builder.groupName;
        this.goodsName = builder.goodsName;
    }

    public static Builder builder() {
        return new Builder();
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
