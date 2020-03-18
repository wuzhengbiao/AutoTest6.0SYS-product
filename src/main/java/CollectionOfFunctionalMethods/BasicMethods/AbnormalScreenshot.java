package CollectionOfFunctionalMethods.BasicMethods;
import macaca.client.MacacaClient;
import org.testng.Assert;

/**
 * 查询用例执行过程中出现抛出的异常,并截图
 * @author wzb
 */
public class AbnormalScreenshot {
    //  返回0：异常没有找到
    //  返回1：找到异常了,并展开明细
    private   String AbnormalXpath = "//*[@id=\"detail__\"]";
    private   String AbnormalXpathDetail="//*[@ng-click=\"msg.showMeItem=!msg.showMeItem\"]";
    public static final String AbnormalXpathDetailGetText="//*[@class=\"message-content ng-binding ng-scope\"]";//获取详细内容
    public    String AbnormalDetailContent=null;
    public int WhetherCatchAbnormal(MacacaClient Driver,String img) throws Exception {
        int frequency=1;//查找次数
        try{
            if (Driver.waitForElementByXPath(AbnormalXpath)==null)
            {
                for (int i=1; i <= frequency; i++)//可以自定义等待区间时长
                {
                    Driver.sleep(1000);
                    if(Driver.waitForElementByXPath(AbnormalXpath)!=null)//隔一秒查找元素
                    {
                        System.out.println("等待了："+i+"秒,找到异常了!");
                        break;
                    }
                    if (i == frequency) {
                        return 0;//没找到
                    }
                }
            }
        }
        catch(NullPointerException e){
            String Abnormal="macaca对象 Driver="+Driver;
            MailDelivery.TCTestCaseMailSending(1);
            Assert.assertEquals(Abnormal,"初始化成功","原因:MacacaClient初始化失败,看下服务是否开启!!");
        }
        Driver.elementByXPath(AbnormalXpath).click();
        Driver.sleep(1000);
        Driver.elementByXPath(AbnormalXpathDetail).click();
        AbnormalDetailContent= Driver.elementByXPath(AbnormalXpathDetailGetText).getText();
        System.out.println("AbnormalXpathDetail 的定位："+AbnormalXpathDetail);
        Driver.sleep(1000);
        Driver.saveScreenshot(img);
        return 1;
    }
   /* //中文转换成GBK码(16进制字符串)，每个汉字2个字节
    public String Chinese2GBK(String chineseStr)throws Exception {
        StringBuffer GBKStr = new StringBuffer();
        byte[] GBKDecode = chineseStr.getBytes("gbk");
        for (byte b : GBKDecode)
            GBKStr.append(Integer.toHexString(b&0xFF));
        return GBKStr.toString().toUpperCase();
    }
    public  String ChinesetoGBK(String source) throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        byte[] bytes = source.getBytes("GBK");
        for(byte b : bytes) {
            sb.append("%" + Integer.toHexString((b & 0xff)).toUpperCase());
        }
        return sb.toString();
    }*/
    /**
     * gbk与utf-8互转
     * 利用BASE64Encoder/BASE64Decoder实现互转
     * @param str
     * @return
     */
    /*private String charsetConvert(String str, String charset) {
        try {
            str = new sun.misc.BASE64Encoder().encode(str.getBytes(charset));
            byte[] bytes = new sun.misc.BASE64Decoder().decodeBuffer(str);
            str = new String(bytes, charset);
        } catch(IOException e) {
            e.printStackTrace();
        }
        return str;
    }*/
}
