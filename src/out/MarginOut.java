package out;

import data.Goods;
import data.GoodsMap;
import excel.ExcelWriter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class MarginOut {
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

    private static List<Method> getDataMethods() {
        try {
            final List<Method> getDataMethods = Arrays.asList(
                    Goods.class.getMethod("getCurrentStock")
                    , Goods.class.getMethod("calculateCurrentTotalStockProductionPrice")
                    , Goods.class.getMethod("getProductionPrice")
                    , Goods.class.getMethod("getSalePrice")
                    , Goods.class.getMethod("calculateMargin")
                    , Goods.class.getMethod("calculateMarginRate")
            );
            return getDataMethods;
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public static void print() {
        List<Method> dataMethods = getDataMethods();
        ExcelWriter writer = new ExcelWriter(Env.이익현황_OutFileName.getValue());
        writer.write(columnNames);
        GoodsMap.MAP.getEntrySet().forEach((entry -> {
            Goods goods = entry.getValue();
            IntStream.range(0, 6).forEach(idx -> {
                List<Object> row = new ArrayList<>();
                row.add(goods.getCode());
                row.add(goods.getGroupName());
                row.add(goods.getGoodsName());
                row.add(dataNames.get(idx));
                try {
                    row.add(dataMethods.get(idx).invoke(goods));
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
                writer.write(row);
            });
        }));
        writer.save();
    }
    public static void main(String[] args) {
        print();
    }
}
