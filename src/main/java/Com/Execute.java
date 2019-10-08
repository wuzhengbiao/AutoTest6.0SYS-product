package Com;

import BasicMethods.RobotAction;
import BasicMethods.SessionListener;
import BasicMethods.StringSubByContent;
import macaca.client.MacacaClient;
import org.testng.Assert;
import org.testng.Reporter;

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
    private String Aftertime="";
    private String beforetime="";
    private String tokenAndopenid="";
    private String access_token="";
    public int execute(MacacaClient Driver, TestingCase Testingcase, int T) throws Exception {
    if (Testingcase.getModel().equals("访问")){
        Driver.sleep(7000);
        Driver.get(Testingcase.getModePath());
        return 1;
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
                Driver.elementByXPath(Testingcase.getModePath()).clearText();
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
            if (Testingcase.getText().contains("接口")) {
                tokenAndopenid = Driver.elementByXPath(Testingcase.getModePath()).getText();
                System.out.println("请求返回值" + tokenAndopenid);
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
        try{
        if (Driver.waitForElementByXPath(Testingcase.getModePath()) == null) {
            //System.out.println("找了四遍没有找到，开始等待！\n");
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
        String IntermediateValue = Driver.elementByXPath(Testingcase.getModePath()).getText();
        Aftertime=IntermediateValue;
        System.out.print("beforetime= "+beforetime+"\n"+"Aftertime= "+Aftertime+"\n");
        if(!Aftertime.equals(beforetime))
        {
            Reporter.log("前后比较成功 ,新获取文本值="+Aftertime);//写入报告图片地址
        }
        else{
            Assert.assertEquals("相等","不相等","前后值比较失败啦!");
        }
        beforetime=Aftertime;
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
        String Returnbody=Driver.elementByXPath(Testingcase.getModePath()).getText();
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
    else if (Testingcase.getModel().equals("接口"))
    {
        try {
        //String openid=StringSubByContent.SubByContentLBorRB(tokenAndopenid,"\"openid\":\"","\",\"refresh_token\":\"",1);
        String[] Interface=null;
        Interface=Testingcase.getText().split("#");
        String TestingcaseModePath=null;
        for(int listnum=0;listnum<=(Interface.length-1)/2;listnum+=2)
        {
            System.out.println(Interface[listnum]+"Interface[listnum+1]的值"+Interface[listnum+1]+"Interface[listnum+2]的值"+Interface[listnum+2]+"\n");
            if(Interface[listnum].contains("解析"))
            {
                access_token= StringSubByContent.SubByContentLBorRB(tokenAndopenid,"\"access_token\":\"","\"},\"code\"",1);
                System.out.println("access_token= "+access_token);
                TestingcaseModePath=StringSubByContent.ReplaceByContentLBorRB(Testingcase.getModePath(),Interface[listnum+1],Interface[listnum+2],access_token,1);
            }
            else {
                TestingcaseModePath = StringSubByContent.ReplaceByContentLBorRB(Testingcase.getModePath(), Interface[listnum + 1], Interface[listnum + 2], tokenAndopenid, 1);

            }
        }
        Driver.get(TestingcaseModePath);
        Driver.sleep(2000);
        return 1;
    }catch (Exception e) {
            return 4;
        }

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


