package template.demo.schedule.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时任务示例
 *
 * CRON:
 * 秒 分钟 小时 日期 月份 星期
 * 秒 分钟 小时 日期 月份 星期 年
 *
 * 秒        0-59        , - * / 
 * 分        0-59        , - * / 
 * 小时       0-23        , - * / 
 * 日期       1-31        , - * ? / L W C 
 * 月份       1-12        , - * / 
 * 星期       1-7         , - * ? / L C # 
 * 年        1970-2099   , - * /
 *
 * * : 都符合
 * 1,3,5 : 1 3 5
 * 10-12 : 10 11 12
 * ? : 日期和星期选择一个使用, 用于标记哪个不用
 * 0/5 : 每隔5
 *
 *
 * @author S.Violet
 */
@Component
public class SampleTask {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 每隔一分钟执行一次
     */
    @Scheduled(cron = "0 0/1 * * * ?")
    public void execute(){
        logger.info("SampleTask execute");
    }

}
