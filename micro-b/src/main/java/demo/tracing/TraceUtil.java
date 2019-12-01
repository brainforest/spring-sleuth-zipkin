package demo.tracing;

import brave.Tracing;

public class TraceUtil {

    static TraceUtil traceUtil;
    public TraceUtil() {
    }

    public static TraceUtil instance() {
        if (traceUtil == null) traceUtil = new TraceUtil();
        return traceUtil;
    }

    public void addTag(String key, String value) {
        Tracing.currentTracer().currentSpan().tag(key,value);
    }

    public void addAnnotation(String annotation) {
        Tracing.currentTracer().currentSpan().annotate(annotation);
    }
}
