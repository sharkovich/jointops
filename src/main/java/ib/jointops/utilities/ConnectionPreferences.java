package ib.jointops.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

public class ConnectionPreferences {
	
	private String username;
	private String password;
	private String host;
	private String database;
	private String hibernateCfgHost;
	private String jdbcCfgFull;
	
	public ConnectionPreferences(String host, String database, String username, String password) {
		this.host = host;
		this.database = database;
		this.username = username;
		this.password = password;
		this.hibernateCfgHost = "jdbc:mysql://" + this.host + "/" + database + "??useUnicode=true&characterEncoding=UTF-8";
		this.jdbcCfgFull = "jdbc:mysql://" + this.host + "/" + database + "?user=" + this.username + "&password=" + this.password;
	}
	public String getUrl() {
		return this.hibernateCfgHost;
	}
	public String getUsername() {
		return this.username;
	}
	public String getHost() {
		return this.host;
	}
	public String getDatabase() {
		return this.database;
	}
	public String getJDBCConInfo() {
		return this.jdbcCfgFull;
	}
	
	public void saveProps() {
		Properties props = new Properties();
		props.setProperty("hibernate.connection.username", this.username);
		props.setProperty("hibernate.connection.url", this.hibernateCfgHost);
		
		StandardPBEStringEncryptor passwordEncryptor = new StandardPBEStringEncryptor();
		passwordEncryptor.setAlgorithm("PBEWITHMD5ANDDES");
		passwordEncryptor.setPassword("blahblah");
		passwordEncryptor.encrypt(password);
		
		String encryptedPass = passwordEncryptor.encrypt(password);
		//TODO change this
		//props.setProperty("hibernate.connection.password", "ENC(" + encryptedPass + ")");
		props.setProperty("hibernate.connection.password", encryptedPass);
		
		File f = new File("hibernate.properties");
		try {
			OutputStream out = new FileOutputStream(f);
			props.store(out, "Temporary properties file");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void saveHistory() {
		File f = new File("jointops.properties");
		Properties props = new Properties();
		try {
			if (f.exists()) {
				InputStream in = new FileInputStream(f);
				props.load(in);
				in.close();
			}
			
			props.setProperty("last.host", this.host);
			props.setProperty("last.db", this.database);
			props.setProperty("last.user", this.username);
			
			OutputStream out = new FileOutputStream(f);
			props.store(out, "Last connection settings");
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public int validate() {
		if (database.equals("") || host.equals("") || username.equals(""))
			return -1;
		else if (password.equals("")) 
			return 0;
		else return 1;
			
	}

}
