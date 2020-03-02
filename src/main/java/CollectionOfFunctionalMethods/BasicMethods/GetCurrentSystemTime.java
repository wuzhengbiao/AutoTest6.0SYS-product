package CollectionOfFunctionalMethods.BasicMethods;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GetCurrentSystemTime {
    /**
     * 获取当地时间
     * Auth WZB
     **/
    public static String GetCurrentTime()  {
        long longtime = System.currentTimeMillis();
        //new日期对
        Date date = new Date(longtime);
        //转换提日期输出格式
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(dateFormat.format(date));
        return dateFormat.format(date);
    }
}
