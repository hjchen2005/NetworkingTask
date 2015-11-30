package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import Server.Server;
import javax.swing.JOptionPane;

public class Client {
	public Client () throws IOException {
		String serverAddress = JOptionPane.showInputDialog(
	            "Enter IP Address of a machine that is\n" +
	            "running the date service on port 9090:");
	    Socket s = new Socket(serverAddress, Server.getPortNumber());
	    BufferedReader input =
	        new BufferedReader(new InputStreamReader(s.getInputStream()));
	    String answer = input.readLine();
	    JOptionPane.showMessageDialog(null, answer);
	    System.exit(0);
	}
}
