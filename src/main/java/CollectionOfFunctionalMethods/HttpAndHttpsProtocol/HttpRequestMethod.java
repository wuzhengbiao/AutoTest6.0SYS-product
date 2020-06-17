package CollectionOfFunctionalMethods.HttpAndHttpsProtocol;
import CollectionOfFunctionalMethods.BasicMethods.EventListenerMonitoring;
import CollectionOfFunctionalMethods.BasicMethods.StringSubByContent;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Apache Http Client 和 OkHttpClient 都不支持 GET 请求发送 Body 数据，而 AsyncHttpClient 是可以的
 * HttpClient发送GET、POST请求
 * @Author wuzb
 * @CreateDate 2018.5.28 16:56
 */
public class HttpRequestMethod {
    private static final int SUCCESS_CODE = 200;
    public static String  HttpStatusFlag="";
    /**
     * 组织请求参数{参数名和参数值下标保持一致}
     *
     * @param params 参数名数组
     * @param values 参数值数组
     * @return 参数对象
     */
    public static List <NameValuePair> GetParams(Object[] params, Object[] values) {
        /**
         * 校验参数合法性
         */
        boolean flag = params.length > 0 && values.length > 0 && params.length == values.length;
        if (flag) {
            List <NameValuePair> nameValuePairList = new ArrayList <>();
            for (int i = 0; i < params.length; i++) {
                nameValuePairList.add( new BasicNameValuePair( params[i].toString(), values[i].toString() ) );
                System.out.print( params[i].toString() + "--" + values[i].toString() + "\n" );
            }
            return nameValuePairList;
        } else {
            System.out.print( "请求参数为空且参数长度不一致" + "\n" );
        }
        return null;
    }
    /**
     * 发送GET请求body是字符串
     * @param url
     * @return JSON或者字符串
     * @throws Exception
     */
    public  Object SendGetNullBody(String url,String AppAuthentication,String Conten_type,String Authorization) throws Exception {
        HttpStatusFlag="";
        CloseableHttpClient client = null;
        CloseableHttpResponse Getresponse = null;
        try {
            /**
             *  创建一个httpclient对象
             */
            client = HttpClients.createDefault();
            /**
             * 创建一个get对象
             */
            HttpGet get = new HttpGet( url.trim() );
            /**
             * 设置请求的报文头部的编码
             */
            get.setHeader( new BasicHeader( "Content-Type",  Conten_type+";charset=utf-8" ) );
            /**
             * 设置请求的报文头部授权和格式
             */
            get.setHeader( new BasicHeader("App-Authentication",AppAuthentication.trim()) );
            get.setHeader( new BasicHeader("Authorization",Authorization.trim()) );
            get.setHeader( new BasicHeader( "accept", "application/json, text/plain, */*" ) );
            /**
             * 执行post请求
             */
            Getresponse = client.execute( get );
            /**
             * 获取响应码
             */
            int statusCode = Getresponse.getStatusLine().getStatusCode();
            System.out.print( "get请求code:" + statusCode + "\n" );
            if (SUCCESS_CODE == statusCode) {
                /**
                 * 通过EntityUitls获取返回内容
                 */
                HttpStatusFlag="1";//成功标记
                String result = EntityUtils.toString( Getresponse.getEntity(), "UTF-8" );
                System.out.print( "get请求结果响应: " + result + "\n" );
                return StringSubByContent.SubByContentLBorRB(result,"{",",\"",1);
            } else {
                System.out.print( "get请求失败！" + "\n" );
                EventListenerMonitoring.Listenerflag=2;
                HttpStatusFlag="0";//失败标记
                return  Getresponse;
            }
        } catch (Exception e) {
            System.out.print( "get请求异常！" + "\n" );
            HttpStatusFlag="0";
            EventListenerMonitoring.Listenerflag=2;
            return Getresponse;
        } finally {
            Getresponse.close();
            client.close();
        }
    }
    /**
     * 发送GET请求
     * @param url               请求url
     * @param nameValuePairList 请求参数
     * @return JSON或者字符串
     * @throws Exception
     */
    public  Object SendGet(String url, List <NameValuePair> nameValuePairList,String Conten_type,String AppAuthentication) throws Exception {
        JSONObject jsonObject = null;
        CloseableHttpClient client = null;
        CloseableHttpResponse response = null;
        try {
            /**
             * 创建HttpClient对象
             */
            client = HttpClients.createDefault();
            /**
             * 创建URIBuilder
             */
            URIBuilder uriBuilder = new URIBuilder( url );
            /**
             * 设置参数
             */
            uriBuilder.addParameters( nameValuePairList );
            /**
             * 创建HttpGet
             */
            HttpGet httpGet = new HttpGet( uriBuilder.build() );
            /**
             * 设置请求头部编码
             */
            httpGet.setHeader( new BasicHeader( "Content-Type", Conten_type+"; charset=utf-8" ) );
            httpGet.setHeader( new BasicHeader("App-Authentication",AppAuthentication) );
            /**
             * 设置返回编码
             */
            httpGet.setHeader( new BasicHeader( "Accept", "text/plain;charset=utf-8" ) );
            /**
             * 请求服务
             */
            response = client.execute( httpGet );
            /**
             * 获取响应吗
             */
            int statusCode = response.getStatusLine().getStatusCode();
            System.out.print( "GET请求code值：" + httpGet + "\n" );
            if (SUCCESS_CODE == statusCode) {
                /**
                 * 获取返回对象
                 */
                HttpEntity entity = response.getEntity();
                /**
                 * 通过EntityUitls获取返回内容
                 */
                String result = EntityUtils.toString( entity, "UTF-8" );
                System.out.print( "GET请求结果响应:" + result + "\n" );
                /**
                 * 转换成json,根据合法性返回json或者字符串
                 */
                try {
                    jsonObject = JSONObject.parseObject( result );
                    return jsonObject;
                } catch (Exception e) {
                    return result;
                }
            } else {
                System.out.print( "GET请求失败！" + "\n" );
            }
        } catch (Exception e) {
            System.out.print( "GET请求异常！" + "\n" );
        } finally {
            response.close();
            client.close();
        }
        return null;
    }

