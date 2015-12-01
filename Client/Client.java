package Client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class Client extends JFrame{
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private JTextField  textField;
	//private String message ="";
	private String serverIP;
	private Socket connection;
	private JTextArea chatWindow;
	
	
	public Client(String hostName){
		super("Client program");
		serverIP = hostName;
		textField = new JTextField();
		textField.setEditable(false);
		textField.addActionListener(new ActionListener (){
			public void actionPerformed(ActionEvent event){
				sendMessage(event.getActionCommand());
				textField.setText("");
			}
		});
		add(textField,BorderLayout.NORTH);
		chatWindow = new JTextArea();
		add(new JScrollPane(chatWindow));
		setSize(400,200);
		setVisible(true);
	}
	
	
	
	public void start(){
		try{
			connectToServer();
			setUpStreams();
			whileChatting();
		} catch(EOFException e){
			showMessage("\n Client terminated connection");
		} catch(IOException e1){
			e1.printStackTrace();
		} finally {
			closeDown();
		}
	}
	
	private void whileChatting() throws IOException{
		// TODO Auto-generated method stub
		String message = "You are now connected";
		sendMessage(message);
		ableToType(true);
		do{
			try{
				message=(String) input.readObject();
				showMessage("\n"+message);
			}
			catch(ClassNotFoundException e){
				showMessage("Cannot receive message!");
			}
		} while(!message.equals("ME - BYE"));
	}

	private void setUpStreams() throws IOException{
		// TODO Auto-generated method stub
		output = new ObjectOutputStream(connection.getOutputStream());
		output.flush();
		input = new ObjectInputStream(connection.getInputStream());
		showMessage("\n Streams are now setup!");
	}

	private void closeDown(){
		showMessage ("\n Closing down");
		ableToType(false);
		try{
			output.close();
			input.close();
			connection.close();
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	
	private void connectToServer() throws IOException{
		showMessage("Trying to connect ...");
		connection = new Socket(InetAddress.getByName(serverIP),1337);
		showMessage("Connected to "+connection.getInetAddress().getHostName());
	}
	
	private void ableToType(final boolean b) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				textField.setEditable(b);
			}
		});
	}
	private void sendMessage(String message) {
		// TODO Auto-generated method stub
		try{
			output.writeObject("Me: "+message);
			output.flush();
			showMessage("\n Me: "+message);
		} catch(IOException e){
			chatWindow.append("\n Something messed up");
		}
	}
	
	
	private void showMessage(String message){
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				chatWindow.append(message);
			}
		});
	}
}
