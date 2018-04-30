package org.hock_bot.ejb;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.hock_bot.dao.VehicleMakeDAOI;
import org.hock_bot.model.VehicleMake;

@Stateless
public class VehicleMakeEJBImpl implements VehicleMakeEJBI {
	
	private Logger logger = Logger.getLogger(getClass());
	
	@Inject
	private VehicleMakeDAOI vehicleMakeDAO;

	@Override
	public VehicleMake getByMakeName(String make) {
		return vehicleMakeDAO.getByMakeName(make);
	}

}
