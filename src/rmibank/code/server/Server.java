/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmibank.code.server;

import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;

import rmibank.code.service.BankImpl;
import rmibank.code.service.LoginImpl;

/**
 *
 * @author a120111
 */
public class Server{
	BankImpl remObjectBank[];
	LoginImpl remObjectLogin[];
	
    public Server(int port){
    	
    	try{
        	remObjectBank = new BankImpl[2];
	    	remObjectLogin = new LoginImpl[2];
	    	
	        Registry registry = LocateRegistry.createRegistry(port);

	        for(int i = 0; i < remObjectBank.length; i++) {
	        	remObjectLogin[i] = new LoginImpl(i+1);
	        	remObjectBank[i] = new BankImpl(i+1);
		        registry.bind("RMI Login Service "+(i+1), remObjectLogin[i]);
	        	registry.bind("RMI Bank Service "+(i+1), remObjectBank[i]);
	        }

	        System.out.println("[!] Serviços em execução");
	        for(String name : registry.list()) {
	        	System.out.println("[!] "+name);
	        }
		} catch (RemoteException e) {
			System.out.println("[!] Impossível iniciar serviço(s): A porta já está em uso!");
			System.exit(0);
		}catch(SQLException e) {
			System.out.println("[!] Impossível iniciar serviço(s): Falha na conexão com o BD");
			System.exit(0);
		}catch( AlreadyBoundException e) {
			System.out.println("[!] Impossível iniciar serviço(s): Este serviço já está sendo executado!");
			System.exit(0);
		}
    }
}
