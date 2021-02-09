package com.github.shepherdviolet.webdemo.demo.micrometer.controller;

import io.micrometer.core.instrument.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Micrometer示例,
 * 单用Micrometer不需要依赖io.micrometer:micrometer-registry-prometheus, 要连Prometheus才需要
 *
 * @author S.Violet
 */
@RestController
@RequestMapping("/micrometer")
public class MicrometerController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MeterRegistry meterRegistry;

    private Random random = new Random(System.currentTimeMillis());

    private Counter testCounter;
    private Timer testTimer;
    private DistributionSummary testSummary;
    private AtomicInteger testGauge;

    @PostConstruct
    private void init(){
        //单纯计数
        testCounter = meterRegistry.counter("micrometer-test-counter","type","1");
        //计数+耗时+最大值
        testTimer = meterRegistry.timer("micrometer-test-timer", "type", "1");
        //追踪数据分布
        testSummary = meterRegistry.summary("micrometer-test-summary","type","1");
        //自由控制数值
        testGauge = meterRegistry.gauge("micrometer-test-gauge", Tags.of("type", "1"), new AtomicInteger(0));
    }

    /**
     * http://localhost:8000/micrometer
     */
    @RequestMapping("")
    public String test() {
        return testTimer.record(() -> {
            try {
                testGauge.incrementAndGet();
                testCounter.increment();
                logger.info("index");
                int duration = random.nextInt(2000) + 100;
                try {
                    Thread.sleep(duration);
                } catch (InterruptedException ignored) {
                }
                testSummary.record(duration);
                return String.valueOf(duration);
            } finally {
                testGauge.decrementAndGet();
            }
        });
    }

}
