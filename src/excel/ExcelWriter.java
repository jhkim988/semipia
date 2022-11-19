package excel;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

public class ExcelWriter {
    private final String filePath;
    private final String fileName;
    private final XSSFWorkbook workbook;
    private final XSSFSheet sheet;
    private final ExcelWriteStrategy strategy;
    private final Iterator<?> iterator;

    public ExcelWriter(String filePath, String fileName, ExcelWriteStrategy strategy, Iterator<?> iterator) {
        this.filePath = filePath;
        this.fileName = fileName;
        this.workbook = new XSSFWorkbook();
        this.sheet = workbook.createSheet();
        this.strategy = strategy;
        this.iterator = iterator;
    }

    public void writeAll() {
        int rowNum = 0;
        while (iterator.hasNext()) {
            Row row = sheet.createRow(rowNum++);
            strategy.rowStrategy(row, iterator.next());
        }
    }

    public void save() {
        try (FileOutputStream out = new FileOutputStream(new File(filePath, fileName))) {
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
