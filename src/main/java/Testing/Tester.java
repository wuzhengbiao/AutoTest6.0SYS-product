package Testing;
import CollectionOfFunctionalMethods.BasicMethods.*;
import CollectionOfFunctionalMethods.BasicMethods.*;
import CollectionOfFunctionalMethods.DatabaseRelatedMethods.DataBase;
import CollectionOfFunctionalMethods.DatabaseRelatedMethods.DatabaseDataOperation;
import CollectionOfFunctionalMethods.UseCaseReRunCorrelation.OverrideIReTry;
import CollectionOfFunctionalMethods.UseCaseReRunCorrelation.ReTryTimes;
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
    @Parameters({"TestFilePath" ,"PlatformName", "TIME" , "TestName"})
    public void tester(String TestFilePath,String PlatformName,int TIME,String TestName) throws Exception {
        InitDriver Init= new InitDriver();
        MacacaClient driver=Init.MacacaInit();//macaca初始化对象
        Execute Execute = new Execute();
        AbnormalScreenshot Abnormal = new AbnormalScreenshot();
        DataBase data=new DataBase();
        DatabaseDataOperation Operation=new DatabaseDataOperation();
        int    QueryCount=0;//统计没有找到元素的次数
        int    ElementCount=1;//代替excle文本序列号
        int RerunTimes ;//重跑次数
        MailDelivery.TestNgType=PlatformName;
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
                   //整行表格数据传入，并对获取的内容进行实质性执行用例
                int ResultNum=Execute.execute(driver,testCase,TIME);//开始执行macaca用例
                System.out.println("ResultNum="+ResultNum+"\n");
                String  img=".\\" + TestName + testCase.getDescription() + "jpg";
                String  reportimg="PHOTO" + "..\\..\\" + TestName + testCase.getDescription() + "jpg";
                /*System.out.print("步骤序列号:"+ElementCount+" ResultNum的查找元素状态返回值:"+ResultNum+"\n");*/
                if(ResultNum==1)//查找元素正常
                {
                    driver.sleep(2000);
                    Reporter.log( "No." + testCase.getId() + "操作说明" + testCase.getDescription()+"\n操作时间: "+ GetCurrentSystemTime.GetCurrentTime());
                    driver.saveScreenshot(img);
                }
                else {
                    //元素没有找到
                    if (ResultNum == 0) {
                        MapListCase.put(0, -1);//设置初始值，不然下面相减会报错
                        QueryCount++;
                        MapListCase.put(QueryCount, ElementCount);//存储没有找到元素的列表信息
                        if(MapListCase.get(QueryCount)-MapListCase.get(QueryCount-1)==1)//判断是不是连续操作2次都报错
                        {
                            Reporter.log( "<p  style=\"font-weight: 900;color:red;font-size:15px\">" + "No." + testCase.getId() + "操作说明" + testCase.getDescription() + "\n操作时间: " + GetCurrentSystemTime.GetCurrentTime() + "</p>" );
                            driver.saveScreenshot( img );
                            Reporter.log( reportimg );//写入报告图片地址
                            //连续2次步骤都报错,会进行断言,并停止程序执行
                            String insertexception1 = Operation.DataToInsertAbnormal( 1, 1, list.get( 0 ).getModePath(), PlatformName, 0, GetCurrentSystemTime.GetCurrentTime(), "序号 No." + list.get( MapListCase.get( QueryCount - 1 ) ).getId() + ", 操作说明：" + list.get( MapListCase.get( QueryCount - 1 ) ).getDescription() + "   原因： 连续2个操作找不到元素，有可能是定位错了，或者是流程出现未知的情况！！" );
                            data.InsertDatabaseSql( insertexception1 );
                            for( RerunTimes=0;RerunTimes < list.size(); RerunTimes++)
                            {
                                if(list.get( RerunTimes ).getText().equals( "重跑" )&list.get( RerunTimes ).getDescription().equalsIgnoreCase( "login" ))
                                {
                                    ReTryTimes.maxReTryNum = ReTryTimes.maxReTryNum+1;
                                    System.out.print( " ReTryTimes.maxReTryNum  = "+  ReTryTimes.maxReTryNum+"\n");
                                    break;
                                }
                            }
                                EventListenerMonitoring.Listenerflag = 2;
                                Runtime.getRuntime().exec("taskkill /f /im chrome.exe");//调用dos命令杀死谷歌进程
                                Assert.assertEquals(ResultNum, 1, "序号 No." + list.get(MapListCase.get(QueryCount-1)).getId() + ", 操作说明：" + list.get(MapListCase.get(QueryCount-1)).getDescription() + "   原因： 连续2个操作找不到元素，有可能是定位错了，或者是流程出现未知的情况！！");
                        }
                            Reporter.log( "<p  style=\"font-weight: 900;color:DarkOrange;font-size:15px\">"+"No." + testCase.getId() + "操作说明" + testCase.getDescription()+"\n操作时间: "+ GetCurrentSystemTime.GetCurrentTime()+"</p>");
                            EventListenerMonitoring.Listenerflag = 3;
                            MyAssertion.verifyEquals( ResultNum, 1, "No." + testCase.getId() + " 操作说明：" + testCase.getDescription() + "  没有找到元素！！" + "\n操作时间: " + GetCurrentSystemTime.GetCurrentTime() );//捕获assert断言
                            driver.saveScreenshot( img );
                    }
                    //用于处理报表数据查询过大，加长等待时间
                    else if (ResultNum == 3) {
                        Reporter.log( "No." + testCase.getId() + "操作说明" + testCase.getDescription()+"\n操作时间: "+ GetCurrentSystemTime.GetCurrentTime());
                        driver.saveScreenshot(img);
                        String insertexception3=Operation.DataToInsertStatictics(list.get(0).getModePath(), PlatformName,Integer.parseInt(Execute.Returnbody),GetCurrentSystemTime.GetCurrentTime(),"每日订单数量统计 !!");
                        data.InsertDatabaseSql(insertexception3);
                    }
                    //针对连接超时异常，跳过执行
                    else if(ResultNum==5)
                    {
                        Reporter.log( "<p  style=\"font-weight: 900;color:DarkOrange;font-size:15px \">"+"No." + testCase.getId() + "操作说明" + testCase.getDescription()+"\n操作时间: "+ GetCurrentSystemTime.GetCurrentTime()+"</p>");
                        String insertexception3=Operation.DataToInsertAbnormal(3,1,list.get(0).getModePath(),PlatformName,0, GetCurrentSystemTime.GetCurrentTime(),"连接超时异常！！");
                        data.InsertDatabaseSql(insertexception3);
                        continue;
                    }
                    else {
                        Reporter.log( "No." + testCase.getId() + "操作说明" + testCase.getDescription()+"\n操作时间: "+ GetCurrentSystemTime.GetCurrentTime());
                        driver.sleep(2000);
                        driver.saveScreenshot(img);
                    }
                    int AbnormalStatus=Abnormal.WhetherCatchAbnormal(driver,img);//查找异常
                    //查询到系统出现异常
                    if(AbnormalStatus==1)
                    {
                       // Reporter.log( "<p  style=\"color:MediumVioletRed \">"+"No." + testCase.getId() + "操作说明" + testCase.getDescription()+"\n操作时间: "+ GetCurrentSystemTime.GetCurrentTime()+"</p>");
                        String insertexception2=Operation.DataToInsertAbnormal(2,1,list.get(0).getModePath(), PlatformName, 0,GetCurrentSystemTime.GetCurrentTime(),"macaca检测平台有异常,联系对应项目经理!!!"+"\n"+Abnormal.AbnormalDetailContent);
                        data.InsertDatabaseSql(insertexception2);
                        Reporter.log(reportimg);
                        System.out.print("AbnormalDetailContent= :"+Abnormal.AbnormalDetailContent+"\n");
                        EventListenerMonitoring.Listenerflag = 2;
                        Assert.assertEquals(" do abnormal","no abnormal ","macaca检测平台有异常!!异常信息如下： "+"\n"+Abnormal.AbnormalDetailContent);
                    }
                }
                Reporter.log(reportimg);
                ElementCount++;
            }
        }
       Runtime.getRuntime().exec("taskkill /f /im chrome.exe");//调用dos命令杀死谷歌进程

    }
}
