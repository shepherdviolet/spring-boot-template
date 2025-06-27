package com.github.shepherdviolet.webdemo.core.controller;

import com.github.shepherdviolet.webdemo.core.entity.TestRequest;
import com.github.shepherdviolet.webdemo.core.entity.TestResponse;
import com.github.shepherdviolet.webdemo.infra.error.HttpStatusException;
import com.github.shepherdviolet.webdemo.infra.error.RejectException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 基本模板Controller
 *
 * 请求报文体和响应报文体可以是byte[]类型, 但是这种情况下, 需要自行处理MediaType(Content-Type), 返回头也得自行设置Content-Type
 *
 * @author S.Violet
 */
@RestController
@RequestMapping("/core")
//允许指定域名跨域访问本服务, 如要上送cookie, 须指定域名 (另外, 可能需要解禁BootApplication中被禁用的OPTIONS方法)
//@CrossOrigin(origins = {"*"})
//@CrossOrigin(origins = {"http://host:port"}, allowCredentials = "true")
public class CoreRestController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * index
     */
//    @RequestMapping("/**")//匹配所有URI
    @RequestMapping("")
    public String index() {
        logger.info("index");
        return "hello spring boot template web-demo";
    }

    /**
     * POST or GET, return JSON
     * http://localhost:8000/core/postget/bean
     */
    @RequestMapping(value = "/postget/bean", method = RequestMethod.GET)
    public TestResponse postOrGetBean1(TestRequest request){
        return handlePostOrGetBean(request);
    }
    @RequestMapping(value = "/postget/bean", method = RequestMethod.POST)
    public TestResponse postOrGetBean2(@RequestBody TestRequest request){
        return handlePostOrGetBean(request);
    }
    private TestResponse handlePostOrGetBean(TestRequest request) {
        //这种方式既能接收POST JSON请求, 又能接收GET请求, 且都经过Bean的过滤
        //可以在TestRequest上加上@Valid注解, 对字段进行校验
        logger.info("postOrGetBean " + request.getId());
        return new TestResponse("hello");
    }

    /**
     * GET, return json
     * http://localhost:8000/core/get/json?name=a&key=b
     */
    @RequestMapping("/get/json")
    public String getJson(@RequestParam String name, @RequestParam String key) {
        logger.info("getJson " + name + " " + key);
        return "{\"result\":\"ok\", \"name\":\"" + name + "\", \"key\":\"" + key + "\"}";
    }

    /**
     * GET, return json
     * http://localhost:8000/core/get/json2?name=a&key=b&any=anyvalue
     */
    @RequestMapping("/get/json2")
    public Map<String, Object> getJson2(@RequestParam Map<String, Object> params) {
        logger.info("getJson2 " + params);
        Map<String, Object> response = new HashMap<>(params);
        response.put("result", "ok");
        return response;
    }

    /**
     * GET, return json
     * http://localhost:8000/core/get/wildcard/somepath
     */
    @RequestMapping("/get/wildcard/**")
    public String getJsonWildcard() {
        logger.info("getJsonWildcard");
        return "{\"result\":\"ok\"}";
    }

    /**
     * POST, return json
     * http://localhost:8000/core/post/json
     */
    @RequestMapping("/post/json")
    public Map<String, Object> postJson(@RequestBody String body) {
        logger.info("postJson " + body);
        Map<String, Object> responseMap = new HashMap<>(2);
        responseMap.put("result", "ok");
        responseMap.put("body", body);
        return responseMap;
    }

    /**
     * GET, return error
     * http://localhost:8000/core/exception
     */
    @RequestMapping("/exception")
    public String exception() {
        throw RejectException.create("exception-test-pass")
                .msg("{} test pass")
                .args("Exception")
                .build();
    }

    /**
     * GET, return http status 555
     * http://localhost:8000/core/return-http-status
     */
    @RequestMapping("/return-http-status")
    public String returnHttpStatus() {
        throw new HttpStatusException(555);
    }

    /**
     * redirect
     * http://localhost:8000/core/redirect
     */
    @RequestMapping("/redirect")
    public byte[] redirect(HttpServletResponse response) throws IOException {
        response.sendRedirect("http://www.baidu.com");
        return null;
    }

//    @Autowired
//    private GlaciHttpClient httpClient;
//    @Autowired
//    private SimpleOkHttpClient httpClient;
//    @HttpClient("default")
//    private GlaciHttpClient httpClient;
//    @HttpClient("default")
//    private SimpleOkHttpClient httpClient;
//
//    /**
//     * http://localhost:8000/core/httpClientTest
//     */
//    @RequestMapping("/httpClientTest")
//    public byte[] httpClientTest() throws HttpRejectException, RequestBuildException, IOException, NoHostException {
//        return httpClient.post("/core/post/json")
//                .body("httpClientTest body".getBytes())
//                .sendForBytes();
//    }

}
