package handlers;

import com.sun.net.httpserver.HttpExchange;
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
public class UserScoreHandlerTest {
    private UserScoreHandler userHandler;
    @Mock private Service service;
    @Mock HttpExchange http;
    @Mock Response response;
    
    @Before
    public void setup(){
    this.userHandler = new UserScoreHandler(this.service,this.response);
    }
    
    @Test
    public void shouldAcceptActionName(){
    boolean result = this.userHandler.isActionRespond("userscores");
    assertTrue(result);
    }
    
    @Test
    public void shouldAcceptRequestTypePost(){
    boolean result = this.userHandler.isTypeRequestRespond(Helper.GET);
    assertTrue(result);
    }
    
    @Test
    public void shouldNotAcceptRequestTypePost(){
    boolean result = this.userHandler.isTypeRequestRespond(Helper.POST);
    assertFalse(result);
    }
    
    @Test
    public void shouldReturnScore() throws IOException{
    //arrange
    String score = "score";
    int id = 11;
    String path = "/"+"userscores?"+"user="+id;
    
    when(service.getScoreForUser(id)).thenReturn(score);
    when(http.getRequestURI()).thenReturn(URI.create(path));
    
    //act
    this.userHandler.handle(http);
    //assert
    verify(this.response).sendResponse(200, score, http);
    }
}
