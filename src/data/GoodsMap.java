package data;

import excel.ExcelReader;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public enum GoodsMap {
    MAP;
    private final Map<String, Goods> goodsMap = new HashMap<>();


    private GoodsMap() {
        new ExcelReader(
                "../2022-11-19/0.품목리스트.xlsx"
                , "품목등록"
                , this::readCell
        ).load();
        new ExcelReader(
                "../2022-11-19/3.품목별이익현황.xlsx"
                , "품목별이익현황"
                , this::readMargin
        ).load();
        System.out.println("품목리스트 로딩 완료");
    }

    private void readCell(Row row) {
        if (row.getRowNum() == 0) return;
        Iterator<Cell> iterator = row.iterator();
        String code = iterator.next().getStringCellValue();
        String goodsName = iterator.next().getStringCellValue();
        String groupName = iterator.next().getStringCellValue();
        Goods goods = new Goods(code, groupName, goodsName);
        goodsMap.put(code, goods);
    }

    private void readMargin(Row row) {
        if (row.getRowNum() == 0) return;
        Iterator<Cell> iterator = row.iterator();
        String code = iterator.next().getStringCellValue();
        Double salePrice = iterator.next().getNumericCellValue();
        Double productPrice = iterator.next().getNumericCellValue();
        Goods goods = goodsMap.get(code);
        goods.setSalePrice(salePrice);
        goods.setProductionPrice(productPrice);
        goodsMap.put(code, goods);
    }

    public Goods put(String code, Goods goods) {
        return goodsMap.put(code, goods);
    }
    public Goods get(String code) {
        if (goodsMap.containsKey(code)) return goodsMap.get(code);
        throw new RuntimeException("goodsMap 에 " + code + " 가 존재하지 않음");
    }

    public Set<Map.Entry<String, Goods>> getEntrySet() {
        return goodsMap.entrySet();
    }
}
