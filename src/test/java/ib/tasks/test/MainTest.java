package ib.tasks.test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;

import ib.jointops.hibernate.domain.Categories;
import ib.jointops.hibernate.domain.Users;
import ib.jointops.hibernate.utils.HibernateUtil;

public class MainTest {
	
	private static final String[] CAT_NAMES = {
		"General Task", "Assignment", "Meeting"
	};

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MainTest mt = new MainTest();
		
		mt.wipeAndCreateBasicCat();
		
		List<Categories> catList = mt.listCategories();
		for (Categories categories : catList) {
			System.out.println(categories.getCategoryName());
		}
		
		HibernateUtil.closeSessionFactory();

	}
	private void createBasicCategories() {
		List categories = new ArrayList();
		for (int i = 0; i < CAT_NAMES.length; i++) {
			Categories category = new Categories();
			category.setCategoryName(CAT_NAMES[i]);
			categories.add(category);
		}
				
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		
		List result = session.createCriteria(Categories.class).list();
		session.getTransaction().commit();
		
		if (!result.equals(categories)) {
		
			Session session2 = HibernateUtil.getSessionFactory().getCurrentSession();
			session2.beginTransaction();
			
			Iterator it = categories.iterator();
			while (it.hasNext()) {
				Categories object = (Categories) it.next();
				session2.save(object);
			}
			session2.getTransaction().commit();
		}
	}
	private void wipeAndCreateBasicCat() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		
		List result = session.createCriteria(Categories.class).list();
		
		if (result.size() > 0) {
			Iterator it = result.iterator();
			while (it.hasNext()) {
				Categories object = (Categories) it.next();
				session.delete(object);
			}		
		}
	
		for (int i = 0; i < CAT_NAMES.length; i++) {
			Categories category = new Categories();
			category.setCategoryName(CAT_NAMES[i]);
			session.save(category);
		}	
		
		session.getTransaction().commit();
	}
	private List<Users> listUsers() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		
		List result = session.createCriteria(Users.class).list();
		session.getTransaction().commit();
		
		List<Users> resultList = new ArrayList<Users>();
		Iterator it = result.iterator();
		while (it.hasNext()) {
			Users anUser = (Users) it.next();
			resultList.add(anUser);
		}
		return resultList;
	}
	private List<Categories> listCategories() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		
		List result = session.createCriteria(Categories.class).list();
		session.getTransaction().commit();
		
		List<Categories> resultList = new ArrayList<Categories>();
		Iterator it = result.iterator();
		while (it.hasNext()) {
			Categories aCategory = (Categories) it.next();
			resultList.add(aCategory);
		}
		return resultList;
	}
	private void addAndSaveUser(String username) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		
		Users anUser = new Users();
		anUser.setName(username);
		session.save(anUser);
		
		session.getTransaction().commit();
	}

}
