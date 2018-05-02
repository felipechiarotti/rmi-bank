/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmibank.code.service;

import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.postgresql.jdbc.PgStatement;

import rmibank.code.database.Database;
import rmibank.code.service.Bank;

/**
 *
 * @author a120111
 */
public class BankImpl extends UnicastRemoteObject implements Bank {
	Connection conn;
	PgStatement statement;
	
    public BankImpl(int agNumber) throws RemoteException, SQLException{
        super();
        conn = new Database(agNumber).getConn();
        statement = (PgStatement) conn.createStatement();
    }


	
	
    public double balance(int id) throws RemoteException{
    	try {
			ResultSet result;
	    	String sql = "SELECT balance FROM client WHERE id = "+id;
			result = statement.executeQuery(sql);
			result.next();
	    	return result.getLong(1);
    	}catch(SQLException ex) {
    		System.out.println("[!] Banco de dados indisponível");
    	}
    	return -1.0;
    }
    
    public boolean widthdraw(int id, double value) throws RemoteException{
		try {
			Transaction t = new Transaction(id, 1, value, conn);
			return t.executeTransaction();
		}catch(SQLException ex) {
    		System.out.println("[!] Banco de dados indisponível");
		}
		return false;
    }
    
    public boolean deposit(int id, double value) throws RemoteException{
    	Transaction t = new Transaction(id, 2, value, conn);
    	try{
    		return t.executeTransaction();
    	}catch(SQLException ex) {
    		System.out.println("[!] Banco de dados indisponível");
    	}
    	return false;
    }
    
    public boolean transfer(int fromId, int destAg, int destAcc, double value) throws RemoteException {
    	boolean t1 = false, t2 = false;
    	ResultSet result;
    	String sql = "SELECT id FROM client WHERE agency_number = "+destAg+" and account_number = "+destAcc;
    	try {
	    	result = statement.executeQuery(sql);
	    	if(result.next()) {
	    		int destId = result.getInt(1);

	        	Transaction debitTransaction = new Transaction(fromId, 3, -1, value, conn);
	        	Transaction creditTransaction = new Transaction(destId, 3, 1, value, conn);
	        	t1 = debitTransaction.executeTransaction();
		    	t2 = creditTransaction.executeTransaction();
	    	}
    	}catch(SQLException ex) {
    		System.out.println("[!] Banco de dados indisponível");
    	}
    	return t1 && t2;
    }
}
