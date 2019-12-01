package demo.util;

import brave.Tracing;
import org.springframework.http.HttpHeaders;

import java.util.Arrays;
import java.util.List;

public class Util {

    public Util() {
    }

    public static Util create() {
        return new Util();
    }

    public void extractHeader(HttpHeaders headers, HttpHeaders extracted, String key) {
        List<String> vals = headers.get(key);
        if (vals != null && !vals.isEmpty()) {
            extracted.put(key, Arrays.asList(vals.get(0)));
            Tracing.currentTracer().currentSpan().tag(key,vals.get(0));
        }
    }
    public static void injectHeader( HttpHeaders injected, String key,String value) {
        injected.add(key,  value);
    }
}
