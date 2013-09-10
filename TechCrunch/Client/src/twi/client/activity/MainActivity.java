package twi.client.activity;

import java.util.Date;

import twi.client.service.MainService;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

	static boolean conLoop = true;
	private TextView oTxtStatusCarDetail, oTxtStatus, oTxtStatusCarDetail_1,
			oTxtStatusOwner, oTxtStatusCarType, oTxtStatusReportTime,
			oTxtStatus_1, oTxtStatusPending;
	private TextView oTxtAlertDetail, oTxtAlertStatus, oTxtAlertDetail_1,
			oTxtAlertStatus_1, oTxtActiveAlert;
	private ImageView btnBarCodeClick, btnCarPlateClick;

	private LinearLayout objLytStatus, objLytAlert;

	private boolean isCountDownTimerStart = false;
	private CountDownTimer mCountDownTimer;
	TwiApplication objAppState;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		objAppState = ((TwiApplication) getApplicationContext());
		Initilize();
		populate();

	}

	private void Initilize() {
		/* Button Click */
		btnBarCodeClick = (ImageView) findViewById(R.id.imgBarCode);
		btnBarCodeClick.setOnClickListener(objOnClickListener);

		btnCarPlateClick = (ImageView) findViewById(R.id.imgCarplate);
		btnCarPlateClick.setOnClickListener(objOnClickListener);

		/* Status */
		oTxtStatusCarDetail = (TextView) findViewById(R.id.txtStatusCarDetail);
		oTxtStatus = (TextView) findViewById(R.id.statusPending);
		oTxtStatusOwner = (TextView) findViewById(R.id.txtStatusOwner);
		oTxtStatusCarType = (TextView) findViewById(R.id.txtStatusCarType);
		oTxtStatusReportTime = (TextView) findViewById(R.id.txtStatusTime);

		oTxtStatusCarDetail_1 = (TextView) findViewById(R.id.txtStatusCarDetail1);
		oTxtStatus_1 = (TextView) findViewById(R.id.txtStatus1);
		oTxtStatusPending = (TextView) findViewById(R.id.statusPending);

		/* Alert */

		oTxtAlertDetail = (TextView) findViewById(R.id.txtAlertCarDetail);
		oTxtAlertStatus = (TextView) findViewById(R.id.txtAlert);
		oTxtAlertDetail_1 = (TextView) findViewById(R.id.txtAlertCarDetail1);
		oTxtAlertStatus_1 = (TextView) findViewById(R.id.txtAlerttime);
		oTxtActiveAlert = (TextView) findViewById(R.id.alertActive);
		oTxtAlertStatus_1.setText(String.valueOf(new Date(System.currentTimeMillis())));
		oTxtStatusReportTime.setText(String.valueOf(new Date(System.currentTimeMillis())));
		objLytAlert = (LinearLayout) findViewById(R.id.lytAlert);
		objLytStatus = (LinearLayout) findViewById(R.id.lytStatus);

		objLytAlert.setVisibility(View.GONE);
		objLytStatus.setVisibility(View.GONE);

	}

	private OnClickListener objOnClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.imgBarCode:
				Intent scan = new Intent(MainActivity.this, ScanActivity.class);

				MainActivity.this.startActivity(scan);
				break;

			case R.id.imgCarplate:
				break;

			}
		}
	};

	private void populate() {

		int userId = objAppState.getUserIdentifier();
		String deviceId = objAppState.getDeviceId();
		WebContiTask ws = new WebContiTask(this, "Loading...", userId, deviceId);
		ws.execute();

	}

	private class WebServiceTask extends AsyncTask<String, Integer, String> {

		private static final String TAG = "WebServiceTask";
		private Context mContext = null;
		private String processMessage = "Processing...";
		private int userId = 0;
		private String devideId = "";

		private ProgressDialog pDlg = null;

		public WebServiceTask(Context mContext, String processMessage,
				int userId, String deviceId) {

			this.mContext = mContext;
			this.processMessage = processMessage;
			this.userId = userId;
			this.devideId = deviceId;
		}

		private void showProgressDialog() {
			// pDlg = new ProgressDialog(mContext);
			pDlg.setMessage(processMessage);
			pDlg.setProgressDrawable(mContext.getWallpaper());
			pDlg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			pDlg.setCancelable(false);
			pDlg.show();
		}

		@Override
		protected void onPreExecute() {

			// showProgressDialog();

		}

		protected String doInBackground(String... params) {
			MainService objMainService = MainService.getInstance();
			return objMainService.GetActiveStatus(111);
			
		}

		@Override
		protected void onPostExecute(String response) {

			if (response == "success") {
				conLoop = false;
				BufferTimer();
			}
			else
			{
				populate();
			}

		}
	}

	private void BufferTimer() {
		mCountDownTimer = new CountDownTimer(3000, 100) {

			private long durationSeconds;

			public void onTick(long millisUntilFinished) {

				durationSeconds = millisUntilFinished / 100;

				String time = String.format("%02d:%02d",
						(durationSeconds % 360) / 60, (durationSeconds % 60));

				if (objAppState.getUserName() == "112"){
					oTxtStatus.setText("Inform to tow in " + time + " mins");
					objLytStatus.setVisibility(View.VISIBLE);
				}
				else{
					oTxtAlertStatus.setText("Inform to tow in " + time + " mins ");
					objLytAlert.setVisibility(View.VISIBLE);
				}

			}

			public void onFinish() {
				oTxtStatus.setText("Tow in 20 mins");
				TowTimer();
			}
		}.start();
	}

	private void TowTimer() {
		mCountDownTimer = new CountDownTimer(3000, 100) {

			private long durationSeconds;

			public void onTick(long millisUntilFinished) {

				durationSeconds = millisUntilFinished / 100;

				String time = String.format("%02d:%02d",
						(durationSeconds % 360) / 60, (durationSeconds % 60));

				//oTxtStatus.setText("Tow in " + time + " mins");
				
				if (objAppState.getUserName() == "112")
					oTxtStatus.setText("Tow in " + time + " mins");
				else
					oTxtAlertStatus.setText("Tow in " + time + " mins");

			}

			public void onFinish() {
				oTxtStatus.setText("Car Towed");
				conLoop = true;
			}
		}.start();
	}

	private class WebContiTask extends AsyncTask<String, Integer, String> {

		private static final String TAG = "WebContiTask";
		private Context mContext = null;
		private String processMessage = "Processing...";
		private int userId = 0;
		private String deviceId = "";

		private ProgressDialog pDlg = null;

		public WebContiTask(Context mContext, String processMessage,
				int userId, String deviceId) {

			this.mContext = mContext;
			this.processMessage = processMessage;
			this.userId = userId;
			this.deviceId = deviceId;
		}

		protected String doInBackground(String... params) {

			try {

				
					WebServiceTask ws = new WebServiceTask(mContext, "Loading",
							userId, deviceId);
					ws.execute();

					Thread.sleep(100);

				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return "df";
		}

		@Override
		protected void onPostExecute(String response) {

		}
	}

}
