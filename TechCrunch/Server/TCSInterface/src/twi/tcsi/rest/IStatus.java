package twi.tcsi.rest;

import java.util.List;

public interface IStatus {
	
	static List<Status> statusInstance = null;
	public List<Status> getStatusInstance();
	
}
