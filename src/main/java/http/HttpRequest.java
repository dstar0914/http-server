package http;

import java.util.Map;

public class HttpRequest {

    private HttpMethod method;

    private String url;

    private Map<String, String> headers;

    private Map<String, String> params;

    private Map<String, String> body;

    public HttpRequest(HttpMethod method, String url, Map<String, String> headers, Map<String, String> params, Map<String, String> body) {
        this.method = method;
        this.url = url;
        this.headers = headers;
        this.params = params;
        this.body = body;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public Map<String, String> getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "HttpRequest{" +
                "method=" + method +
                ", url='" + url + '\'' +
                ", headers=" + headers +
                ", params=" + params +
                ", body=" + body +
                '}';
    }

}
