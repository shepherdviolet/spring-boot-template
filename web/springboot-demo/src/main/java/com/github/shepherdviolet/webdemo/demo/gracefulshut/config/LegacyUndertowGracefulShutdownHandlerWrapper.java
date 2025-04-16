//package com.github.shepherdviolet.webdemo.demo.gracefulshut.config;
//
//import com.github.shepherdviolet.glacimon.spring.config.lifecycle.GracefulShutdown;
//import io.undertow.server.ExchangeCompletionListener;
//import io.undertow.server.HandlerWrapper;
//import io.undertow.server.HttpHandler;
//import io.undertow.server.HttpServerExchange;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.concurrent.atomic.AtomicInteger;
//
///**
// * <p>Springboot优雅停机示例, Springboot版本低于2.3(不含) 或 手动实现优雅停机专用</p>
// *
// * <p>此包仅作为示例, 本工程实际不需要手动配置优雅停机, springboot2.3+只需要按如下配置即可启用WEB服务的优雅停机: </p>
// *
// * <pre>
// * spring:
// *   lifecycle:
// *     timeout-per-shutdown-phase: 60s
// * server:
// *   shutdown: graceful
// * <pre>
// *
// *
// * <p>如果你需要实现自己的优雅停机逻辑, 或者你的springboot版本低于2.3(不含), 可以参考本示例. </p>
// *
// * <p>其它说明: SmartLifecycle默认由DefaultLifecycleProcessor管理, 它有一个超时参数, 每一个SmartLifecycle执行shutdown phase是有
// * 时间限制的, 超时会强制结束 (从调用SmartLifecycle#stop(Runnable callback)方法到callback被调用为止). Springboot2.3以上(含)版本中
// * 允许通过如下参数配置, 低于2.3(不含)版本中, 无法通过参数配置. 因此, 本GracefulShutdown提供了一种覆盖DefaultLifecycleProcessor超时
// * 时间的机制, 详见overwriteTimeout方法, 保证DefaultLifecycleProcessor的超时时间始终大于SmartLifecycle的超时时间. </p>
// *
// * <pre>
// * spring:
// *   lifecycle:
// *     timeout-per-shutdown-phase: 60s
// * </pre>
// */
//public class LegacyUndertowGracefulShutdownHandlerWrapper extends GracefulShutdown implements HandlerWrapper {
//
//    private GracefulShutdownHandler gracefulShutdownHandler;
//
//    public LegacyUndertowGracefulShutdownHandlerWrapper(long waitTimeout) {
//        super(waitTimeout);
//    }
//
//    @Override
//    public HttpHandler wrap(HttpHandler httpHandler) {
//        // 给Undertow提供GracefulShutdownHandler
//        if (gracefulShutdownHandler == null) {
//            synchronized (this) {
//                if (gracefulShutdownHandler == null) {
//                    gracefulShutdownHandler = new GracefulShutdownHandler(httpHandler);
//                }
//            }
//        }
//        return gracefulShutdownHandler;
//    }
//
//    /**
//     * 实现"停止接收新请求"
//     */
//    @Override
//    public void stopAcceptingRequests() {
//        if (gracefulShutdownHandler != null) {
//            gracefulShutdownHandler.stopAcceptingRequests();
//        }
//    }
//
//    /**
//     * 实现"检查在途请求是否完成"
//     */
//    @Override
//    public boolean isAllRequestsCompleted() {
//        if (gracefulShutdownHandler != null) {
//            return gracefulShutdownHandler.isAllRequestsCompleted();
//        }
//        return true;
//    }
//
//    public static class GracefulShutdownHandler implements HttpHandler {
//
//        private final Logger logger = LoggerFactory.getLogger(getClass());
//
//        private final GracefulShutdownListener listener = new GracefulShutdownListener();
//        private final HttpHandler next;
//
//        private final AtomicInteger activeRequests = new AtomicInteger(0);
//        private final AtomicInteger rejectedRequests = new AtomicInteger(0);
//        private volatile boolean shutdown = false;
//
//        public GracefulShutdownHandler(HttpHandler next) {
//            this.next = next;
//        }
//
//        public void handleRequest(HttpServerExchange exchange) throws Exception {
//            incrementRequests();
//            if (shutdown) {
//                // 拒绝请求
//                decrementRequests();
//                exchange.setStatusCode(503);
//                exchange.endExchange();
//                logger.info("The application is shutting down, " + rejectedRequests.incrementAndGet() + " requests rejected");
//            } else {
//                // 继续处理请求
//                exchange.addExchangeCompleteListener(this.listener);
//                this.next.handleRequest(exchange);
//            }
//        }
//
//        public void stopAcceptingRequests() {
//            shutdown = true;
//        }
//
//        public boolean isAllRequestsCompleted() {
//            int activeTransactions = activeRequests.get();
//            logger.info("Active requests: " + activeTransactions);
//            return activeTransactions <= 0;
//        }
//
//        private void incrementRequests() {
//            activeRequests.getAndIncrement();
//        }
//
//        private void decrementRequests() {
//            activeRequests.getAndDecrement();
//        }
//
//        /**
//         * Undertow的HandlerWrapper在拦截请求的时候, 需要这个监听器监听处理完成事件, 实现在途请求计数
//         */
//        private final class GracefulShutdownListener implements ExchangeCompletionListener {
//            public void exchangeEvent(HttpServerExchange exchange, ExchangeCompletionListener.NextListener nextListener) {
//                try {
//                    GracefulShutdownHandler.this.decrementRequests();
//                } finally {
//                    nextListener.proceed();
//                }
//            }
//        }
//    }
//
//}
