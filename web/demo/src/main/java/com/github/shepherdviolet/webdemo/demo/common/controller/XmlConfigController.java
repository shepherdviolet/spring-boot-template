package com.github.shepherdviolet.webdemo.demo.common.controller;

import com.github.shepherdviolet.webdemo.demo.common.entity.FatBean;
import com.github.shepherdviolet.webdemo.demo.common.entity.SimpleBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * XML方式配置示例
 *
 * @author S.Violet
 */
@RestController
@RequestMapping("/common/xmlconfig")
public class XmlConfigController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private FatBean xmlFatBean; // 这个会注入有@Primary注解的那个

    /**
     * http://localhost:8000/common/xmlconfig/normal
     */
    @RequestMapping("/normal")
    public String normal() {
        return String.valueOf(xmlFatBean);
    }

    @Autowired
    @Qualifier("bean1FromStaticFactory")
    private SimpleBean bean1FromStaticFactory;

    /**
     * 静态工厂方法
     * http://localhost:8000/common/xmlconfig/static
     */
    @RequestMapping("/static")
    public String staticFactory() {
        return String.valueOf(bean1FromStaticFactory);
    }

    @Autowired
    @Qualifier("bean1ForCascadeConf")
    private FatBean bean1ForCascadeConf;

    /**
     * 属性级联设置
     * http://localhost:8000/common/xmlconfig/cascade
     */
    @RequestMapping("/cascade")
    public String cascadeConfig() {
        return String.valueOf(bean1ForCascadeConf);
    }

    @Autowired
    @Qualifier("bean1IsChild")
    private SimpleBean bean1IsChild;

    /**
     * Bean继承+集合merge
     * http://localhost:8000/common/xmlconfig/parent
     */
    @RequestMapping("/parent")
    public String parent() {
        return String.valueOf(bean1IsChild);
    }

    @Autowired
    @Qualifier("bean1WithLookup")
    private FatBean bean1WithLookup;

    /**
     * lookup方法:代理getSimpleBean方法, 每次都返回新的bean1ByLookup实例(因为它是prototype型, lookup方法会每次从上下文取)
     * 这个在注解配置方式中, 建议用AOP实现
     * http://localhost:8000/common/xmlconfig/lookup
     */
    @RequestMapping("/lookup")
    public String lookup() {
        return String.valueOf(bean1WithLookup.getSimpleBean());
    }

    @Autowired
    @Qualifier("bean1WithReplacer")
    private FatBean bean1WithReplacer;

    /**
     * method-replacer
     * 这个在注解配置方式中, 建议用AOP实现
     * http://localhost:8000/common/xmlconfig/replacer
     */
    @RequestMapping("/replacer")
    public String replacer() {
        return String.valueOf(bean1WithReplacer.getSimpleBean());
    }

    @Autowired
    @Qualifier("bean2ForSpel")
    private FatBean bean2ForSpel;

    @Autowired
    @Qualifier("bean3ForSpel")
    private SimpleBean bean3ForSpel;

    @Autowired
    @Qualifier("bean4ForSpel")
    private SimpleBean bean4ForSpel;

    /**
     * SpEL测试
     * 数值运算: + - * / % ^
     * 比较运算: > < == >= <= lt gt eq le ge
     * 逻辑运算: and or not !
     *
     * 条件运算示例: #{a > 0 ? 'yes' : 'no'}
     * 正则表达式示例: #{bean.prop matches '^(\d{3,4}-)\d{7,8}$'}
     * 集合查询示例:
     *      #{bean.?[prop > 0]}
     *          从bean集合中, 查询并返回一个集合, 满足条件prop属性>0
     *          .?改成.^表示返回满足条件的第一个, 改成.$表示返回满足条件的最后一个
     *      #{bean.![prop > 0]}
     *          从bean集合中, 查询并返回一个"指定属性组成的"集合, 满足条件prop属性>0
     *
     * http://localhost:8000/common/xmlconfig/spel
     */
    @RequestMapping("/spel")
    public String spel() {
        return bean2ForSpel + "<Br>" +
                bean3ForSpel + "<Br>" +
                bean4ForSpel;
    }

}
