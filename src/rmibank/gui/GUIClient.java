package rmibank.gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
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
	JComboBox<String> actions;
	String actionsList[] = {"Sacar", "Ver Saldo"};
	Bank stub;
	
	public GUIClient(Client c) throws AccessException, RemoteException, NotBoundException {
		this.client = c;
		Registry reg = LocateRegistry.getRegistry(5000);
		this.stub = (Bank) reg.lookup("RMI Bank Service");
	}
	
	
	public void setFrame() {
		frame = new JFrame(client.getName());
		
		executeAction = new JButton("executar");
		executeAction.requestFocus();
		actions = new JComboBox<String>(actionsList);
		
		executeAction.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String tmp = "";
				try {
					if(actions.getSelectedIndex() == 0) {
						tmp = JOptionPane.showInputDialog(null, "Digite o valor para sacar:");
						if(stub.widthdraw(client.getId(), Double.parseDouble(tmp))) {
							JOptionPane.showMessageDialog(null, "Operação realizada com sucesso!");
						}else {
							JOptionPane.showMessageDialog(null, "Você não tem saldo suficiente!");
						}
					}else {
						JOptionPane.showMessageDialog(null, "R$:"+stub.balance(client.getId()));
					}
				} catch (NumberFormatException nfex) {
					JOptionPane.showMessageDialog(null, "Valor Inválido");
				}catch (RemoteException rex) {
					JOptionPane.showMessageDialog(null, "Falha na conexão com o servidor");
				}

			}
			
		});
		
		frame.setLayout(new GridLayout(1,2));
		frame.add(actions);
		
		frame.getRootPane().setDefaultButton(executeAction);
		frame.add(executeAction);
		
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		frame.setSize(300,100);
		frame.setVisible(true);
	}
	
	

}
