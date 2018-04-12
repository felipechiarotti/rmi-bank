package rmibank.code.service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;

import rmibank.code.client.Client;

public interface Login extends Remote{
	public Client logIn(String agnum, String accnum, String pass) throws RemoteException, SQLException;
}
