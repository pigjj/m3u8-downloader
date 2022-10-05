package com.amazing2j.m3u8downloader.entity;

/**
 * Socks5 代理配置对象
 */
public class ProxyEntity {
    private String host;
    private int port;

    public ProxyEntity() {
    }

    public ProxyEntity(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
