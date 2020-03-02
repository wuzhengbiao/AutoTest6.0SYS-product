package CollectionOfFunctionalMethods.BasicMethods;

import org.apache.commons.lang3.StringUtils;

import java.io.*;

public class GetLocalConfig {
    /**
     * 读取文件内容
     **/
    public  String ReadConfigFile() throws UnsupportedEncodingException {
        BufferedReader br = null;
        String line = null;
        String filePath = null;
        String packagePath=null;
        //List<String> list_file = new ArrayList<String>();
        StringBuffer buf = new StringBuffer();
        packagePath = this.getClass().getResource("").getPath();
        packagePath = StringUtils.substringBefore(packagePath,"/target")+"/config.txt";
        filePath =java.net.URLDecoder.decode(packagePath,"utf-8");
       // filePath = StringUtils.substringAfter(filePath,"file:/");
        //String filePath= System.getProperty("user.dir")+"\\config.txt";//获取用户的当前工作目录
        System.out.println("获取目录地址:"+filePath+"\n");
        try {
            // 根据文件路径创建缓冲输入流
            br = new BufferedReader(new FileReader(filePath));
            // 循环读取文件的每一行, 对需要修改的行进行修改, 放入缓冲对象中
            while ((line = br.readLine()) != null) {
                // 如果不用修改, 则按原来的内容回写
                //System.out.println("list_file :"+ line+"\n");
                buf.append(line);
                buf.append(System.getProperty("line.separator"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            // 关闭流
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    br = null;
                }
            }
        }
        return buf.toString();
    }
    public  String[] GetBySemicolonFromConfigFile(String ConfigFileContexts)
    {
        String[] ConfigFileContextsList=null;
        try {
            ConfigFileContextsList =ConfigFileContexts.split(";");
        }catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("config file:"+ConfigFileContextsList+"\n");
        return ConfigFileContextsList;
    }
    /**
     * 清空文件内容
     *
     */
    public  void clearInfoForFile() throws UnsupportedEncodingException {
        String filePath = null;
        String packagePath=null;
        packagePath = this.getClass().getResource("").getPath();
        packagePath = StringUtils.substringBefore(packagePath,"/target")+"/config.txt";
        filePath =java.net.URLDecoder.decode(packagePath,"utf-8");
        File file =new File(filePath);
        try {
            if(!file.exists()) {
                file.createNewFile();
            }
            FileWriter fileWriter =new FileWriter(file);
            fileWriter.write("");
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

