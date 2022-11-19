package data;

import excel.ExcelReader;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.BiConsumer;

import static excel.ExcelReader.numericalElseZero;

public class StockManageMap {
    private static final Map<GoodsMonth, StockManage> rows = new TreeMap<>();

    static {
        new ExcelReader(
                "../2022-11-19"
                , "2.재고변동표.xlsx"
                , "재고변동표"
                , StockManageMap::add_재고변동표
        ).load();

        new ExcelReader(
                "../2022-11-19"
                , "1.판매현황.xlsx"
                , "판매현황"
                , StockManageMap::add_판매현황
        ).load();
    }

    public static void add_재고변동표(Row row) {
        Iterator<Cell> iterator = row.cellIterator();
        String code = iterator.next().getStringCellValue();
        Date date = iterator.next().getDateCellValue();
        int stockIn = numericalElseZero(iterator.next());
        int stockOut = numericalElseZero(iterator.next());
        int remain = numericalElseZero(iterator.next());

        Goods goods = GoodsMap.get(code);
        GoodsMonth goodsMonth = new GoodsMonth(goods, date);

        StockManage stockManage = new StockManage(goodsMonth);
        stockManage.setStockInMonth(stockIn);
        stockManage.setStockOutMonth(stockOut);
        stockManage.setStock(remain);
        rows.put(goodsMonth, stockManage);
    }

    public static void add_판매현황(Row row) {
        Iterator<Cell> iterator = row.cellIterator();
        String code = iterator.next().getStringCellValue();
        Date date = iterator.next().getDateCellValue();
        String partner = iterator.next().getStringCellValue();
        int quantity = numericalElseZero(iterator.next());

        Goods goods = GoodsMap.get(code);
        GoodsMonth goodsMonth = new GoodsMonth(goods, date);

        StockManage stockManage = get(goodsMonth);
        stockManage.addSaleInfo(new SaleInfo(partner, quantity));
    }
    
    public static StockManage get(GoodsMonth goodsMonth) {
        try {
            return rows.get(goodsMonth);
        } catch (Exception e){
            System.err.println("StockManage 에 " + goodsMonth.getGoods().getCode() + " 가 존재하지 않습니다.");
            e.printStackTrace();
        }
        return null;
    }

    public static void forEach(BiConsumer<? super GoodsMonth, ? super StockManage> biConsumer) {
        rows.forEach(biConsumer);
    }

    public static StockManage put(GoodsMonth key, StockManage value){
        return rows.put(key, value);
    }
}
