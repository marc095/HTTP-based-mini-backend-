package handlers;

import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import static java.net.HttpURLConnection.HTTP_OK;
import services.Service;

public class LevelScoreHandler implements Handler{
    private static final String ACTION_NAME = "levelscores";
    final Service score;
    Response response;
    
    public LevelScoreHandler(Service score,Response response){
    this.response = response;
    this.score = score;
    }
    
    @Override
    public boolean isActionRespond(String action) {
     return ACTION_NAME.equals(action);
    }

    @Override
    public boolean isTypeRequestRespond(String requestType) {
    return Helper.GET.equals(requestType);
    }

    @Override
    public void handle(HttpExchange he) throws IOException {
    Integer level = Helper.getParameter(he);
    String result = score.getScoreForLvl(level);
    this.response.sendResponse(HTTP_OK,result, he);
    }
    
}
