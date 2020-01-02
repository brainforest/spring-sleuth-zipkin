package demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
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
    public void run(String... args) throws InterruptedException {
        LOG.info("invoking..");

        final int a = 0;
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                String body =restTemplate.getForEntity("http://localhost:8070/invoke",String.class).getBody();
            }
        },0,1000L,TimeUnit.MILLISECONDS);

        executor.awaitTermination(1, TimeUnit.DAYS);
        executor.shutdown();
    }

}
