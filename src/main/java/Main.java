import java.util.List;

public class Main {
    private static final int PORT = 9999;

    public static void main(String[] args) {
        Server server = new Server();
        Handler handler = new StaticHandler();
        final List<String> validPaths = List.of("/index.html", "/spring.svg", "/spring.png", "/resources.html", "/styles.css", "/app.js", "/links.html", "/classic.html", "/events.html", "/events.js");
        for (String path : validPaths) {
            server.addHandler("GET", path, handler);
            server.addHandler("POST", path, handler);
        }

        LoginHandler loginHandler = new LoginHandler();
        server.addHandler("GET", LoginHandler.PATH, loginHandler);
        server.addHandler("POST", LoginHandler.PATH, loginHandler);

        server.start(PORT);
    }
}
