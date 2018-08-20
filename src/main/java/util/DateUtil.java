package util;

import enums.DateType;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class DateUtil {
    

    private static SimpleDateFormat format;
    
    private static SimpleDateFormat getFormat(String pattern){
        if (format == null){
            return new SimpleDateFormat(pattern);
        }
        format.applyPattern(pattern);
        return format;
    }
    
    public static String getTimestamp(DateType dateType){
        return getFormat(dateType.getFormat()).format(new Date())
                .replace("-","")
                .replace(" ","")
                .replace(":","");
    }
    
}
