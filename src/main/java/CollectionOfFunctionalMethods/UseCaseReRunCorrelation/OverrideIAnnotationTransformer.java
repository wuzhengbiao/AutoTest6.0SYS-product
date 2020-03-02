package CollectionOfFunctionalMethods.UseCaseReRunCorrelation;

import org.testng.IAnnotationTransformer;
import org.testng.IRetryAnalyzer;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
/**
 * 重写transform方法,实现监听失败重跑用例
 * @author wzb
 */
//IAnnotationTransformer 只能用来修改 @Test 注释,只有一个接口ITestAnnotation,
// 此接口用于在运行时以编程方式向测试方法添加注释。在测试运行期间为每个测试调用Transform方法
//通过在@Test注释中指定retryAnalyzer值
//通过在Listener接口上实现，在运行时添加Retry分析器
public class OverrideIAnnotationTransformer implements IAnnotationTransformer {
    @Override//重写testng的失败用例是否重跑方法
    public void transform(ITestAnnotation iTestAnnotation, Class TestClass, Constructor constructor, Method method) {
        IRetryAnalyzer iRetryAnalyzer= iTestAnnotation.getRetryAnalyzer();//获取重跑监听状态
        if(iRetryAnalyzer==null){//状态失败
            iTestAnnotation.setRetryAnalyzer(OverrideIReTry.class); //调用IRetryAnalyzer的重跑方法,即重试分析器
        }
    }
}