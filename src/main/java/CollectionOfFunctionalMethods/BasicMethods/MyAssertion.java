package CollectionOfFunctionalMethods.BasicMethods;
import java.util.ArrayList;
import java.util.List;

import macaca.client.MacacaClient;
import org.testng.Assert;
/**
 * java断言方法不中断执行过程
 * @author wzb
 */
public class MyAssertion {

    public static boolean flag = true;

    public static List<Error> errors = new ArrayList<Error>();

    public static String verifyEquals(Object actual, Object expected){
        String logcontext="";
        try{
            Assert.assertEquals(actual, expected);
        }catch(Error e){
            errors.add(e);
            flag = false;
            logcontext=" "+e;
        }
        return logcontext;
    }
    public  static  String verifyEquals(Object actual, Object expected, String message){
        String logcontext="";
        try{
            Assert.assertEquals(actual, expected, message);
        }catch(Error e){
            errors.add(e);
            flag = false;
            logcontext=" "+e;
        }
        return logcontext;
    }
}
