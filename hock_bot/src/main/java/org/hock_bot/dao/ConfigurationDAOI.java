package org.hock_bot.dao;

import org.hock_bot.model.Configuration;

public interface ConfigurationDAOI extends GenericDAOI<Configuration, Long> {

	void updateConfiguration(String cfgKey, String valueOf);

}
