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
public class Server<T>{
	BankImpl remObjectBank;
	LoginImpl remObjectLogin;
	
    public Server(int portLogIn, int portBank) throws RemoteException, AlreadyBoundException, SQLException{
    	remObjectBank = new BankImpl();
    	remObjectLogin = new LoginImpl();
    	
        Registry registry = LocateRegistry.createRegistry(portLogIn);
        registry.bind("RMI Login Service", remObjectLogin);
        
        registry = LocateRegistry.createRegistry(portBank);
        registry.bind("RMI Bank Service", remObjectBank);
    }
}
