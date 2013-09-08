package twi.server.rest;

import java.util.LinkedList;
import java.util.List;

import twi.tcsi.rest.Alert;
import twi.tcsi.rest.IAlert;
import twi.tcsi.rest.IStatus;
import twi.tcsi.rest.Status;

public class Main implements IStatus, IAlert {
	
	private static Alert alertInstance = null;
	public Alert getAlertInstance() {
		if(alertInstance == null)
			alertInstance = new Alert();
		return alertInstance;
	}

	private static List<Status>  statusInstance = null;
	public List<Status> getStatusInstance() {
		if(statusInstance == null)
			statusInstance = new LinkedList<Status>();
		return statusInstance;
	}
	
	

}
