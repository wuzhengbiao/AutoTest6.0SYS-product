package CollectionOfFunctionalMethods.UseCaseReRunCorrelation;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import org.testng.Reporter;
import java.util.logging.Logger;
/**
 * 重写retry方法,设置用例失败重跑次数
 * @author wzb
 */
public class OverrideIReTry implements IRetryAnalyzer {
    @Override//重写重跑方法,添加重跑次数
    //TestNG提供了一个IRetryAnalyzer的接口,该接口只有一种方法,一旦测试方法失败，将调用此方法
    public boolean retry(ITestResult iTestResult) {
        //在retry方法里，判断Retry次数是否已经超过指定的最大retry次数。如果没有返回true，否则返回false,返回true会自动重跑用例
        if (ReTryTimes.maxReTryNum < 4)//最多重跑4次就不管了
        {
            if (ReTryTimes.initReTryNum < ReTryTimes.maxReTryNum) {
                System.out.print( "Testng用例<" + iTestResult.getName() + ">执行失败，重试第" + ReTryTimes.initReTryNum + "次" );
                ReTryTimes.initReTryNum++;
                return true;
            }
                return false;
        }
        return false;
    }
}
