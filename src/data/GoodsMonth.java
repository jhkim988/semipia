package data;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

public class GoodsMonth implements Comparable<GoodsMonth> {
    private Goods goods;
    private Date date;

    public GoodsMonth(Goods goods, Date date) {
        this.goods = goods;
        this.date = date;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return true;
        if (!getClass().equals(obj.getClass())) return false;
        GoodsMonth other = (GoodsMonth) obj;
        return this.goods.getCode().equals(other.getGoods().getCode()) && this.getDate().equals(other.getDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(goods, date);
    }

    public Goods getGoods() {
        return goods;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public int compareTo(GoodsMonth o) {
        if (!this.goods.equals(o.goods)) return this.goods.getCode().compareTo(o.goods.getCode());
        return this.date.compareTo(o.date);
    }
}
