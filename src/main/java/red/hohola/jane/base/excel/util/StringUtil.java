package red.hohola.jane.base.excel.util;

import com.google.common.base.Strings;

public class StringUtil {

    public static String replaceLast(String obj,String target, String replacement) {
        if (!Strings.isNullOrEmpty(obj)) {
            int lastIndex = obj.lastIndexOf(target);
            if (lastIndex != -1) {
                obj = obj.substring(0,lastIndex);
                return obj + replacement;
            }
        }
        return null;
    }
}
