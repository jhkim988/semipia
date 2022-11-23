package out;

public class Main {
    public static void main(String[] args) {
        try {
            Class.forName("data.GoodsMap");
            Class.forName("data.StockManageMap");
            StockManageOut.print();
            MarginOut.print();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
