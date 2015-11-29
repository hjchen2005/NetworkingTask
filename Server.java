import java.lang.Thread;

public class Server implements Runnable{
  ServerSocket serverSocket;
  private final int port = 1337 ;
  
  public static int getPortNumber(){
    return port;
  }
  
  @Override
  public void run(){
    try{
      serverSocket = new ServerSocket (getPortNumber());
    }
    catch(IOExceptio e){
      System.out.println(e);
    }
  }
}
