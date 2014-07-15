package ib.jointops.hibernate.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.hibernate4.encryptor.HibernatePBEEncryptorRegistry;

public class HibernateUtil {
	
	private static final SessionFactory sessionFactory;
	private static StandardServiceRegistry serviceRegistry;
	
	private static Configuration configuration = new Configuration();
	
	static { 
		try {	
			Properties hibernateProperties = new Properties();
			//hibernateProperties.load(HibernateUtil.class.getResourceAsStream("/hibernate.properties"));
			File f = new File("hibernate.properties");
			InputStream in = new FileInputStream(f);
			
			hibernateProperties.load(in);
			
			StandardPBEStringEncryptor strongEncryptor = new StandardPBEStringEncryptor();
			strongEncryptor.setAlgorithm("PBEWITHMD5ANDDES");
			strongEncryptor.setPassword("blahblah");
			HibernatePBEEncryptorRegistry registry = HibernatePBEEncryptorRegistry.getInstance();
			registry.registerPBEStringEncryptor("configurationHibernateEncryptor", strongEncryptor); 
			
			
			configuration.configure();
			configuration.addProperties(hibernateProperties);
			
			// TODO: change this shit when patched	
			configuration.setProperty("hibernate.connection.password", strongEncryptor.decrypt(configuration.getProperty("hibernate.connection.password")));
			
			
			serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
			sessionFactory =  configuration.buildSessionFactory(serviceRegistry);
			
		} catch (Throwable ex) {
			System.err.println("Initial SessionFactory creation failed.\n" + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}
		
	
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	public static void closeSessionFactory() {
		if (sessionFactory != null)
			sessionFactory.close();
	}

}
