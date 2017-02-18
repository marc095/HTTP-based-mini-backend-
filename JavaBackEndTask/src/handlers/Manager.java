package handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.util.List;

public class Manager implements HttpHandler {
    private final List<Handler> handlers;
    Response response;    
    @Override
    public void handle(HttpExchange he) throws IOException {
     String [] requestPath = he.getRequestURI().getPath().split("/");
     start(requestPath[1], he.getRequestMethod(), he);
    }
    
    void start(String action, String requestMethod,HttpExchange he) throws IOException{
        for (Handler handler: handlers) {
            if(handler.isActionRespond(action) && handler.isTypeRequestRespond(requestMethod)){
             try{
            handler.handle(he);
             }catch(Exception e){
             response.sendResponse(404, e.getLocalizedMessage(), he);
             }
            return;
            }
        }
    }
    
    public Manager(List<Handler> handlers, Response response){
    this.handlers = handlers;
    this.response = response;
    }
}
