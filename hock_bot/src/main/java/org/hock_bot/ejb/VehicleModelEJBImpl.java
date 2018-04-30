package org.hock_bot.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.hock_bot.dao.VehicleModelDAOI;
import org.hock_bot.model.VehicleModel;

@Stateless
public class VehicleModelEJBImpl implements VehicleModelEJBI {
	

	private Logger logger = Logger.getLogger(getClass());
	
	@Inject
	private VehicleModelDAOI vehicleModelDAO;

	@Override
	public List<VehicleModel> getByMakeName(String makeName, int start, int size) {
		return vehicleModelDAO.getByMakeName(makeName,  start,  size);
	}


}