    /**
     * 发送POST请求
     *
     * @param url
     * @param nameValuePairList
     * @return JSON或者字符串
     * @throws Exception
     */
    public static Object SendPostJosn(String url, List <NameValuePair> nameValuePairList,String Conten_type,String AppAuthentication) throws Exception {
        JSONObject jsonObject = null;
        CloseableHttpClient client = null;
        CloseableHttpResponse response = null;
        try {
            /**
             *  创建一个httpclient对象
             */
            client = HttpClients.createDefault();
            /**
             * 创建一个post对象
             */
            HttpPost post = new HttpPost( url );
            /**
             * 包装成一个Entity对象
             */
            StringEntity entity = new UrlEncodedFormEntity( nameValuePairList, "UTF-8" );
            post.setEntity( entity );
            System.out.print( "POST请求的entity:" + entity + "\n" );
            /**
             * 设置请求的报文头部的编码
             */
            post.setHeader( new BasicHeader( "Content-Type",  Conten_type+";charset=utf-8" ) );
            post.setHeader( new BasicHeader("App-Authentication",AppAuthentication) );
            /**
             * 设置请求的报文头部的编码
             */
            post.setHeader( new BasicHeader( "accept", "application/json" ) );
            /**
             * 执行post请求
             */
            System.out.print( "POST请求的全部内容:" + post + "\n" );
            response = client.execute( post );
            /**
             * 获取响应码
             */
            int statusCode = response.getStatusLine().getStatusCode();
            if (SUCCESS_CODE == statusCode) {
                /**
                 * 通过EntityUitls获取返回内容
                 */
                String result = EntityUtils.toString( response.getEntity(), "UTF-8" );
                System.out.print( "POST请求结果响应: " + result + "\n" );
                /**
                 * 转换成json,根据合法性返回json或者字符串
                 */
                try {
                    jsonObject = JSONObject.parseObject( result );
                    return jsonObject;
                } catch (Exception e) {
                    return result;
                }
            } else {
                System.out.print( "POST请求失败！" + "\n" );
            }
        } catch (Exception e) {
            System.out.print( "POST请求异常！" + "\n" );
        } finally {
            response.close();
            client.close();
        }
        return null;
    }
    /**
     * 发送POST请求body是字符串
     * @param url
     * @param json
     * @return JSON或者字符串
     * @throws Exception
     */
    public  Object SendPostString(String url, String json,String AppAuthentication,String Conten_type,String Authorization) throws Exception {
        JSONObject jsonObject = null;
        HttpStatusFlag="";
        CloseableHttpClient client = null;
        CloseableHttpResponse response = null;
        try {
            /**
             *  创建一个httpclient对象
             */
            client = HttpClients.createDefault();
            /**
             * 创建一个post对象
             */
            HttpPost post = new HttpPost( url.trim() );
            /**
             * 包装成一个Entity对象
             */
            StringEntity entity = new StringEntity( json.trim(), "UTF-8" );
            post.setEntity( entity );
            System.out.print( "POST请求的entity:" + entity + "\n" );
            /**
             * 设置请求的报文头部的编码
             */
            post.setHeader( new BasicHeader( "Content-Type",  Conten_type+";charset=utf-8" ) );
            /**
             * 设置请求的报文头部授权和格式
             */
            post.setHeader( new BasicHeader("App-Authentication",AppAuthentication.trim()) );
            post.setHeader( new BasicHeader("Authorization",Authorization.trim()) );
            post.setHeader( new BasicHeader( "accept", "application/json, text/plain, */*" ) );
            /**
             * 执行post请求
             */
            response = client.execute( post );
            /**
             * 获取响应码
             */
            int statusCode = response.getStatusLine().getStatusCode();
            System.out.print( "POST请求code:" + statusCode + "\n" );
            if (SUCCESS_CODE == statusCode) {
                /**
                 * 通过EntityUitls获取返回内容
                 */
                HttpStatusFlag="1";//成功标记
                String result = EntityUtils.toString( response.getEntity(), "UTF-8" );
                System.out.print( "POST请求结果响应: " + result + "\n" );
                return StringSubByContent.SubByContentLBorRB(result,"{",",\"",1);
            } else {
                System.out.print( "POST请求失败！" + "\n" );
                HttpStatusFlag="0";//失败标记
                EventListenerMonitoring.Listenerflag=2;
                return  response;
            }
        } catch (Exception e) {
            System.out.print( "POST请求异常！" + "\n" );
            HttpStatusFlag="0";
            EventListenerMonitoring.Listenerflag=2;
            return response;
        } finally {
            response.close();
            client.close();
        }
    }
    //参数格式使用map传递
    public  static String HttpPostMap(String url, Map<String, String> headerMap, Map <String, ?> contentMap) {
        String result = null;
        CloseableHttpClient httpClient = null;
        try {
            httpClient=HttpsRequest.createHttpClient();
        } catch (Exception e) {
            System.out.print( "请求异常！" + "\n" );
        }
        HttpPost post = new HttpPost( url );
        System.out.print( "请求头！" + "\n"+post );
        List <BasicNameValuePair> content = new ArrayList <>();
        Iterator iterator = contentMap.entrySet().iterator();           //将content生成entity
        while (iterator.hasNext()) {
            Map.Entry <String, String> elem = (Map.Entry <String, String>) iterator.next();
            content.add( new BasicNameValuePair( elem.getKey(), elem.getValue() ) );
        }
        CloseableHttpResponse response = null;
        try {
            Iterator headerIterator = headerMap.entrySet().iterator();          //循环增加header
            while (headerIterator.hasNext()) {
                Map.Entry <String, String> elem = (Map.Entry <String, String>) headerIterator.next();
                post.addHeader( elem.getKey(), elem.getValue() );
            }
            if (content.size() > 0) {
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity( content, "UTF-8" );
                post.setEntity( entity );
            }
            response = httpClient.execute( post );            //发送请求并接收返回数据
            System.out.print( "请求返回的全部内容： " + response );
            if (response != null && response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();       //获取response的body部分
                result = EntityUtils.toString( entity );          //读取reponse的body部分并转化成字符串
                System.out.print( "请求返回： " + result );
            }
            return result;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return null;
    }

}

