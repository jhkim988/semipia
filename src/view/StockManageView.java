package view;

import data.Goods;
import data.SaleInfo;
import data.StockManageMap;
import excel.ExcelWriter;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StockManageView {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");

    private static String partnerFormatting(List<SaleInfo> list) {
        StringBuilder sb = new StringBuilder();
        int num = 1;
        for (SaleInfo saleInfo : list) {
            sb.append(num++);
            sb.append('.');
            sb.append(saleInfo.getPartner());
            sb.append(' ');
        }
        return sb.toString();
    }

    private static List<Integer> partnerSaleQuantityFormatting(int num, List<SaleInfo> list) {
        return Stream.concat(list.stream().map(SaleInfo::getQuantity), IntStream.generate(() -> 0).boxed())
                .limit(num).collect(Collectors.toList());
    }


    public static void main(String[] args) {
        ExcelWriter writer = new ExcelWriter("../", "재고관리_Sophie.xlsx");
        writer.createRow();
        Arrays.asList("품목코드"
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
        ).forEach(x -> writer.getNextCell().setCellValue(x));
        StockManageMap.forEach((key, value) -> {
            Goods goods = key.getGoods();
            Date date = key.getDate();
            int stockInMonth = value.getStockInMonth();
            List<SaleInfo> primary = value.primarySaleInfo(3);
            int etcSum = value.etcSaleInfo(3)
                    .stream()
                    .map(SaleInfo::getQuantity)
                    .reduce(Integer::sum).orElse(0);
            int totOutMonth = value.getStockOutMonth();
            int currentStock = value.getStock();
            writer.createRow();
            writer.getNextCell().setCellValue(goods.getCode());
            writer.getNextCell().setCellValue(goods.getGroupName());
            writer.getNextCell().setCellValue(goods.getGroupName());
            writer.getNextCell().setCellValue(dateFormat.format(date));
            writer.getNextCell().setCellValue("");
            writer.getNextCell().setCellValue("");
            writer.getNextCell().setCellValue(stockInMonth);
            writer.getNextCell().setCellValue(partnerFormatting(primary));
            partnerSaleQuantityFormatting(3, primary).forEach(x -> writer.getNextCell().setCellValue(x));
            writer.getNextCell().setCellValue(etcSum);
            writer.getNextCell().setCellValue(totOutMonth);
            writer.getNextCell().setCellValue(currentStock);
        });
        writer.save();
    }
}
