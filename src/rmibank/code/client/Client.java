/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmibank.code.client;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import rmibank.code.service.Bank;

/**
 *
 * @author a120111
 */
public class Client {
    private Bank stub;
    private int id;

    public Client(String ip, int port, String serviceName) throws RemoteException, NotBoundException{
            Registry registry = LocateRegistry.getRegistry(ip, port);
            stub = (Bank) registry.lookup(serviceName);
    }
    
    public Bank getStub() {
    	return this.stub;
    }
    
    public void setID(int id) {
    	this.id = id;
    }
    
    public int getID() {
    	return this.id;
    }
}
