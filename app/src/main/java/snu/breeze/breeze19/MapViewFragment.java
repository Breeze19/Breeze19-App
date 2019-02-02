package snu.breeze.breeze19;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import android.Manifest;

import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


import java.util.List;

import snu.breeze.breeze19.R;

import static android.content.ContentValues.TAG;
import static android.content.Context.LOCATION_SERVICE;

public class MapViewFragment extends Fragment implements OnMapReadyCallback, LocationListener {
    private final String TAG = MapViewFragment.class.getSimpleName();
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private MapView mMapView;
    private GoogleMap googleMap;
    private String provider;
    private FloatingActionButton button1;
    private FloatingActionButton button2;
    LocationManager locationManager;
    private LatLngBounds bounds;
    private Boolean mLocationPermissionGranted;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    public static int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean mPermissionDenied = false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.mapview_fragmnet, container, false);
        button1 = rootView.findViewById(R.id.button1);
        button2 = rootView.findViewById(R.id.button2);
        mMapView = (MapView) rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }


        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(final GoogleMap mMap) {
                googleMap = mMap;
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                }


                Log.e(TAG, "Map is ready");

                //googleMap.setMyLocationEnabled(true);

                // For dropping a marker at a point on the Ma

                try {
                    // Customise the styling of the base map using a JSON object defined
                    // in a raw resource file.
                    boolean success = googleMap.setMapStyle(
                            MapStyleOptions.loadRawResourceStyle(
                                    getContext(), R.raw.style_json));
                    Log.e(TAG, String.valueOf(success));
                    if (!success) {
                        Log.e(TAG, "Style parsing failed.");
                    }
                } catch (Resources.NotFoundException e) {
                    Log.e(TAG, "Can't find style. Error: ", e);
                }
                final LatLngBounds.Builder builder = new LatLngBounds.Builder();
                final LatLng d_block = new LatLng(28.525377, 77.575448);
                LatLng b_block = new LatLng(28.526261, 77.576537);
                LatLng c_block = new LatLng(28.525974, 77.575863);
                LatLng a_block = new LatLng(28.526721, 77.577139);
                LatLng library = new LatLng(28.524945, 77.574395);
                LatLng lake = new LatLng(28.525177, 77.576923);
                LatLng ab_atrium = new LatLng(28.526372, 77.576805);
                LatLng mountSnu = new LatLng(28.526170, 77.575117);
                LatLng central_vista = new LatLng(28.525759, 77.574609);
                LatLng dh2 = new LatLng(28.524473, 77.570245);
                LatLng football = new LatLng(28.523075, 77.571815);
                LatLng ISC = new LatLng(28.521413, 77.571175);
                LatLng tennis = new LatLng(28.524066, 77.571332);
                LatLng volleyball = new LatLng(28.524520, 77.571674);
                LatLng baskbetball = new LatLng(28.524132, 77.571147);
                final LatLng main_stage = new LatLng(28.526047, 77.571124);

                final MarkerOptions marker1 =new MarkerOptions().position(d_block).title("D Dlock").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location));
                final MarkerOptions marker2 =new MarkerOptions().position(b_block).title("B Block").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location));
                final MarkerOptions marker3 =new MarkerOptions().position(c_block).title("C Block").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location));
                final MarkerOptions marker4 =new MarkerOptions().position(a_block).title("A Block").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location));
                final MarkerOptions marker5 =new MarkerOptions().position(library).title("Library").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location));
                final MarkerOptions marker6 =new MarkerOptions().position(lake).title("Lake").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location));
                final MarkerOptions marker7 =new MarkerOptions().position(ab_atrium).title("A-B Atrium").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location));
                final MarkerOptions marker8 =new MarkerOptions().position(mountSnu).title("Mount SNU").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location));
                final MarkerOptions marker9 =new MarkerOptions().position(central_vista).title("Central Vista").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location));
                final MarkerOptions marker10 =new MarkerOptions().position(dh2).title("DH2").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location));
                final MarkerOptions marker11=new MarkerOptions().position(football).title("Football Field").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location));
                final MarkerOptions marker12 =new MarkerOptions().position(ISC).title("Indoor Sports Complex").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location));
                final MarkerOptions marker13 =new MarkerOptions().position(tennis).title("Tennis Court").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location));
                final MarkerOptions marker14 =new MarkerOptions().position(volleyball).title("Volleyball Court").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location));
                final MarkerOptions marker15 =new MarkerOptions().position(baskbetball).title("Basketball Court").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location));
                final MarkerOptions marker16 =new MarkerOptions().position(main_stage).title("Main Stage").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location));

