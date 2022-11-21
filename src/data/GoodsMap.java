package data;

import excel.ExcelReader;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class GoodsMap {
    private static final Map<String, Goods> goodsMap = new HashMap<>();
    private static final LocalDateTime START = LocalDateTime.of(2022, 1, 1, 0, 0);
    private static final LocalDateTime END = LocalDateTime.of(2024, 12, 1, 0, 0);
    static {
        new ExcelReader(
                "../2022-11-19/0.품목리스트.xlsx"
                , "품목등록"
                , GoodsMap::readCell
        ).load();
        new ExcelReader(
                "../2022-11-19/3.품목별이익현황.xlsx"
                , "품목별이익현황"
                , GoodsMap::readMargin
        ).load();
        goodsMap.forEach((key, value) -> {
            for (LocalDateTime date = START; !date.isAfter(END); date = date.plusMonths(1)) {
                GoodsMonth goodsMonth = new GoodsMonth(value, date);
                StockManageMap.put(goodsMonth, new StockManage());
            }
        });
        System.out.println("품목리스트 로딩 완료");
    }

    private static void readCell(Row row) {
        if (row.getRowNum() == 0) return;
        Iterator<Cell> iterator = row.iterator();
        String code = iterator.next().getStringCellValue();
        String goodsName = iterator.next().getStringCellValue();
        String groupName = iterator.next().getStringCellValue();
        Goods goods = new Goods(code, groupName, goodsName);
        GoodsMap.put(code, goods);
    }

    private static void readMargin(Row row) {
        if (row.getRowNum() == 0) return;
        Iterator<Cell> iterator = row.iterator();
        String code = iterator.next().getStringCellValue();
        Double salePrice = iterator.next().getNumericCellValue();
        Double productPrice = iterator.next().getNumericCellValue();
        Goods goods = GoodsMap.get(code);
        goods.setSalePrice(salePrice);
        goods.setProductionPrice(productPrice);
        GoodsMap.put(code, goods);
    }

    private GoodsMap() { }

    public static Goods put(String code, Goods goods) {
        return goodsMap.put(code, goods);
    }
    public static Goods get(String code) {
        if (goodsMap.containsKey(code)) return goodsMap.get(code);
        throw new RuntimeException("goodsMap 에 " + code + " 가 존재하지 않음");
    }

    public static Set<Map.Entry<String, Goods>> getEntrySet() {
        return goodsMap.entrySet();
    }
}
