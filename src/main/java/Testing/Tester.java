package Testing;
import BasicMethods.*;
import Com.*;
import Init.InitDriver;
import macaca.client.MacacaClient;
import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2017/10/27.
 */
public class Tester  {

    //设置一个延迟时长从testng.xml文件中读取的变量
    public String ProgramPath;
    @Test
    @Parameters({"TestFilePath" , "TIME" , "TestName"})
    public void tester(String TestFilePath,int TIME,String TestName) throws Exception {
        InitDriver Init= new InitDriver();
        MacacaClient driver=Init.MacacaInit();//macaca初始化对象
        Execute Execute = new Execute();
        AbnormalScreenshot Abnormal = new AbnormalScreenshot();
        int    QueryCount=0;//统计没有找到元素的次数
        int    ElementCount=1;//代替excle文本序列号
        String TestNameToEnglish=null;
        Map<Integer,Integer> MapListCase = new HashMap<Integer, Integer>();//储存没找到元素的序号
        ProgramPath = URLDecoder.decode(Tester.class.getResource("").toString(),"UTF-8");
        ProgramPath = StringUtils.substringBefore(ProgramPath,"/target");
        ProgramPath = StringUtils.substringAfter(ProgramPath,"file:/");
        ReadExcel reade = new ReadExcel();

        List<TestingCase> list =  reade.readXlsx(ProgramPath+TestFilePath);
        driver.maximize();
        if (list != null) {
            for (TestingCase testCase : list) {
                //输出读取对应的用例正行内容
                System.out.println("No." + testCase.getId() + "操作说明" + testCase.getDescription() + ",操作方法:" + testCase.getModel() + ",获取元素方法:" + testCase.getMode() + ",获取元素路径:" + testCase.getModePath() + ",文本内容:" + testCase.getText());
//                Reporter.log("No." + testCase.getId() + "操作说明" + testCase.getDescription() + "_操作方法:" + testCase.getModel() + "_获取元素方法_" + testCase.getMode() + "_获取元素路径_" + testCase.getModePath() + "_文本内容_" + testCase.getText());
                Reporter.log( "No." + testCase.getId() + "操作说明" + testCase.getDescription()+"\n操作时间: "+ GetCurrentSystemTime.GetCurrentTime());
                //整行表格数据传入，并对获取的内容进行实质性执行用例
              //  System.out.println(".\\" + TestName + testCase.getDescription() + "jpg");
                int ResultNum=Execute.execute(driver,testCase,TIME);//开始执行macaca用例
                System.out.println("ResultNum="+ResultNum+"\n");
                String  img=".\\" + TestName + testCase.getDescription() + "jpg";
                String  reportimg="PHOTO" + "..\\..\\" + TestName + testCase.getDescription() + "jpg";
                /*System.out.print("步骤序列号:"+ElementCount+" ResultNum的查找元素状态返回值:"+ResultNum+"\n");*/
                if(ResultNum==1)//判断元素
                {
                    driver.sleep(2000);
                    driver.saveScreenshot(img);
                }
                else {
                    if (ResultNum == 0) {
                        MapListCase.put(0, -1);//设置初始值，不然下面相减会报错
                        QueryCount++;
                        MapListCase.put(QueryCount, ElementCount);//存储没有找到元素的列表信息
                    /*System.out.print("QueryCount统计没有找到元素的值:"+QueryCount+"\n");
                    System.out.print("MapListCase.get(QueryCount)的值:"+MapListCase.get(QueryCount)+"\n");
                    System.out.print("MapListCase.get(QueryCount-1)的值:"+MapListCase.get(QueryCount-1)+"\n");*/
                        if (MapListCase.get(QueryCount) - MapListCase.get(QueryCount - 1) == 1)//判断是不是连续操作2次都报错
                        {
                            driver.saveScreenshot(img);
                            Reporter.log(reportimg);
                            MailDelivery.TCTestCaseMailSending(1);
                            Assert.assertEquals(ResultNum, 1, "No." + list.get(MapListCase.get(QueryCount - 1)).getId() + ", 操作说明：" + list.get(MapListCase.get(QueryCount - 1)).getDescription() + "   原因： 找不到元素，有可能是定位错了，或者是流程出现未知的情况！！" + "\n操作时间: " + GetCurrentSystemTime.GetCurrentTime());
                        }
                        MyAssertion.verifyEquals(ResultNum, 1, "No." + testCase.getId() + " 操作说明：" + testCase.getDescription() + "  没有找到元素！！" + "\n操作时间: " + GetCurrentSystemTime.GetCurrentTime());//捕获assert断言
                        driver.saveScreenshot(img);
                    } else if (ResultNum == 3) {
                        driver.sleep(30000);
                        driver.saveScreenshot(img);
                    } else {
                        driver.sleep(2000);
                        driver.saveScreenshot(img);
                    }
                    int AbnormalStatus=Abnormal.WhetherCatchAbnormal(driver,img);//查找异常
                    if(AbnormalStatus==1)
                    {
                        Reporter.log(reportimg);
                        Assert.assertEquals(" do abnormal","no abnormal ","macaca检测平台有异常,联系对应项目经理!!!"+"\n");
                    }
                }
                Reporter.log(reportimg);
                ElementCount++;
            }
        }
       Runtime.getRuntime().exec("taskkill /f /im chrome.exe");//调用dos命令杀死谷歌进程

    }
}
