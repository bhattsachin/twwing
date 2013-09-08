package twi.client.service;

import java.util.LinkedList;
import java.util.List;

import twi.tcsi.rest.Alert;
import twi.tcsi.rest.IAlert;
import twi.tcsi.rest.IStatus;
import twi.tcsi.rest.Status;
import twi.tcsi.rest.User;

public class MainService implements IStatus, IAlert {

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
	
	public void SetStatus()
	{
		
		for(int i=0; i<5; i++)
		{
			Status objStatus = new Status();
			objStatus.carCode = "12121";
			objStatus.raiseDate = "1/1/2013";
			statusInstance.add(objStatus);
			
			
		}
	}
	

}