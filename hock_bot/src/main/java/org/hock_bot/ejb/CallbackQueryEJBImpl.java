package org.hock_bot.ejb;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.hock_bot.dao.CallbackQueryDAOI;

@Stateless
public class CallbackQueryEJBImpl implements CallbackQueryEJBI {
	
	private Logger logger = Logger.getLogger(getClass());
	
	@Inject
	private CallbackQueryDAOI callBackQueryDAO;

}
