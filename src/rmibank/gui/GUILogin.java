package rmibank.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import rmibank.code.client.Client;
import rmibank.code.service.Login;

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
		exec.requestFocus();
		
		exec.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(agnum.getText().length() > 0 && accnum.getText().length() >0 && String.valueOf(pass.getPassword()).length() > 0) {
					Client client;
					try {
						Registry reg = LocateRegistry.getRegistry("127.0.0.1", 5000);
						Login login = (Login) reg.lookup("RMI Login Service");
						if((client = login.logIn(agnum.getText(), accnum.getText(), (String) String.valueOf(pass.getPassword())))!= null) {
							new GUIClient(client).setFrame();
							frame.setVisible(false);
							frame.dispose();
						}else {
							JOptionPane.showMessageDialog(null, "Credenciais Incorretas!");
						}
					} catch (RemoteException | NotBoundException ex) {
						JOptionPane.showMessageDialog(null, "Servidor indisponível!");
					}
				}else{
					JOptionPane.showMessageDialog(null, "Preencha os campos");
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
		frame.getRootPane().setDefaultButton(exec);
		
		frame.setSize(300,200);
		frame.setVisible(true);
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
	}
}
