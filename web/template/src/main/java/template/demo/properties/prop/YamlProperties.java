package template.demo.properties.prop;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 将Spring Environment中的参数绑定到这个Bean上.
 *
 * @author zhuqinchao
 */
//可以扫包, 也可以在Configuration中声明Bean
@Component
//绑定到Bean上
@ConfigurationProperties(prefix="demo.properties.yaml")
public class YamlProperties {

    private List<String> list;

    private Map<String, Map<String, String>> map;

    public List<String> getList() {
        return list;
    }

    /**
     * 注意, 必须要有get/set方法, 否则注入不进去!!!
     */
    public void setList(List<String> list) {
        this.list = list;
    }

    public Map<String, Map<String, String>> getMap() {
        return map;
    }

    /**
     * 注意, 必须要有get/set方法, 否则注入不进去!!!
     */
    public void setMap(Map<String, Map<String, String>> map) {
        this.map = map;
    }

    @Override
    public String toString() {
        return "YamlProperties{" +
                "list=" + list +
                ", map=" + map +
                '}';
    }
}
