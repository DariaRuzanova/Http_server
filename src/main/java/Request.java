import java.io.BufferedReader;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Request {

    public final static char QUERY_PARAMETER_MARKER = '?';
    public final static String QUERY_PARAMETER_DELIMITER = "&";
    public final static String QUERY_PAIR_DELIMITER = "=";


    private final String method;
    private String path;
    private final BufferedReader body;
    private final Map<String, String> queryParams;

    public Request(String method, String path, BufferedReader body) {
        this.method = method;
        queryParams = new HashMap<>();
        parseComponents(path);
        this.body = body;
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public BufferedReader getBody() {
        return body;
    }

    public List<String> getQueryParams() {
        return new ArrayList<>(queryParams.keySet());
    }

    public String getQueryParam(String name) {
        return queryParams.getOrDefault(name, null);
    }

    private void parseComponents(String path) {
        int pos = path.indexOf(QUERY_PARAMETER_MARKER);
        if (0 <= pos) {
            this.path = path.substring(0, pos);
            String parametersStr = path.substring(pos + 1);
            String[] parameters = parametersStr.split(QUERY_PARAMETER_DELIMITER);
            for (String parameter : parameters) {
                String parameterName;
                String parameterValue;
                int equalPos = parameter.indexOf(QUERY_PAIR_DELIMITER);
                if (0 <= equalPos) {
                    parameterName = parameter.substring(0, equalPos);
                    parameterValue = parameter.substring(equalPos + 1);
                } else {
                    parameterName = parameter;
                    parameterValue = "";
                }
                if(parameterName.length()!=0) {
                    parameterName = URLDecoder.decode(parameterName, StandardCharsets.UTF_8);
                    parameterValue = URLDecoder.decode(parameterValue, StandardCharsets.UTF_8);
                    queryParams.put(parameterName, parameterValue);
                }
            }
        } else {
            this.path = path;
        }
    }

    @Override
    public String toString() {
        return "Request{" +
                "method='" + method + '\'' +
                ", path='" + path + '\'' +
                ", body=" + body +
                '}';
    }
}
