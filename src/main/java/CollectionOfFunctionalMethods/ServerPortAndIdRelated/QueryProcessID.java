package CollectionOfFunctionalMethods.ServerPortAndIdRelated;
import CollectionOfFunctionalMethods.BasicMethods.DosCommand;

import java.util.ArrayList;
import java.util.List;
public class QueryProcessID {
    public  List<String> GetChromeProcessPID() {
        DosCommand ChromeProcess = new DosCommand();
        int ChromeValueNumber;
        int ChromeServerResultNumber;
        String ChromePidValue = "";
        String Chromeinformation = "";//获取服务信息
        List<String> ChromePidValues=new ArrayList<String>();//收集有效pid值
        try {
            List<String> ChromeProcessSever = ChromeProcess.RunDosCommand("tasklist|find /i \"Chrome.exe\"");//查找macaca服务
            if (ChromeProcessSever.size() != 0) {
                for (ChromeValueNumber = 0; ChromeValueNumber < ChromeProcessSever.size(); ChromeValueNumber++) {
                    Chromeinformation = ChromeProcessSever.get(ChromeValueNumber);
                    String[] StringValueList = Chromeinformation.split("\\s+");
                    for (ChromeServerResultNumber = 0; ChromeServerResultNumber < StringValueList.length; ChromeServerResultNumber++) {
                        System.out.println("每行信息值:" + StringValueList[ChromeServerResultNumber]);
                        if (StringValueList[ChromeServerResultNumber].contains("Console") || StringValueList[ChromeServerResultNumber].contains("RDP") || StringValueList[ChromeServerResultNumber].contains("Ser")) {
                            ChromePidValue = StringValueList[ChromeServerResultNumber - 1];
                            System.out.println("找到pid值= :" + ChromePidValue);
                        } else {
                            ChromePidValue = StringValueList[1];
                            System.out.println(" 没有平台类型,所以pid= :" + ChromePidValue);
                        }
                        List<String> ChromePidResult = ChromeProcess.RunDosCommand("netstat -ano | findstr "+ChromePidValue);
                        System.out.println("PidResult :"+ChromePidResult.size());
                        if(ChromePidResult.size() !=0)//找到正确的pid值才进入循环
                        {
                            ChromePidValues.add(ChromeValueNumber,ChromePidValue);//找到正确的pid并赋值
                            System.out.println("ReturnPid :"+ChromePidValues.get(ChromeValueNumber));
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ChromePidValues;
    }
    public  String KillChromeProcessPID() {

        return "";
    }
    }

