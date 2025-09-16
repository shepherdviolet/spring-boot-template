package com.github.shepherdviolet.webdemo.demo.qwen2vl.controller;

import com.github.shepherdviolet.glacimon.java.conversion.Base64Utils;
import com.github.shepherdviolet.glacimon.java.collections.StreamingBuildable;
import com.github.shepherdviolet.glacimon.spring.x.net.loadbalance.classic.GlaciHttpClient;
import com.github.shepherdviolet.glacimon.spring.x.net.loadbalance.classic.HttpRejectException;
import com.github.shepherdviolet.glacimon.spring.x.net.loadbalance.classic.NoHostException;
import com.github.shepherdviolet.glacimon.spring.x.net.loadbalance.classic.RequestBuildException;
import com.github.shepherdviolet.glacimon.spring.x.net.loadbalance.springboot.autowired.HttpClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

/**
 * <p>Qwen2vl对接示例(HTTP API)</p>
 *
 * @author S.Violet
 */
@Controller
@RequestMapping("/qwen2vl")
public class Qwen2vlController implements StreamingBuildable {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @HttpClient("qwen2vl")
    private GlaciHttpClient qwen2vlClient;

    @Value("${demo.qwen2vl.apikey:}")
    private String apiKey;

    @Value("${demo.qwen2vl.model:}")
    private String model;

    private Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    /**
     * 获取文件上传页面
     * http://localhost:8000/qwen2vl/html
     */
    @RequestMapping("/html")
    public String html() {
        return "demo/qwen2vl/ocr";
    }

    /**
     * 文件上传+识别
     * http://localhost:8000/qwen2vl/upload
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    @SuppressWarnings("unchecked")
    public String uploadImg(@RequestParam("file") MultipartFile file, @RequestParam("prompt") String prompt) throws HttpRejectException, RequestBuildException, IOException, NoHostException {

        String imgBase64 = Base64Utils.encodeToString(file.getBytes());

        Map<String, Object> requestMap = buildHashMap()
                .put("model", model)
                .put("messages", buildArrayList()
                        .add(buildHashMap()
                                .put("role", "system")
                                .put("content", "你是OCR识别器")
                                .build()
                        )
                        .add(buildHashMap()
                                .put("role", "user")
                                .put("content", buildArrayList()
                                        .add(buildHashMap()
                                                .put("type", "text")
                                                .put("text", prompt)
                                                .build()
                                        )
                                        .add(buildHashMap()
                                                .put("type", "image_url")
                                                .put("image_url", buildHashMap()
                                                        .put("detail", "hign")
                                                        .put("url", "data:image/png;base64," + imgBase64)
                                                        .build()
                                                )
                                                .build()
                                        )
                                        .build()
                                )
                                .build()
                        )
                        .build()
                )
                .build();

        // 返回向qwen2vl发送的请求报文
//        return gson.toJson(requestMap).replaceAll("\\n", "<br>");

        Map<String, Object> responseMap = qwen2vlClient.post("")
                .httpHeader("Authorization", "Bearer " + apiKey)
                .mediaType("application/json")
                .beanBody(requestMap)
                .sendForBean(Map.class);

        return "Result: <br>" + gson.toJson(responseMap).replaceAll("\\n", "<br>");
    }

}
