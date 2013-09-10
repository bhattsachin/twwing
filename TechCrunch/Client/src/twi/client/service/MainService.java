package twi.client.service;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import twi.tcsi.rest.Alert;
import twi.tcsi.rest.IAlert;
import twi.tcsi.rest.IStatus;

public class MainService extends RestBaseClass implements IStatus, IAlert {

	private static MainService instance;

	public static MainService getInstance() {
		if (instance == null)
			instance = new MainService();
		return instance;
	}

	private static Alert alertInstance = null;

	public Alert getAlertInstance() {
		if (alertInstance == null)
			alertInstance = new Alert();
		return alertInstance;
	}

	private static List<twi.tcsi.rest.Status> statusInstance = null;

	public List<twi.tcsi.rest.Status> getStatusInstance() {
		if (statusInstance == null)
			statusInstance = new LinkedList<twi.tcsi.rest.Status>();
		return statusInstance;
	}

	public String GetActiveStatus(int statuOwnerID ) {

		try {
			String returnJson = this.ExecuteRequest(RequestMethod.GET, "status?userId=" + statuOwnerID,
					null);
			statusInstance = new LinkedList<twi.tcsi.rest.Status>();
			statusInstance = (new Gson()).fromJson(returnJson, (new LinkedList<twi.tcsi.rest.Status>()).getClass());
			if(statusInstance.size()>0){
				return "success";
			}else{
				return "failure";
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "Failure";

	}
	
	

}