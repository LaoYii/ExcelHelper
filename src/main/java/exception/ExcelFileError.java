package exception;

public class ExcelFileError extends Exception{
    
    public static final String FILE_NAME_ERROR = "文件名错误,请确认文件后缀是.xls或者.xlsx";
    
    public ExcelFileError(String message) {
        super(message);
    }
}
