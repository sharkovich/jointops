package ib.jointops.hibernate.utils;

import org.eclipse.swt.widgets.Shell;


public class InitConnection extends Thread {
	private Shell parent;
	private boolean isRunning = false;
	
	public InitConnection(Shell parent) {
		this.parent = parent;
	}
	
	public void run() {
		isRunning = true;
		HibernateUtil.getSessionFactory();
		isRunning = false;
	}
	public boolean isRunning() {
		return this.isRunning;
	}
}
