package twi.client.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import twi.client.service.Address.Response;
import twi.tcsi.rest.Alert;
import twi.tcsi.rest.IAlert;

import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class TowService extends RestBaseClass implements IAlert {

	private static Alert alertInstance = null;
	private static final String TOKEN= "cc8f9deebf98f5699fab55a10dd56ec94c83b05259571e390";

	public static TowService towkabaccha = new TowService();

	

	
	public void  towHisCar(String carid, String lat, String lon) {
		
		twi.tcsi.rest.Status status = new twi.tcsi.rest.Status();
		status.latitude=String.valueOf(lat);
		status.longitude = String.valueOf(lon);
		status.carCode = "Nissan";
		status.userId = 112;
		Random genrator = new Random();
		
		status.statusId = (int)(Math.random()*100000) + (int)(System.currentTimeMillis()/1000000);
		try{
			Gson gson = new Gson();
			String result = this.ExecuteRequest(RequestMethod.GET, "laststatus?inputjson=" +  gson.toJson(status), "laststatus");
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
		
	}
	
	public static String getGeoLocation(String target_lat, String target_lng ){



		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);



		//   ?target_lat=37.396892&target_lng=-121.928578&token=cc8f9deebf98f5699fab55a10dd56ec94c83b05259571e390'

		String url = "https://pais-dev.zypr.net/api/v2/map/reverse_geocode/?target_lat="+target_lat+"&target_lng="+target_lng+"&token="+TOKEN;
		WebResource service = client.resource(url);
		

		ClientResponse response = service.get(ClientResponse.class);

		if(response.getStatus() != 200) {
			System.out.println("Error: " + response.getStatus());
		}



		String output =response.getEntity(String.class);
	
		Gson gson = new Gson();

		GeoServiceResponse responseObject = gson.fromJson(output, GeoServiceResponse.class);

		StringBuffer sb = new StringBuffer();
		sb.append(responseObject.getResponse().getData().get(0).getAddress().getStreet() + ", ");
		sb.append(responseObject.getResponse().getData().get(0).getAddress().getCity() + "\n");
		sb.append(responseObject.getResponse().getData().get(0).getAddress().getCounty());
		sb.append(responseObject.getResponse().getData().get(0).getAddress().getState() + " - ");
		sb.append(responseObject.getResponse().getData().get(0).getAddress().getPostal() + "\n");
		sb.append(responseObject.getResponse().getData().get(0).getAddress().getCountry());

		

		client.destroy();
		return sb.toString();
	}

	@Override
	public twi.tcsi.rest.Alert getAlertInstance() {
		// TODO Auto-generated method stub
		return null;
	}
	
	


	
	

}

class GeoServiceResponse {
	
	public Response response = null;

	public Response getResponse() {
		return response;
	}

	public void setResponse(Response response) {
		this.response = response;
	}
	
	

}

class Address {
	
	private Point point = null;
	private String street = null;
	private String city = null;
	private String county = null;
	private String state = null;
	private String postal = null;
	private String country = null;
	
	public Point getPoint() {
		return point;
	}
	public void setPoint(Point point) {
		this.point = point;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCounty() {
		return county;
	}
	public void setCounty(String county) {
		this.county = county;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getPostal() {
		return postal;
	}
	public void setPostal(String postal) {
		this.postal = postal;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	
	class Data {

		private Address address = null;

		public Address getAddress() {
			return address;
		}

		public void setAddress(Address address) {
			this.address = address;
		}

		
		
		
		
	}
	
	
	class Point {

		private double lat = -1;
		private double lng = -1;
		
		public double getLat() {
			return lat;
		}
		public void setLat(double lat) {
			this.lat = lat;
		}
		public double getLng() {
			return lng;
		}
		public void setLng(double lng) {
			this.lng = lng;
		}
		
		
	}
	
	class Response {

		private List<Data> data = new ArrayList<Data>();

		public List<Data> getData() {
			return data;
		}

		public void setData(List<Data> data) {
			this.data = data;
		}


		public void addData(Data data) {
			this.data.add(data);
		}




	}




}
