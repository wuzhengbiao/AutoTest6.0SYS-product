package Testing;

import CollectionOfFunctionalMethods.BasicMethods.EventListenerMonitoring;
import CollectionOfFunctionalMethods.BasicMethods.GetCurrentSystemTime;
import CollectionOfFunctionalMethods.BasicMethods.SpecifyLengthWrap;
import CollectionOfFunctionalMethods.HttpAndHttpsProtocol.HttpRequestMethod;
import Com.InterfaceExecute;
import Com.ReadExcel;
import Com.TestingCase;
import org.apache.commons.lang3.StringUtils;
import org.testng.Reporter;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.net.URLDecoder;
import java.util.List;

public class InterfaceTest {
    public int Time;
    public String ProgramPath;

    @Parameters({"TestFilePath" ,"PlatformName", "TIME" , "TestName"})
    @Test
    public void interfacetest(String TestFilePath, String PlatformName, int TIME, String TestName) throws Exception {
        InterfaceExecute Execute = new InterfaceExecute();
        Time = TIME;
        ProgramPath = URLDecoder.decode(Tester.class.getResource("").toString(),"UTF-8");
        ProgramPath = StringUtils.substringBefore(ProgramPath,"/target");
        ProgramPath = StringUtils.substringAfter(ProgramPath,"file:/");
        ReadExcel reade = new ReadExcel();
        List<TestingCase> list =  reade.readXlsx(ProgramPath+TestFilePath);
        if (list != null) {
            for (TestingCase testCase : list) {
                String Loginstruction="No." + testCase.getId() + " 协议说明: " + testCase.getDescription() + ",传输协议: " + testCase.getModel() + ", 请求方式: " + testCase.getMode()  +" 操作时间:  "+ GetCurrentSystemTime.GetCurrentTime();
                Object Response = Execute.Interfaceexecute(testCase );//获取协议响应内容
                String ResponseSubcontent= SpecifyLengthWrap.getStringByEnter(90,Response.toString());
                System.out.print("标志 "+ EventListenerMonitoring.Listenerflag+"\n");//写入报告步骤说明
                if(HttpRequestMethod.HttpStatusFlag.equals( "0" ))
                {
                    Reporter.log("<p  style=\"font-weight: 900;color:red;text-align: left;font-size:15px\">"+Loginstruction+"</p>");
                    Reporter.log( "<p  style=\"color:red\"> 请求url：" + SpecifyLengthWrap.getStringByEnter(90,testCase.getModePath())+"</p>" );
                    Reporter.log( "<p  style=\"color:red\"> 参数值： " + SpecifyLengthWrap.getStringByEnter(90,testCase.getText()) +"</p>");
                    Reporter.log( "<p  style=\"color:red\"> 返回值： " + ResponseSubcontent +"</p>");
                }
                else {
                    Reporter.log("<p  style=\"font-weight: 900;color:black;text-align: left;font-size:15px\">"+Loginstruction+"</p>");
                    Reporter.log( "<p>请求url：" + SpecifyLengthWrap.getStringByEnter(90,testCase.getModePath())+"</p>");
                    Reporter.log( "<p>参数值： " + SpecifyLengthWrap.getStringByEnter(90,testCase.getText()) +"</p>");
                    Reporter.log( "<p>返回值： " + ResponseSubcontent+"</p>" );
                }
            }
        }
    }
}
