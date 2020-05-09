package Testing;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import CollectionOfFunctionalMethods.BasicMethods.GetLocalConfig;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.testng.annotations.BeforeTest;

public class RewriteXml {
    @BeforeTest
    public  void CreateTestngXml() throws IOException{
        String classPath=null;
        String[] XmlContext= null;
        GetLocalConfig configclass=new GetLocalConfig();
        String config=configclass.ReadConfigFile();
        XmlContext= configclass.GetBySemicolonFromConfigFile(config);
        //创建Document实例  
        Document document = DocumentHelper.createDocument();
        //记录要保存的xml文件位置  
        classPath=this.getClass().getResource("").getPath();
        String Subsfilepath= StringUtils.substringBefore(classPath,"/target")+"\\testng_StoreTestXml.xml";
        String xmlfilepath =java.net.URLDecoder.decode(Subsfilepath,"utf-8");
        System.out.print("xmlfilepath ="+xmlfilepath+"\n");
        //创建根节点suite，并设置name属性为xmlsuitename  
        Element root = document.addElement( "suite" ).addAttribute("name", "Suite");
        //创建监听值
        Element listeners = root.addElement( "listeners" );
        listeners.addElement( "listener" ).addAttribute("class-name","org.uncommons.reportng.HTMLReporter");
        listeners.addElement( "listener" ).addAttribute("class-name","org.uncommons.reportng.JUnitXMLReporter");
        listeners.addElement( "listener" ).addAttribute("class-name","CollectionOfFunctionalMethods.BasicMethods.AssertionListener");
        listeners.addElement( "listener" ).addAttribute("class-name","CollectionOfFunctionalMethods.UseCaseReRunCorrelation.OverrideIAnnotationTransformer");
        for(int testnum=0;testnum<=XmlContext.length-1; testnum++)
        {
            System.out.print("get file name ="+XmlContext[testnum]+"\n");
            //创建节点test，并设置name、属性  
            String testcasename=XmlContext[testnum].trim().substring(0, XmlContext[testnum].trim().indexOf(".xls"));
            Element test = root.addElement( "test" ).addAttribute("name", "现网测试用例_"+testcasename+"_调试");
            //创建xml的参数值
            Element parameter= test.addElement( "parameter" );
            parameter.addAttribute("name", "TestFilePath");
            parameter.addAttribute("value", "/TestList/"+XmlContext[testnum].trim());
            Element parameter4= test.addElement( "parameter" );
            parameter4.addAttribute("name", "PlatformName");
            parameter4.addAttribute("value", "动态TMP用例-"+XmlContext[testnum].trim());
            Element parameter2= test.addElement( "parameter" );
            parameter2.addAttribute("name", "TIME");
            parameter2.addAttribute("value", "20");
            Element parameter3= test.addElement( "parameter" );
            parameter3.addAttribute("name", "TestName");
            parameter3.addAttribute("value", XmlContext[testnum].trim());
            //创建节点classes，无属性  
            Element classes = test.addElement( "classes" );
            //创建节点classs，并设置name属性  
            Element classs= classes.addElement( "class" ).addAttribute("name","Testing.Tester");
        }
        document.addDocType("suite", null,"http://testng.org/testng-1.0.dtd");
        //输出格式设置  
        OutputFormat format = OutputFormat.createPrettyPrint();
        format = OutputFormat.createCompactFormat();
        //设置输出编码  
        format.setEncoding("UTF-8");
        //创建XML文件  
        XMLWriter writer= new XMLWriter(new OutputStreamWriter(new FileOutputStream(xmlfilepath),format.getEncoding()),format);
        writer.write( document );
        writer.close();
        document=null;
        configclass.clearInfoForFile();
    }

}
