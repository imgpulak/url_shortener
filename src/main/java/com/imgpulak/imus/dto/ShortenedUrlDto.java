package com.imgpulak.imus.dto;

import java.net.URL;

public class ShortenedUrlDto {
    private String clientId;
    private URL url;
    private String shortenedId;
    private Long hitCount;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public String getShortenedId() {
        return shortenedId;
    }

    public void setShortenedId(String shortenedId) {
        this.shortenedId = shortenedId;
    }

    public Long getHitCount() {
        return hitCount;
    }

    public void setHitCount(Long hitCount) {
        this.hitCount = hitCount;
    }
}
