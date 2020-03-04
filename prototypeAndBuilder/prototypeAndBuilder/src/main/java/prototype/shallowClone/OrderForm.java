package prototype.shallowClone;

import net.sf.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 订单
 */
public class OrderForm implements Cloneable, Serializable {
    private String clientName;//客户名称
    private String productName;//商品名称
    private double totlePrice;//总价格
    private List<ZengPin> premium;//赠品

    public OrderForm jsonDeepClone(){
        JSONObject jsonObject = JSONObject.fromObject(this);
        List<ZengPin> list = new LinkedList<>();
        List<Map> p = (List) jsonObject.get("premium");
        if(p!=null) {
            for (Map one : p) {
                ZengPin z = new ZengPin(one.get("name").toString());
                list.add(z);
            }
        }
        OrderForm res = (OrderForm) JSONObject.toBean(jsonObject,OrderForm.class);
        res.setPremium(list);
        return res;
    }
    public OrderForm deepClone() throws IOException, ClassNotFoundException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream o = new ObjectOutputStream(byteArrayOutputStream);
        o.writeObject(this);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        ObjectInputStream i = new ObjectInputStream(byteArrayInputStream);
        OrderForm res = (OrderForm) i.readObject();
        return res;
    }
    @Override
    public OrderForm clone() throws CloneNotSupportedException {
        OrderForm res = (OrderForm) super.clone();
        Object object = ((ArrayList<ZengPin>)res.getPremium()).clone();
        res.setPremium((List<ZengPin>) object);
        return res;
    }
    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getTotlePrice() {
        return totlePrice;
    }

    public void setTotlePrice(double totlePrice) {
        this.totlePrice = totlePrice;
    }

    public List<ZengPin> getPremium() {
        return premium;
    }

    public void setPremium(List<ZengPin> premium) {
        this.premium = premium;
    }
    @Override
    public String toString() {
        return "订单信息{" +
                "客户名称='" + clientName + '\'' +
                ", 商品名称='" + productName + '\'' +
                ", 总价格=" + totlePrice +
                ", 赠品=" + premium +
                '}';
    }
}
