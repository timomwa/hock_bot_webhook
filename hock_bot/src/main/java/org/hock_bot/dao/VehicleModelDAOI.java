package org.hock_bot.dao;

import java.util.List;

import org.hock_bot.model.VehicleModel;

public interface VehicleModelDAOI extends GenericDAOI<VehicleModel, Long> {

	List<VehicleModel> getByMakeName(String makeName, int start, int size);

}
