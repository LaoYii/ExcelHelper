import annotation.AutoGeneateCoding;
import annotation.ExcelColumn;
import annotation.ExcelSheet;
import annotation.ExcelTitle;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ExcelBean {
    private boolean autoColumnFormat = true;
    private boolean autoGeneateCoding = false;
    private String codingColumnName;
    private int codingStart;
    private String sheetName;
    private String sheetTitle;
    private int columnSize = 0;
    private List<ColumnInfo> columnInfos;
    
    protected String getSheetName() {
        return sheetName;
    }

    protected boolean isAutoColumnFormat() {
        return autoColumnFormat;
    }

    protected boolean isAutoGeneateCoding() {
        return autoGeneateCoding;
    }

    protected String getCodingColumnName() {
        return codingColumnName;
    }

    protected int getCodingStart() {
        return codingStart;
    }

    protected String getSheetTitle() {
        return sheetTitle;
    }

    protected int getColumnSize() {
        return columnSize;
    }

    public List<ColumnInfo> getColumnInfos() {
        return columnInfos;
    }

    protected void initExcelBean() {
        Class<? extends ExcelBean> clazz = this.getClass();
        boolean hasExcelSheetAnnotation = clazz.isAnnotationPresent(ExcelSheet.class);
        if (hasExcelSheetAnnotation) {
            ExcelSheet annExcelSheet = clazz.getAnnotation(ExcelSheet.class);
            this.autoColumnFormat = annExcelSheet.autoColumnFormat();
            this.sheetName = annExcelSheet.sheetName();
        }
        boolean hasExcelTitle = clazz.isAnnotationPresent(ExcelTitle.class);
        if (hasExcelTitle) {
            this.sheetTitle = clazz.getAnnotation(ExcelTitle.class).title();
        }
        boolean hasAutoGeneateCoding = clazz.isAnnotationPresent(AutoGeneateCoding.class);
        if (hasAutoGeneateCoding) {
            this.autoGeneateCoding = true;
            AutoGeneateCoding annGeneateCoding = clazz.getAnnotation(AutoGeneateCoding.class);
            this.codingColumnName = annGeneateCoding.codingColumnName();
            this.codingStart = annGeneateCoding.codingStart();
        }
        Field[] fields = clazz.getDeclaredFields();
        List<Field> fieldList = new ArrayList<>();
        for (Field field : fields) {
            boolean hasExcelColumnAnnotation = field.isAnnotationPresent(ExcelColumn.class);
            if (hasExcelColumnAnnotation) {
                columnSize++;
                fieldList.add(field);
            }
        }
        this.columnInfos = rankField(fieldList);
    }

    private List<ColumnInfo> rankField(List<Field> source) {
        //sort list
        List<ColumnInfo> collect = source.stream().sorted((s1, s2) -> {
            String sort1 = String.valueOf(s1.getAnnotation(ExcelColumn.class).sort());
            String sort2 = String.valueOf(s2.getAnnotation(ExcelColumn.class).sort());
            return sort1.compareTo(sort2);
        }).collect(Collectors.groupingBy((e) -> {
            ExcelColumn annotation = e.getAnnotation(ExcelColumn.class);
            return annotation.sort();
        })).entrySet().stream().map(
                (entry) -> {
                    System.out.println(entry.getKey());
                    return new ColumnInfo(entry.getValue());
                }
        ).collect(Collectors.toList());
        return collect;
    }


    protected class ColumnInfo {
        private String titleName; //标题名
        private int columnNum;  //占用的列数
        private List<FieldInfo> fieldInfoList;
        public ColumnInfo(List<Field> fields) {
            this.columnNum = fields.size();
            fieldInfoList = new ArrayList<>();
            for (Field field : fields) {
                boolean hasann = field.isAnnotationPresent(ExcelTitle.class);
                if (hasann) {
                    ExcelTitle etann = field.getAnnotation(ExcelTitle.class);
                    titleName = etann.title();
                }
                fieldInfoList.add(new FieldInfo(field));
            }
        }
        public String getTitleName() {
            return titleName;
        }

        public int getColumnNum() {
            return columnNum;
        }

        public List<FieldInfo> getFieldInfoList() {
            return fieldInfoList;
        }
    }

    protected class FieldInfo{
        private String columnName;
        private int sort;
        private String defaultValue;
        private Field field;

        public FieldInfo(Field field) {
            ExcelColumn annotation = field.getAnnotation(ExcelColumn.class);
            this.columnName = annotation.columnName();
            this.sort = annotation.sort();
            this.defaultValue = annotation.defaultValue();
            this.field = field;
        }

        public String getColumnName() {
            return columnName;
        }

        public int getSort() {
            return sort;
        }

        public String getDefaultValue() {
            return defaultValue;
        }

        public Field getField() {
            return field;
        }
    }
}
