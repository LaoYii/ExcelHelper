import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class DateUtil {
    
    private static final String YEAR_MONTH_DAY_24HOUR_MIN_SEC = "yyyy-MM-dd HH:mm:ss"; 
    
    private static SimpleDateFormat format;
    
    private static SimpleDateFormat getFormat(String pattern){
        if (format == null){
            return new SimpleDateFormat(pattern);
        }
        format.applyPattern(pattern);
        return format;
    }
    
    public static String getTimestamp(){
        return getFormat(YEAR_MONTH_DAY_24HOUR_MIN_SEC).format(new Date())
                .replace("-","")
                .replace(" ","")
                .replace(":","");
    }
    
}
