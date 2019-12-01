package demo.service;

import brave.Tracer;
import demo.tracing.TraceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.annotation.NewSpan;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.logging.Logger;

/**
 * Huabing Zhao
 */
@RestController
public class BillingServiceController {

    private static final Logger LOG = Logger.getLogger(BillingServiceController.class.getName());

    @Autowired
    private BankTransaction bankTransaction;
    @Autowired
    private DBAccess dbAccess;

    @NewSpan
    @RequestMapping(value = "/payment")
    public String payment(@RequestHeader HttpHeaders headers)  {

        // Adding HttHeader
        Map<String, String> stringStringMap = headers.toSingleValueMap();
        for(String key : stringStringMap.keySet()) {
            TraceUtil.instance().addTag(key,stringStringMap.get(key));
        }
        LOG.info("payment");
        bankTransaction.transfer();
        dbAccess.save2db();
        bankTransaction.notification();
        return "Your order has been paid!";
    }
}
