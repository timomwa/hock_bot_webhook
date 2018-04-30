package org.hock_bot.dao;

import org.hock_bot.model.MenuMap;

public interface MenuMapDAOI extends GenericDAOI<MenuMap, Long>{

	MenuMap findMenu(String menuKey);

}
