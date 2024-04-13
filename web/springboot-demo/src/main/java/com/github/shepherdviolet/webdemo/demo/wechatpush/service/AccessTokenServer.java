package com.github.shepherdviolet.webdemo.demo.wechatpush.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.shepherdviolet.glacimon.java.datastruc.cache.ExpirableCache;
import com.github.shepherdviolet.glacimon.java.misc.CheckUtils;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

/**
 * 微信access_token管理器
 *
 * @author S.Violet
 */
@Service
public class AccessTokenServer extends ExpirableCache<String> implements InitializingBean {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private OkHttpClient client;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Value("${demo.wechatpush.accesstoken.url}")
    private String accessTokenUrl;

    @Value("${demo.wechatpush.appid}")
    private String defaultAppid;

    @Value("${demo.wechatpush.secret}")
    private String defaultSecret;

    @Override
    public void afterPropertiesSet() {
        setDoCheckInterval(5000L);
        setDoUpdateTimeout(500L);

        client = new OkHttpClient.Builder()
                .build();
    }

    /**
     * 获取access_token
     * @param appid appid
     * @param secret secret
     * @return access_token
     */
    public String getAccessToken(String appid, String secret){
        return get(accessTokenUrl + "?grant_type=client_credential&appid=" + (appid != null ? appid : defaultAppid) + "&secret=" + (secret != null ? secret : defaultSecret));
    }

    /**
     * 实现access_token的更新逻辑
     */
    @Override
    protected UpdateResult<String> onUpdate(String key) {

        try {

            Response response = client.newCall(new Request.Builder()
                    .url(key)
                    .get()
                    .addHeader("Content-Type", "application/json;charset=utf8")
                    .build())
                    .execute();

            if (response.code() != 200){
                throw new RuntimeException("Error while getting access_token, http reject:" + response.code() + ", key:" + key);
            }

            ResponseBody body = response.body();

            if (body == null) {
                throw new RuntimeException("Error while getting access_token, empty body, key:" + key);
            }

            String result = body.string();
            logger.debug("Update access_token response body:" + result + ", key:" + key);

            Map<?, ?> map = objectMapper.readValue(result, Map.class);
            Double errorCode = (Double) map.get("errcode");
            String accessToken = (String) map.get("access_token");
            Double expires = (Double) map.get("expires_in");

            if (errorCode != null){
                throw new RuntimeException("Error while parsing access_token from response body, reject code:" + errorCode + ", key:" + key);
            }

            if (CheckUtils.isEmptyOrBlank(accessToken)){
                throw new RuntimeException("Error while parsing access_token from response body, missing access_token, key:" + key);
            }

            if (expires == null){
                throw new RuntimeException("Error while parsing access_token from response body, missing expires, key:" + key);
            }

            return new ExpirableCache.UpdateResult<>(accessToken, (long)(expires * 1000D));

        } catch (IOException e) {
            throw new RuntimeException("Error while getting access_token, Exception, key:" + key, e);
        }

    }

    @Override
    protected long onError(String key, Throwable t) {
        return 0;
    }

}
