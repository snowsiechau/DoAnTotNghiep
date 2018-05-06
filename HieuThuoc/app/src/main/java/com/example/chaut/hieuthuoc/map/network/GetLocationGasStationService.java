package com.example.chaut.hieuthuoc.map.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by SNOW on 11/21/2017.
 */

public interface GetLocationGasStationService {
    @GET("https://maps.googleapis.com/maps/api/place/nearbysearch/json?")
    Call<MainObjectJSON> getGasStation(@Query("location") String location,
                                       @Query("radius") int radius,
                                       @Query("type") String type,
                                       @Query("key") String key
    );
}
