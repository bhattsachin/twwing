package twi.client.activity;

import twi.client.service.TowService;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class ScanActivity extends Activity implements OnClickListener {
	private ImageButton scanButton;
	private Button saveToServer;
	public static final int BARCODE_CONSTANT = 0;
	private TextView barcode, latcode, loncode, addrscode;
	TwiApplication objAppState;
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.scan);
	        objAppState = ((TwiApplication) getApplicationContext());
	        //attaching on click event on scan button
	        //scanButton = (ImageButton) findViewById(R.id.scanbtn);
	        //scanButton.setOnClickListener(this);
	        barcode = (TextView) findViewById(R.id.barcode);
	        latcode = (TextView) findViewById(R.id.lat);
	        loncode = (TextView) findViewById(R.id.lon);
	        addrscode = (TextView) findViewById(R.id.addr);
	        saveToServer = (Button)findViewById(R.id.savetoserver);
	        openScan();
	        objAppState.setUserName("112");
	        saveToServer.setOnClickListener(this);
	        try{
	        	WebServiceTask ws = new WebServiceTask(this, "Loading...",42.890+"", -121.893490+"", "device");
				ws.execute();
	        }catch(Exception ex){
	        	System.out.println(ex.getMessage());
	        }
	        
	       
	    }
	 
	 public void openScan(){
		 Intent intent = new Intent("com.google.zxing.client.android.SCAN");
			intent.putExtra("SCAN_MODE", "PRODUCT_MODE");
			try{
			startActivityForResult(intent, BARCODE_CONSTANT);
			}catch(Exception ex){
				System.out.println(ex.getMessage());
				ex.printStackTrace();
			}
	 }
	 
	 
	 @Override
		public void onClick(View v) {
		 	if(v.getId()==R.id.scanbtn){

				Intent intent = new Intent("com.google.zxing.client.android.SCAN");
				intent.putExtra("SCAN_MODE", "PRODUCT_MODE");
				try{
				startActivityForResult(intent, BARCODE_CONSTANT);
				}catch(Exception ex){
					System.out.println(ex.getMessage());
					ex.printStackTrace();
				}
				
		 	
		 	}
		 
		}

		@SuppressWarnings("static-access")
		public void onActivityResult(int requestCode, int resultCode, Intent intent) {
			if(requestCode == BARCODE_CONSTANT){
				LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE); 
				Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
				double longitude = location.getLongitude();
				double latitude = location.getLatitude();
				
				if(resultCode == RESULT_OK){
					latcode.setText(latitude + "");
					loncode.setText(longitude + "");
					String barcodetxt = intent.getStringExtra("SCAN_RESULT");
					barcode.setText(barcodetxt);
					objAppState.setUserName("112");
					Intent it = new Intent(ScanActivity.this, MainActivity.class);
					ScanActivity.this.startActivity(it);
					//String addr = TowService.getGeoLocation(latitude+"", longitude+"");
					//addrscode.setText(addr);
					try{
			        	WebServiceTask ws = new WebServiceTask(this, "Loading...",latitude+"", longitude+"", "device");
						ws.execute();
			        }catch(Exception ex){
			        	System.out.println(ex.getMessage());
			        }
				}
			}
			
		}
		
		
		private class WebServiceTask extends AsyncTask<String, Integer, String> {

			private static final String TAG = "WebServiceTask";
			private Context mContext = null;
			private String processMessage = "Processing...";
			private String lat;
			private String lng;
			private String devideId = "";

			private ProgressDialog pDlg = null;

			public WebServiceTask(Context mContext, String processMessage,
					String lat, String lng, String deviceId) {

				this.mContext = mContext;
				this.processMessage = processMessage;
				this.lat = lat;
				this.lng = lng;
				this.devideId = deviceId;
			}

			private void showProgressDialog() {
				pDlg = new ProgressDialog(mContext);
				pDlg.setMessage(processMessage);
				pDlg.setProgressDrawable(mContext.getWallpaper());
				pDlg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				pDlg.setCancelable(false);
				pDlg.show();
			}

			@Override
			protected void onPreExecute() {

				//showProgressDialog();

			}

			protected String doInBackground(String... params) {
				try{
					TowService srv = TowService.towkabaccha;
			 		srv.towHisCar("someid", this.lat, this.lng);
				}catch(Exception ex){
					System.out.println(ex.getMessage());
				}
				return "nothing";
			}

			@Override
			protected void onPostExecute(String response) {
				
				//addrscode.setText(response);

			}
		}


	 
	 
	
}
