package twi.client.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class ScanActivity extends Activity implements OnClickListener {
	private ImageButton scanButton;
	public static final int BARCODE_CONSTANT = 0;
	private TextView barcode;

	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.scan);
	        
	        //attaching on click event on scan button
	        scanButton = (ImageButton) findViewById(R.id.scanbtn);
	        scanButton.setOnClickListener(this);
	        barcode = (TextView) findViewById(R.id.barcode);
	    }
	 
	 
	 @Override
		public void onClick(View v) {
		 	switch(v.getId()){
		 	case R.id.scanbtn:

				Intent intent = new Intent("com.google.zxing.client.android.SCAN");
				intent.putExtra("SCAN_MODE", "PRODUCT_MODE");
				try{
				startActivityForResult(intent, BARCODE_CONSTANT);
				}catch(Exception ex){
					System.out.println(ex.getMessage());
					ex.printStackTrace();
				}
				break;
		 	
		 	}
		 
		}

		@SuppressWarnings("static-access")
		public void onActivityResult(int requestCode, int resultCode, Intent intent) {
			if(requestCode == BARCODE_CONSTANT){
				if(resultCode == RESULT_OK){
					
					String barcodetxt = intent.getStringExtra("SCAN_RESULT");
					barcode.setText(barcodetxt);
				}
			}
			
		}

	 
	 
	
}
