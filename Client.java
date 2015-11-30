import java.lang.Thread;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
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
    catch(IOException e){
      System.out.println(e);
    } // end 
    
    /*
    After a connection is established, a server application uses the same kind of Socket object for its side of the communications. 
    However, to accept a connection from a client, it must first create a ServerSocket, bound to the correct port.
    
    First, our server creates a ServerSocket attached to port 1234. On some systems, there are rules about which ports an application can use. Port numbers below 1024 are usually reserved for system processes and standard, well-known services, so we pick a port number outside of this range. The ServerSocket is created only once; thereafter, we can accept as many connections as arrive.
Next, we enter a loop, waiting for the accept() method of the ServerSocket to return an active Socket connection from a client. When a connection has been established, we perform the server side of our dialog, then close the connection and return to the top of the loop to wait for another connection. Finally, when the server application wants to stop listening for connections altogether,
it calls the close() method of the ServerSocket.
    */
    try {
        ServerSocket listener = new ServerSocket(getPortNumber());

        while ( !finished ) {
            Socket client = listener.accept();  // wait for connection

            InputStream in = client.getInputStream();
            OutputStream out = client.getOutputStream();

            // read a byte
            byte someByte = (byte)in.read();

            // read a newline or carriage-return-delimited string
            BufferedReader bin =
              new BufferedReader( new InputStreamReader( in ) );
            String someString = bin.readLine();

            // write a byte
            out.write(43);

            // say goodbye
            PrintWriter pout = new PrintWriter( out, true );
            pout.println("Goodbye!");
      
            // read a serialized Java object
            ObjectInputStream oin = new ObjectInputStream( in );
            Date date = (Date)oin.readObject();

            client.close();
        }

        listener.close();
    }
    catch (IOException e ) {}
    catch (ClassNotFoundException e2){}// end catch
  }// end run
}
