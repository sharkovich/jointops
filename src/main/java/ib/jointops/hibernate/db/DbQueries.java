package ib.jointops.hibernate.db;

import ib.jointops.hibernate.domain.Categories;
import ib.jointops.hibernate.domain.Users;
import ib.jointops.hibernate.utils.HibernateUtil;

import java.util.List;

import org.hibernate.Session;

@SuppressWarnings("rawtypes")
public class DbQueries {
	public static List listUsers() {
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		try {
			
			List result = session.createCriteria(Users.class).list();
			session.getTransaction().commit();
			return result;

		} catch (Exception e) {
			// TODO: handle exception
			session.getTransaction().rollback();
			return null;
		}

	}
	public static List listCategories() {
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		try {
			
			List result = session.createQuery("select c from Categories c left join fetch c.tasks task left join fetch task.who").list();
	
			session.getTransaction().commit();
			return result;
			
		} catch (Exception e) {
			session.getTransaction().rollback();
			return null;
		}
	}

}
