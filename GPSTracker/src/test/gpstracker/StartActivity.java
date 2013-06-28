package test.gpstracker;


import test.gpstracker.database.DBUtil;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class StartActivity extends Activity implements OnClickListener{
	GetLocationService service;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        
        Button btnStart = (Button)findViewById(R.id.btnStart);
        btnStart.setOnClickListener(this);
        Button btnStop = (Button)findViewById(R.id.btnStop);
        btnStop.setOnClickListener(this);
        Button btnViewLocation = (Button)findViewById(R.id.btnViewLocation);
        btnViewLocation.setOnClickListener(this);
        service = new GetLocationService();
        try {
			DBUtil.initialize(this);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
  
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		//start record user's location
		case R.id.btnStart:
		{
			 Toast.makeText(getApplicationContext(), "Start record your location", Toast.LENGTH_LONG).show();
			 Intent i = new Intent(this, GetLocationService.class);
			 service.onStart(this, i, 1);
			 
			 break;
		}
		//stop record user's location
		case R.id.btnStop:
		{
			 Toast.makeText(getApplicationContext(), "Stop record your location", Toast.LENGTH_LONG).show();
			 Intent i = new Intent(this, GetLocationService.class);
			 service.onStop(this, i, 1);
			 break;
		}
		//view user 's visited location
		case R.id.btnViewLocation:
			Intent intent = new Intent(StartActivity.this, MainActivity.class);
			startActivity(intent);
			break;
		default:
			break;
		}
	}
}
