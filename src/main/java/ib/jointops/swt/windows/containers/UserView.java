package ib.jointops.swt.windows.containers;

import ib.jointops.hibernate.db.DbQueries;
import ib.jointops.hibernate.domain.Users;
import ib.jointops.hibernate.utils.HibernateUtil;

import java.util.Iterator;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Text;
import org.hibernate.Session;

public class UserView extends Composite {

	private static Users selectedUser = new Users();
	private Text text;
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public UserView(Composite parent, int style) {
		super(parent, SWT.NONE);

		GridLayout gridLayout = new GridLayout(2, false);
		gridLayout.marginTop = 8;
		setLayout(gridLayout);
		
		final ListViewer listViewer = new ListViewer(this, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		listViewer.setLabelProvider(new LabelProvider() {
			public Image getImage(Object element) {
				return null;
			}
			public String getText(Object element) {
				return ((Users)element).getName();
			}
		});

		addFromDbToListView(listViewer);
		
		List list = listViewer.getList();
		GridData gd_list = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_list.minimumWidth = 30;
		gd_list.minimumHeight = 20;
		gd_list.heightHint = 310;
		gd_list.widthHint = 191;
		list.setLayoutData(gd_list);
		

		
		Composite composite = new Composite(this, SWT.BORDER);
		GridLayout gl_composite = new GridLayout(2, false);
		gl_composite.marginLeft = 10;
		gl_composite.marginTop = 10;
		gl_composite.verticalSpacing = 10;
		composite.setLayout(gl_composite);
		GridData gd_composite = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_composite.minimumWidth = 300;
		gd_composite.minimumHeight = 100;
		gd_composite.widthHint = 284;
		gd_composite.heightHint = 494;
		composite.setLayoutData(gd_composite);
		
		Label lblUsername = new Label(composite, SWT.NONE);
		lblUsername.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		lblUsername.setText("User name:");
		
		text = new Text(composite, SWT.BORDER);
		GridData gd_text = new GridData(SWT.CENTER, SWT.TOP, true, false, 1, 1);
		gd_text.widthHint = 135;
		text.setLayoutData(gd_text);
				new Label(composite, SWT.NONE);
		
				GridData gd_btnModify = new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1);
				gd_btnModify.widthHint = 100;
				Button btnModify = new Button(composite, SWT.NONE);
				btnModify.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent arg0) {
						
						if (selectedUser.getId() == null) {
							MessageDialog.openError(getShell(), "Error", "Please select user to modify!");
						} else {
							if (!text.getText().equals(""))
							{
								System.out.println(text.getText());
								Session session = HibernateUtil.getSessionFactory().getCurrentSession();
								session.beginTransaction();			
								selectedUser.setName(text.getText());
								session.update(selectedUser);			
								session.getTransaction().commit();		
								
								listViewer.update(selectedUser, null);
							} else {
								MessageDialog.openError(getShell(), "Error", "Please input user name!");
							}
						} 
					}
				});
				btnModify.setText("Modify");
				btnModify.setLayoutData(gd_btnModify);
		
		GridData gd_btnDelete = new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1);
		gd_btnDelete.widthHint = 100;
		Button btnDelete = new Button(composite, SWT.NONE);
		btnDelete.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if (selectedUser.getId() == null) {
					MessageDialog.openError(getShell(), "Error", "Please select user to delete!");
				} else {
					boolean result = MessageDialog.openConfirm(getShell(), "Are you sure?", "Delete user " + selectedUser.getName()+ "?");
					if (result) {
						Session session = HibernateUtil.getSessionFactory().getCurrentSession();
						session.beginTransaction();
						session.delete(selectedUser);
						session.getTransaction().commit();
						
						listViewer.remove(selectedUser);
						selectedUser = null;
					}
				}
			}
		});
		btnDelete.setText("Delete");
		btnDelete.setLayoutData(gd_btnDelete);
		
		GridData gd_btnAddNew = new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1);
		gd_btnAddNew.widthHint = 100;
		Button btnAddNew = new Button(composite, SWT.NONE);
		btnAddNew.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if (text.getText().equals("")) {
					MessageDialog.openError(getShell(), "Error", "Please input user name!");
				} else {
					Session session = HibernateUtil.getSessionFactory().getCurrentSession();
					session.beginTransaction();
					Users newUser = new Users();
					newUser.setName(text.getText());
					session.save(newUser);
					session.getTransaction().commit();
					
					listViewer.add(newUser);
				}
			}
		});
		btnAddNew.setText("Add New");
		btnAddNew.setLayoutData(gd_btnAddNew);
		
		listViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			
			@Override
			public void selectionChanged(SelectionChangedEvent arg0) {
				IStructuredSelection selection = (IStructuredSelection)arg0.getSelection();

				try {
					selectedUser =  (Users) selection.getFirstElement();
					System.out.println("Selected user: \n\tname:" + selectedUser.getName() + "\n\tid: " + selectedUser.getId());
					text.setText(selectedUser.getName());
				} catch (NullPointerException e) {
					text.setText("");
				}
			}
		});

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
	
	@SuppressWarnings("rawtypes")
	protected void addFromDbToListView(ListViewer lV) {
		
		java.util.List data = DbQueries.listUsers();
		
		Iterator it = data.iterator();
		while (it.hasNext()) {
			Users anUser = (Users) it.next();
			lV.add(anUser);
		}

	}
	

}
