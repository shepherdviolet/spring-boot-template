/**
 * <p>Springboot优雅停机示例, Springboot版本低于2.3(不含) 或 手动实现优雅停机专用</p>
 *
 * <p>此包仅作为示例, 本工程实际不需要手动配置优雅停机, springboot2.3+只需要按如下配置即可启用WEB服务的优雅停机: </p>
 *
 * <pre>
 * spring:
 *   lifecycle:
 *     timeout-per-shutdown-phase: 60s
 * server:
 *   shutdown: graceful
 * <pre>
 *
 *
 * <p>如果你需要实现自己的优雅停机逻辑, 或者你的springboot版本低于2.3(不含), 可以参考本示例. </p>
 *
 * <p>其它说明: SmartLifecycle默认由DefaultLifecycleProcessor管理, 它有一个超时参数, 每一个SmartLifecycle执行shutdown phase是有
 * 时间限制的, 超时会强制结束 (从调用SmartLifecycle#stop(Runnable callback)方法到callback被调用为止). Springboot2.3以上(含)版本中
 * 允许通过如下参数配置, 低于2.3(不含)版本中, 无法通过参数配置. 因此, 本GracefulShutdown提供了一种覆盖DefaultLifecycleProcessor超时
 * 时间的机制, 详见overwriteTimeout方法, 保证DefaultLifecycleProcessor的超时时间始终大于SmartLifecycle的超时时间. </p>
 *
 * <pre>
 * spring:
 *   lifecycle:
 *     timeout-per-shutdown-phase: 60s
 * </pre>
 */
package com.github.shepherdviolet.webdemo.demo.gracefulshut;