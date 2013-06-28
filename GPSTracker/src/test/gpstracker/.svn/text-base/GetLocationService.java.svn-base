package test.gpstracker;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

public class GetLocationService extends Service
{
	LocationBroadcast broadcast = new LocationBroadcast();

	public void onCreate()
	{
		super.onCreate();       
	}

	public void onStart(Context context,Intent intent, int startId)
	{
		broadcast.SetAlarm(context);
	}
	
	public void onStop(Context context,Intent intent, int startId)
	{
		broadcast.CancelAlarm(context);
	}

	@Override
	public IBinder onBind(Intent intent) 
	{
		return null;
	}
}