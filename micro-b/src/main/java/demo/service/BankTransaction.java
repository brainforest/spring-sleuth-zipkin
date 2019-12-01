package demo.service;





import brave.Tracer;
import brave.Tracing;
import demo.tracing.TraceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.annotation.NewSpan;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

/**
 * Huabing Zhao
 */
@Component
public class BankTransaction {

    private static final Logger LOG = Logger.getLogger(BankTransaction.class.getName());
    @NewSpan
    public void notification() {
        try {
            LOG.info("notification");
            TraceUtil.instance().addTag("key","value");
            TraceUtil.instance().addAnnotation("annotation");
            Thread.sleep((long) (Math.random() * 100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @NewSpan
    public void transfer() {
        try {
            LOG.info("transfer");
            Thread.sleep((long) (Math.random() * 100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}