package excel;

import org.apache.poi.ss.usermodel.Row;

public interface ExceAllStrategy {
    void rowStrategy(Row row, Object next);
}
