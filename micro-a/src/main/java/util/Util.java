package util;

import org.springframework.cloud.sleuth.annotation.NewSpan;
import org.springframework.http.HttpHeaders;

import java.util.Arrays;
import java.util.List;

public class Util {

    public static void extractHeader(HttpHeaders headers, HttpHeaders extracted, String key) {
        List<String> vals = headers.get(key);
        if (vals != null && !vals.isEmpty()) {
            extracted.put(key, Arrays.asList(vals.get(0)));
        }
    }
    public static void injectHeader( HttpHeaders injected, String key,String value) {
        injected.add(key,  value);
    }
}
