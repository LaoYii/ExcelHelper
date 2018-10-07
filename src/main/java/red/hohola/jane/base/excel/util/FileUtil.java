package red.hohola.jane.base.excel.util;

import red.hohola.jane.base.excel.enums.DateType;

import java.io.File;

public final class FileUtil {

    public static File fileRename(String newName, File file) {
        return new File(file.getPath().replace(getFileName(file), newName));
    }

    public static String getFileSuffix(String path) {
        return path.substring(path.lastIndexOf("."));
    }

    public static String getFileName(File file) {
        return file.getName().replace(getFileSuffix(file.getName()), "");
    }


    public static File timestampFile(File file) {
        String fileName = getFileName(file);
        return fileRename(fileName + DateUtil.getTimestamp(DateType.YEAR_MONTH_DAY_24HOUR_MIN_SEC), file);
    }

    /**
     * 检测文件是否存在，存在则返回原文件名+时间戳的文件
     *
     * @return
     */
    public static File checkFileIfExistReturnTimestamp(File file) {
        if (file.exists()) return timestampFile(file);
        else return file;
    }

}
