/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmibank.code.service;

/**
 *
 * @author a120111
 */
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;

public interface Bank extends Remote{
	public int logIn(String agnum, String accnum, String pass) throws RemoteException, SQLException;
    public double balance(int id) throws RemoteException, SQLException;
    public boolean widthdraw(int id, double value) throws RemoteException, SQLException;
}