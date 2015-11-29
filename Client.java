import java.util.Thread;
import java.net.*;
import java.io.*;

// http://chimera.labs.oreilly.com/books/1234000001805/ch13.html#learnjava3-CHP-13-SECT-1.1
public class Client implements Runnable{

  Socket sock;
  
  public Client(InetAddress addr){
     sock = new Socket(addr, Server.getPortNumber());
  }
  
  @Override
  public void Run(){
    try {
        //Socket sock = new Socket("127.0.0.1.", Server.getPortNumber());
        ServerSocket listener = new ServerSocket(Server.getPortNumber() );

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
      } catch ( UnknownHostException e ) {
          System.out.println("Can't find host.");
      } catch ( IOException e ) {
          System.out.println("Error connecting to host.");
      }
    }
}
