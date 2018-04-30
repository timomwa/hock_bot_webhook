package org.hock_bot.dao;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.hock_bot.model.MenuMap;

public class MenuMapDAOImpl   extends GenericDAOImpl<MenuMap, Long> implements MenuMapDAOI {
	
	private Logger logger = Logger.getLogger(getClass());

	@SuppressWarnings("unchecked")
	@Override
	public MenuMap findMenu(String menuKey) {
		
		MenuMap map = null;
		
		try{
			
			Query qry = em.createQuery("from MenuMap mm WHERE mm.menuKey = :menuKey");
			qry.setParameter("menuKey", menuKey);
			List<MenuMap> menumaps = qry.getResultList();
			if(menumaps!=null &&  !menumaps.isEmpty())
				map = menumaps.get(0);
			
		}catch(NoResultException e){
			
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
		
		return map;
	}

}
