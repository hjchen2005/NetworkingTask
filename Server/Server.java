package Server;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class Server extends JFrame{
	
	public final static int portNumber = 1339;
	private final int maxQueueLength =100;
	private boolean finished = false;
	
	//private String message = "";
	private JTextField textField;
	private JTextArea chatWindow;
	private ObjectInputStream input;
	private ObjectOutputStream output;
	private ServerSocket server;
	private Socket connection;
	
	public Server(){
		super("Server program");
		textField = new JTextField();
		textField.setEditable(false);
		textField.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				sendMessage(e.getActionCommand());
				textField.setText("");
			}
		});
		add(textField,BorderLayout.NORTH);
		chatWindow = new JTextArea();
		add(new JScrollPane(chatWindow));
		setSize(400,200);
		setVisible(true);
	}
	
	private void sendMessage(String message) {
		// TODO Auto-generated method stub
		try{
			output.writeObject("Server: " + message);
			output.flush();
			showMessage("\n Server: " + message);
		} catch(IOException e){
			chatWindow.append("\n Something messed up");
		}
	}
	
	public void start(){
		try{
			server = new ServerSocket(portNumber, maxQueueLength);
			while (!finished){
				try{
					waitForConnection();
					seUpStreams();
					whileChatting();
				} catch (EOFException e){
					showMessage("\nServer ended the connection");
				}
				finally {
					closeDown();
				}
			}
		} catch(IOException e){}
	}

	private void closeDown() {
		// TODO Auto-generated method stub
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

	private void ableToType(final boolean b) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				textField.setEditable(b);;
			}
			
		});
	}

	private void showMessage(final String text) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				chatWindow.append(text);
			}
			
		});
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

	private void seUpStreams() throws IOException{
		// TODO Auto-generated method stub
		output = new ObjectOutputStream(connection.getOutputStream());
		output.flush();
		input = new ObjectInputStream(connection.getInputStream());
		showMessage("\n Streams are now setup!");
	}

	private void waitForConnection() throws IOException{
		// TODO Auto-generated method stub
		showMessage("Waiting for someone to connect ... ");
		connection = server.accept();
		showMessage("Now connected to "+connection.getInetAddress().getHostName());
	}

	
}	
