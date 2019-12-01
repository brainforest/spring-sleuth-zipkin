package demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.annotation.NewSpan;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import util.Util;

import java.util.logging.Logger;

/**
 * Huabing Zhao
 */
@RestController
public class RequestController {

    private static final Logger LOG = Logger.getLogger(RequestController.class.getName());

    @Autowired
    private RestTemplate restTemplate;

    @NewSpan
    @RequestMapping(value = "/invoke")
    public String checkout(@RequestHeader HttpHeaders headers) {

        HttpHeaders tracingHeaders = new HttpHeaders();
        Util.extractHeader(headers, tracingHeaders, "x-request-id");
        Util.extractHeader(headers, tracingHeaders, "x-b3-traceid");
        Util.extractHeader(headers, tracingHeaders, "x-b3-spanid");
        Util.extractHeader(headers, tracingHeaders, "x-b3-parentspanid");
        Util.extractHeader(headers, tracingHeaders, "x-b3-sampled");
        Util.extractHeader(headers, tracingHeaders, "x-b3-flags");
        Util.extractHeader(headers, tracingHeaders, "x-ot-span-context");

        LOG.info(  "tracingHeaders : " + tracingHeaders.toString());
        String body =restTemplate.exchange("http://localhost:8080/checkout", HttpMethod.POST,new HttpEntity<>(headers), String.class).getBody();
        LOG.info(body);

        return "ok";
    }
}
