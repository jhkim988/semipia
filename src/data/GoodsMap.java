package data;

import excel.ExcelReader;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class GoodsMap {
    private static final Map<String, Goods> goodsMap = new HashMap<>();
    private static final LocalDate START = LocalDate.of(2022, 1, 1);
    private static final LocalDate END = LocalDate.of(2024, 12, 1);
    static {
        new ExcelReader(
                "../2022-11-19"
                , "0.품목리스트.xlsx"
                , "품목등록"
                , GoodsMap::readCell
        ).load();
        goodsMap.forEach((key, value) -> {
            for (LocalDate localDate = START; !localDate.equals(END); localDate = localDate.plusMonths(1)) {
                Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
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

    private GoodsMap() { }

    public static Goods put(String code, Goods goods) {
        return goodsMap.put(code, goods);
    }
    public static Goods get(String code) {
        if (goodsMap.containsKey(code)) return goodsMap.get(code);
        try {
            throw new Exception("goodsMap 에 " + code + " 가 존재하지 않음");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
