import java.lang.Thread;
import java.io.*;

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
      InputStream in = server.getInputStream();
        OutputStream out = server.getOutputStream();

        // write a byte
        out.write(42);

        // write a newline or carriage return delimited string
        PrintWriter pout = new PrintWriter( out, true );
        pout.println("Hello!");

        // read a byte
        byte back = (byte)in.read();

        // read a newline or carriage return delimited string
        BufferedReader bin =
          new BufferedReader( new InputStreamReader( in ) );
        String response = bin.readLine();

        // send a serialized Java object
        ObjectOutputStream oout = new
        ObjectOutputStream( out );
        oout.writeObject( new java.util.Date() );
        oout.flush();
      
        serverSocket.close();
    }
    catch(IOExceptio e){
      System.out.println(e);
    }
  }
}
