package BasicMethods;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class DosCommand {
    StringBuffer tv_result =  null;
    /**
     * 调用dos命令，关闭浏览器（这个关闭会把浏览器的进程全部杀死）
     * @throws
     */
    public List<String> RunDosCommand(String DosCommand) throws Exception {
        InputStream ins = null;
        List<String> list_Command = new ArrayList<String>();
        String[] cmd = new String[]{"cmd.exe","/C", DosCommand};  // dos命令
        try {
            Process process = Runtime.getRuntime().exec(cmd);
            ins = process.getInputStream();  // 获取执行cmd命令后的信息
            BufferedReader reader = new BufferedReader(new InputStreamReader(ins, "gb2312"));
            String line = null;
            while ((line = reader.readLine()) != null) {
                System.out.println("line :"+ line+"\n");
                list_Command.add(line+"\n");//获取命令执行返回结果
            }
            int exitValue = process.waitFor();
            process.getOutputStream().close();  // 不要忘记了一定要关
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list_Command;
    }

}
