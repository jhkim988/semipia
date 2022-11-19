package excel;

import data.Goods;
import data.SaleInfo;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
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
    public interface Actor {
        void act(Row row);
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
            boolean isFirst = true;
            for (Row row : sheet) {
                if (isFirst) {
                    isFirst = false;
                    continue;
                }
                actor.act(row);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int numericalElseZero(Cell cell) {
        if (cell.getCellType().equals(CellType.NUMERIC)) return (int) cell.getNumericCellValue();
        return 0;
    }
}
