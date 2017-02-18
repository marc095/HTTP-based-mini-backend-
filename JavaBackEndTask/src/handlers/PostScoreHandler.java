package handlers;

import com.sun.net.httpserver.HttpExchange;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import static java.net.HttpURLConnection.HTTP_OK;
import services.Service;
import static java.lang.Integer.parseInt;

public class PostScoreHandler implements Handler{
    final Service scoreService;
    final Response response;
    final static String ACTION_NAME = "postscore";

    public PostScoreHandler(Service scoreService,Response response){
    this.response = response;
    this.scoreService = scoreService;
    }
   
    @Override
    public boolean isActionRespond(String action) {
        return ACTION_NAME.equals(action);
    }

    @Override
    public boolean isTypeRequestRespond(String requestType) {
    return Helper.POST.equals(requestType);
    }
    
    private int queryParse(String query, int index){
    String[] array = query.split("&");
    String[] objects = array[index].split("=");
    int result =  parseInt(objects[1]);
    return result;
    }
    
    @Override
    public void handle(HttpExchange he) throws IOException {
    String query = getQueryString(he);
    
    int userId = queryParse(query, 0);
    int lvl = queryParse(query, 1);
    int score = queryParse(query, 2);

    this.scoreService.submitNewScore(userId, lvl, score);
    this.response.sendResponse(HTTP_OK, "", he);
    }
  
    String getQueryString(HttpExchange he){
    try{
        InputStreamReader reader = new InputStreamReader(he.getRequestBody(), "utf-8");
        BufferedReader br = new BufferedReader(reader);
        String query = br.readLine();
        return query;
    }catch(Exception e){
    throw new RuntimeException("Cannot read score from the body" + e.getMessage());
    }
   } 
}
