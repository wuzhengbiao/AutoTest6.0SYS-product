package BasicMethods;

import java.util.ArrayList;
import java.util.List;

import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
/**
 * 重写testng监听器三种测试结果的测试方法
 * @author wzb
 */
//TestListenerAdapter 已经实现 ITestListener，并且提供了一些有用的方法，比如分别获取所有成功失败跳过三种测试结果的测试方法的方法
public class AssertionListener extends TestListenerAdapter {
    public  static String  port="";
    @Override
    public void onTestStart(ITestResult result) {
        MyAssertion.flag = true;
        MyAssertion.errors.clear();
        port= QueryMacacaSeverPort.GetMacacaServerPort();
        System.out.println("Testng的监听器onTestStart！！端口号="+port);
    }
    @Override
    public void onTestFailure(ITestResult tr) {
        this.handleAssertion(tr,1);
        try {
            MailDelivery.TCTestCaseMailSending(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Testng的监听器onTestFailure！！");
    }

    @Override
    public void onTestSkipped(ITestResult tr) {
        this.handleAssertion(tr,0);
        System.out.println("Testng的监听器onTestSkipped！！");
    }
    @Override
    public void onTestSuccess(ITestResult tr) {
        this.handleAssertion(tr,0);
        System.out.println("Testng的监听器onTestSuccess！！");
    }

    private int index = 0;

    /**
     * 收集断言异常结果并输出
     * @author wzb
     */
    private void handleAssertion(ITestResult tr,int ListenStatus){
        if(!MyAssertion.flag){ //为假，就是断言出错了就执行下面的
            //获取异常
            Throwable throwable = tr.getThrowable();
            if(throwable==null){
                throwable = new Throwable();
            }
            //获取异常堆栈信息
            StackTraceElement[] traces = throwable.getStackTrace();
            //创建要输出的所有堆栈信息
            StackTraceElement[] alltrace = new StackTraceElement[0];
            //循环获取断言的异常信息，
            for (Error e : MyAssertion.errors) {
                //获取错误的堆栈数组信息
                StackTraceElement[] errorTraces = e.getStackTrace();
                StackTraceElement[] et = this.getKeyStackTrace(tr, errorTraces);
                //设置异常信息堆栈内容
                StackTraceElement[] message = new StackTraceElement[]{new StackTraceElement("Message : "+e.getMessage()+" Class Method : ", tr.getMethod().getMethodName(), tr.getTestClass().getRealClass().getSimpleName(), index)};
                //行号初始化为0
                index = 0;
                //堆栈信息合并
                alltrace = this.merge(alltrace, message);
                alltrace = this.merge(alltrace, et);
            }
            //如果异常信息不为空
            if(traces!=null){
                traces = this.getKeyStackTrace(tr, traces);
                alltrace = this.merge(alltrace, traces);
            }
            //保存异常信息
            throwable.setStackTrace(alltrace);
            tr.setThrowable(throwable);
            //清空
            MyAssertion.flag = true;
            MyAssertion.errors.clear();
            //输出异常信息

            if(ListenStatus==1)
            {
                tr.setStatus(ITestResult.FAILURE);
            }
            else{
                tr.setStatus(ITestResult.SKIP);
            }
        }
    }

    private StackTraceElement[] getKeyStackTrace(ITestResult tr, StackTraceElement[] stackTraceElements){
        List<StackTraceElement> ets = new ArrayList<StackTraceElement>();
        //循环获取信息
        for (StackTraceElement stackTraceElement : stackTraceElements) {
            //返回测试类的堆栈信息
            if(stackTraceElement.getClassName().equals(tr.getTestClass().getName())){
                ets.add(stackTraceElement);
                index = stackTraceElement.getLineNumber();//错误行号
            }
        }
        StackTraceElement[] et = new StackTraceElement[ets.size()];
        for (int i = 0; i < et.length; i++) {
            et[i] = ets.get(i);
        }
        return et;
    }
    /**
     * 合并两个堆栈信息
     * @param traces1 第一个
     * @param traces2
     * @author wzb
     */
    private StackTraceElement[] merge(StackTraceElement[] traces1, StackTraceElement[] traces2){
        StackTraceElement[] ste = new StackTraceElement[traces1.length+traces2.length];
        for (int i = 0; i < traces1.length; i++) {
            ste[i] = traces1[i];
        }
        for (int i = 0; i < traces2.length; i++) {
            ste[traces1.length+i] = traces2[i];
        }
        return ste;
    }
}
