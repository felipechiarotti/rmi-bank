/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmibank;

import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import rmibank.code.server.Server;
import rmibank.code.service.BankImpl;
import rmibank.code.service.LoginImpl;

/**
 *
 * @author a120111
 */
public class RunServer {
    public static void main(String[] args){
            try {
				new Server(5000,5001);
			} catch (RemoteException | AlreadyBoundException | SQLException e) {
				e.printStackTrace();
			}
    }
}
