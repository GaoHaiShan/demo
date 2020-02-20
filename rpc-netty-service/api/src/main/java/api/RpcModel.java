package api;

import java.io.Serializable;

public class RpcModel implements Serializable {
    private String className;
    private String methodName;
    private Boolean isServer; //true 注册  flase 访问
    private Class<?>[] type; //参数类型
    private Object[] param;//参数列表
    private String ip;
    private String serverName;
    private String invorkServerName;
    private int port;

    public String getInvorkServerName() {
        return invorkServerName;
    }

    public void setInvorkServerName(String invorkServerName) {
        this.invorkServerName = invorkServerName;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Boolean getServer() {
        return isServer;
    }

    public void setServer(Boolean server) {
        isServer = server;
    }

    public Class<?>[] getType() {
        return type;
    }

    public void setType(Class<?>[] type) {
        this.type = type;
    }

    public Object[] getParam() {
        return param;
    }

    public void setParam(Object[] param) {
        this.param = param;
    }
}
