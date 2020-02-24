package nofactory;

public class YinLianPay {
    private String version="";            //版本号 全渠道默认值
    private String encoding="";     //字符集编码 可以使用UTF-8,GBK两种方式
    private String signMethod="";                    //签名方法 目前只支持01：RSA方式证书加密
    private String txnType="";                       //交易类型 01:消费
    private String txnSubType="";                    //交易子类 01：消费
    private String bizType="";                   //填写000201
    private String channelType="";                   //渠道类型 08手机
    private String merId="";                        //商户号码，请改成自己申请的商户号或者open上注册得来的777商户号测试
    private String accessType="";                     //接入类型，商户接入填0 ，不需修改（0：直连商户， 1： 收单机构 2：平台商户）
    private String orderId="";                    //商户订单号，8-40位数字字母，不能含“-”或“_”，可以自行定制规则
    private String txnTime="";                    //订单发送时间，取系统时间，格式为YYYYMMDDhhmmss，必须取当前时间，否则会报txnTime无效
    private String accType="";                       //账号类型 01：银行卡02：存折03：IC卡帐号类型(卡介质)
    private String txnAmt="";                      //交易金额 单位为分，不能带小数点
    private String currencyCode="";                 //境内商户固定 156 人民币
    private String backUrl="";

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public String getSignMethod() {
        return signMethod;
    }

    public void setSignMethod(String signMethod) {
        this.signMethod = signMethod;
    }

    public String getTxnType() {
        return txnType;
    }

    public void setTxnType(String txnType) {
        this.txnType = txnType;
    }

    public String getTxnSubType() {
        return txnSubType;
    }

    public void setTxnSubType(String txnSubType) {
        this.txnSubType = txnSubType;
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }

    public String getMerId() {
        return merId;
    }

    public void setMerId(String merId) {
        this.merId = merId;
    }

    public String getAccessType() {
        return accessType;
    }

    public void setAccessType(String accessType) {
        this.accessType = accessType;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getTxnTime() {
        return txnTime;
    }

    public void setTxnTime(String txnTime) {
        this.txnTime = txnTime;
    }

    public String getAccType() {
        return accType;
    }

    public void setAccType(String accType) {
        this.accType = accType;
    }

    public String getTxnAmt() {
        return txnAmt;
    }

    public void setTxnAmt(String txnAmt) {
        this.txnAmt = txnAmt;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getBackUrl() {
        return backUrl;
    }

    public void setBackUrl(String backUrl) {
        this.backUrl = backUrl;
    }
}