//the include method will calculate the min and max bound.
                builder.include(marker1.getPosition());
                builder.include(marker2.getPosition());
                builder.include(marker3.getPosition());
                builder.include(marker4.getPosition());
                builder.include(marker5.getPosition());
                builder.include(marker6.getPosition());
                builder.include(marker7.getPosition());
                builder.include(marker8.getPosition());
                builder.include(marker9.getPosition());
                builder.include(marker10.getPosition());
                builder.include(marker11.getPosition());
                builder.include(marker12.getPosition());
                builder.include(marker13.getPosition());
                builder.include(marker14.getPosition());
                builder.include(marker15.getPosition());
                builder.include(marker16.getPosition());

                bounds = builder.build();


                int width = getResources().getDisplayMetrics().widthPixels;
                int height = getResources().getDisplayMetrics().heightPixels;
                int padding = (int) (width * 0.10); // offset from edges of the map 10% of screen

                CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);
                Marker marker22 = googleMap.addMarker(marker16);

                marker22.showInfoWindow();

//                googleMap.setMyLocationEnabled(true);
      //          Criteria criteria = new Criteria();
    //            locationManager = (LocationManager) getContext().getSystemService(LOCATION_SERVICE);
  //              provider = locationManager.getBestProvider(criteria, true);
//                Location location = locationManager.getLastKnownLocation(provider);
//                locationManager.requestLocationUpdates(bestProvider, 1000, 0, (LocationListener) getContext());

//                double latitude = location.getLatitude();
//                double longitude = location.getLongitude();
        //LatLng myPosition = new LatLng(latitude, longitude);

               // mMap.animateCamera(cu);
                /*CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(sydney)             // Sets the center of the map to Mountain View
                        .bearing(90)                // Sets the orientation of the camera to east
                        .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                        .build();                   // Creates a CameraPosition from the builder
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition)); */
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(main_stage, 15));

// Zoom in, animating the camera.
                mMap.animateCamera(CameraUpdateFactory.zoomIn());

// Zoom out to zoom level 10, animating with a duration of 2 seconds.
                mMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);

