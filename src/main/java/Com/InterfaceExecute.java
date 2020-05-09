package Com;

import CollectionOfFunctionalMethods.HttpAndHttpsProtocol.HttpRequestMethod;

/**
 * Created by wuzb on 2020/04/21.
 */
public class InterfaceExecute {
    //返回响应结果
    // private String Appauthor="Basic eyJhcHBFbmQiOiJXZWIiLCJhcHBJZCI6InRlbXBfYXBwX2lkIiwicGxhdGZvcm1JZCI6IjA3NTQwZDc3Y3AyMTQ2M2I5NDk4MmM4NDU2anNrcTAyIiwicGxhdGZvcm1WZXJzaW9uSWQiOiJjcDk1YmM0NzIxNDc0MTAwODA4YmNhNTg4Mmpza3EwMiIsInByb2plY3RJZCI6IjQwMjg5Njc4NmU0NDlmMzkwMTZlNDQ5ZjIwMjAwMjA2Iiwic3ViUHJvamVjdElkIjoiNDAyODk2Nzg2ZTQ0OWYzOTAxNmU0NDlmMjAyMDBzdWIifQ==";
    private String ContentType="application/json";
    public  Object result;
    public  HttpRequestMethod httpRequest = new HttpRequestMethod();
    public Object Interfaceexecute( TestingCase Testingcase) throws Exception {

        if (Testingcase.getModel().equals("http")){
            if(Testingcase.getMode().equals("post"))
            {
                result=httpRequest.SendPostString( Testingcase.getModePath(),Testingcase.getText(), Testingcase.getAppAuthentication(),ContentType);
            }
            else if(Testingcase.getMode().equals("get"))
            {
                result=httpRequest.SendGetNullBody( Testingcase.getModePath(), Testingcase.getAppAuthentication(), ContentType );
            }
        }
        return result;
    }
}
