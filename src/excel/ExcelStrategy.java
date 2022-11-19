package excel;

import org.apache.poi.ss.usermodel.Row;

public interface ExcelStrategy {
    void rowStrategy(Row row);
}
