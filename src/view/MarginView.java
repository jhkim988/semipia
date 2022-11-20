package view;

import data.Goods;
import data.GoodsMap;
import excel.ExcelWriter;
import org.apache.poi.ss.usermodel.Row;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class MarginView {
    private static class Data {
        Goods goods;
        int idx;

        public Data(Goods goods, int idx) {
            this.goods = goods;
            this.idx = idx;
        }

        public Goods getGoods() {
            return goods;
        }

        public int getIdx() {
            return idx;
        }
    }
    private static final List<Data> rows = new ArrayList<>();
    private static final List<String> columnNames = Arrays.asList(
            "품목코드"
            , "벤더"
            , "품목명"
            , "자료"
            , "값"
    );
    private static final List<String> dataNames = Arrays.asList(
            "현 재고 수량"
            , "현 재고 금액"
            , "원가"
            , "최근 판매가"
            , "마진"
            , "마진율"
    );
    private static final List<Method> getDataMethods;

    static {
        try {
            getDataMethods = Arrays.asList(
                    Goods.class.getMethod("getCurrentQuantity")
                    , Goods.class.getMethod("calculateCurrentTotalStockProductionPrice")
                    , Goods.class.getMethod("getProductionPrice")
                    , Goods.class.getMethod("getSalePrice")
                    , Goods.class.getMethod("calculateMargin")
                    , Goods.class.getMethod("calculateMarginRate")
            );
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    private static void writeRow(Row row, Object obj) {
        if (row.getRowNum() == 0) {
            IntStream.range(0, columnNames.size()).forEach(idx -> row.createCell(idx).setCellValue(columnNames.get(idx)));
            return;
        }
        Data data = (Data) obj;
        Goods goods = data.getGoods();
        row.createCell(0).setCellValue(goods.getCode());
        row.createCell(1).setCellValue(goods.getGroupName());
        row.createCell(2).setCellValue(goods.getGoodsName());
        row.createCell(3).setCellValue(dataNames.get(data.getIdx()));
        try {
            row.createCell(4).setCellValue((Double) getDataMethods.get(data.getIdx()).invoke(goods));
        } catch (IllegalAccessException |InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        GoodsMap.getEntrySet().forEach((entry -> {
            Goods goods = entry.getValue();
            IntStream.range(0, 6).forEach(idx -> rows.add(new Data(goods, idx)));
        }));
        ExcelWriter writer = new ExcelWriter(
                "../"
                , "이익현황_Sophie.xlsx"
                , MarginView::writeRow
                , rows.iterator()
        );
        writer.writeAll();
        writer.save();
    }
}
