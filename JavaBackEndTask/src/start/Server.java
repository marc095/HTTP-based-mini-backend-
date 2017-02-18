package start;

import services.Service;
import handlers.LevelScoreHandler;
import handlers.Response;
import handlers.PostScoreHandler;
import handlers.Manager;
import com.sun.net.httpserver.HttpServer;
import java.util.List;
import java.io.IOException;
import java.net.InetSocketAddress;

import static java.util.Arrays.asList;
import handlers.UserScoreHandler;
import handlers.Handler;
import static java.util.concurrent.Executors.newCachedThreadPool;
import static com.sun.net.httpserver.HttpServer.create;

public class Server {
    private final int port;
    HttpServer server;
    Response responseHandler = new Response();
    Service score = new Service();
    
    public Server(int port){
    this.port = port;
    }
    
    public void start() throws IOException{
    server = create(new InetSocketAddress(port), 0);
    server.createContext("/", new Manager(getAllHandlers(), responseHandler));
    server.setExecutor(newCachedThreadPool());
    server.start();
    System.out.println("Server:"+ server.getAddress());
    }
    
    private List<Handler> getAllHandlers(){
    return asList(new PostScoreHandler(score,this.responseHandler),
            new LevelScoreHandler(this.score, this.responseHandler),
            new UserScoreHandler(this.score, this.responseHandler)
            );
    }
}
