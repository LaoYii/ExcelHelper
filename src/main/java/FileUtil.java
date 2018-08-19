import java.io.File;

public final class FileUtil {
    
    public static File fileRename(String newName,File file){
        String oldPath = file.getPath();
        oldPath = oldPath.replace("\\", "/");
        return new File(getFilePath(oldPath)+newName+getFileSuffix(oldPath));
    }

    public static String getFileSuffix(String path){
        return path.substring(path.lastIndexOf("."), path.length());
    }
    
    public static String getFileName(File file){
        return file.getName().replace(getFileSuffix(file.getPath()),"");
    }
    
    public static String getFilePath(String path){
        return path.substring(0, path.lastIndexOf("/")+1);
    }
    
    public static File timestampFile(File file){
        String fileName = getFileName(file);
        return fileRename(fileName+DateUtil.getTimestamp(),file);
    }

    /**
     * 检测文件是否存在，存在则返回原文件名+时间戳的文件
     * @return
     */
    public static File checkFileIfExistReturnTimestamp(File file){
        if (file.exists()) return timestampFile(file);
        else return file;
    }
    
    public static void main(String[] args) {
        File file = new File("C:\\Users\\tyhdl\\Desktop\\qwe.txt");
        System.out.println(getFileName(file));

    }
}
