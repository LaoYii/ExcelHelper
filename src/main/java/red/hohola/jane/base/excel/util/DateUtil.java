package red.hohola.jane.base.excel.util;

import red.hohola.jane.base.excel.enums.DateType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class DateUtil {

    private static SimpleDateFormat format;

    private static SimpleDateFormat getFormat(DateType pattern) {
        if (format == null) {
            format = new SimpleDateFormat(pattern.getFormat());
        } else {
            format.applyPattern(pattern.getFormat());
        }
        return format;
    }

    public static String getTimestamp(DateType dateType) {
        synchronized (DateUtil.class) {
            return getFormat(dateType).format(new Date())
                    .replace("-", "")
                    .replace(" ", "")
                    .replace(":", "");
        }
    }

    public static String getFormat(DateType dateType, Date date) {
        synchronized (DateUtil.class) {
            return getFormat(dateType).format(date);
        }
    }
    
    public static Date parse(DateType dateType,String date) throws ParseException {
        synchronized (DateUtil.class){
            return getFormat(dateType).parse(date);
        }
    }

}
