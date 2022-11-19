package excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.stream.StreamSupport;

public class ExcelReader {
    private final String filePath;
    private final String fileName;
    private final String sheetName;
    private final ExcelStrategy strategy;

    public ExcelReader(String filePath, String fileName, String sheetName, ExcelStrategy strategy) {
        this.filePath = filePath;
        this.fileName = fileName;
        this.sheetName = sheetName;
        this.strategy = strategy;
    }

    public void load() {
        try (FileInputStream fis = new FileInputStream(new File(filePath, fileName))) {
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            XSSFSheet sheet = workbook.getSheet(sheetName);
            StreamSupport.stream(sheet.spliterator(), false).forEach(strategy::rowStrategy);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int numericalElseZero(Cell cell) {
        if (cell.getCellType().equals(CellType.NUMERIC)) return (int) cell.getNumericCellValue();
        return 0;
    }
}
