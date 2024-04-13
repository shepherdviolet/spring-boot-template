/*
 *    Copyright 2009-2021 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.github.shepherdviolet.webdemo.demo.mybatis.logenhance;

import org.apache.ibatis.logging.Log;
import org.slf4j.Logger;

/**
 * Mybatis Slf4j 日志增强: 源码取自org.mybatis:mybatis:3.5.9 org/apache/ibatis/logging/slf4j/*
 * 1.把DEBUG级别调整为INFO级别 (这样生产环境Mybatis日志默认开启)
 *
 * @author Eduardo Macarron
 */
class EnhancedSlf4jLoggerImpl implements Log {

  private final Logger log;

  public EnhancedSlf4jLoggerImpl(Logger logger) {
    log = logger;
  }

  @Override
  public boolean isDebugEnabled() {
    // [改造点] 替换成Info
    return log.isInfoEnabled();
  }

  @Override
  public boolean isTraceEnabled() {
    return log.isTraceEnabled();
  }

  @Override
  public void error(String s, Throwable e) {
    log.error(s, e);
  }

  @Override
  public void error(String s) {
    log.error(s);
  }

  @Override
  public void debug(String s) {
    // [改造点] 替换成Info
    log.info(s);
  }

  @Override
  public void trace(String s) {
    log.trace(s);
  }

  @Override
  public void warn(String s) {
    log.warn(s);
  }

}
