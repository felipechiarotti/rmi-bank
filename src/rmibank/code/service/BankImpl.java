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
	
    public BankImpl() throws RemoteException, SQLException{
        super();
        conn = Database.getConnection();
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
    		throw new RemoteException();
    	}
    }
    
    public boolean widthdraw(int id, double value) throws RemoteException{
		try {
    	
	    	ResultSet result;
	    	String sql = "SELECT balance FROM client WHERE id = "+id;
	    	result = statement.executeQuery(sql);
	    	result.next();
			double newValue = result.getLong(1) - value;
			if(newValue > 0) {
				sql = "UPDATE client SET balance = balance - "+value+" WHERE id = "+id;
				statement.executeUpdate(sql);
				return true;
			}
	    	return false;
		}catch(SQLException ex) {
    		System.out.println("[!] Banco de dados indisponível");
    		throw new RemoteException();	
		}
    }
}