// Construct a CameraPosition focusing on Mountain View and animate the camera to that position.
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(main_stage)      // Sets the center of the map to Mountain View
                        .zoom(17)                   // Sets the zoom
                        .bearing(90)                // Sets the orientation of the camera to east
                        .tilt(45)                   // Sets the tilt of the camera to 30 degrees
                        .build();                   // Creates a CameraPosition from the builder
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                button1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bounds = builder.build();

                        int width = getResources().getDisplayMetrics().widthPixels;
                        int height = getResources().getDisplayMetrics().heightPixels;
                        int padding = (int) (width * 0.10); // offset from edges of the map 10% of screen

                        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);
                        mMap.animateCamera(cu);
                    }
                });
                button2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15));
                        CameraPosition cameraPosition = new CameraPosition.Builder()
                                .target(main_stage)      // Sets the center of the map to Mountain View
                                .zoom(17)                   // Sets the zoom
                                .bearing(90)                // Sets the orientation of the camera to east
                                .tilt(90)                   // Sets the tilt of the camera to 30 degrees
                                .build();                   // Creates a CameraPosition from the builder
                        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                    }
                });
                // Position the map's camera near Sydney, Australia.
                //googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(28.5267345,77.5731743)));
                //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(markerLatLng, DEFAULT_ZOOM));
               // googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker Title").snippet("Marker Description"));
                //googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(getContext(), R.raw.style_json));



            }
        });

        checkLocationPermission();

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }


    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                    || ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(getActivity())
                        .setTitle("Location")
                        .setMessage("Enable location")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(getActivity(),
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else{
            Log.e(TAG, "Map loading");
            mMapView.getMapAsync(this);
            mMapView.onResume(); // needed to get the map to display immediately

            try {
                MapsInitializer.initialize(getActivity().getApplicationContext());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0){
                    if(grantResults.length == 2){
                        if(grantResults[0] == PackageManager.PERMISSION_GRANTED || grantResults[1] == PackageManager.PERMISSION_GRANTED){
                            mMapView.getMapAsync(this);
                        }
                    }
                    else{
                        if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                            mMapView.getMapAsync(this);
                        }
                }
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                }
                return;
            }

        }
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
            }


            Log.e(TAG, "Map is ready");

            //googleMap.setMyLocationEnabled(true);

            // For dropping a marker at a point on the Ma

            try {
                // Customise the styling of the base map using a JSON object defined
                // in a raw resource file.
                boolean success = googleMap.setMapStyle(
                        MapStyleOptions.loadRawResourceStyle(
                                getContext(), R.raw.style_json));
                Log.e(TAG, String.valueOf(success));
                if (!success) {
                    Log.e(TAG, "Style parsing failed.");
                }
            } catch (Resources.NotFoundException e) {
                Log.e(TAG, "Can't find style. Error: ", e);
            }
        final LatLngBounds.Builder builder = new LatLngBounds.Builder();
        final LatLng d_block = new LatLng(28.525377, 77.575448);
        LatLng b_block = new LatLng(28.526261, 77.576537);
        LatLng c_block = new LatLng(28.525974, 77.575863);
        LatLng a_block = new LatLng(28.526721, 77.577139);
        LatLng library = new LatLng(28.524945, 77.574395);
        LatLng lake = new LatLng(28.525177, 77.576923);
        LatLng ab_atrium = new LatLng(28.526372, 77.576805);
        LatLng mountSnu = new LatLng(28.526170, 77.575117);
        LatLng central_vista = new LatLng(28.525759, 77.574609);
        LatLng dh2 = new LatLng(28.524473, 77.570245);
        LatLng football = new LatLng(28.523075, 77.571815);
        LatLng ISC = new LatLng(28.521413, 77.571175);
        LatLng tennis = new LatLng(28.524066, 77.571332);
        LatLng volleyball = new LatLng(28.524520, 77.571674);
        LatLng baskbetball = new LatLng(28.524132, 77.571147);
        final LatLng main_stage = new LatLng(28.526047, 77.571124);

        final MarkerOptions marker1 =new MarkerOptions().position(d_block).title("D Dlock").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location));
        final MarkerOptions marker2 =new MarkerOptions().position(b_block).title("B Block").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location));
        final MarkerOptions marker3 =new MarkerOptions().position(c_block).title("C Block").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location));
        final MarkerOptions marker4 =new MarkerOptions().position(a_block).title("A Block").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location));
        final MarkerOptions marker5 =new MarkerOptions().position(library).title("Library").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location));
        final MarkerOptions marker6 =new MarkerOptions().position(lake).title("Lake").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location));
        final MarkerOptions marker7 =new MarkerOptions().position(ab_atrium).title("A-B Atrium").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location));
        final MarkerOptions marker8 =new MarkerOptions().position(mountSnu).title("Mount SNU").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location));
        final MarkerOptions marker9 =new MarkerOptions().position(central_vista).title("Central Vista").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location));
        final MarkerOptions marker10 =new MarkerOptions().position(dh2).title("DH2").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location));
        final MarkerOptions marker11=new MarkerOptions().position(football).title("Football Field").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location));
        final MarkerOptions marker12 =new MarkerOptions().position(ISC).title("Indoor Sports Complex").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location));
        final MarkerOptions marker13 =new MarkerOptions().position(tennis).title("Tennis Court").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location));
        final MarkerOptions marker14 =new MarkerOptions().position(volleyball).title("Volleyball Court").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location));
        final MarkerOptions marker15 =new MarkerOptions().position(baskbetball).title("Basketball Court").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location));
        final MarkerOptions marker16 =new MarkerOptions().position(main_stage).title("Main Stage").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location));

