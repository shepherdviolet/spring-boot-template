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
import org.apache.ibatis.logging.LogFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.slf4j.spi.LocationAwareLogger;

/**
 * Mybatis Slf4j 日志增强: 源码取自org.mybatis:mybatis:3.5.9 org/apache/ibatis/logging/slf4j/*
 * 1.把DEBUG级别调整为INFO级别 (这样生产环境Mybatis日志默认开启)
 *
 * @author Eduardo Macarron
 */
class EnhancedSlf4jLocationAwareLoggerImpl implements Log {

  private static final Marker MARKER = MarkerFactory.getMarker(LogFactory.MARKER);

  private static final String FQCN = EnhancedSlf4jImpl.class.getName();

  private final LocationAwareLogger logger;

  EnhancedSlf4jLocationAwareLoggerImpl(LocationAwareLogger logger) {
    this.logger = logger;
  }

  @Override
  public boolean isDebugEnabled() {
    // [改造点] 替换成Info
    return logger.isInfoEnabled();
  }

  @Override
  public boolean isTraceEnabled() {
    return logger.isTraceEnabled();
  }

  @Override
  public void error(String s, Throwable e) {
    logger.log(MARKER, FQCN, LocationAwareLogger.ERROR_INT, s, null, e);
  }

  @Override
  public void error(String s) {
    logger.log(MARKER, FQCN, LocationAwareLogger.ERROR_INT, s, null, null);
  }

  @Override
  public void debug(String s) {
    // [改造点] 替换成Info
    logger.log(MARKER, FQCN, LocationAwareLogger.INFO_INT, s, null, null);
  }

  @Override
  public void trace(String s) {
    logger.log(MARKER, FQCN, LocationAwareLogger.TRACE_INT, s, null, null);
  }

  @Override
  public void warn(String s) {
    logger.log(MARKER, FQCN, LocationAwareLogger.WARN_INT, s, null, null);
  }

}
