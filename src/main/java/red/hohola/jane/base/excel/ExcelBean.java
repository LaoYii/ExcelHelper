package red.hohola.jane.base.excel;

import red.hohola.jane.base.excel.annotation.AutoGeneateCoding;
import red.hohola.jane.base.excel.annotation.ExcelColumn;
import red.hohola.jane.base.excel.annotation.ExcelSheet;
import red.hohola.jane.base.excel.annotation.ExcelTitle;
import com.google.common.base.Strings;
import red.hohola.jane.base.excel.enums.DateType;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

final class ExcelBean {

    private Class clazz;

    private boolean autoFormat = true;
    private boolean autoGeneateCoding = false;
    private String codingColumnName;
    private int codingStart;
    private String sheetName;
    private String sheetTitle;
    private int columnSize = 0;
    private List<ColumnInfo> columnInfos;

    private boolean hasColumuTitle = false;
    private List<FieldInfo> fieldInfos = new LinkedList<>();


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

    public List<FieldInfo> getFieldInfos() {
        return fieldInfos;
    }

    public ExcelBean(Class clazz){
        this.clazz = clazz;
        initExcelBean();
    }


    protected void initExcelBean() {
        Class<?> clazz = this.clazz;
        ExcelSheet annotation = clazz.getAnnotation(ExcelSheet.class);
        //获取工作簿基本信息
        Optional.ofNullable(clazz.getAnnotation(ExcelSheet.class)).ifPresent(annExcelSheet -> {
            this.autoFormat = annExcelSheet.autoFormat();
            this.sheetName = annExcelSheet.value();
        });
        //获取工作簿大标题
        Optional.ofNullable(clazz.getAnnotation(ExcelTitle.class)).ifPresent(excelTitle -> this.sheetTitle = excelTitle.value());
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
                ExcelTitle annotation = field.getAnnotation(ExcelTitle.class);
                Optional.ofNullable(field.getAnnotation(ExcelTitle.class)).ifPresent(etann->{
                    if (!Strings.isNullOrEmpty(etann.value())) {
                        titleName = etann.value();
                        hasColumuTitle = true;
                    }
                });
                FieldInfo fieldInfo = new FieldInfo(field);
                fieldInfoList.add(fieldInfo);
                fieldInfos.add(fieldInfo);
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
        private DateType dateType;
        private Field field;

        public FieldInfo(Field field) {
            ExcelColumn annotation = field.getAnnotation(ExcelColumn.class);
            this.columnName = annotation.name();
            this.defaultValue = annotation.defaultValue();
            this.dateType = annotation.dateType();
            this.field = field;
        }

        public String getColumnName() {
            return columnName;
        }

        public String getDefaultValue() {
            return defaultValue;
        }

        public DateType getDateType() {
            return dateType;
        }

        public Field getField() {
            return field;
        }
    }
}
