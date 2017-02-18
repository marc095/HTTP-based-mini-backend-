package start;

import java.io.IOException;
import static java.lang.System.exit;

public class Start {
public static final int PORT_NUMBER = 8080;

public static void main(String[] args){
   startServer();
}

private static void startServer() 
{
try{
 new Server(PORT_NUMBER).start();
 }catch(IOException e)
 {
     System.err.println("failed run" + e.getLocalizedMessage());
     exit(-1);
 }
}
}
