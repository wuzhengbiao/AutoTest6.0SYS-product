package BasicMethods;
import java.util.Date;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
/**
 *
 * java实现邮箱发送邮件功能
 * @author wzb
 */
public class MailDelivery {
    public  static int SendMailValueCount=0;//统计开关=1数量
    // 发件人的邮箱地址和密码
    public static String sendEmailAccount = "942189908@qq.com";
    //如果有授权码，此处填写授权码
    public static String sendEmailPassword = "gitmwwffczafbedc";
    // 发件人邮箱的 SMTP 服务器地址, 可以登录web邮箱查询
    public static String sendEmailSMTPHost = "smtp.qq.com";
    // 收件人邮箱地址
    public static String receiveMailAccount = "942189908@qq.com";
    public static void TCTestCaseMailSending(int isSendMailValue) throws Exception {
        // 参数配置
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.smtp.host", sendEmailSMTPHost);
        props.setProperty("mail.smtp.auth", "true");
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.socketFactory.port", "465");
        // 根据配置创建会话对象, 用于和邮件服务器交互
        Session session = Session.getDefaultInstance(props);
        session.setDebug(true);   // 设置为debug模式, 可以查看详细的发送 log
        try{
                // 创建一封邮件
                Message message = createMimeMessage(session, sendEmailAccount, receiveMailAccount);
                // 根据 Session 获取邮件传输对象
                Transport transport = session.getTransport();
                // 使用 邮箱账号 和 密码 连接邮件服务器, 这里认证的邮箱必须与 message 中的发件人邮箱一致, 否则会报错
                transport.connect(sendEmailAccount, sendEmailPassword);
            if(isSendMailValue==1)
            {
                if(SendMailValueCount==0)
                {
                    // 发送邮件
                    transport.sendMessage(message, message.getAllRecipients());
                    // 关闭连接
                    transport.close();
                }
                SendMailValueCount++;
                System.out.print("SendMailValueCount 的 value 值=="+SendMailValueCount+"\n");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 创建一封简单邮件
     */
    private static Message createMimeMessage(Session session, String sendMail, String receiveMail) throws Exception {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(sendMail));
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMail));
        // 设置邮件标题
        message.setSubject("TC现网测试用例报告反馈结果");
        // 设置邮件正文
        message.setText("TC测试用例执行失败了,请点击后面的url查看报告结果  http://macaca.fjchjlan.59iedu.com:1457/History/TheReport/surefire-reports/html/index.html");
        message.setSentDate(new Date());
        //保存设置
        message.saveChanges();
        return message;
    }
}
