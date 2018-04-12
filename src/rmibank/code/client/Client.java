/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmibank.code.client;

import java.io.Serializable;
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
public class Client implements Serializable{
	private int id;
	private String name;
	private int agnum;
	private int accnum;
	private double balance;
	
	public Client(int id, String name, int agnum, int accnum, double balance) {
		this.id = id;
		this.name = name;
		this.agnum = agnum;
		this.accnum = accnum;
		this.balance = balance;
	}
	
	public Client() {
		
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAgnum() {
		return agnum;
	}
	public void setAgnum(int agnum) {
		this.agnum = agnum;
	}
	public int getAccnum() {
		return accnum;
	}
	public void setAccnum(int accnum) {
		this.accnum = accnum;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}

    
}
