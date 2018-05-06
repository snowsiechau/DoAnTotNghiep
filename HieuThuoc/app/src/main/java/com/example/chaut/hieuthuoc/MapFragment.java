package com.example.chaut.hieuthuoc;


import android.*;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.example.chaut.hieuthuoc.map.map_direction.DirectionHandler;
import com.example.chaut.hieuthuoc.map.map_direction.DirectionResponse;
import com.example.chaut.hieuthuoc.map.map_direction.RetrofitInstance;
import com.example.chaut.hieuthuoc.map.map_direction.RetrofitService;
import com.example.chaut.hieuthuoc.map.map_direction.RouteModel;
import com.example.chaut.hieuthuoc.map.map_distance.GetDistanceService;
import com.example.chaut.hieuthuoc.map.map_distance.MainDistanceObjecJson;
import com.example.chaut.hieuthuoc.map.map_distance.RetrofitDistance;
import com.example.chaut.hieuthuoc.map.network.GetLocationGasStationService;
import com.example.chaut.hieuthuoc.map.network.MainObjectJSON;
import com.example.chaut.hieuthuoc.map.network.RetrofitFactory;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.LOCATION_SERVICE;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements LocationListener, ActivityCompat.OnRequestPermissionsResultCallback {
    private static final String TAG = "location";
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;

    private GoogleMap myMap;
   // private ProgressDialog myProgress;

    private static final String MYTAG = "MYTAG";

    // Mã yêu cầu người dùng cho phép xem vị trí hiện tại của họ (***).
    // Giá trị mã 8bit (value < 256).
    public static final int REQUEST_ID_ACCESS_COURSE_FINE_LOCATION = 100;

    private Location myLocation;
    private Polyline polylineFinal;



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.fragment_map);

        // Tạo Progress Bar
//        myProgress = new ProgressDialog(getActivity());
//        myProgress.setTitle("Loading ...");
//        myProgress.setMessage("Please wait...");
//        myProgress.setCancelable(true);
//
//        // Hiển thị Progress Bar
//        myProgress.show();


        // Sét đặt sự kiện thời điểm GoogleMap đã sẵn sàng.
        mapFragment.getMapAsync(new OnMapReadyCallback() {

            @Override
            public void onMapReady(GoogleMap googleMap) {
                onMyMapReady(googleMap);
            }
        });


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    private void onMyMapReady(GoogleMap googleMap) {

        // Lấy đối tượng Google Map ra:
        myMap = googleMap;

        // Thiết lập sự kiện đã tải Map thành công
        myMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {

            @Override
            public void onMapLoaded() {
                // Đã tải thành công thì tắt Dialog Progress đi
               // myProgress.dismiss();
                // Hiển thị vị trí người dùng.
                askPermissionsAndShowMyLocation();
            }
        });
        myMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        myMap.getUiSettings().setZoomControlsEnabled(true);

        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        myMap.setMyLocationEnabled(true);
    }


    private void askPermissionsAndShowMyLocation() {
        // Với API >= 23, bạn phải hỏi người dùng cho phép xem vị trí của họ.
        if (Build.VERSION.SDK_INT >= 23) {
            int accessCoarsePermission
                    = ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION);
            int accessFinePermission
                    = ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION);


            if (accessCoarsePermission != PackageManager.PERMISSION_GRANTED
                    || accessFinePermission != PackageManager.PERMISSION_GRANTED) {

                // Các quyền cần người dùng cho phép.
                String[] permissions = new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION,
                        android.Manifest.permission.ACCESS_FINE_LOCATION};

                // Hiển thị một Dialog hỏi người dùng cho phép các quyền trên.
                ActivityCompat.requestPermissions(getActivity(), permissions,
                        REQUEST_ID_ACCESS_COURSE_FINE_LOCATION);

                return;
            }
        }

        // Hiển thị vị trí hiện thời trên bản đồ.
        this.showMyLocation();
    }


    // Khi người dùng trả lời yêu cầu cấp quyền (cho phép hoặc từ chối).
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //
        switch (requestCode) {
            case REQUEST_ID_ACCESS_COURSE_FINE_LOCATION: {


                // Chú ý: Nếu yêu cầu bị bỏ qua, mảng kết quả là rỗng.
                if (grantResults.length > 1
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(getActivity(), "Permission granted!", Toast.LENGTH_LONG).show();

                    // Hiển thị vị trí hiện thời trên bản đồ.
                    this.showMyLocation();
                }
                // Hủy bỏ hoặc từ chối.
                else {
                    Toast.makeText(getActivity(), "Permission denied!", Toast.LENGTH_LONG).show();
                }
                break;
            }

            case MY_PERMISSIONS_REQUEST_CALL_PHONE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the phone call

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    // Tìm một nhà cung cấp vị trị hiện thời đang được mở.
    private String getEnabledLocationProvider() {
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);


        // Tiêu chí để tìm một nhà cung cấp vị trí.
        Criteria criteria = new Criteria();

        // Tìm một nhà cung vị trí hiện thời tốt nhất theo tiêu chí trên.
        // ==> "gps", "network",...
        String bestProvider = locationManager.getBestProvider(criteria, true);

        boolean enabled = locationManager.isProviderEnabled(bestProvider);

        if (!enabled) {
            Toast.makeText(getActivity(), "No location provider enabled!", Toast.LENGTH_LONG).show();
            Log.i(MYTAG, "No location provider enabled!");
            return null;
        }
        return bestProvider;
    }

    // Chỉ gọi phương thức này khi đã có quyền xem vị trí người dùng.
    private void showMyLocation() {

        LocationManager locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);

        String locationProvider = this.getEnabledLocationProvider();

        if (locationProvider == null) {
            return;
        }

        // Millisecond
        final long MIN_TIME_BW_UPDATES = 1000;
        // Met
        final float MIN_DISTANCE_CHANGE_FOR_UPDATES = 1;

        myLocation = null;
        try {

            // Đoạn code nay cần người dùng cho phép (Hỏi ở trên ***).
            locationManager.requestLocationUpdates(
                    locationProvider,
                    MIN_TIME_BW_UPDATES,
                    MIN_DISTANCE_CHANGE_FOR_UPDATES, (LocationListener) this);

            // Lấy ra vị trí.
            myLocation = locationManager
                    .getLastKnownLocation(locationProvider);

        }
        // Với Android API >= 23 phải catch SecurityException.
        catch (SecurityException e) {
            Toast.makeText(getActivity(), "Show My Location Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            Log.e(MYTAG, "Show My Location Error:" + e.getMessage());
            e.printStackTrace();
            return;
        }

        if (myLocation != null) {

            LatLng latLng = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
            myMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13));

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(latLng)             // Sets the center of the map to location user
                    .zoom(15)                   // Sets the zoom
                    .bearing(90)                // Sets the orientation of the camera to east
                    .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                    .build();                   // Creates a CameraPosition from the builder
            myMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

            //TODO: get fixer in firebase
