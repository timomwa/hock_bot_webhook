package org.hock_bot.dao;


import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.hock_bot.model.VehicleMake;

public class VehicleMakeDAOImpl extends GenericDAOImpl<VehicleMake, Long> implements VehicleMakeDAOI {
	
	private Logger logger = Logger.getLogger(getClass());

	@Override
	public VehicleMake getByMakeName(String make) {
		
		VehicleMake vehicleMake = null;
		
		try{
			
			Query qry = em.createQuery("from VehicleMake vm WHERE lower(vm.name) = lower(:name) ");
			qry.setParameter("name", make);
			vehicleMake = (VehicleMake) qry.getSingleResult();
			
		}catch(NoResultException e){
			
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
		return vehicleMake;
	}

}
