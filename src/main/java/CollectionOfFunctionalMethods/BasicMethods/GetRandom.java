package CollectionOfFunctionalMethods.BasicMethods;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GetRandom {
    /**
     * 生成1-Range不重复的随机数
     * @Range  设置随机数的范围
     * @return 将不重复的随机数赋给数组中的各个元素并返回
     */
    public static List<String> randomArr(String Range) {
        int[] intRandom = new int[Integer.parseInt(Range)];
        List mylist = new ArrayList(); //生成数据集，用来保存随即生成数，并用于判断
        Random rd = new Random();
        while(mylist.size() < Integer.parseInt(Range)) {
            int num = rd.nextInt(Integer.parseInt(Range))+1;
            if(!mylist.contains(num)) {
                mylist.add(num); //往集合里面添加数据。
            }
        }
        return  mylist;
    }

}
