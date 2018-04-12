package rmibank.gui;

import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import rmibank.code.client.Client;
import rmibank.code.service.Bank;

public class GUIClient {
	Client client;
	JFrame frame;
	JButton executeAction;
	JComboBox actions;
	String actionsList[] = {"Sacar", "Ver Saldo"};
	
	public GUIClient(Client c) {
		this.client = c;
	}
	
	
	public void setFrame() {
		frame = new JFrame();
		
		executeAction = new JButton("executar");
		actions = new JComboBox(actionsList);
		
		executeAction.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String tmp = "";
				try {
					if(actions.getSelectedIndex() == 0) {
						tmp = JOptionPane.showInputDialog(null, "Digite o valor para sacar:");
						if(client.getStub().widthdraw(client.getID(), Double.parseDouble(tmp))) {
							JOptionPane.showMessageDialog(null, "Operação realizada com sucesso!");
						}else {
							JOptionPane.showMessageDialog(null, "Você não tem saldo suficiente!");
						}
					}else {
						JOptionPane.showMessageDialog(null, "R$:"+client.getStub().balance(client.getID()));
					}
				} catch (NumberFormatException | RemoteException | HeadlessException | SQLException e1) {
					e1.printStackTrace();
				}

			}
			
		});
		
		frame.setLayout(new GridLayout(1,2));
		frame.add(actions);
		frame.add(executeAction);
		
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		frame.setSize(300,100);
		frame.setVisible(true);
	}
	
	

}
