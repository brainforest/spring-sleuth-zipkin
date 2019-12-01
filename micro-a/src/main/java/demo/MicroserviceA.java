package demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Logger;

/**
 * Huabing Zhao
 */


@SpringBootApplication
public class MicroserviceA implements CommandLineRunner {

    private static final Logger LOG = Logger.getLogger(MicroserviceA.class.getName());
    @Autowired
    private RestTemplate restTemplate;



    public static void main(String[] args) {
        SpringApplication.run(MicroserviceA.class, args);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.build();
    }




    @Override
    public void run(String... args) {
        LOG.info("invoking..");
        String body =restTemplate.getForEntity("http://localhost:8070/invoke",String.class).getBody();
        LOG.info(body);
    }

}
