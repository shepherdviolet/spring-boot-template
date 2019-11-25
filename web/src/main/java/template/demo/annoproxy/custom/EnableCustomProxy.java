/*
 * Copyright (C) 2015-2018 S.Violet
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Project GitHub: https://github.com/shepherdviolet/slate
 * Email: shepherdviolet@163.com
 */

package template.demo.annoproxy.custom;

import org.springframework.context.annotation.Import;
import sviolet.slate.common.x.proxy.interfaceinst.InterfaceInstantiator;

import java.lang.annotation.*;

/**
 * 接口类实例化(有实现)示例
 * 自定义的开关注解
 *
 * @author S.Violet
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({CustomInterfaceInstSelector.class})
public @interface EnableCustomProxy {

    /**
     * 配置需要实例化的接口类包路径(可指定多个)
     */
    String[] basePackages();

    /**
     * 自定义的接口类实例化器
     */
    Class<? extends InterfaceInstantiator> interfaceInstantiator() default CustomInterfaceInstantiator.class;

    /**
     * true: 指定包路径下的接口类, 必须申明指定注解才进行实例化(注解类型由annotationClass指定, 默认@InterfaceInstance).
     * false: 指定包路径下的接口类, 不声明指定注解也进行实例化.
     * 默认true
     */
    boolean annotationRequired() default true;

    /**
     * 当annotationRequired为true时, 指定包路径下的接口类必须声明指定的注解才能实例化, 注解类型可以在这里定义.
     * 自定义为CustomAnnotation
     */
    Class<? extends Annotation> annotationClass() default CustomAnnotation.class;

}
