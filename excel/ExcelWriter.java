package excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelWriter {
    private String filePath;
    private String fileName;
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;

    public void setCell() {
        int rowNumber = 0;
        int cellNumber = 0;
        Row row = sheet.createRow(rowNumber++);
        Cell cell = row.createCell(cellNumber++);
        cell.setCellValue("value");
    }

    public void save() {
        try (FileOutputStream out = new FileOutputStream(new File(filePath, fileName))) {
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
