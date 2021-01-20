package com.vkcrestore.gps;




import com.vkcrestore.constants.GpsSettings;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.widget.Toast;

public class RepeatGpsReciever extends BroadcastReceiver  
{
Context context ;
	@Override
	public void onReceive(Context ctx, Intent intent)
	{
		// TODO Auto-generated method stub
		///INITIALIZE GPS THREADS 
		this.context = ctx;
		
		//System.out.println("212---------->");
		//System.out.println("ewewe");
		//System.out.println("ewewe");
		
		GpsApiThread gpsapithread = new GpsApiThread(context)	;
		gpsapithread.start();
		
	}

		
	

	
}
