package ib.jointops.swt.windows;

import ib.jointops.hibernate.utils.InitConnection;
import ib.jointops.hibernate.utils.TestConnection;
import ib.jointops.utilities.ConnectionPreferences;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

public class SplashScreen extends Dialog {

	protected Shell shell;
	private static ProgressBar progressBar = null;
	private Text txtPwd;
	public enum Status {CONNECTED, COULD_NOT_CONNECT, INVALID_USER, UNKNOWN, CLOSE, IDLE};
	private Status status = Status.IDLE;
	InitConnection initializer = new InitConnection(shell);
	
	

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public SplashScreen(Shell parent, int style) {
		super(parent, SWT.SHELL_TRIM);
		setText("Splash Screen");
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Status open() {
		createContents();
		status = Status.IDLE;
				
		Display display = Display.getDefault();
		Monitor primary = display.getPrimaryMonitor();
		Rectangle bounds = primary.getBounds();
		Rectangle rect = shell.getBounds();
		int x = bounds.x + (bounds.width - rect.width)/2;
		int y = bounds.y + (bounds.height - rect.height)/2;
		shell.setLocation(x, y);
		
		if (progressBar!=null) 
			progressBar.setVisible(false);
		
		shell.open();
		shell.layout();
		
		while (!display.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
			
			if (status == Status.IDLE) {}
			else if (status == Status.CONNECTED) {
				initializer.start();
				break;
			}
			else if (status == Status.COULD_NOT_CONNECT) {
				MessageDialog.openError(shell, "Connection error!",
						"Could not connect to database.\n" +
						"Please check your connection settings and try again.");
				progressBar.setVisible(false);
				status = Status.IDLE;
			}
			else if (status == Status.INVALID_USER) {
				MessageDialog.openError(shell, "Connection error!",
						"Wrong username/password.\n" +
						"Please check your settings and try again.");
				progressBar.setVisible(false);
				status = Status.IDLE;
			} else if (status == Status.UNKNOWN) {
				MessageDialog.openError(shell, "Connection error!",
						"Unknown connection error.\n" +
						"Please check your settings and try again.");
				progressBar.setVisible(false);
				status = Status.IDLE;				
			}
		}
		return status;
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shell = new Shell(getParent(), SWT.CLOSE );
		shell.setSize(450, 319);
		shell.setText(getText());
		
		GridLayout gl_shell = new GridLayout(1, false);
		gl_shell.horizontalSpacing = 10;
		shell.setLayout(gl_shell);
		
		Composite composite = new Composite(shell, SWT.NONE);
		composite.setLayout(new GridLayout(2, false));
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		
		Label lblTasker = new Label(composite, SWT.NONE);
		GridData gd_lblTasker = new GridData(SWT.LEFT, SWT.BOTTOM, false, false, 1, 1);
		gd_lblTasker.widthHint = 82;
		lblTasker.setLayoutData(gd_lblTasker);
		lblTasker.setFont(SWTResourceManager.getFont("Sans", 16, SWT.BOLD));
		lblTasker.setText("Tasker");
		
		Label label_1 = new Label(composite, SWT.NONE);
		label_1.setLayoutData(new GridData(SWT.LEFT, SWT.BOTTOM, false, false, 1, 1));
		label_1.setText("0.0.1");
		
		Label label = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Composite composite_1 = new Composite(shell, SWT.NONE);
		GridLayout gl_composite_1 = new GridLayout(2, false);
		gl_composite_1.horizontalSpacing = 20;
		composite_1.setLayout(gl_composite_1);
		composite_1.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		Label lblConnectionAddress = new Label(composite_1, SWT.NONE);
		lblConnectionAddress.setText("Database address:");
		
		ControlDecoration controlDecoration = new ControlDecoration(lblConnectionAddress, SWT.RIGHT | SWT.TOP);
		controlDecoration.setDescriptionText("Please specify addres of database.\n\teg. \"localhost:3306\"");
		
		ComboViewer comboViewer = new ComboViewer(composite_1, SWT.NONE);
		final Combo comboAddr = comboViewer.getCombo();
		comboAddr.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblDatabaseName = new Label(composite_1, SWT.NONE);
		lblDatabaseName.setText("Database name:");
		
		ControlDecoration controlDecoration_1 = new ControlDecoration(lblDatabaseName, SWT.RIGHT | SWT.TOP);
		controlDecoration_1.setDescriptionText("Please specify name of database.\n\teg. tasker_db");
		
		ComboViewer comboViewer_1 = new ComboViewer(composite_1, SWT.NONE);
		final Combo comboName = comboViewer_1.getCombo();
		comboName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblUsername = new Label(composite_1, SWT.NONE);
		lblUsername.setText("Username:");
		
		ControlDecoration controlDecoration_2 = new ControlDecoration(lblUsername, SWT.RIGHT | SWT.TOP);
		controlDecoration_2.setDescriptionText("Please specify username and password.");
		
		ComboViewer comboViewer_2 = new ComboViewer(composite_1, SWT.NONE);
		final Combo comboUname = comboViewer_2.getCombo();
		comboUname.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblPassword = new Label(composite_1, SWT.NONE);
		lblPassword.setText("Password:");
		
		txtPwd = new Text(composite_1, SWT.BORDER | SWT.PASSWORD);
		txtPwd.setText("zaq1@WSX");
		txtPwd.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Composite buttonHolder = new Composite(composite_1, SWT.NONE);
		buttonHolder.setLayout(new GridLayout(2, false));
		buttonHolder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		
		Button btnClose = new Button(buttonHolder, SWT.NONE);
		btnClose.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				shell.close();
				status = Status.CLOSE;
			}
		});
		GridData gd_btnClose = new GridData(SWT.CENTER, SWT.CENTER, true, true, 1, 1);
		gd_btnClose.widthHint = 80;
		btnClose.setLayoutData(gd_btnClose);
		btnClose.setText("Close");
		
		Button btnConnect = new Button(buttonHolder, SWT.NONE);
		btnConnect.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				
				
				ConnectionPreferences prefs = new ConnectionPreferences(comboAddr.getText(), comboName.getText(), comboUname.getText(), txtPwd.getText());
				int validationResult = prefs.validate();
				switch (validationResult) {
				case -1:
					MessageDialog.openError(shell, "Invalid data", "Please input connection data!");
					break;
				case 0:
					MessageDialog.openError(shell, "No password", "Please imput password!");
					break;
				case 1:
					progressBar.setVisible(true);
					prefs.saveProps();
					ExecutorService pool = 	Executors.newFixedThreadPool(1);
					Future<Status> value = pool.submit(new TestConnection(prefs));
		
					try {
						status = value.get();
						if (status == Status.CONNECTED) {
							prefs.saveHistory();
						}
					} catch (InterruptedException | ExecutionException e) {
						e.printStackTrace();
					} 	
					break;
				default:
					MessageDialog.openError(shell, "Invalid data", "Please input connection data!");
					break;
				}
				
			}
		});
		GridData gd_btnConnect = new GridData(SWT.CENTER, SWT.CENTER, true, true, 1, 1);
		gd_btnConnect.widthHint = 80;
		btnConnect.setLayoutData(gd_btnConnect);
		btnConnect.setText("Connect");
		
		progressBar = new ProgressBar(shell, SWT.SMOOTH | SWT.INDETERMINATE);
		GridData gd_progressBar_1 = new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1);
		gd_progressBar_1.heightHint = 5;
		progressBar.setLayoutData(gd_progressBar_1);
		progressBar.setEnabled(true);
		
		Properties history = new Properties();

		try {
			File file = new File("jointops.properties");
			if (file.exists()) {
				history.load(new FileInputStream(file));
				comboAddr.add(history.getProperty("last.host"));
				comboAddr.setText(comboAddr.getItem(0));
				comboName.add(history.getProperty("last.db"));
				comboName.setText(comboName.getItem(0));
				comboUname.add(history.getProperty("last.user"));
				comboUname.setText(comboUname.getItem(0));
			} else {
				comboAddr.setText("localhost");
				comboName.setText("tasker");
				comboUname.setText("tasker");
				String def = "No history";
				comboAddr.add(def);
				comboName.add(def);
				comboUname.add(def);
			}
		} catch (IOException e) {}	
	}
	public void destroyWindow() {
		shell.close();
		shell.dispose();
	}
}


