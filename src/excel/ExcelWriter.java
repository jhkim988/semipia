package excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.invoke.MethodType;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.IntStream;

public class ExcelWriter {
    private final String filePath;
    private final XSSFWorkbook workbook;
    private final XSSFSheet sheet;
    private int rowNum = 0;

    public ExcelWriter(String filePath) {
        this.filePath = filePath;
        this.workbook = new XSSFWorkbook();
        this.sheet = workbook.createSheet();
    }
    private Row createRow() {
        return sheet.createRow(rowNum++);
    }

    public void write(List<?> list) {
        Row row = createRow();
        IntStream.range(0, list.size()).forEach(idx -> {
                Object obj = list.get(idx);
                Class<?> cls = MethodType.methodType(obj.getClass()).unwrap().returnType();
                try {
                    Cell.class.getMethod("setCellValue", cls).invoke(row.createCell(idx), obj);
                } catch (IllegalAccessException | InvocationTargetException |NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
            }
        );
    }

    public void save() {
        try (FileOutputStream out = new FileOutputStream(filePath)) {
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
