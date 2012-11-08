package main.dto;

import main.dao.DAOFactory;
import main.dao.DAOProperties;

public abstract class BaseModel {
	
	protected DAOFactory factory;
	
	public abstract int getId();
	
	final DAOFactory getFactory() {
		if(factory == null ) {
			factory = DAOFactory.getInstance();
		}
		return factory;
	}

}
