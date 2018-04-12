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

/**
 *
 * @author a120111
 */
public class Server{
	BankImpl remObject;
	
	
    public Server(int port) throws RemoteException, AlreadyBoundException, SQLException{
        remObject = new BankImpl();
        Registry registry = LocateRegistry.createRegistry(port);
        registry.bind("RMI Bank Service", remObject);
        System.out.println("[!] Seridor RMI aberto na porta "+port);
    }
}
