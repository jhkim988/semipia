package data;

import excel.ExcelReader;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class GoodsMap {
    private static final Map<String, Goods> goodsMap = new HashMap<>();

    static {
        new ExcelReader(
                "../2022-11-19"
                , "0.품목리스트.xlsx"
                , "품목등록"
                , GoodsMap::add
        ).load();
        goodsMap.forEach((key, value) -> {
            for (int year = 2022; year <= 2024; year++) {
                for (int month = 1; month <= 12; month++) {
                    LocalDate localDate = LocalDate.of(year, month, 1);
                    Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
                    GoodsMonth goodsMonth = new GoodsMonth(value, date);
                    StockManageMap.put(goodsMonth, new StockManage());
                }
            }

        });
    }

    private GoodsMap() { }

    public static void add(Row row) {
        Iterator<Cell> iterator = row.iterator();
        String code = iterator.next().getStringCellValue();
        String groupName = iterator.next().getStringCellValue();
        String goodsName = iterator.next().getStringCellValue();

        Goods goods = new Goods(code, groupName, goodsName);
        goodsMap.put(code, goods);
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
