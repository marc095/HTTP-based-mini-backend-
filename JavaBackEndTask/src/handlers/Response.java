package handlers;

import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;

public class Response {
    public void sendResponse(int responseCode, String body, HttpExchange http) throws IOException{
     http.sendResponseHeaders(responseCode, body.length());
     OutputStream os = http.getResponseBody();
     os.write(body.getBytes());
     os.close();
    }
}
