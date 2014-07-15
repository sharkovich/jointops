package ib.jointops.swt.windows;

import ib.jointops.hibernate.utils.HibernateUtil;
import ib.jointops.swt.windows.containers.TaskView;
import ib.jointops.swt.windows.containers.UserView;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

public class MainWindow {

	/**
	 * Launch the application.
	 * @param args
	 * @throws Throwable 
	 */

	final Shell shlTasker = new Shell();
	/**
	 * Open the window.
	 * @wbp.parser.entryPoint
	 */
	public void open() {
		
		
		Display display = Display.getDefault();


		shlTasker.setSize(653, 422);
		shlTasker.setText("Tasker");
		shlTasker.setLayout(new GridLayout(1, false));
		
		Menu menu = new Menu(shlTasker, SWT.BAR);
		shlTasker.setMenuBar(menu);
		
		MenuItem mntmFile = new MenuItem(menu, SWT.CASCADE);
		mntmFile.setText("File");
		
		Menu menu_1 = new Menu(mntmFile);
		mntmFile.setMenu(menu_1);
		
		MenuItem mntmExit = new MenuItem(menu_1, SWT.NONE);
		mntmExit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				shlTasker.dispose();
			}
		});
		mntmExit.setText("Exit");
		
		MenuItem mntmHelp = new MenuItem(menu, SWT.CASCADE);
		mntmHelp.setText("Help");
		
		Menu menuHelp = new Menu(mntmHelp);
		mntmHelp.setMenu(menuHelp);
		
		MenuItem mntmAbout = new MenuItem(menuHelp, SWT.NONE);
		mntmAbout.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				AboutWindow about = new AboutWindow(shlTasker, SWT.FLAT);
				about.open();
			}
		});
		mntmAbout.setText("About");
		
		final TabFolder tabFolder = new TabFolder(shlTasker, SWT.NONE);
		tabFolder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
				
				final TabItem tbtmNeItem = new TabItem(tabFolder, SWT.NONE);
				tbtmNeItem.setText("Tasks");
				
				Composite tasksComposite = new Composite(tabFolder, SWT.NONE);
				tbtmNeItem.setControl(tasksComposite);
				tasksComposite.setLayout(new FillLayout(SWT.HORIZONTAL));
				
				final TabItem tbtmNewItem = new TabItem(tabFolder, SWT.NONE);
				tbtmNewItem.setText("Manage users");
				

				
				TaskView tv_1 = new TaskView(tabFolder, SWT.NONE);
				tbtmNeItem.setControl(tv_1);
				
				tabFolder.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent arg0) {
						int choice = tabFolder.getSelectionIndex();
						org.eclipse.swt.widgets.Control[] children = tabFolder.getChildren();
						for (int i = 0; i < children.length; i++) {
							children[i].dispose();
						}
						switch (choice) {
						case 0:
							TaskView tv = new TaskView(tabFolder, SWT.NONE);
							tbtmNeItem.setControl(tv);
							break;
						case 1:			
							UserView uv = new UserView(tabFolder, SWT.NONE);
							tbtmNewItem.setControl(uv);
							break;

						default:
							break;
						}
						
					}
				});
		

		shlTasker.open();
		shlTasker.layout();
		while (!shlTasker.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		if (HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().isActive()) 
			HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().rollback();
		HibernateUtil.closeSessionFactory();
		System.out.println("SessionFactory closed");
	}
	public void destroyWindow() {
		shlTasker.close();
		shlTasker.dispose();
	}

}
