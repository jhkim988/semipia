package data;

import excel.ExcelReader;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.time.LocalDateTime;
import java.util.*;

import static excel.ExcelReader.numericalElseZero;

public class StockManageMap {
    private static final LocalDateTime now = LocalDateTime.now();
    private static final Map<GoodsMonth, StockManage> rows = new TreeMap<>();

    static {
        new ExcelReader(
                "../2022-11-19"
                , "2.재고변동표.xlsx"
                , "재고변동표"
                , StockManageMap::readCell_재고변동표
        ).load();
        System.out.println("재고변동표 로딩 완료");
        new ExcelReader(
                "../2022-11-19"
                , "1.판매현황.xlsx"
                , "판매현황"
                , StockManageMap::readCell_판매현황
        ).load();
        System.out.println("판매현황 로딩 완료");
    }

    public static void readCell_재고변동표(Row row) {
        if (row.getRowNum() == 0) return;
        Iterator<Cell> iterator = row.cellIterator();
        String code = iterator.next().getStringCellValue();
        LocalDateTime date = iterator.next().getLocalDateTimeCellValue();

        Double stockIn = numericalElseZero(iterator.next());
        Double stockOut = numericalElseZero(iterator.next());
        Double remain = numericalElseZero(iterator.next());

        Goods goods = GoodsMap.get(code);
        GoodsMonth goodsMonth = new GoodsMonth(goods, date);

        StockManage stockManage = new StockManage();
        stockManage.setStockInMonth(stockIn);
        stockManage.setStockOutMonth(stockOut);
        stockManage.setStock(remain);
        if (date.getYear() == now.getYear() && date.getMonth() == now.getMonth()) {
            goods.setCurrentQuantity(remain);
        }
        rows.put(goodsMonth, stockManage);
    }

    public static void readCell_판매현황(Row row) {
        if (row.getRowNum() == 0) return;
        Iterator<Cell> iterator = row.cellIterator();
        String code = iterator.next().getStringCellValue();
        LocalDateTime date = iterator.next().getLocalDateTimeCellValue();
        String partner = iterator.next().getStringCellValue();
        Double quantity = numericalElseZero(iterator.next());

        Goods goods = GoodsMap.get(code);
        GoodsMonth goodsMonth = new GoodsMonth(goods, date);

        StockManage stockManage = get(goodsMonth);
        Objects.requireNonNull(stockManage).addSaleInfo(new SaleInfo(partner, quantity));
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

    public static StockManage put(GoodsMonth key, StockManage value){
        return rows.put(key, value);
    }

    public static Set<Map.Entry<GoodsMonth, StockManage>> getEntrySet() {
        return rows.entrySet();
    }
}
