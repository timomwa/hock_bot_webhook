package org.hock_bot.dao;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.hock_bot.model.VehicleModel;

public class VehicleModelDAOImpl extends GenericDAOImpl<VehicleModel, Long> implements VehicleModelDAOI {
	
	private Logger logger = Logger.getLogger(getClass());

	@SuppressWarnings("unchecked")
	@Override
	public List<VehicleModel> getByMakeName(String makeName) {
		
		List<VehicleModel> vehicleModels = null;
		
		try{
			
			Query query = em.createQuery("from VehicleModel vm WHERE lower(vm.make.name) = lower(:makeName) ");
			query.setParameter("makeName", makeName);
			vehicleModels = query.getResultList();
			
		}catch(NoResultException nre){
			
		}catch(Exception e){
			logger.error(e.getMessage(),e);
		}
		
		return vehicleModels;
	}
	

}
