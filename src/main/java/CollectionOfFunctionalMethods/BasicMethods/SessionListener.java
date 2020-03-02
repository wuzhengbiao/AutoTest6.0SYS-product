package CollectionOfFunctionalMethods.BasicMethods;
import org.apache.http.protocol.HttpService;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SessionListener  {
    public static String HttpGet(String url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection)(new URL(url).openConnection());
        // 设置链接超时时间
        //     connection.setReadTimeout(1000);
        // 设置请求方法，默认是GET
        //      connection.setRequestMethod("GET");
        // 设置请求参数
//        connection.setRequestProperty("Content-Type", "application/json");
        // 获取输入流，用于获取服务器传回的数据
        InputStream in = connection.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String result;
        StringBuffer buf = new StringBuffer();
        try {
            while ((result = br.readLine()) != null){
                buf.append(result.trim());

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭流
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    br = null;
                }
            }
        }
        System.out.println("返回报文内容:"+buf.toString()+"\n");
        return buf.toString();
    }

}