package org.hock_bot.ejb;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.hock_bot.dao.VehicleModelYearDAOI;

@Stateless
public class VehicleModelYearEJBImpl implements VehicleModelYearEJBI {
	

	private Logger logger = Logger.getLogger(getClass());
	
	@Inject
	private VehicleModelYearDAOI vehicleModeYearlDAO;

}
