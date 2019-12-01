package demo.controller;

import brave.Tracer;
import demo.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.annotation.NewSpan;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


import java.util.UUID;
import java.util.logging.Logger;

/**
 * Huabing Zhao
 */
@RestController
public class EShopController {

    private static final Logger LOG = Logger.getLogger(EShopController.class.getName());

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    Tracer tracer;

    @NewSpan
    @RequestMapping(value = "/checkout")
    public String checkout(@RequestHeader HttpHeaders headers) {

        String result = "";
        HttpHeaders tracingHeaders = new HttpHeaders();
        Util.create().extractHeader(headers, tracingHeaders, "x-request-id");
        Util.create().extractHeader(headers, tracingHeaders, "x-b3-traceid");
        Util.create().extractHeader(headers, tracingHeaders, "x-b3-spanid");
        Util.create().extractHeader(headers, tracingHeaders, "x-b3-parentspanid");
        Util.create().extractHeader(headers, tracingHeaders, "x-b3-sampled");
        Util.create().extractHeader(headers, tracingHeaders, "x-b3-flags");
        Util.create().extractHeader(headers, tracingHeaders, "x-ot-span-context");

        LOG.info(  "tracingHeaders : " + tracingHeaders.toString());

        tracer.currentSpan().tag("x-country-code", "TR");
        tracer.currentSpan().tag("x-request-id", UUID.randomUUID().toString());

        LOG.info("checkout");
        result += restTemplate.getForEntity("http://localhost:8080/createOrder", String.class).getBody();
        result += "<BR>";
        result += restTemplate.getForEntity("http://localhost:8080/payment", String.class).getBody();
        result += "<BR>";
        result += restTemplate.getForEntity("http://localhost:8080/arrangeDelivery", String.class).getBody();


        return result;
    }
}
