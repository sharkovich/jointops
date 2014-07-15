package ib.jointops.hibernate.utils;

import ib.jointops.swt.windows.SplashScreen;
import ib.jointops.swt.windows.SplashScreen.Status;
import ib.jointops.utilities.ConnectionPreferences;

import java.util.concurrent.Callable;

public class TestConnection implements Callable<SplashScreen.Status> {
	
	protected Status status = Status.IDLE;
	protected String connectionString;
	
	public TestConnection() {}
	public TestConnection(ConnectionPreferences cpref) {
		this.connectionString = cpref.getJDBCConInfo();
	}
	
	@Override
	public Status call() throws Exception {
		try {
			java.sql.Connection connectionTest = java.sql.DriverManager.getConnection(connectionString);
			if(connectionTest.isValid(5))
				status = Status.CONNECTED;
			else
				status = Status.COULD_NOT_CONNECT;
			connectionTest.close();
		} catch (java.sql.SQLTimeoutException e) {
			status = Status.COULD_NOT_CONNECT;
		} catch (com.mysql.jdbc.exceptions.jdbc4.MySQLInvalidAuthorizationSpecException e) {
			status = Status.INVALID_USER;
		} catch (com.mysql.jdbc.exceptions.jdbc4.CommunicationsException e) {
			status = Status.COULD_NOT_CONNECT;		
		} catch (java.sql.SQLException e) {
			System.out.println("SQL ERROR: " + e.getErrorCode());
			e.printStackTrace();
			status = Status.UNKNOWN;
		} 
		return status;
	}
}


