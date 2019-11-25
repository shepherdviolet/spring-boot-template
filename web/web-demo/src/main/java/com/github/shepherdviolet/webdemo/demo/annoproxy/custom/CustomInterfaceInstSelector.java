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

package com.github.shepherdviolet.webdemo.demo.annoproxy.custom;

import sviolet.slate.common.x.proxy.interfaceinst.InterfaceInstSelector;

import java.lang.annotation.Annotation;

/**
 * 接口类实例化(有实现)示例
 * 自定义的ImportSelector, 配合EnableCustomProxy注解开启功能
 * @author S.Violet
 */
public class CustomInterfaceInstSelector extends InterfaceInstSelector {

    /**
     * @return 返回配套的EnableCustomProxy注解类(父类InterfaceInstSelector会根据这个类型解析注解参数)
     */
    @Override
    protected Class<? extends Annotation> getEnableAnnotationType() {
        return EnableCustomProxy.class;
    }

}
