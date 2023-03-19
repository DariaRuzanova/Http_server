import org.junit.jupiter.api.Test;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RequestTest {
    private String convertToQuery(Map<String, String> parameters) {
        if (parameters == null || parameters.isEmpty()) return "";
        StringBuilder sb = new StringBuilder();
        sb.append(Request.QUERY_PARAMETER_MARKER);
        for(Map.Entry<String, String> parameter : parameters.entrySet()) {
            sb.append(URLEncoder.encode(parameter.getKey(), StandardCharsets.UTF_8));
            sb.append(Request.QUERY_PAIR_DELIMITER);
            sb.append(URLEncoder.encode(parameter.getValue(), StandardCharsets.UTF_8));
            sb.append(Request.QUERY_PARAMETER_DELIMITER);
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    @Test
    void execute() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("переменная1", "значение1");
        parameters.put("переменная2", "значение 2");
        parameters.put("переменная3", " Значение 3!!");

        String method = "GET";
        String path = "/test.html" + convertToQuery(parameters);
//        String path = "/test.html?";

        Request request = new Request(method, path, null);
    }
}
