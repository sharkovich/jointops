package ib.jointops.main;

import java.io.File;

import ib.jointops.swt.windows.MainWindow;
import ib.jointops.swt.windows.SplashScreen;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Throwable {
		Shell shell = new Shell(Display.getDefault());
		SplashScreen ss = new SplashScreen(shell, SWT.CENTER);
		MainWindow window = new MainWindow();
		
		try {
			SplashScreen.Status status = ss.open();
			switch (status) {
			case CONNECTED:
				ss.destroyWindow();
				window.open();
				break;
			case UNKNOWN:
				System.out.println("UNKNOWN ERROR");
				break;
			case COULD_NOT_CONNECT:
				System.out.print("COULD NOT CONNECT");
				break;
			default:
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		new File("hibernate.properties").delete();
		System.exit(0);
	}


}
