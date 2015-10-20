package dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import exception.DAOException;

public class GenericDAO {
	private static SessionFactory factory;

	static {
		//factory = new AnnotationConfiguration().configure().buildSessionFactory();
		Configuration configuration = new Configuration().configure();
		ServiceRegistryBuilder registry = new ServiceRegistryBuilder();
		registry.applySettings(configuration.getProperties());
		ServiceRegistry serviceRegistry = registry.buildServiceRegistry();
		factory = configuration.buildSessionFactory(serviceRegistry);
	}

	public List<?> findByQuery(StringBuilder hql, Map<String, Object> ArgMap)
			throws DAOException {
		Session session = openSession();
		try {
			Query query = session.createQuery(hql.toString());
			if (ArgMap != null && !ArgMap.isEmpty()) {
				for (String x : ArgMap.keySet()) {
					Object arg = ArgMap.get(x);
					query.setParameter(x, arg);
				}
			}
			List<?> list = query.list();
			return list;
		} catch (Exception e) {
			throw new DAOException(e);
		} finally {
			closeSession(session);
		}

	}

	public <T> void add(T obj) throws DAOException {
		Session session = openSession();
		Transaction tx = session.beginTransaction();
		try {
			session.save(obj);
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			throw new DAOException(e);
		} finally {
			closeSession(session);
		}
	}

	public <T> void update(T obj) throws DAOException {
		Session session = openSession();
		Transaction tx = session.beginTransaction();
		try {
			session.update(obj);
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			throw new DAOException(e);
		} finally {
			closeSession(session);
		}
	}

	public <T> void addSet(Set<T> set) throws DAOException {
		Session session = openSession();
		Transaction tx = session.beginTransaction();
		try {
			for (T obj : set)
				session.save(obj);
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			throw new DAOException(e);
		} finally {
			closeSession(session);
		}
	}

	public <T> void updateCollection(Collection<T> list) throws DAOException {
		Session session = openSession();
		Transaction tx = session.beginTransaction();
		try {
			for (T obj : list)
				session.update(obj);
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			throw new DAOException(e);
		} finally {
			closeSession(session);
		}
	}

	private static Session openSession() {
		return factory.openSession();
	}

	private static void closeSession(Session session) {
		if (session != null && session.isOpen())
			session.close();
	}

}
