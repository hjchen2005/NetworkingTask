import java.util.Thread;

public class Client implements Runnable{

  @Override
  public void Run(){
    try {
        Socket sock = new Socket("127.0.0.1.", Server.getPortNumber());
      } catch ( UnknownHostException e ) {
          System.out.println("Can't find host.");
      } catch ( IOException e ) {
          System.out.println("Error connecting to host.");
      }
    }
}
