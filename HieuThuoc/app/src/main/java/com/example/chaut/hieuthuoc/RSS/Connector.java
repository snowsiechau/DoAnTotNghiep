package com.example.chaut.hieuthuoc.RSS;

import android.view.VelocityTracker;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import retrofit2.http.GET;

/**
 * Created by chaut on 3/27/2018.
 */

public class Connector {
    public static Object connect(String urlAddress) {
        {
            try {
                URL url = new URL(urlAddress);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();

                //set properties
                con.setRequestMethod("GET");
                con.setConnectTimeout(15000);
                con.setReadTimeout(15000);
                con.setDoInput(true);


                return con;

            } catch (MalformedURLException e) {
                e.printStackTrace();
                return ErrorTracker.WRONG_URL_FORMAT;

            } catch (IOException e) {
                e.printStackTrace();
                return ErrorTracker.CONNECTION_ERROR;
            }
        }
    }
}
