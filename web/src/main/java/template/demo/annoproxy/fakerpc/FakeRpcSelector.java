package template.demo.annoproxy.fakerpc;

import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.AdviceModeImportSelector;
import org.springframework.context.annotation.AutoProxyRegistrar;

import java.util.ArrayList;
import java.util.List;

/**
 * 配置EnableFakeRpc注解需要引用的配置类
 *
 * @author S.Violet
 */
public class FakeRpcSelector extends AdviceModeImportSelector<EnableFakeRpc> {

    @Override
    public String[] selectImports(AdviceMode adviceMode) {
        switch (adviceMode) {
            case PROXY:
                return getProxyImports();
            case ASPECTJ:
                //暂不实现AspectJ的方式
//                return getAspectJImports();
            default:
                return null;
        }
    }

    /**
     * 返回需要生效的配置类:
     * Return the imports to use if the {@link AdviceMode} is set to {@link AdviceMode#PROXY}.
     * <p>Take care of adding the necessary JSR-107 import if it is available.
     */
    private String[] getProxyImports() {
        List<String> result = new ArrayList<String>();
        //自动代理
        result.add(AutoProxyRegistrar.class.getName());
        //自定义配置类
        result.add(FakeRpcConfiguration.class.getName());
        return result.toArray(new String[result.size()]);
    }

    /**
     * 暂不实现AspectJ的方式:
     * Return the imports to use if the {@link AdviceMode} is set to {@link AdviceMode#ASPECTJ}.
     * <p>Take care of adding the necessary JSR-107 import if it is available.
     */
//    private String[] getAspectJImports() {
//        List<String> result = new ArrayList<String>();
//        result.add(CACHE_ASPECT_CONFIGURATION_CLASS_NAME);
//        return result.toArray(new String[result.size()]);
//    }
}

