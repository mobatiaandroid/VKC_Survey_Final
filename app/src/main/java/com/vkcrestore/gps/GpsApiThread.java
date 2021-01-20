package com.vkcrestore.gps;

import com.vkcrestore.AddUserActivity;
import com.vkcrestore.Profiles.GpsModel;
import com.vkcrestore.api.VkcApis;
import com.vkcrestore.constants.GpsSettings;
import com.vkcrestore.manager.AppPreferenceManager;
import com.vkcrestore.manager.Utils;

import android.app.Activity;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.widget.Toast;


public class GpsApiThread extends Thread {
    //VideoUploads video_model ;
    Thread t;
    Context context;
    Toast toast;
    VkcApis api;
    GpsModel model;
    LocationTrack locationTrack;

    /*public GpsApiThread(VideoUploads video_model, Context context ) {
        // TODO Auto-generated constructor stub
        t=new Thread(this);
        this.context = context ;
        this.video_model = video_model ;

    }*/
    public GpsApiThread(Context context) {
        // TODO Auto-generated constructor stub
        t = new Thread(this);
        this.context = context;
        api = new VkcApis(context);
        model = new GpsModel();
        locationTrack = new LocationTrack(context);

    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        //super.run();

        String response = "";
        try {

            //response = 	ConnectionManager.gpsLogApi("", this.video_model);

            model.setLatitude(String.valueOf(locationTrack.getLatitude()));
            model.setLongitude(String.valueOf(locationTrack.getLongitude()));
            //System.out.println("DFFFFF--------->"+gps.getLongitude());
            model.setDevice_id(Utils.getDeviceId(context));
            model.setUser_id(AppPreferenceManager.getUserId(context));
            model.setLocation(String.valueOf(GpsSettings.LOCATION));

            api.gpsApi(model);

        } catch (Exception e) {
            //	FileUtils.writeLogFile("Gps log exception");
            // TODO: handle exception


        }

        //Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
        //System.out.println("Response from Gps Api " + response);

    }

    public void start() {
        t.start();
    }
}
