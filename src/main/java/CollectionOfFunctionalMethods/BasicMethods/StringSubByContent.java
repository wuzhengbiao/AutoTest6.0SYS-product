package CollectionOfFunctionalMethods.BasicMethods;

import java.io.IOException;

public class StringSubByContent {
    public static String SubByContentLBorRB(String buffer,String LB,String RB,int skip) throws IOException {
        String context = "";
        if(skip==1)
        {
            context=buffer.substring(0, buffer.indexOf(RB));
            context=context.substring(buffer.indexOf(LB)+LB.length());
            System.out.println("截取获得值:"+context+"\n");
        }
        else
        {
            context=buffer;
            System.out.println("请求返回值 跳过:"+context+"\n");
        }
        return context;
    }
    public static String ReplaceByContentLBorRB(String buffer,String LB,String RB,String needcontext,int skip) throws IOException {
        String Replacecontext = "";
        String Replacecontextstart = "";
        String ReplacecontextLast = "";
        int startlenth=0;
        int endlenth=0;
        if(skip==1)
        {
            System.out.println("截取获得值0:"+buffer+"\n");
            Replacecontextstart=buffer.substring(0, buffer.indexOf(RB));
            System.out.println("截取获得值1:"+Replacecontextstart+"\n");
            endlenth=Replacecontextstart.length();
            System.out.println("end:"+endlenth+"\n");
            Replacecontext=Replacecontextstart.substring(buffer.indexOf(LB)+LB.length());
            System.out.println("截取获得值2:"+Replacecontext+"\n");
            startlenth=Replacecontextstart.length()-Replacecontext.length();
            System.out.println("startlenth:"+startlenth+"\n");
            //System.out.println("截取获得值最后:"+Replacecontext+"\n");
            System.out.println("按开始和结束截取:"+buffer.subSequence(startlenth,endlenth)+"\n");
            ReplacecontextLast=buffer.replace(buffer.subSequence(startlenth,endlenth),needcontext);
            System.out.println("最终替换:"+ReplacecontextLast+"\n");
        }
        else
        {
            ReplacecontextLast=buffer;
            System.out.println("请求返回值 跳过:"+ReplacecontextLast+"\n");
        }
        return ReplacecontextLast;
    }
}
