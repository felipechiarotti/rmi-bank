package rmibank.code.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.postgresql.jdbc.PgStatement;

import rmibank.code.database.Database;

public class Transaction {
	private Connection conn;
	private int id;
	private int clientId;
	private int transactionType;
	private int transactionOperation;
	private double value;
	
	public Transaction(int clientId, int transactionOperation, double value, Connection conn) {
		this.conn = conn;
		this.setId();
		this.clientId = clientId;
		this.setTransactionData(transactionOperation);
		this.value = value;
		
		
	}
	
	public Transaction(int clientId, int transactionOperation, int transactionType, double value, Connection conn) {
		this.conn = conn;
		this.setId();
		this.clientId = clientId;
		this.transactionOperation = transactionOperation;
		this.transactionType = transactionType;
		this.value = value;
	}
	
	private void setTransactionData(int transactionOperation) {
		this.transactionOperation = transactionOperation;
		
		switch(transactionOperation) {
			case 1:
				this.transactionType = -1;
				
			break;
			
			case 2:
					this.transactionType = 1;
			break;
		}
		
	}
	
	private void setId() {
		try{
			PgStatement stmt = (PgStatement) this.conn.createStatement();
			String sql = "SELECT id FROM transactions ORDER BY id DESC LIMIT 1";
			ResultSet result = stmt.executeQuery(sql);
			result.next();
			this.id = result.getInt(1)+1;
		}catch(SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	public boolean executeTransaction() throws SQLException {
		PgStatement statement = (PgStatement) this.conn.createStatement();
		String sql = "SELECT balance FROM client WHERE id = "+this.clientId;
		String logSql = "INSERT INTO transactions VALUES ("+ (this.id) +","+this.clientId+","+this.transactionType+","+this.transactionOperation+","+this.value+")";
		
		ResultSet result = statement.executeQuery(sql);
		result.next();
		
    	switch(this.transactionType) {
    		case -1:
    			double newValue = result.getLong(1) - this.value;
    			if(newValue > 0) {
    				sql = "UPDATE client SET balance = balance - "+this.value+" WHERE id = "+this.clientId;
    			}
    		break;
    		
    		case 1:
    			sql = "UPDATE client SET balance = balance + "+this.value+" WHERE id = "+this.clientId;
    		break;
    	}
    	statement.execute(logSql);
    	return statement.executeUpdate(sql) == 1;
	}
}