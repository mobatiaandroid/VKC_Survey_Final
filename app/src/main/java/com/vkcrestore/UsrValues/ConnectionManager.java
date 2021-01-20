package com.vkcrestore.UsrValues;

import android.content.Context;
import android.net.ConnectivityManager;

public class ConnectionManager {
	public static boolean checkIntenetConnection(Context context)
	{
		ConnectivityManager connec =  (ConnectivityManager)context.getSystemService(context.CONNECTIVITY_SERVICE);

    
		
		if(connec.getNetworkInfo(0).isConnectedOrConnecting() || connec.getNetworkInfo(1).isConnectedOrConnecting() )
		{
			
			return true ;
		}
		else
		{
		
			return false ;
		}
       
	}

}
