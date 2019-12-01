package demo.tracing;

import org.springframework.cloud.sleuth.zipkin2.ZipkinAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import zipkin2.Call;
import zipkin2.Span;
import zipkin2.codec.Encoding;
import zipkin2.reporter.AsyncReporter;
import zipkin2.reporter.Reporter;
import zipkin2.reporter.Sender;
import zipkin2.reporter.urlconnection.URLConnectionSender;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

@Configuration
public class TraceSender {

    private static final Logger LOG = Logger.getLogger(TraceSender.class.getName());
    String url = "http://localhost:9411/api/v2/spans";
    URLConnectionSender sender = URLConnectionSender.newBuilder()
            .endpoint(url)
            .encoding(Encoding.JSON)
            .build();

    @Bean(ZipkinAutoConfiguration.REPORTER_BEAN_NAME)
    Reporter<Span> myReporter() {
        return AsyncReporter.create(mySender());
    }

    @Bean(ZipkinAutoConfiguration.SENDER_BEAN_NAME)
    MySender mySender() {
        return new MySender();
    }

    public class MySender extends Sender {

        private boolean spanSent = false;
        @Override
        public Encoding encoding() {
            return Encoding.JSON;
        }

        @Override
        public int messageMaxBytes() {
            return Integer.MAX_VALUE;
        }

        @Override
        public int messageSizeInBytes(List<byte[]> encodedSpans) {
            return encoding().listSizeInBytes(encodedSpans);
        }

        @Override
        public Call<Void> sendSpans(List<byte[]> encodedSpans)   {
            this.spanSent = true;
            LOG.info("sending " + encodedSpans.size() + " spans");

            if (sender.check().ok()) {
                LOG.info("sender is ok, sending all spans now...");
                Call<Void> voidCall = sender.sendSpans(encodedSpans);
                try {
                    voidCall.execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            for (int i = 0; i < encodedSpans.size(); i++) {
                LOG.info(new String(encodedSpans.get(i)));
            }
            return  Call.create(null);
        }

    }
}
