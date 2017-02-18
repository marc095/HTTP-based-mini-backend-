package handlers;

import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import static java.net.HttpURLConnection.HTTP_OK;
import services.Service;

public class UserScoreHandler implements Handler{
    private static final String ACTION_NAME = "userscores";
    final Service score;
    Response response;
    
    public UserScoreHandler(Service score,  Response response){
    this.score = score;
    this.response = response;
    }
    
    @Override
    public boolean isActionRespond(String action){
    return ACTION_NAME.equals(action);
    }

    @Override
    public boolean isTypeRequestRespond(String requestType){
    return Helper.GET.equals(requestType);
    }

    @Override
    public void handle(HttpExchange he) throws IOException{
     Integer id = Helper.getParameter(he);
     String result = score.getScoreForUser(id);
     this.response.sendResponse(HTTP_OK,result, he);
    }   
}
