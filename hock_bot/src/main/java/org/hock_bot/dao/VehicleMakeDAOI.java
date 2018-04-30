package org.hock_bot.dao;

import org.hock_bot.model.VehicleMake;

public interface VehicleMakeDAOI extends GenericDAOI<VehicleMake, Long> {

	public VehicleMake getByMakeName(String make);

}
