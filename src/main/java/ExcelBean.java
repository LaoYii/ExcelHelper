import annotation.ExcelColumn;
import annotation.ExcelSheet;
import annotation.ExcelTitle;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ExcelBean {
    private int maxHigh = 0;
    private boolean autoColumnFormat = true;
    private boolean autoGeneateCoding = false;
    private String codingColumnName;
    private int codingStart;
    private String sheetName;
    private String title;

    public String getTitle() {
        return title;
    }

    public int getMaxHigh() {
        return maxHigh;
    }

    public boolean isAutoColumnFormat() {
        return autoColumnFormat;
    }

    public String getSheetName() {
        if (sheetName == null) return "Sheet1";
        return sheetName;
    }

    public void initExcelBean() {
        Class<? extends ExcelBean> clazz = this.getClass();
        boolean hasExcelSheetAnnotation = clazz.isAnnotationPresent(ExcelSheet.class);
        if (hasExcelSheetAnnotation) {
            ExcelSheet excelSheet = clazz.getAnnotation(ExcelSheet.class);
            this.autoColumnFormat = excelSheet.autoColumnFormat();
            this.sheetName = excelSheet.sheetName();
        }
        boolean hasExcelTitle = clazz.isAnnotationPresent(ExcelTitle.class);
        if (hasExcelTitle) {
            this.title = clazz.getAnnotation(ExcelTitle.class).title();
        }
        Field[] fields = clazz.getDeclaredFields();
        List<Field> fieldList = new ArrayList<>();
        for (Field field : fields) {
            boolean hasExcelColumnAnnotation = field.isAnnotationPresent(ExcelColumn.class);
            if (hasExcelColumnAnnotation) {
                ExcelColumn excelColumn = field.getAnnotation(ExcelColumn.class);
                fieldList.add(field);
                //find maxHigh
                if (this.autoColumnFormat) {
                    int high = excelColumn.high();
                    if (high > 1) maxHigh = high;
                }
            }
        }
        rankField(fieldList);
    }

    private List<ColumnInfo> rankField(List<Field> source) {
        List<ColumnInfo> columnInfos = new ArrayList<>();
        //sort list
        source.stream().sorted((s1, s2) -> {
            String sort1 = String.valueOf(s1.getAnnotation(ExcelColumn.class).sort());
            String sort2 = String.valueOf(s2.getAnnotation(ExcelColumn.class).sort());
            return sort1.compareTo(sort2);
        });
        //rank source

        return null;
    }


    class ColumnInfo {
        private String columnName;
        private int high;
        private int sort;
        private int columnNum;
        private String fieldName[];

        public ColumnInfo(String columnName, int high, int sort, int columnNum, String[] fieldName) {
            this.columnName = columnName;
            this.high = high;
            this.sort = sort;
            this.columnNum = columnNum;
            this.fieldName = fieldName;
        }

        public String getColumnName() {
            return columnName;
        }

        public int getHigh() {
            return high;
        }

        public int getSort() {
            return sort;
        }

        public int getColumnNum() {
            return columnNum;
        }

        public String[] getFieldName() {
            return fieldName;
        }
    }
}
