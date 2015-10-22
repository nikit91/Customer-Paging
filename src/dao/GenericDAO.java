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
	/**
	 * Session Factory initialization
	 */
	static {
		Configuration configuration = new Configuration().configure();
		ServiceRegistryBuilder registry = new ServiceRegistryBuilder();
		registry.applySettings(configuration.getProperties());
		ServiceRegistry serviceRegistry = registry.buildServiceRegistry();
		factory = configuration.buildSessionFactory(serviceRegistry);
	}
	/**
	 * Method to retrieve fixed number of records of a table from DB
	 * @param hql - Query for the record
	 * @param ArgMap - Arguments if any
	 * @param from - offset of the records
	 * @param recCount - number of records to be retrieved from offset
	 * @return List of records
	 * @throws DAOException - Exception if any
	 */
	public List<?> findByQueryPaged(StringBuilder hql, Map<String, Object> ArgMap,int from,int recCount)
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
			query.setFirstResult(from);
			query.setMaxResults(recCount);
			List<?> list = query.list();
			return list;
		} catch (Exception e) {
			throw new DAOException(e);
		} finally {
			closeSession(session);
		}

	}
	/**
	 * Method to retrieve all records of a table from DB
	 * @param hql - Query for the record
	 * @param ArgMap - Arguments if any
	 * @return List of records
	 * @throws DAOException - Exception if any
	 */
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
	/**
	 * Add a record to db for a given table
	 * @param obj - Record to be added
	 * @throws DAOException - exception if any
	 */
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
	/**
	 * Update record of a table in db
	 * @param obj - record to be updated
	 * @throws DAOException - Exception if any
	 */
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
	/**
	 * Add a particular set of records in a db
	 * @param set - set of records to be added
	 * @throws DAOException - Exception if any
	 */
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
	/**
	 * Update a collection of records of a particular table in db
	 * @param list - collection of records to be updated
	 * @throws DAOException - Exception if any
	 */
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
	/**
	 * Method to open hibernate vsession from the session factory
	 * @return new hibernate session
	 */
	private static Session openSession() {
		return factory.openSession();
	}
	/**
	 * Method to close an existing hibernate session
	 * @param session - session to be closed
	 */
	private static void closeSession(Session session) {
		if (session != null && session.isOpen())
			session.close();
	}

}
