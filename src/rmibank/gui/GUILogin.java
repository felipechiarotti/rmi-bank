package rmibank.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
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
	JRadioButton agency[];
	JButton exec;
	
	
	public GUILogin() {
		frame = new JFrame("LOGIN");
		panel = new JPanel();
		agnum = new JTextField();
		accnum = new JTextField();
		pass = new JPasswordField();
		agency = new JRadioButton[2];
		agency[0] = new JRadioButton("Agência 1");
		agency[1] = new JRadioButton("Agência 2");
		exec = new JButton("LogIn");
		exec.requestFocus();
		
		exec.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(agnum.getText().length() > 0 && accnum.getText().length() >0 && String.valueOf(pass.getPassword()).length() > 0) {
					Client client;
					try {
						int agencyNumber = 0;
						for(int i = 0; i<agency.length; i++) {
							if(agency[i].isSelected()) {
								agencyNumber = i+1;
							}
						}
						Registry reg = LocateRegistry.getRegistry("127.0.0.1", 9765);
						Login login = (Login) reg.lookup("RMI Login Service "+agencyNumber);
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
		
		
		panel.setLayout(new GridLayout(4,2));
		
		panel.add(new JLabel("Agência:"));
		panel.add(agnum);
		
		panel.add(new JLabel("Conta:"));
		panel.add(accnum);
		
		panel.add(new JLabel("Senha:"));
		panel.add(pass);
		
		ButtonGroup buttonGroup = new ButtonGroup();
		agency[0].setSelected(true);
		buttonGroup.add(agency[0]);
		buttonGroup.add(agency[1]);
		panel.add(agency[0]);
		panel.add(agency[1]);
		
		panel.setBorder(new EmptyBorder(10,10,10,10));
		frame.add(panel);
		frame.add(exec, BorderLayout.SOUTH);
		frame.getRootPane().setDefaultButton(exec);
		
		frame.setSize(300,200);
		frame.setVisible(true);
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
	}
}
