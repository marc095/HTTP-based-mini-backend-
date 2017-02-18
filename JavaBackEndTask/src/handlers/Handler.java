package handlers;
import com.sun.net.httpserver.HttpHandler;

public interface Handler extends HttpHandler{
    boolean isActionRespond(String action);
    boolean isTypeRequestRespond(String requestType);
}
