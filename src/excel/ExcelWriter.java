package excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class ExcelWriter {
    private int rowNum = 0;
    private int colNum = 0;
    private String filePath;
    private String fileName;
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;

    private Row row;
    private Cell cell;

    public ExcelWriter(String filePath, String fileName) {
        this.filePath = filePath;
        this.fileName = fileName;
        this.workbook = new XSSFWorkbook();
        this.sheet = workbook.createSheet();
    }

    public void createRow() {
        colNum = 0;
        this.row = sheet.createRow(rowNum++);
    }

    public Cell getNextCell() {
        return row.createCell(colNum++);
    }

    public void save() {
        try (FileOutputStream out = new FileOutputStream(new File(filePath, fileName))) {
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
