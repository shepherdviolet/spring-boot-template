package template.demo.schedule.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 定时任务示例
 *
 * @author S.Violet
 */
@Configuration
@EnableScheduling
@ComponentScan({
        "template.demo.schedule.task"
})
public class ScheduleConfiguration {

}