//            databaseReference.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
//                        UserModel userModel = postSnapshot.getValue(UserModel.class);
//
//                        if (userModel.isFixer) {
//                            userModels.add(userModel);
//                            Log.i(TAG, "onDataChange: " + userModels.size());
//                            MarkerOptions option = new MarkerOptions();
//                            option.title(userModel.name);
//                            option.snippet(userModel.phoneNumber);
//
//                            LatLng latLng = new LatLng(userModel.lat, userModel.lon);
//                            option.position(latLng);
//                            BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.raw.support);
//                            option.icon(icon);
//                            Marker currentMarker = myMap.addMarker(option);
//                            currentMarker.showInfoWindow();
//                        }
//                    }
//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//
//                }
//            });

            //TODO: test gas_station location

            GetLocationGasStationService getLocationGasStationService = RetrofitFactory.getInstance()
                    .create(GetLocationGasStationService.class);

            getLocationGasStationService.getGasStation(myLocation.getLatitude() + "," + myLocation.getLongitude(),
                    5000,
                    "hospital",
                    "AIzaSyC_USdup6JhPfDVN_TdV8syHoQYhDPSzwQ"
            ).enqueue(new Callback<MainObjectJSON>() {
                @Override
                public void onResponse(Call<MainObjectJSON> call, Response<MainObjectJSON> response) {
                    List<MainObjectJSON.ResultJSON> resultJSONS = response.body().getResult();

                    for (MainObjectJSON.ResultJSON resultJSON : resultJSONS){
                        MarkerOptions option = new MarkerOptions();
                        option.title(resultJSON.getName());
                        option.snippet(resultJSON.getVicinity());
                        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.raw.hospital);
                        option.icon(icon);

                        LatLng latLng = new LatLng(resultJSON.getGeometry().getLocation().getLat(),
                                resultJSON.getGeometry().getLocation().getLng());
                        option.position(latLng);
                        Marker currentMarker = myMap.addMarker(option);
                        currentMarker.setTag(10);
                        currentMarker.showInfoWindow();
                    }

                }

                @Override
                public void onFailure(Call<MainObjectJSON> call, Throwable t) {
                    Toast.makeText(getActivity(), "No Connection", Toast.LENGTH_SHORT).show();
                }
            });
            //^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

        } else {
            Toast.makeText(getActivity(), "Location not found!", Toast.LENGTH_LONG).show();
            Log.i(MYTAG, "Location not found");
        }

        myMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            public void onInfoWindowClick(final Marker marker) {
                Log.i(TAG, "onInfoWindowClick: " + marker.getTag());
                String[] items={"Call"};
                AlertDialog.Builder itemDilog = new AlertDialog.Builder(getActivity());
                itemDilog.setTitle("");
                itemDilog.setCancelable(true);
                itemDilog.setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch(which){
                            case 0:{
                                Log.d(TAG, "onClick: " + "1");
                                if (marker.getTag() == null) {

                                    Intent mIntent;
                                    String number = ("tel:" + marker.getSnippet());
                                    mIntent = new Intent(Intent.ACTION_CALL);
                                    mIntent.setData(Uri.parse(number));
// Here, thisActivity is the current activity
                                    if (ContextCompat.checkSelfPermission(getActivity(),
                                            android.Manifest.permission.CALL_PHONE)
                                            != PackageManager.PERMISSION_GRANTED) {

                                        ActivityCompat.requestPermissions(getActivity(),
                                                new String[]{android.Manifest.permission.CALL_PHONE},
                                                MY_PERMISSIONS_REQUEST_CALL_PHONE);

                                        // MY_PERMISSIONS_REQUEST_CALL_PHONE is an
                                        // app-defined int constant. The callback method gets the
                                        // result of the request.
                                    } else {
                                        //You already have permission
                                        try {
                                            startActivity(mIntent);
                                        } catch(SecurityException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }break;
                        }

                    }
                });
                itemDilog.show();
            }
        });

        myMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                GetDistanceService getDistanceService = RetrofitDistance.getInstance().create(GetDistanceService.class);
                getDistanceService.getDistace("metric",
                        getLatLng(new LatLng(myLocation.getLatitude(), myLocation.getLongitude())),
                        getLatLng(new LatLng(marker.getPosition().latitude, marker.getPosition().longitude)),
                        "AIzaSyCLs9CNnz8DR95DtrzUWDxp3qjI9MkClAI"
                ).enqueue(new Callback<MainDistanceObjecJson>(){
                    @Override
                    public void onResponse(Call<MainDistanceObjecJson> call, Response<MainDistanceObjecJson> response) {
                        List<MainDistanceObjecJson.RowsObject> rowsObjects = response.body().getRows();
                        Toast.makeText(getActivity(), rowsObjects.get(0).getElements().get(0).getDistance().getText(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<MainDistanceObjecJson> call, Throwable t) {

                    }
                });


                RetrofitService retrofitService = RetrofitInstance.getInstance().create(RetrofitService.class);
                retrofitService.getDirection(
                        getLatLng(new LatLng(myLocation.getLatitude(), myLocation.getLongitude())),
                        getLatLng(new LatLng(marker.getPosition().latitude, marker.getPosition().longitude)),
                        "AIzaSyCLs9CNnz8DR95DtrzUWDxp3qjI9MkClAI"
                ).enqueue(new Callback<DirectionResponse>() {
                    @Override
                    public void onResponse(Call<DirectionResponse> call, Response<DirectionResponse> response) {
                        RouteModel routeModel = DirectionHandler.getListRoute(response.body()).get(0);

                        PolylineOptions polylineOptions = new PolylineOptions()
                                .color(Color.BLUE)
                                .width(5);

                        for (int  i = 0; i < routeModel.points.size(); i++){
                            polylineOptions.add(routeModel.points.get(i));
                        }

                        if (polylineFinal == null) {
                            polylineFinal = myMap.addPolyline(polylineOptions);
                        }else {
                            polylineFinal.remove();
                            polylineFinal = myMap.addPolyline(polylineOptions);
                        }
                        DirectionHandler.zoomRoute(myMap, routeModel.points);
                    }

                    @Override
                    public void onFailure(Call<DirectionResponse> call, Throwable t) {

                    }
                });
                return false;
            }
        });
    }

    public String getLatLng(LatLng latLng){
        double lat = latLng.latitude;
        double lng = latLng.longitude;
        return lat + "," + lng;
    }




    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

}
