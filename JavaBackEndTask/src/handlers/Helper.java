package handlers;

import com.sun.net.httpserver.HttpExchange;

public class Helper {
    public static Integer getParameter(HttpExchange he){
    return Integer.parseInt(he.getRequestURI().getRawQuery().split("=")[1]);
    }
    public static final String GET = "GET";
    public static final String POST = "POST";
}
