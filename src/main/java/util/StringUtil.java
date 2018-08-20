package util;

public final class StringUtil {

    public static boolean isNull(Object obj){
        if(obj == null) return true;
        if(obj instanceof String) return "".equals(obj.toString().trim());
        return false;
    }

    public static boolean isNotNull(Object obj){
        return !isNull(obj);
    }

}
