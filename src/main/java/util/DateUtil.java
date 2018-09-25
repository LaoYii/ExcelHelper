package util;

import enums.DateType;

import java.text.SimpleDateFormat;
import java.util.Date;

public final class DateUtil {

    private static SimpleDateFormat format;

    private static SimpleDateFormat getFormat(String pattern){
        if (format == null){
            format = new SimpleDateFormat(pattern);
        }else{
            format.applyPattern(pattern);
        }
        return format;
    }
    
    public static String getTimestamp(DateType dateType){
        synchronized (DateUtil.class){
            return getFormat(dateType.getFormat()).format(new Date())
                    .replace("-","")
                    .replace(" ","")
                    .replace(":","");
        }
    }

    public synchronized static String getFormat(DateType dateType,Date date){
        synchronized (DateUtil.class) {
            return getFormat(dateType.getFormat()).format(date);
        }
    }

}
