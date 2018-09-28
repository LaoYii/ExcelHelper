import red.hohola.jane.base.excel.ExcelHelper;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Test {

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException, IOException, InstantiationException, ParseException {

        /*Field[] declaredFields = ExampleBean.class.getDeclaredFields();
        for (Field s : declaredFields) {
            System.out.println(s.getType());
            System.out.println(s.getType().equals(Date.class));
        }*/
        /*int loop = 0;
        String name = "abc";
        while (!"abc10".equals(name)){
            if (loop == 0) {
                name = name + ++loop;
            }else{
                name = StringUtil.replaceLast(name,String.valueOf(loop),String.valueOf(++loop));
            }
            System.out.println(name);
        }
        System.out.println("--------------");
        System.out.println(name);*/
        
        /*File file = new File("C:\\Users\\tyhdl\\Desktop\\123.xls");
        try {
            Workbook workbook = ExcelUtil.getWorkbook(file);
            ExcelUtil.getSheet(workbook,null);
        } catch (IOException e) {
            
            
        }*/
        
        
        //readTest
        /*File file = new File("C:\\Users\\tyhdl\\Desktop\\example.xlsx");
        List<ExampleBean> parse = ExcelHelper.parse(file, ExampleBean.class);
        System.out.println(parse);*/
        
        ArrayList<ExampleBean> list = new ArrayList<>();
        String[] names = {"写","示","例","脑","壳","疼","好","想","睡","觉"};
        DecimalFormat df = new DecimalFormat("#.00");
        for (int i = 0; i < 10; i++) {
            ExampleBean eb = new ExampleBean();
            int nameLenght = (int)(2+Math.random()*(3-2+1));
            StringBuilder name = new StringBuilder();
            while (nameLenght-- > 0){
                name.append(names[(int) (0 + Math.random() * (10))]);
            }
            eb.setName(name.toString());
            eb.setAge((int) (10 + Math.random() * (20-9)));
            eb.setBirthday(new Date());
            eb.setChinese(Double.valueOf(df.format(1 + Math.random() * (100))));
            eb.setEnglish(Double.valueOf(df.format(1 + Math.random() * (100))));
            eb.setMath(Double.valueOf(df.format(1 + Math.random() * (100))));
            list.add(eb);
        }
        try {
            ExcelHelper.export(new File("C:\\Users\\tyhdl\\Desktop\\example.xlsx"), list);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
