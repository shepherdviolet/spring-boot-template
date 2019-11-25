package template.demo.properties.prop;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 加载classpath:config/demo/properties/bind.properties配置, 并将参数绑定到这个Bean上.
 *
 * @author zhuqinchao
 */
@Component
//配置路径(不能用通配符), 当文件不存在时不报错
@PropertySource(value = "classpath:config/demo/properties/bind.properties", ignoreResourceNotFound = true)
//绑定到Bean上
@ConfigurationProperties(prefix="demo.properties.bind")
public class BindProperties {

    private String currentVersion;
    private String createTime;

    public String getCurrentVersion() {
        return currentVersion;
    }

    /**
     * 注意, 必须要有get/set方法, 否则注入不进去!!!
     */
    public void setCurrentVersion(String currentVersion) {
        this.currentVersion = currentVersion;
    }

    public String getCreateTime() {
        return createTime;
    }

    /**
     * 注意, 必须要有get/set方法, 否则注入不进去!!!
     */
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "BindProperties{" +
                "currentVersion='" + currentVersion + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';
    }
}
