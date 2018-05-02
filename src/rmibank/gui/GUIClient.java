package rmibank.gui;

import java.awt.BorderLayout;
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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import rmibank.code.client.Client;
import rmibank.code.service.Bank;

public class GUIClient {
	Client client;
	JFrame frame;
	JButton executeAction;
	JComboBox<String> actions;
	String actionsList[] = {"Selecione uma operação", "Sacar", "Depositar", "Transferir", "Ver Saldo"};
	Bank stub;
	
	public GUIClient(Client c) throws AccessException, RemoteException, NotBoundException {
		this.client = c;
		Registry reg = LocateRegistry.getRegistry("127.0.0.1",9765);
		this.stub = (Bank) reg.lookup("RMI Bank Service "+this.client.getAgnum());
	}
	
	
	public void setFrame() {
		frame = new JFrame(client.getName());
		
		executeAction = new JButton("executar");
		executeAction.requestFocus();
		actions = new JComboBox<String>(actionsList);
		actions.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String tmp = "";
				try {
					if(actions.getSelectedIndex() == 1) {
						tmp = JOptionPane.showInputDialog(null, "Digite o valor para sacar:");
						if(stub.widthdraw(client.getId(), Double.parseDouble(tmp))) {
							JOptionPane.showMessageDialog(null, "Operação realizada com sucesso!");
						}else {
							JOptionPane.showMessageDialog(null, "Você não tem saldo suficiente!");
						}
					}else if(actions.getSelectedIndex() == 2) {
						tmp = JOptionPane.showInputDialog(null, "Digite o valor para depositar:");
						if(!stub.deposit(client.getId(), Double.parseDouble(tmp))) {
							JOptionPane.showMessageDialog(null, "Ocorreu um erro ao efetuar deposito.");
						}
					}else if(actions.getSelectedIndex() == 3) {
						JFrame transFrame = new JFrame("Transferência");
						JPanel transPanel = new JPanel(new GridLayout(3,2));
						JButton execTransf = new JButton("Transferir");
						JTextField agencyTrans = new JTextField();
						JTextField accountTrans = new JTextField();
						JTextField valueTrans = new JTextField();
						
						execTransf.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								try {
									if(stub.transfer(client.getId(), Integer.parseInt(agencyTrans.getText()), Integer.parseInt(accountTrans.getText()), Double.parseDouble(valueTrans.getText()))) {
										JOptionPane.showMessageDialog(null, "Transferência realizada com sucesso!");
										transFrame.dispose();
									}else {
										JOptionPane.showMessageDialog(null, "Ocorreu um erro ao efetuar a transferência");
									}
								} catch (NumberFormatException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								} catch (RemoteException e1) {
									JOptionPane.showMessageDialog(null, "Falha na conexão com o servidor");
									e1.printStackTrace();
								}
							}
							
						});
						
						execTransf.requestFocus();
						
						transPanel.add(new JLabel("Agência:"));
						transPanel.add(agencyTrans);
							
						transPanel.add(new JLabel("Conta:"));
						transPanel.add(accountTrans);
						
						transPanel.add(new JLabel("Valor:"));
						transPanel.add(valueTrans);		
						
						transPanel.setBorder(new EmptyBorder(10,10,10,10));
						
						transFrame.add(transPanel);
						transFrame.add(execTransf, BorderLayout.SOUTH);
						transFrame.getRootPane().setDefaultButton(execTransf);					
						transFrame.setSize(200,150);
						transFrame.setVisible(true);
						
						
						transFrame.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE);
					}else if(actions.getSelectedIndex() == 4) {
						JOptionPane.showMessageDialog(null, "R$:"+stub.balance(client.getId()));
					}
				} catch (NumberFormatException nfex) {
					JOptionPane.showMessageDialog(null, "Valor Inválido");
				}catch (RemoteException rex) {
					JOptionPane.showMessageDialog(null, "Falha na conexão com o servidor");
				}				//put the code here
			}
		});
		
		frame.setLayout(new GridLayout(1,2));
		frame.add(actions);

		
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		frame.setSize(400,100);
		frame.setVisible(true);
	}
	
	

}
