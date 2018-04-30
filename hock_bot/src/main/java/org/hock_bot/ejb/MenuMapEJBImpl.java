package org.hock_bot.ejb;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.hock_bot.dao.MenuMapDAOI;
import org.hock_bot.model.MenuMap;

@Stateless
public class MenuMapEJBImpl implements MenuMapEJBI {
	
	@Inject 
	private MenuMapDAOI menuMapDAO;

	@Override
	public MenuMap findMenu(String menuKey) {
		return menuMapDAO.findMenu(menuKey);
	}

}
