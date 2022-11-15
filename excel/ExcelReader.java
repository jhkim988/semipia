package excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ExcelReader {
    private String filePath;
    private String fileName;

    public void load() {
        try (FileInputStream fis = new FileInputStream(new File(filePath, fileName))) {
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            XSSFSheet sheet = workbook.getSheetAt(0);
//            XSSFSheet sheet = workbook.getSheetAt("Sheet Name");
            for (Row row : sheet) {
                for (Cell cell : row) {
                    System.out.println(cell.getCellType());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
