package com.imgpulak.imus.request.model;

public class CreateShortURLRequest {
    private String clientId;
    private String urlStr;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getUrlStr() {
        return urlStr;
    }

    public void setUrlStr(String urlStr) {
        this.urlStr = urlStr;
    }
}
