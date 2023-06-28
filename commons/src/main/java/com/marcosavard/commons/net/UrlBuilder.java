package com.marcosavard.commons.net;

import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class UrlBuilder {
    private String base;
    private Charset charset;
    private Map<String, Object> parameters = new LinkedHashMap<>();

    public static UrlBuilder of(String base) {
        return of(base, StandardCharsets.UTF_8);
    }

    public static UrlBuilder of(String base, Charset charset) {
        return new UrlBuilder(base, charset);
    }

    private UrlBuilder(String base, Charset charset) {
        this.base = (base == null) ? "" : base;
        this.charset = charset;
    }

    public UrlBuilder with(String parameter, Object value) {
        parameters.put(parameter, value);
        return this;
    }

    public UrlBuilder with(Map<String, Object> parameters) {
        parameters.putAll(parameters);
        return this;
    }

    @Override
    public String toString() {
        List<String> parameterList = new ArrayList<>();

        for (String key : parameters.keySet()) {
            String name = URLEncoder.encode(key, charset);
            String value = String.valueOf(parameters.get(key));
            value = URLEncoder.encode(value, charset);
            parameterList.add(name + "=" + value);
        }

        boolean prefixRequired = !(base.isEmpty()) && !(parameterList.isEmpty());
        StringBuilder sb = new StringBuilder(base);
        sb.append(prefixRequired ? "?" : "");
        sb.append(String.join("&", parameterList));
        return  sb.toString();
    }
}
