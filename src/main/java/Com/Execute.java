package Com;

import CollectionOfFunctionalMethods.BasicMethods.EventListenerMonitoring;
import CollectionOfFunctionalMethods.BasicMethods.GetRandom;
import CollectionOfFunctionalMethods.BasicMethods.RobotAction;
import CollectionOfFunctionalMethods.BasicMethods.StringSubByContent;
import macaca.client.MacacaClient;
import org.testng.Assert;
import org.testng.Reporter;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by admin on 2017/10/31.
 */
public class Execute {
//  返回0：元素未找寻到
//  返回1：正常
//  返回2：未进入合理判断的分支
//  返回3: 特殊处理报表数据过大截图时间
//  返回4:未知的异常
//  返回5：超时
    private String Aftertime="";
    private String beforetime="0";
    private String GetClickText="";
    private String Intercept_content="";
    public String   Returnbody="";
    public int execute(MacacaClient Driver, TestingCase Testingcase, int T) throws Exception {
        if (Testingcase.getModel().equals("访问")){
            try {
                Driver.sleep(7000);
                Driver.get(Testingcase.getModePath());
                return 1;
            }
            catch (Exception e) {
                return 5;
            }

        }
    else if (Testingcase.getModel().equals("输入")){
        //当前绑死使用Xpath方法
        try {
        if (Driver.waitForElementByXPath(Testingcase.getModePath())==null)
        {
            for (int i=1; i <= T; i++)//可以自定义等待区间时长
            {
                Driver.sleep(1000);
                if(Driver.waitForElementByXPath(Testingcase.getModePath())!=null)//隔一秒查找元素,找到元素跳出等待
                {
                    break;
                }
                if (i == T) {
                    return 0;
                }
            }
        }
              //  Driver.elementByXPath(Testingcase.getModePath()).clearText();
                Driver.elementByXPath(Testingcase.getModePath()).sendKeys(Testingcase.getText());
                return 1;
    }catch (Exception e) {
            return 4;
        }
            }
    else if (Testingcase.getModel().equals("点击")) {
        try {
            if (Driver.waitForElementByXPath(Testingcase.getModePath()) == null) {
                for (int i = 1; i <= T; i++)//可以自定义等待区间时长
                {
                    Driver.sleep(1000);
                    if (Driver.waitForElementByXPath(Testingcase.getModePath()) != null)//隔一秒查找元素,找到元素跳出等待
                    {
                        break;
                    }
                    if (i == T) {
                        return 0;
                    }
                }
            }
            Driver.elementByXPath(Testingcase.getModePath()).click();
            if (Testingcase.getText().contains("文本")) {
                GetClickText = Driver.elementByXPath(Testingcase.getModePath()).getText();
                Aftertime=GetClickText;//当前点击元素获取到的文本值赋值给被比较的成员
                System.out.println("当前点击元素获取到的文本值=" + GetClickText);
            }
            return 1;
        } catch (Exception e) {
            return 4;
        }
    }
    else  if(Testingcase.getModel().equals("悬停"))
    {
        try {
            RobotAction.MouseMovement(0, 566, 566);
            if (Driver.waitForElementByXPath(Testingcase.getModePath()) == null) {
                System.out.println("找了四遍没有找到，开始等待！\n");
                for (int i = 1; i <= T; i++)//可以自定义等待区间时长
                {
                    Driver.sleep(1000);
                    if (Driver.waitForElementByXPath(Testingcase.getModePath()) != null)//隔一秒查找元素
                    {
                        System.out.println("等待了：" + i + "秒,找到元素了");
                        break;
                    }
                    if (i == T) {
                        return 0;
                    }
                }
            }

            Driver.elementByXPath(Testingcase.getModePath()).click();
            return 1;
        }catch (Exception e) {
            return 4;
        }

    }
    else if (Testingcase.getModel().equals("返回"))
    {
        String regEx="[^0-9]";
        Pattern Mypattern = Pattern.compile(regEx);//创建正则法则匹配的表达式
        Matcher matcherstring = Mypattern.matcher(Testingcase.getText());//Pattern调用matcher返回Matcher 类的实例,提供了对正则表达式支持,按正则匹配
        String  GetNumString=matcherstring.replaceAll("").trim();//不匹配的字符全部设置成空格,并去掉
        int intmatcher=Integer.parseInt(GetNumString);
        System.out.println(intmatcher+"字符:"+GetNumString);
        for(int backnum=1;backnum<=intmatcher;backnum++)
        {
            Driver.back();
            Driver.sleep(500);
        }
    }
    else if (Testingcase.getModel().equals("JavaScript"))
    {
        try{
        String javascriptcode=Testingcase.getModePath();
        //"var alpha = 30;var x=document.getElementsByClassName(\"hover-block\")[0];x.style.opacity = alpha ; alert(document.body.contains(document.getElementsByClassName(\"hover-block\")[0]));alert(x.style.opacity );"
        Driver.execute(javascriptcode);
        return 1;
    }catch (Exception e) {
            return 4;
        }

    }
    else if (Testingcase.getModel().equals("比较")) {
        System.out.print("beforetime= " + beforetime + "\n" + "Aftertime= " + Aftertime + "\n");
        if (!Aftertime.equals(beforetime)) {
            System.out.print("进入比较不相等了" + "\n");
            if (Testingcase.getText().equals("相等")) {
                Assert.assertEquals("不相等", "相等", "前后值比较失败啦!初始值="+beforetime+" 比较值= "+Aftertime);
            }
            else{
                Reporter.log("前后比较成功 ,新获取文本值=" + Aftertime);//写入报告图片地址
            }
        }
        else {
            if (Testingcase.getText().equals("相等")) {
                Reporter.log("前后比较成功 ,新获取文本值=" + Aftertime);//写入报告图片地址
            }
            else{
                EventListenerMonitoring.Listenerflag = 2;
				Assert.assertEquals("相等", "不相等", "前后值比较失败啦!初始值="+beforetime+" 比较值= "+Aftertime);
            }
        }
        beforetime=Aftertime;
        return 1;
    }
    else if (Testingcase.getModel().equals("接口截取"))
    {
        try{
            String[] Interface=null;
            Interface=Testingcase.getText().split("#");
            String TestingcaseModePath=null;
            for(int listnum=0;listnum<=(Interface.length-1)/2;listnum+=2) {
                System.out.println(Interface[listnum] + "Interface[listnum+1]的值=" + Interface[listnum + 1] + "Interface[listnum+2]的值=" + Interface[listnum + 2] + "\n");
                Intercept_content = StringSubByContent.SubByContentLBorRB(GetClickText, Interface[listnum+1],Interface[listnum+2], 1);
                System.out.println( "Intercept_contentb报文截取值="+ Intercept_content);
            }
            Aftertime=Intercept_content;//要比较的值赋值给被比较成员
            return 1;
        }catch (Exception e) {
            return 4;
        }
    }
    else if (Testingcase.getModel().equals("查验")) {
        try{
        if (Driver.waitForElementByXPath(Testingcase.getModePath())==null)
        {
            for (int i=1; i <= T; i++)//可以自定义等待区间时长
            {
                Driver.sleep(1000);
                if(Driver.waitForElementByXPath(Testingcase.getModePath())!=null)//隔一秒查找元素,找到元素跳出等待
                {
                    break;
                }
                if (i == T) {
                    return 1;
                }
            }
        }
         Returnbody=Driver.elementByXPath(Testingcase.getModePath()).getText();
        if(Returnbody.contains("未完成")||Returnbody.contains("正在")||Returnbody.contains("未搜索"))
        {
            Assert.assertEquals("  "+ Returnbody +"  !  ","");
        }
        else
        {
            return 3;
        }
    }catch (Exception e) {
            return 4;
        }
    }
    else if(Testingcase.getModel().equals("多选"))
        {
            String[] RangeAndNum=null;
            RangeAndNum=Testingcase.getText().split( "/" );
            List random=new ArrayList();
            random= GetRandom.randomArr(RangeAndNum[0]);
            if(Testingcase.getModePath().contains("x"))
            {
                for(int n=0;n<Integer.parseInt(RangeAndNum[1]);n++) {
                    System.out.print("生成的随机数： "+ random.get( n ).toString()+"\n");
                    String NewPath = Testingcase.getModePath().replaceAll( "x", random.get( n ).toString() );
                    System.out.print("随机生成的xpath路径 ： "+ NewPath+"\n");
                    if (Driver.waitForElementByXPath( NewPath ) == null) {
                        System.out.println( "找了四遍没有找到，开始等待！\n" );
                        for (int i = 1; i <= T; i++)//可以自定义等待区间时长
                        {
                            Driver.sleep( 1000 );
                            if (Driver.waitForElementByXPath( NewPath ) != null)//隔一秒查找元素
                            {
                                System.out.println( "等待了：" + i + "秒,找到元素了" );
                                break;
                            }
                            if (i == T) {
                                return 0;
                            }
                        }
                    }
                    Driver.elementByXPath( NewPath ).click();
                }
            }
            return 1;
        }
    else if (Testingcase.getModel().equals("等待")){
        int i = 0;
        do {
            Driver.sleep(1000);
            i++;
        }while (i <= T);
        return 1;
    }
        return 2;
    }
}


