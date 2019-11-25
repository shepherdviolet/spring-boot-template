package template.demo.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import template.demo.common.logic.Logic;

/**
 * 处理后会打印日志的服务
 */
public abstract class PrintService extends BaseService{

    /**
     * 指定BaseService中的处理后逻辑为printLogic
     */
    @Autowired
    @Qualifier("printLogic")
    @Override
    protected void setAfterLogic(Logic afterLogic) {
        super.setAfterLogic(afterLogic);
    }

}
