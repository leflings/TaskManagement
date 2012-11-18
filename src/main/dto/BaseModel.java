package main.dto;

import main.dao.DAOFactory;

public abstract class BaseModel {
	
	protected DAOFactory factory;
	
	public abstract int getId();
	public abstract String toString();
	
	final DAOFactory getFactory() {
		if(factory == null ) {
			factory = DAOFactory.getInstance();
		}
		return factory;
	}

}
