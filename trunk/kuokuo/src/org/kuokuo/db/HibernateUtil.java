/**
 * 
 */
package org.kuokuo.db;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

/**
 * @author Felix
 * 
 */
public class HibernateUtil {
	private static final SessionFactory sessionFactory;

	static {
		try {

			sessionFactory = new AnnotationConfiguration().configure()
					.buildSessionFactory();
		} catch (Throwable ex) {
			// Log exception!
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static Session getSession() throws HibernateException {
		return sessionFactory.openSession();
	}
	public static void closeSession(Session session) throws HibernateException {
		session.close();
	}
	public static void closeSessionFactory() throws HibernateException {
		sessionFactory.close();
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
