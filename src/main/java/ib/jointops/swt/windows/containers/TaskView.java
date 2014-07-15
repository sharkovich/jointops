package ib.jointops.swt.windows.containers;

import ib.jointops.hibernate.db.DbQueries;
import ib.jointops.hibernate.domain.Categories;
import ib.jointops.hibernate.domain.Task;
import ib.jointops.hibernate.domain.Users;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.eclipse.jface.viewers.DecoratingLabelProvider;
import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Tree;

public class TaskView extends Composite {
	private final TreeViewer treeViewer = new TreeViewer(this, SWT.BORDER);
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public TaskView(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(1, false));
		
		ToolBar toolBar = new ToolBar(this, SWT.FLAT | SWT.RIGHT);
		toolBar.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		
		ToolItem tltmNewTask = new ToolItem(toolBar, SWT.NONE);
		tltmNewTask.setText("Add new task");
		
		treeViewer.setContentProvider(new TaskTreeContentProvider());
		treeViewer.setLabelProvider(new TaskTreeLabelProvider());
		treeViewer.setInput("aa");	
		

		Tree tree = treeViewer.getTree();
		tree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
	protected void updateView() {
		List categories = DbQueries.listCategories();
	}

}
class TaskTreeContentProvider implements ITreeContentProvider {

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void inputChanged(Viewer arg0, Object arg1, Object arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object[] getChildren(Object arg0) {
		// TODO Auto-generated method stub
		if (arg0 instanceof Task) {
			System.out.println(((Task)arg0).getDescription());
			return ((Task)arg0).getWho().toArray();
		}
		else 
			return ((Categories)arg0).getTasks().toArray();
	}

	@Override
	public Object[] getElements(Object arg0) {
		// TODO Auto-generated method stub
		return DbQueries.listCategories().toArray();
	}

	@Override
	public Object getParent(Object arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasChildren(Object arg0) {
		// TODO Auto-generated method stub
		return true;
	}
	
}
class TaskTreeLabelProvider implements ILabelProvider {
	
	private List listeners;
	private Image categoryImg;
	private Image taskImg;
	
	public TaskTreeLabelProvider() {
		listeners = new ArrayList();
	}
	

	@Override
	public void addListener(ILabelProviderListener arg0) {
		listeners.add(arg0);
		
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		if (categoryImg!=null) categoryImg.dispose();
		if (taskImg!=null) taskImg.dispose();
		
	}

	@Override
	public boolean isLabelProperty(Object arg0, String arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeListener(ILabelProviderListener arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Image getImage(Object element) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getText(Object element) {
		if (element instanceof Categories) 
			return ((Categories)element).getCategoryName();
		if (element instanceof Task)
			return ((Task)element).getDescription().toString();
		if (element instanceof Users)
			return ((Users)element).getName();
		if (element instanceof HashSet<?>) 
			return ((HashSet<?>)element).toString();
		else return "ZOMG";
	}
	
}
class TaskColumnLabelProvider extends DecoratingLabelProvider {

	public TaskColumnLabelProvider(ILabelProvider provider,
			ILabelDecorator decorator) {
		super(provider, decorator);
		// TODO Auto-generated constructor stub
	}
	
}
