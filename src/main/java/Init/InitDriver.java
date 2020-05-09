package Init;
import CollectionOfFunctionalMethods.BasicMethods.AssertionListener;
import CollectionOfFunctionalMethods.BasicMethods.EventListenerMonitoring;
import CollectionOfFunctionalMethods.BasicMethods.GetCurrentSystemTime;
import com.alibaba.fastjson.JSONObject;
import macaca.client.MacacaClient;
import org.testng.Reporter;

/**
 * Created by admin on 2017/10/27.
 */
public class InitDriver {
    //处理来自testng.xml的文件夹路径(testExcelPath)参数，并传入文件
//    protected static String testDataUrl = "D:/DepartmentFiles/fjchjMacacaProject/src/main/resources/testData.xlsx";
    protected MacacaClient driver ;
//    protected static String testExcelPath = "";
    //初始化macacaDriver
//    @BeforeSuite
    //@BeforeTest
    public  InitDriver()
    {
        driver=new MacacaClient();
    }
    public MacacaClient MacacaInit() throws Exception {
		/*
           Desired Capabilities are used to configure webdriver when initiating the session.
           Document URL: https://macacajs.github.io/desired-caps.html
         */
        JSONObject jsono = new JSONObject();
        JSONObject desiredCapabilities = new JSONObject();
        jsono.put("browserName", "chrome");
        jsono.put("platformName", "desktop");
        jsono.put("port", Integer.parseInt(AssertionListener.port));

        desiredCapabilities.put("desiredCapabilities", jsono);
        try{
            driver.initDriver(desiredCapabilities);//initDriver有传port和host值会默认修改
        }
        catch(org.apache.http.conn.HttpHostConnectException e){
            System.out.print("启动失败！");
            EventListenerMonitoring.Listenerflag = 2;
            Reporter.log( "<p  style=\"color:red\">"+" Connect to localhost:3456 [localhost/127.0.0.1, localhost/0:0:0:0:0:0:0:1] failed: Connection refused: connect"+"</p>");

        }

        return driver;
    }
}
