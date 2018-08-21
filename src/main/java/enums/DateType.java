package enums;

public enum DateType {
    YEAR_MONTH_DAY_24HOUR_MIN_SEC ("yyyy-MM-dd HH:mm:ss"),
    YEAR_MONTH_DAY ("yyyy-MM-dd");


    private String format;

    DateType(String format){
        this.format = format;
    }

    public String getFormat() {
        return format;
    }
}

