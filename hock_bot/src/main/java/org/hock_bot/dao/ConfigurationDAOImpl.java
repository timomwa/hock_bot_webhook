package org.hock_bot.dao;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.hock_bot.model.Configuration;

public class ConfigurationDAOImpl extends GenericDAOImpl<Configuration, Long> implements ConfigurationDAOI {

	private Logger logger = Logger.getLogger(getClass());

	@Override
	public void updateConfiguration(String cfgName, String cfgVal) {
		
		try{
			
			Query qry = em.createQuery("UPDATE Configuration cfg_ SET cfg_.cfgVal = :cfgVal WHERE cfg_.cfgName = :cfgName");
			qry.setParameter("cfgVal", cfgVal);
			qry.setParameter("cfgName", cfgName);
			qry.executeUpdate();
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
		
	}

	
}
