package Com;

/**
 * Created by admin on 2016/4/18.
 */
public class TestingCase {
    /**
     * 序号
     */
    private String id;
    /**
     * 测试文本相关说明
     */
    private String description;
    /**
     * 执行方式（点击、输入、核查）
     */
    private String model;
    /**
     * 找寻元素方式
     */
    private String mode;
    /**
     * 对应找寻元素的路径
     */
    private String modepath;
    /**
     * 文本内容（目前仅为“输入”使用）
     */
    private String text;
    /**
     * 是否跳过执行步骤,是:跳过,否:执行
     */
    private String whetherskip;

    private String AppAuthentication;
    /**
     * app授权码，只用于接口请求
     */
    private String Authorization;
    /**
     * 验证码授权token，只用于接口请求
     */

    public String getId() { return id; }

    public void setId(String id) {
        this.id = id;
    }

    public String getModel() { return model; }

    public void setModel(String model) {
        this.model = model;
    }

    public String getDescription(){return  description;}

    public void setDescription(String description){ this.description = description;}

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getModePath() {
        return modepath;
    }

    public void setModePath(String modepath) {
        this.modepath = modepath;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getWhetherskip() {
        return whetherskip;
    }

    public void setWhetherskip(String whetherskip) {
        this.whetherskip = whetherskip;
    }
    public String getAppAuthentication() {
        return AppAuthentication;
    }

    public void setAppAuthentication(String AppAuthentication) {
        this.AppAuthentication = AppAuthentication;
    }

    public String getAuthorization() {
        return Authorization;
    }

    public void setAuthorization(String Authorization) {
        this.Authorization = Authorization;
    }

}