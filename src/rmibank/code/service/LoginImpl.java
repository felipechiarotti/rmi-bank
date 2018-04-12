package rmibank.code.service;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.postgresql.jdbc.PgStatement;

import rmibank.code.client.Client;
import rmibank.code.database.Database;

public class LoginImpl extends UnicastRemoteObject implements Login {
	Connection conn;
	PgStatement statement;
	
	public LoginImpl() throws RemoteException, SQLException {
		super();
        conn = Database.getConnection();
        statement = (PgStatement) conn.createStatement();
	}

	@Override
	public Client logIn(String agnum, String accnum, String pass) throws RemoteException {
		try{
			String sql = "SELECT id,name,agency_number,account_number,balance FROM client WHERE agency_number="+Integer.parseInt(agnum)+" AND account_number = "+Integer.parseInt(accnum)+" AND password = '"+pass+"'";	
			ResultSet result = statement.executeQuery(sql);
			if(result.next()) {
				return new Client(result.getInt(1), result.getString(2), result.getInt(3), result.getInt(4), result.getInt(5));
			}
			return null;
		}catch(SQLException sqlex){
			System.out.println("[ERRO] Banco de Dados Indisponível");
			throw new RemoteException();
		}
	}
}
