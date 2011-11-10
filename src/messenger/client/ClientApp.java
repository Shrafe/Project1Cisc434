package client;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ClientApp extends JApplet{

	private JFrame frame;
	private int width = 300, height = 300;
	private int xdim = 600, ydim = 300;
	private int xspacing = 20;
	private int txtheight = 25;
	private int btnheight = 35;
	
	private ArrayList<Component> components;
	
	public ClientApp (JFrame frame) {
		this.frame = frame;
	}
	
	public void init() {
		
		components = new ArrayList<Component>();
		
		frame.setBounds(xdim, ydim, width, height);
		frame.setResizable(false);
		getContentPane().setBackground(Color.lightGray);
		
		JTextField username = new JTextField();
		username.setBounds(xspacing, height/4 , width-8*xspacing, txtheight);
		
		JPasswordField password = new JPasswordField();
		password.setBounds(xspacing, height/2, width-8*xspacing, txtheight);
		
		JLabel userLabel = new JLabel();
		userLabel.setText("Username");
		userLabel.setBounds(username.getX() + username.getWidth() + xspacing,
				username.getY(), 3*xspacing, txtheight);
		
		JLabel passLabel = new JLabel();
		passLabel.setText("Password");
		passLabel.setBounds(password.getX() + password.getWidth() + xspacing,
				password.getY(), 3*xspacing, txtheight);
		
		JButton login = new JButton();
		login.setText("Login");
		login.setBounds(xspacing, 3*height/4, width/3, btnheight);
		login.addActionListener(new LoginListener());
		
		JButton newUser = new JButton();
		newUser.setText("New User?");
		newUser.setBounds(login.getWidth() + 3*xspacing, 3*height/4, width/3, btnheight);
		
		components.add(username);
		components.add(password);
		components.add(userLabel);
		components.add(passLabel);
		components.add(login);
		components.add(newUser);
		
		for (int i = 0; i < components.size(); i++) {
			frame.add(components.get(i));
		}
	}
	
	// Clear every component from the current frame
	private void clearComponents() {
		for (int i = 0; i < components.size(); i++) {
			frame.remove(components.get(i));
		}
		
		components.clear();
	}
	
	// Create the main window
	private void mainWindow() {
		
		ydim = ydim/2;
		width = 500;
		height = 700;
		
		Dimension dim = new Dimension(500, 700);
		
		frame.setBounds(xdim, ydim/2, width, height);
		getContentPane().setBackground(Color.lightGray);
		
		JPanel mainScreen = new JPanel();
		mainScreen.setBounds(0, 0, width, height/2);
		mainScreen.setLayout(new GridLayout(2, 2));
		
		JPanel userList = new JPanel();
		JPanel roomList = new JPanel();
		userList.setBounds(xspacing, xspacing, width/2 - 40, height - xspacing - 150);
		roomList.setBounds(xspacing + width/2, xspacing, width/2 - 40, height - xspacing - 150);
		userList.setBackground(Color.blue);
		roomList.setBackground(Color.green);
		
		String[] list = { "black", "blue", "green", "yellow", "white" };
		String[] otherlist = { "blue", "blue", "blue", "yellow", "white" };
		
		JScrollPane userScroll = new JScrollPane(new JList(list));
		JScrollPane roomScroll = new JScrollPane(new JList(list));
		userScroll.setBounds(xspacing, xspacing, width/2 - 40, height - xspacing - 150);
		userScroll.setMinimumSize(dim);
		roomScroll.setBounds(xspacing + width/2, xspacing, width/2 - 40, height - xspacing - 150);
		
		JTextArea chatBox = new JTextArea();
		
		JButton send = new JButton();
		send.setText("Send");
		send.addActionListener(new SendListener());
		
		userList.add(userScroll);
		roomList.add(roomScroll);
		
		components.add(userList);
		components.add(roomList);
		components.add(chatBox);
		components.add(send);
		
		for (int i = 0; i < components.size(); i++) {
			mainScreen.add(components.get(i));
		}
		getContentPane().add(mainScreen);
	}
	
	// Handles the sending of the message
	class SendListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			//TODO: Determine whether post to chat room or whisper.
			//TODO: Send the message
		}
	}
	
	
	
	// Handles the Login button click event
	class LoginListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// TODO: Send the textfield data to the server then wait for a reply.
			// TODO: Clear the components currently in the frame and resize it
			
			clearComponents();
			
			mainWindow();
			
			Graphics g = frame.getGraphics();
			//g.drawRect(0, 0, width, height);
			//frame.paint(g);
		}
	}
	
	// Handles the New User button click event
	class NewUserListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// TODO: Clear the components currently in the frame
		}
	}
}
