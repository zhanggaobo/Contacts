package com.zhanggb.contacts.app.util;


import com.zhanggb.contacts.app.IConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhanggaobo
 * @since 04/13/2015
 */
public class Log {
    private Logger logger;

    private Log(Class cls) {
        this.logger = LoggerFactory.getLogger(cls.getSimpleName());
    }

    public static Log build(Class cls) {
        return new Log(cls);
    }

    public void debug(String format, Object... params) {
        if (IConstant.VersionControl.IS_DEBUG) {
            logger.debug(format, params);
        }
    }
    public void loge(String format, Object... params) {
        if (IConstant.VersionControl.IS_DEBUG) {
            logger.error(format, params);
        }
    }
}
