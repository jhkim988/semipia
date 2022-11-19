package excel;

import org.apache.poi.ss.usermodel.Row;

public interface ExcelWriteStrategy {
    void rowStrategy(Row row, Object next);
}
