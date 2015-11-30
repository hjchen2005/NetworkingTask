import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.io.*;

public class Server implements Runnable  {
	//boolean finished = false;
  ServerSocket listener;
  private final static int port = 1337 ;
  
  public static int getPortNumber(){
    return port;
  }
  
  @Override
  public void run() {
	  try {
		listener = new ServerSocket(getPortNumber());
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	  try {
          while (true) {
              Socket socket = listener.accept();
              try {
                  PrintWriter out =
                      new PrintWriter(socket.getOutputStream(), true);
                  out.println(new Date().toString());
              } 
              catch (IOException e){
            	  e.printStackTrace();
              }
              
              
          }
      }
	  catch (IOException e){
		  e.printStackTrace();
	  }
      finally {
          try {
			listener.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      }
  }
}
