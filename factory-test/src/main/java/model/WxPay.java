package model;

import java.util.Map;

public class WxPay implements IPay{
    private String appid="";
    //商品名称
    private String body="";
    //总价格
    private String total_fee="";
    //商户号
    private String mch_id="";
    //支付场景 微信app支付 JSAPI 公众号支付  NATIVE 扫码支付
    private String trade_type="";
    //回调地址
    private String notify_url="";
    //终端ip
    private String spbill_create_ip="";
    //币种 默认人民币
    private String fee_type="";
    //交易号
    private String out_trade_no="";
    //随机字符串
    private String nonce_str="";
    //签名
    private String sign="";

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(String total_fee) {
        this.total_fee = total_fee;
    }

    public String getMch_id() {
        return mch_id;
    }

    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }

    public String getTrade_type() {
        return trade_type;
    }

    public void setTrade_type(String trade_type) {
        this.trade_type = trade_type;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getSpbill_create_ip() {
        return spbill_create_ip;
    }

    public void setSpbill_create_ip(String spbill_create_ip) {
        this.spbill_create_ip = spbill_create_ip;
    }

    public String getFee_type() {
        return fee_type;
    }

    public void setFee_type(String fee_type) {
        this.fee_type = fee_type;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getNonce_str() {
        return nonce_str;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    @Override
    public void send(Map map) {
        System.out.println("请求微信接口，发送数据包 ："+map.toString());
    }
}
