package demo.service;


import org.springframework.cloud.sleuth.annotation.NewSpan;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

/**
 * Huabing Zhao
 */
@Component
public class DBAccess {
    private static final Logger LOG = Logger.getLogger(DBAccess.class.getName());

    @NewSpan
    public void save2db() {
        try {
            LOG.info("save2db");
            Thread.sleep((long) (Math.random() * 100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}