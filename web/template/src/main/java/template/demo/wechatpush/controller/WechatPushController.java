package template.demo.wechatpush.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import template.demo.wechatpush.service.WechatPushServer;

/**
 * 微信Controller
 * @author S.Violet
 */
@Controller
@RequestMapping("/wechatpush")
public class WechatPushController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private WechatPushServer wechatPushServer;

    /**
     * 微信推送示例
     * @param type base64:content是base64编码
     * @param openid 目标openid
     * @param content 消息内容
     * @param appid appid
     * @param secret secret
     */
    @RequestMapping("/push")
    @ResponseBody
    public String push(@RequestParam(required = false) String type,
                      @RequestParam String openid,
                      @RequestParam String content,
                      @RequestParam(required = false) String appid,
                      @RequestParam(required = false) String secret){
        logger.info("weChatPush: openid:" + openid + ", content:" + content);
        wechatPushServer.push(type, openid, content, appid, secret);
        return "{\"result\":\"ok\"}";
    }

    /**
     * 获取微信推送HTML页面
     */
    @RequestMapping("/html")
    public String htmlPost(){
        return "demo/wechatpush/wechatPushHtml";
    }

}
