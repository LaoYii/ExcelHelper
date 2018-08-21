import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;

public class Test {

    public static void main(String[] args) {
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
