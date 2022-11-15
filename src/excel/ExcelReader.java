package excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ExcelReader {
    @FunctionalInterface
    private static interface Actor {
        void act(XSSFSheet sheet);
    }
    private String filePath;
    private String fileName;
    private String sheetName;

    private Actor actor;
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
        ExcelReader reader = new ExcelReader(
                "../2022-11-13"
                , "0.품목리스트.xlsx"
                , "품목등록"
                , sheet -> {
            for (Row row : sheet) {
                for (Cell cell : row) {
                    System.out.print(cell.getStringCellValue() + "\t");
                }
                System.out.println();
            }
        });
        reader.load();
    }
}
