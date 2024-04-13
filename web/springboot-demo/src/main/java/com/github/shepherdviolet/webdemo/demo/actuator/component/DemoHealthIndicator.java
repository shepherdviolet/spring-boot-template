package com.github.shepherdviolet.webdemo.demo.actuator.component;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

/**
 * Actuator 健康检查端点示例
 */
@Component
public class DemoHealthIndicator implements HealthIndicator {

    public static final int STATE_UP = 0;
    public static final int STATE_DOWN = 1;
    public static final int STATE_UNKNOWN = 2;

    private int state = STATE_UP;

    @Override
    public Health health() {
        switch (state) {
            case STATE_UP:
                return Health.up().withDetail("Demo health state", "ok").build();
            case STATE_DOWN:
                return Health.down().withDetail("Demo health state", "down").build();
            case STATE_UNKNOWN:
            default:
                return Health.unknown().withDetail("Demo health state", "unknown").build();
        }
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

}