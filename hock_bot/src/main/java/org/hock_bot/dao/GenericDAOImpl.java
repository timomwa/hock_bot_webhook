package org.hock_bot.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.hock_bot.core.ConfigurationI;


public class GenericDAOImpl<T, ID extends Serializable> implements GenericDAOI<T, ID> {
	private final Class<T> persistentClass;
	
	@SuppressWarnings("unchecked")
	public GenericDAOImpl() {
		this.persistentClass = (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}

	public GenericDAOImpl(final Class<T> persistentClass) {
		super();
		this.persistentClass = persistentClass;
	}
	
	@PersistenceContext(unitName=ConfigurationI.REPORTING_PU)
	protected EntityManager em;
	
	private Logger log = Logger.getLogger(getClass());
	
	@Override
	public T findBy(String fieldName, Object value) {
		Query query = em
				.createQuery(getQuery(fieldName))
				.setParameter(fieldName, value);
		return getSingleResult(query);
	}
	
	@SuppressWarnings("unchecked")
	private T getSingleResult(Query query) {
		try {
			return (T) query.getSingleResult();
		} catch (NonUniqueResultException exc) {
			return (T) query.getResultList().get(0);
		} catch (NoResultException exc) {
			return null;
		}
	}
	
	
	private String getQuery(String fieldName) {
		String query = "from " + persistentClass.getName() + " t " + "where t."
				+ fieldName + " = :" + fieldName;
		return query;
	}
	
	/**
	 * @see GenericDAO#getEntityClass()
	 */
	public Class<T> getEntityClass() {
		return persistentClass;
	}
	
	/**
	 * @see GenericDAO#save(Object)
         * #save(java.lang.Object)
	 */
	public T save(T entity) throws Exception{
		entity = em.merge(entity);
		return entity;
	}
	
	/**
	 * @see GenericDAO#delete(java.lang.Object)
	 */
	public void delete(T entity) throws Exception{
		em.remove(entity);
	}
	
	/**
	 * @see GenericDAO#deleteById(java.lang.Object)
	 */
	public void deleteById(final ID id) throws Exception{
		T entity = em.find(persistentClass, id);
		if(entity != null) em.remove(entity);
	}
	
	public void delete(ID ids []) throws Exception{
		int size = ids.length;
		
		for(int idx = 0; idx < size; idx++){
			T entity = em.find(persistentClass, ids[idx]);
			if(entity != null) em.remove(entity);
		}
		
	}
	
	/**
	 * @see GenericDAO#deleteBatchById(java.lang.Object)
	 */
	public void deleteBatchById(ID ids []) throws Exception{
		int size = ids.length;
		
		for(int idx = 0; idx < size; idx++){
			T entity = em.find(persistentClass, ids[idx]);
			if(entity != null) em.remove(entity);
		}
		
	}
	
	/**
	 * @see GenericDAO#findById(java.io.Serializable)
	 */
	public T findById(final ID id) {
		final T result = em.find(persistentClass, id);
		return result;
	}
	
	
	
	/* (non-Javadoc)
	 * @see com.systech.fm.dao.generic.GenericDAO#getReference(java.io.Serializable)
	 */
	public T getReference(final ID id){
		return em.getReference(persistentClass, id);
	}
	
	/**
	 * @see GenericDAO#findByNamedQuery(String, Object...)
         * #findByNamedQuery(java.lang.String, java.lang.Object[])
	 */
	@SuppressWarnings("unchecked")
	public List<T> findByNamedQuery(final String name, Object... params) {
		javax.persistence.Query query = em.createNamedQuery(name);

		for (int i = 0; i < params.length; i++) {
			query.setParameter(i + 1, params[i]);
		}
		
		final List<T> result = (List<T>) query.getResultList();
		return result;
	}

	/**
	 * @see GenericDAO#findByNamedQuery(String, Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> findByNamedQuery(final String name,
			final Map<String, ? extends Object> params) {
		javax.persistence.Query query = em.createNamedQuery(name);

		for (final Map.Entry<String, ? extends Object> param : params.entrySet()) {
			query.setParameter(param.getKey(), param.getValue());
		}

		return query.getResultList();
	}
	
	
	@SuppressWarnings("unchecked")
	public List<T> findByNamedQuery(final String queryName, final Map<String, ?extends Object> params, int start, int limit){
		
		javax.persistence.Query query = em.createNamedQuery(queryName);

		for (final Map.Entry<String, ? extends Object> param : params.entrySet()) {
			query.setParameter(param.getKey(), param.getValue());
		}
		query.setFirstResult(start);
		query.setMaxResults(limit);

		return query.getResultList();
	}
	
	
	
	@SuppressWarnings("unchecked")
	public List<T> list(final int firstResult,final int maxResults){
		try{
			Query qry = em.createQuery("from "+getEntityClass().getName());
			if(firstResult>0)
				qry.setFirstResult(firstResult);
			if(maxResults>0)
				qry.setMaxResults(maxResults);
			return qry.getResultList();
		}catch(javax.persistence.NoResultException ex){
			log.warn("Could not find any results with the query\"from "+getEntityClass().getName());
		}
		
		return new ArrayList<T>();
		
	}
	

	@Override
	@SuppressWarnings("unchecked")
	public List<T> findByNamedQueryAndNamedParams(final String name,
			final Map<String, ? extends Object> params) {
		javax.persistence.Query query = em.createNamedQuery(name);

		for (final Map.Entry<String, ? extends Object> param : params.entrySet()) {
			query.setParameter(param.getKey(), param.getValue());
		}

		final List<T> result = (List<T>) query.getResultList();
		return result;
	}
	
	@Override
	public void lock(T entity, LockModeType type){
		em.lock(entity, type);
	}
	
}
