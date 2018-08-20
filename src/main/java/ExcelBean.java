import annotation.AutoGeneateCoding;
import annotation.ExcelColumn;
import annotation.ExcelSheet;
import annotation.ExcelTitle;
import util.StringUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ExcelBean {
    private boolean autoFormat = true;
    private boolean autoGeneateCoding = false;
    private String codingColumnName;
    private int codingStart;
    private String sheetName;
    private String sheetTitle;
    private int columnSize = 0;
    private List<ColumnInfo> columnInfos;

    private boolean hasColumuTitle = false;
    private List<Field> fields = new ArrayList<>();


    protected String getSheetName() {
        return sheetName;
    }

    protected boolean isAutoFormat() {
        return autoFormat;
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

    public boolean isHasColumuTitle() {
        return hasColumuTitle;
    }

    public List<Field> getFields() {
        return fields;
    }

    protected void initExcelBean() {
        Class<? extends ExcelBean> clazz = this.getClass();
        //获取工作簿基本信息
        Optional.ofNullable(clazz.getAnnotation(ExcelSheet.class)).ifPresent(annExcelSheet -> {
            this.autoFormat = annExcelSheet.autoFormat();
            this.sheetName = annExcelSheet.sheetName();
        });
        //获取工作簿大标题
        Optional.ofNullable(clazz.getAnnotation(ExcelTitle.class)).ifPresent(excelTitle -> this.sheetTitle = excelTitle.title());
        //获取工作簿自动编码
        Optional.ofNullable(clazz.getAnnotation(AutoGeneateCoding.class)).ifPresent(annGeneateCoding -> {
            this.codingColumnName = annGeneateCoding.codingColumnName();
            this.codingStart = annGeneateCoding.codingStart();
        });
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
                (entry) -> new ColumnInfo(entry.getValue())
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
                    if (StringUtil.isNotNull(etann.title())) {
                        titleName = etann.title();
                        hasColumuTitle = true;
                    }
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

    protected class FieldInfo {
        private String columnName;
        private String defaultValue;

        public FieldInfo(Field field) {
            fields.add(field);
            ExcelColumn annotation = field.getAnnotation(ExcelColumn.class);
            this.columnName = annotation.columnName();
            this.defaultValue = annotation.defaultValue();
        }

        public String getColumnName() {
            return columnName;
        }

        public String getDefaultValue() {
            return defaultValue;
        }

    }
}
