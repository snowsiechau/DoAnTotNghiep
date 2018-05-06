package com.example.chaut.hieuthuoc.map.map_distance;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by SNOW on 12/11/2017.
 */

public interface GetDistanceService {
    @GET("https://maps.googleapis.com/maps/api/distancematrix/json?")
    Call<MainDistanceObjecJson> getDistace(@Query("units") String units,
                                           @Query("origins") String origins,
                                           @Query("destinations") String destinations,
                                           @Query("key") String key
    );
}
