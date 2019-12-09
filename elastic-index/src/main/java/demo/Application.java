package demo;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthRequest;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.client.*;

import java.io.IOException;
import java.util.Set;

public class Application {

    public static void main(String[] args) throws IOException {
        BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("username", "password"));
        RestClientBuilder builder = RestClient
                .builder(new HttpHost("localhost", 9200, "http"))
                .setHttpClientConfigCallback(httpClientBuilder ->
                        httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider));
        RestHighLevelClient client = new RestHighLevelClient(builder);

        ClusterHealthRequest request = new ClusterHealthRequest();
        ClusterHealthResponse response = client.cluster().health(request, RequestOptions.DEFAULT);
        Set<String> indices = response.getIndices().keySet();
        for(String i : indices) {
            System.out.printf("Indexes : ", i);
            if (i.contains("ocp")) {
                // create kibana index pattern
            }
        }
    }
}
