package main.dto;

import main.dao.DAOFactory;
import main.dao.DAOProperties;

public abstract class BaseModel {
	
	DAOFactory factory;
	
	final DAOFactory getFactory() {
		if(factory == null ) {
			factory = DAOFactory.getInstance();
		}
		return factory;
	}

}
