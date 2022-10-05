package com.amazing2j.m3u8downloader.download;

import com.amazing2j.m3u8downloader.exception.DownloadException;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 下载器
 */
public class Downloader {

    private final Logger log = LoggerFactory.getLogger(Downloader.class);

    private final Proxy proxy;


    public Downloader() {
        this.proxy = null;
    }

    public Downloader(String host, int port) {
        if ("".equals(host)) {
            this.proxy = null;
        } else {
            this.proxy = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress(host, port));
        }
    }

    /**
     * 下载指定地址文件
     *
     * @param url 文件地址
     * @return 文件二进制流
     * @throws IOException 如果下载文件二进制流为空则抛出
     */
    public byte[] download(String url) throws IOException {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS).readTimeout(10, TimeUnit.SECONDS);
        OkHttpClient httpClient;
        if (this.proxy != null) {
            httpClient = builder.proxy(proxy).build();
        } else {
            httpClient = builder.build();
        }
        Request request = new Request.Builder().url(url).build();

        Response response = httpClient.newCall(request).execute();
        if (!response.isSuccessful()) {
            log.error("请求异常： {}", Objects.requireNonNull(response.body()).string());
            throw new DownloadException(String.format("请求异常: %s", response.code()));
        }

        return Objects.requireNonNull(response.body()).bytes();
    }
}