//the include method will calculate the min and max bound.
        builder.include(marker1.getPosition());
        builder.include(marker2.getPosition());
        builder.include(marker3.getPosition());
        builder.include(marker4.getPosition());
        builder.include(marker5.getPosition());
        builder.include(marker6.getPosition());
        builder.include(marker7.getPosition());
        builder.include(marker8.getPosition());
        builder.include(marker9.getPosition());
        builder.include(marker10.getPosition());
        builder.include(marker11.getPosition());
        builder.include(marker12.getPosition());
        builder.include(marker13.getPosition());
        builder.include(marker14.getPosition());
        builder.include(marker15.getPosition());
        builder.include(marker16.getPosition());

        bounds = builder.build();

            int width = getResources().getDisplayMetrics().widthPixels;
            int height = getResources().getDisplayMetrics().heightPixels;
            int padding = (int) (width * 0.10); // offset from edges of the map 10% of screen

            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);
            Marker marker21 = googleMap.addMarker(marker1);
            Marker marker22 = googleMap.addMarker(marker2);
            Marker marker33 = googleMap.addMarker(marker3);
            Marker marker44 =  googleMap.addMarker(marker4);
            Marker marker45 =  googleMap.addMarker(marker5);
            Marker marker46 =  googleMap.addMarker(marker6);
            Marker marker47 =  googleMap.addMarker(marker7);
            Marker marker48 =  googleMap.addMarker(marker8);
            Marker marker49 =  googleMap.addMarker(marker9);
            Marker marker83 =  googleMap.addMarker(marker10);
            Marker marker85 =  googleMap.addMarker(marker11);
            Marker marker86 =  googleMap.addMarker(marker12);
            Marker marker90 =  googleMap.addMarker(marker13);
            Marker marker460 =  googleMap.addMarker(marker14);
            Marker marker461 =  googleMap.addMarker(marker15);
            Marker marker462 =  googleMap.addMarker(marker16);

            googleMap.setMyLocationEnabled(true);
            Criteria criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_FINE);
            criteria.setAltitudeRequired(true);
            criteria.setBearingRequired(true);
            criteria.setCostAllowed(true);
            criteria.setPowerRequirement(Criteria.POWER_LOW);
            locationManager = (LocationManager) getContext().getSystemService(LOCATION_SERVICE);
            provider = locationManager.getBestProvider(criteria, true);
    //        locationManager.requestLocationUpdates(provider, 1000, 0, this);
//            Location location = locationManager.getLastKnownLocation(provider);
 //           double latitude = location.getLatitude();
  //          double longitude = location.getLongitude();
      //      LatLng myPosition = new LatLng(latitude, longitude);

            googleMap.animateCamera(cu);
            button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bounds = builder.build();

                    int width = getResources().getDisplayMetrics().widthPixels;
                    int height = getResources().getDisplayMetrics().heightPixels;
                    int padding = (int) (width * 0.10); // offset from edges of the map 10% of screen

                    CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);
                    googleMap.animateCamera(cu);
                }
            });
            // Position the map's camera near Sydney, Australia.
            //googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(28.5267345,77.5731743)));
            //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(markerLatLng, DEFAULT_ZOOM));
            // googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker Title").snippet("Marker Description"));
            //googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(getContext(), R.raw.style_json));
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}