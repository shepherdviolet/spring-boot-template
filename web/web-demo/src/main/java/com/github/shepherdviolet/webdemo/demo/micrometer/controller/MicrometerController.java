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
 * Micrometer示例
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
        testCounter = meterRegistry.counter("test-counter","method","test");
        testTimer = meterRegistry.timer("test-timer", "method", "test");
        testSummary = meterRegistry.summary("test-summary","method","test");
        testGauge = meterRegistry.gauge("test-gauge", Tags.of("method", "test"), new AtomicInteger(0));
    }

    /**
     * http://localhost:8080/micrometer
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
