package main.dto;

import main.dao.DAOFactory;

public abstract class BaseModel {
	
	DAOFactory factory;
	
	final DAOFactory getFactory() {
		if(factory == null ) {
			factory = DAOFactory.getInstance("javabase.jdbc");
		}
		return factory;
	}

}
