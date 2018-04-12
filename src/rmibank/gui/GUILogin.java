package rmibank.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import rmibank.code.client.Client;

public class GUILogin {
	JFrame frame;
	JPanel panel;
	JTextField agnum;
	JTextField accnum;
	JPasswordField pass;
	JButton exec;
	
	public GUILogin() {
		frame = new JFrame("LOGIN");
		panel = new JPanel();
		agnum = new JTextField();
		accnum = new JTextField();
		pass = new JPasswordField();
		exec = new JButton("LogIn");
		exec.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Client client;
				try {
					client = new Client("127.0.0.1",5000, "RMI Bank Service");
					int id = client.getStub().logIn(agnum.getText(), accnum.getText(), (String) String.valueOf(pass.getPassword()));
					if(id >= 0) {
						client.setID(id);
						new GUIClient(client).setFrame();
						frame.setVisible(false);
						frame.dispose();
					}else {
						JOptionPane.showMessageDialog(null, "Falha ao efetuar login");
					}
				} catch (RemoteException | NotBoundException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		
		
		panel.setLayout(new GridLayout(3,2));
		
		panel.add(new JLabel("Agência:"));
		panel.add(agnum);
		
		panel.add(new JLabel("Conta:"));
		panel.add(accnum);
		
		panel.add(new JLabel("Senha:"));
		panel.add(pass);
		
		panel.setBorder(new EmptyBorder(10,10,10,10));
		frame.add(panel);
		frame.add(exec, BorderLayout.SOUTH);
		
		frame.setSize(300,200);
		frame.setVisible(true);
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
	}
}
