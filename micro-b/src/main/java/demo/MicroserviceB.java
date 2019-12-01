package demo;



import brave.Tracer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import zipkin2.Span;


import java.util.logging.Logger;

/**
 * Huabing Zhao
 */


@SpringBootApplication
public class MicroserviceB implements CommandLineRunner {


    private static final Logger LOG = Logger.getLogger(MicroserviceB.class.getName());

    @Autowired
    private RestTemplate restTemplate;

    public static void main(String[] args) {

        SpringApplication.run(MicroserviceB.class, args);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.build();
    }

    @Override
    public void run(String... args) {
        LOG.info("invoking..");
        String body =restTemplate.getForEntity("http://localhost:8080/checkout",     String.class).getBody();
        LOG.info(body);
    }


}
