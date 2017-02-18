package handlers;

import com.sun.net.httpserver.HttpExchange;
import java.io.ByteArrayInputStream;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import services.Service;

import java.net.URI;
import java.io.IOException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PostScoreHandlerTest {
    private PostScoreHandler scoreHandler;
    @Mock private Service service;
    @Mock HttpExchange http;
    @Mock Response response;
    
    @Before
    public void setup(){
    this.scoreHandler = new PostScoreHandler(this.service,this.response);
    }
    
    @Test
    public void shouldAcceptActionName(){
    boolean result = this.scoreHandler.isActionRespond("postscore");
    assertTrue(result);
    }
    
    @Test
    public void shouldAcceptRequestTypePost(){
    boolean result = this.scoreHandler.isTypeRequestRespond(Helper.POST);
    assertTrue(result);
    }
    
    @Test
    public void shouldNotAcceptRequestTypePost(){
    boolean result = this.scoreHandler.isTypeRequestRespond(Helper.GET);
    assertFalse(result);
    }
    
    @Test
    public void shouldSubmitNewScore() throws IOException{
    int userid = 1;
    int lvl = 1;
    int score = 20;
    String path = "/" + "postscore?";
    String input = "user="+ userid+ "&"+ "level=" + lvl + "&"+ "score=" + score;
    when(http.getRequestURI()).thenReturn(URI.create(path));
    when(http.getRequestBody()).thenReturn(new ByteArrayInputStream(input.getBytes()));
    this.scoreHandler.handle(http);
    verify(this.service).submitNewScore(userid, lvl, score);
    }
}
