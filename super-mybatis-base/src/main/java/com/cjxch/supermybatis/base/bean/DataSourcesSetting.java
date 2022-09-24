package com.cjxch.supermybatis.base.bean;

public class DataSourcesSetting {
    private String type;
    private String driverClassName;
    private String url;
    private String userName;
    private String password;
    private Boolean encrypt;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getEncrypt() {
        return encrypt;
    }

    public void setEncrypt(Boolean encrypt) {
        this.encrypt = encrypt;
    }

    @Override
    public String toString() {
        return "DataSourcesSetting{" +
                "type='" + type + '\'' +
                ", driverClassName='" + driverClassName + '\'' +
                ", url='" + url + '\'' +
                ", userName='" + userName + '\'' +
                ", password='*********" + '\'' +
                ", encrypt=" + encrypt +
                '}';
    }
}
