package excel;

import data.Goods;
import data.SaleInfo;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class ExcelReader {
    @FunctionalInterface
    private interface Actor {
        void act(XSSFSheet sheet);
    }
    private final String filePath;
    private final String fileName;
    private final String sheetName;

    private final Actor actor;
    public ExcelReader(String filePath, String fileName, String sheetName, Actor actor) {
        this.filePath = filePath;
        this.fileName = fileName;
        this.sheetName = sheetName;
        this.actor = actor;
    }

    public void load() {
        try (FileInputStream fis = new FileInputStream(new File(filePath, fileName))) {
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            XSSFSheet sheet = workbook.getSheet(sheetName);
            actor.act(sheet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        final Map<String, Goods> goodsMap = new HashMap<>();
        new ExcelReader(
                "../2022-11-13"
                , "0.품목리스트.xlsx"
                , "품목등록"
                , sheet -> {
            for (Row row : sheet) {
                Iterator<Cell> iter = row.iterator();
                String code = iter.next().getStringCellValue();
                String groupName = iter.next().getStringCellValue();
                String goodsName = iter.next().getStringCellValue();
                goodsMap.put(code, new Goods(code, groupName, goodsName));
            }
        }).load();

        final List<SaleInfo> saleInfoList = new ArrayList<>();
        new ExcelReader(
                "../2022-11-13"
                , "1.판매현황.xlsx"
                , "판매현황"
                , sheet -> {
                    for (Row row : sheet) {
                        Iterator<Cell> iter = row.iterator();
                        String goodsName = iter.next().getStringCellValue();
                        LocalDate localDate = iter.next().getLocalDateTimeCellValue().toLocalDate();
                        String partner = iter.next().getStringCellValue();
                        int quantity = (int) iter.next().getNumericCellValue();
                        saleInfoList.add(new SaleInfo(goodsName, localDate, partner, quantity));
                    }
        }
        ).load();

        saleInfoList.stream().collect(Collectors.groupingBy(SaleInfo::getGoodsName));
    }
}
