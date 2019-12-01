package demo.service;

import org.springframework.cloud.sleuth.annotation.NewSpan;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

/**
 * Huabing Zhao
 */
@RestController
public class InventoryServiceController {

    private static final Logger LOG = Logger.getLogger(InventoryServiceController.class.getName());
    @NewSpan
    @RequestMapping(value = "/createOrder")
    public String createOrder(@RequestHeader HttpHeaders headers) {
        try {
            LOG.info("createOrder");
            Thread.sleep((long) (Math.random() * 100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "Your order has been created!";
    }
}
