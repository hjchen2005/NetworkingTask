import java.lang.Thread;

public class Server implements Runnable{
  ServerSocket serverSocket;
  int port = 1337 ;
  public void run(){
    try{
      serverSocket = new (port);
    }
    catch(IOExceptio e){
      System.out.println(e);
    }
  }
}
