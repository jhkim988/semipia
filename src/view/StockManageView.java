package view;

import data.*;
import excel.ExcelWriter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.lang.reflect.InvocationTargetException;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StockManageView {
    private static final ExcelWriter writer = new ExcelWriter(
            "../"
            , "재고관리_Sophie.xlsx"
            , StockManageView::writeRow
            , StockManageMap.getEntrySet().iterator()
    );
    private static final List<String> columnNames = Arrays.asList("품목코드"
            , "벤더"
            , "품목명"
            , "연월"
            , "P/O 현황 (오더북 Req Del)"
            , "입고예정 (오더북 Conf. Del)"
            , "입고수량"
            ,"판매 주요 거래처(3순위까지)"
            ,"판매수량 1"
            ,"판매수량 2"
            ,"판매수량 3"
            ,"판매수량 기타"
            ,"총 판매수량"
            ,"현 재고수량"
    );
    private static final Class[] types = {
            String.class
            , String.class
            , String.class
            , String.class
            , String.class
            , String.class
            , double.class
            , String.class
            , double.class
            , double.class
            , double.class
            , double.class
            , double.class
            , double.class
    };

    private static String partnerFormatting(List<SaleInfo> list) {
        StringBuilder sb = new StringBuilder();
        IntStream.rangeClosed(1, list.size()).forEach(x -> sb.append(x).append('.').append(list.get(x-1).getPartner()).append(' '));
        return sb.toString();
    }

    private static List<Double> partnerSaleQuantityFormatting(int num, List<SaleInfo> list) {
        return Stream.concat(list.stream().map(SaleInfo::getQuantity), DoubleStream.generate(() -> 0).boxed())
                .limit(num).collect(Collectors.toList());
    }

    private static void writeRow(Row row, Object obj) {
        if (row.getRowNum() == 0) {
            IntStream.range(0, columnNames.size()).forEach(idx -> row.createCell(idx).setCellValue(columnNames.get(idx)));
            return;
        }
        Map.Entry<GoodsMonth, StockManage> entry = (Map.Entry<GoodsMonth, StockManage>) obj;
        GoodsMonth key = entry.getKey();
        StockManage value = entry.getValue();
        Goods goods = key.getGoods();
        List<SaleInfo> primary = value.primarySaleInfo(3);

        Stream stream = Stream.of(
                goods.getCode()
                , goods.getGroupName()
                , goods.getGoodsName()
                , key.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM"))
                , ""
                , ""
                , value.getStockInMonth()
                , partnerFormatting(primary)
        );
        stream = Stream.concat(stream, partnerSaleQuantityFormatting(3, primary).stream());
        stream = Stream.concat(stream, Stream.of(value.etcSum(3)
                , value.getStockOutMonth()
                , value.getStock()));

        List<Object> data = (List<Object>) stream.collect(Collectors.toList());
        IntStream.range(0, types.length).forEach(idx -> {
            Cell cell = row.createCell(idx);
            try {
                Cell.class.getMethod("setCellValue", types[idx]).invoke(cell, data.get(idx));
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public static void main(String[] args) {
        StockManageView.writer.writeAll();
        StockManageView.writer.save();
    }
}
