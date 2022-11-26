package out;

public enum Env {
    이익현황_OutFileName("../이익현황_Sophie.xlsx")
    , 재고관리_OutFileName("../재고관리_Sophie.xlsx")
    , 품목리스트_InFileName("../2022-11-19/0.품목리스트.xlsx")
    , 품목리스트_InFileSheetName("품목등록")
    , 판매현황_InFileName("../2022-11-19/1.판매현황.xlsx")
    , 판매현황_InFileNameSheetName("판매현황")
    , 재고변동표_InFileName("../2022-11-19/2.재고변동표.xlsx")
    , 재고변동표_InFileNameSheetName("재고변동표")
    , 품목별이익현황_InFileName("../2022-11-19/3.품목별이익현황.xlsx")
    , 품목별이익현황_InFileSheetName("품목별이익현황");
    private final String value;

    Env(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
