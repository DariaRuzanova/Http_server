import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class LoginHandler implements Handler {
    public final static String PATH = "/forms.html";

    @Override
    public void handle(Request request, OutputStream responseStream) {
        byte[] response;
        String contentType = "text/html; charset=utf-8";

        try {
            if (request.getQueryParams().isEmpty()) {
                response = getFileContent();
            } else {
                response = generateGreetingContent(request);
            }

            responseStream.write((
                    "HTTP/1.1 200 OK\r\n" +
                            "Content-Type: " + contentType + "\r\n" +
                            "Content-Length: " + response.length + "\r\n" +
                            "Connection: close\r\n" +
                            "\r\n"
            ).getBytes());
            responseStream.write(response);
            responseStream.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private byte[] getFileContent() throws IOException {
        final var filePath = Path.of(".", "public", PATH);
        return Files.readString(filePath).getBytes(StandardCharsets.UTF_8);
    }

    private byte[] generateGreetingContent(Request request) {
        String response = """
                <!doctype html>
                <html lang="ru">
                <head>
                  <meta charset="UTF-8">
                  <title>Hello {login}</title>
                </head>
                <body>
                <h1>Hello {login}</h1>
                </body>
                </html>""";
        response = response.replace("{login}", request.getQueryParam("login"));
        return response.getBytes(StandardCharsets.UTF_8);
    }
}
