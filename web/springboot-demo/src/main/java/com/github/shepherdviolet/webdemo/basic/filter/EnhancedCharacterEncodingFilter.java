package com.github.shepherdviolet.webdemo.basic.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.filter.OrderedFilter;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Tomcat优化: 根据SpEL表达式配置的规则, 动态调整x-www-form-urlencoded表单请求的解码字符集.
 * 一般情况下, Content-Type=application/x-www-form-urlencoded是不指定charset的, Springboot默认用UTF-8解码FROM表单.
 * 如果请求方用其他字符编码, 可以用servlet.encoding.charset=GBK指定字符集.
 * 如果请求方用多种字符编码, 则需要自定义CharacterEncodingFilter, 实现动态字符集. (即此处的EnhancedCharacterEncodingFilter)
 *
 * 注意, 不同版本的Springboot, OrderedCharacterEncodingFilter源码有所不同, 你可以搜索OrderedCharacterEncodingFilter类参考当前
 * 版本的源码修改.
 *
 * ## set dynamic charset for x-www-form-urlencoded, SpEL root object is HttpServletRequest
 * server:
 *   encoding-filter:
 *     enable: true
 *     rules:
 *       GBK: "'application/x-www-form-urlencoded; charset=gbk'.equalsIgnoreCase(getContentType())"
 */
public class EnhancedCharacterEncodingFilter extends CharacterEncodingFilter implements OrderedFilter {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private int order = Integer.MIN_VALUE;
    private Map<String, String> rules = new HashMap<>();

    private final ExpressionParser expressionParser = new SpelExpressionParser();
    protected final Map<String, Expression> ruleExpressions = new HashMap<>();

    public EnhancedCharacterEncodingFilter() {
    }

    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();
        for (Map.Entry<String, String> rule : rules.entrySet()) {
            try {
                ruleExpressions.put(rule.getKey(), expressionParser.parseExpression(rule.getValue()));
            } catch (Exception e) {
                throw new ServletException("EnhancedCharacterEncodingFilter | An error occurred when parsing rule expression: " +
                        rule.getValue(), e);
            }
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        for (Map.Entry<String, Expression> rule : ruleExpressions.entrySet()) {
            Object checkResult;
            try {
                checkResult = rule.getValue().getValue(new StandardEvaluationContext(request));
            } catch (Exception e) {
                logger.warn("EnhancedCharacterEncodingFilter | An error occurred when evaluating rule expression: ", e);
                continue;
            }
            if ((checkResult instanceof Boolean) && ((Boolean) checkResult)) {
                if (this.isForceRequestEncoding() || request.getCharacterEncoding() == null) {
                    request.setCharacterEncoding(rule.getKey());
                }
                if (this.isForceResponseEncoding()) {
                    response.setCharacterEncoding(rule.getKey());
                }
                filterChain.doFilter(request, response);
                return;
            }
        }

        super.doFilterInternal(request, response, filterChain);
    }

    public int getOrder() {
        return this.order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public Map<String, String> getRules() {
        return rules;
    }

    public void setRules(Map<String, String> rules) {
        if (rules == null) {
            rules = new HashMap<>();
        }
        this.rules = rules;
    }

}
