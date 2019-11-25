package com.github.shepherdviolet.webdemo.demo.wechatpush.service;

import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sviolet.thistle.util.concurrent.ThreadPoolExecutorUtils;
import sviolet.thistle.util.conversion.Base64Utils;
import sviolet.thistle.util.judge.CheckUtils;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutorService;

/**
 * 微信推送服务
 *
 * @author S.Violet
 */
@Service
public class WechatPushServer implements InitializingBean {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private AccessTokenServer accessTokenServer;

    private ExecutorService executorService;
    private OkHttpClient client;

    @Value("${demo.wechatpush.push.url}")
    private String pushUrl;

    @Value("${demo.wechatpush.appid}")
    private String defaultAppid;

    @Value("${demo.wechatpush.secret}")
    private String defaultSecret;

    @Override
    public void afterPropertiesSet() {
        executorService = ThreadPoolExecutorUtils.createFixed(3, "WeChatPush-pool-%d");
        client = new OkHttpClient.Builder()
                .build();
    }

    /**
     * [异步]推送消息
     * @param type base64:content是base64编码
     * @param openid 目标openid
     * @param content 消息内容
     * @param appid appid
     * @param secret secret
     */
    public void push(String type, String openid, String content, String appid, String secret) {

        logger.debug("Push start, type:" + type + ", openid:" + openid + ", content:" + content + ", appid:" + appid + ", secret:" + secret);

        if (CheckUtils.isEmptyOrBlank(openid)){
            logger.error("Push failed, openid is null or empty, type:" + type + ", openid:" + openid + ", content:" + content + ", appid:" + appid + ", secret:" + secret);
            return;
        }

        if (CheckUtils.isEmptyOrBlank(content)){
            logger.error("Push failed, content is null or empty, type:" + type + ", openid:" + openid + ", content:" + content + ", appid:" + appid + ", secret:" + secret);
            return;
        }

        if ("base64".equalsIgnoreCase(type)){
            try {
                content = new String(Base64Utils.decode(content), "utf-8");
            } catch (UnsupportedEncodingException e) {
                logger.error("Push failed, parsing content from base64 failed, type:" + type + ", openid:" + openid + ", content:" + content + ", appid:" + appid + ", secret:" + secret, e);
                return;
            }
        }

        final String finalContent = content;

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                String accessToken = accessTokenServer.getAccessToken(appid, secret);
                if (accessToken == null){
                    logger.error("Push failed, can not get access_token, type:" + type + ", openid:" + openid + ", content:" + finalContent + ", appid:" + appid + ", secret:" + secret);
                    return;
                }

                String url = pushUrl+ "?access_token=" + accessToken;
                String body = "{\"touser\":\"" + openid + "\", \"msgtype\":\"text\", \"text\":{\"content\":\"" + finalContent + "\"}}";

                logger.debug("Push request, url:" + url + ", body:" + body + ", appid:" + appid + ", secret:" + secret);

                try {
                    Response response = client.newCall(new Request.Builder()
                            .url(url)
                            .addHeader("Content-Type", "application/json;charset=utf8")
                            .post(okhttp3.RequestBody.create(MediaType.parse("application/json;charset=utf-8"), body))
                            .build())
                            .execute();

                    if (response.code() != 200){
                        logger.error("Push request failed, http reject:" + response.code() + ", type:" + type + ", openid:" + openid + ", content:" + finalContent + ", appid:" + appid + ", secret:" + secret);
                        return;
                    }

                    ResponseBody responseBody = response.body();

                    if (responseBody == null) {
                        logger.error("Push request failed, empty body, type:" + type + ", openid:" + openid + ", content:" + finalContent + ", appid:" + appid + ", secret:" + secret);
                        return;
                    }

                    String responseBodyString = responseBody.string();
                    logger.debug("Push request finish, response body:" + responseBodyString + ", type:" + type + ", openid:" + openid + ", content:" + finalContent + ", appid:" + appid + ", secret:" + secret);

                } catch (Exception e) {
                    logger.error("Push request failed, Exception, type:" + type + ", openid:" + openid + ", content:" + finalContent + ", appid:" + appid + ", secret:" + secret, e);
                }
            }
        });

    }

}
