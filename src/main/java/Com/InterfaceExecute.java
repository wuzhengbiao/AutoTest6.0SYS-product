package Com;

import CollectionOfFunctionalMethods.HttpAndHttpsProtocol.HttpRequestMethod;

/**
 * Created by wuzb on 2020/04/21.
 */
public class InterfaceExecute {
    //返回响应结果
    private String ContentType="application/json";
    public  Object result;
    public  HttpRequestMethod httpRequest = new HttpRequestMethod();
    public Object Interfaceexecute( TestingCase Testingcase) throws Exception {

        if (Testingcase.getModel().equals("http")){
            if(Testingcase.getMode().equals("post"))
            {
                result=httpRequest.SendPostString( Testingcase.getModePath(),Testingcase.getText(), Testingcase.getAppAuthentication(),ContentType,Testingcase.getAuthorization());
            }
            else if(Testingcase.getMode().equals("get"))
            {
                result=httpRequest.SendGetNullBody( Testingcase.getModePath(), Testingcase.getAppAuthentication(), ContentType ,Testingcase.getAuthorization());
            }
        }
        return result;
    }
}
