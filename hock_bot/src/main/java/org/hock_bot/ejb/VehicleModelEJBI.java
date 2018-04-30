package org.hock_bot.ejb;

import java.util.List;

import org.hock_bot.model.VehicleModel;

public interface VehicleModelEJBI {

	List<VehicleModel> getByMakeName(String string);

}
