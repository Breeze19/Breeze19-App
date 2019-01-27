package snu.breeze.breeze19;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


import snu.breeze.breeze19.R;

import static android.content.ContentValues.TAG;

public class MapViewFragment extends Fragment {
    private final String TAG = MapViewFragment.class.getSimpleName();

    private MapView mMapView;
    private GoogleMap googleMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.mapview_fragmnet, container, false);

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
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;
                Log.e(TAG, "Map is ready");

                //googleMap.setMyLocationEnabled(true);

                // For dropping a marker at a point on the Ma
                Log.e(TAG, "Map is ready");

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
                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                LatLng sydney = new LatLng(28.5267345,77.5731743);
                LatLng sydney1 = new LatLng(28.525427, 77.575383);
                LatLng sydney2 = new LatLng(28.526492, 77.572689);
                LatLng sydney3 = new LatLng(28.524774, 77.572937);
                MarkerOptions marker1 =new MarkerOptions().position(sydney).title("title1").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location));
                MarkerOptions marker2 =new MarkerOptions().position(sydney1).title("title2").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location));
                MarkerOptions marker3 =new MarkerOptions().position(sydney2).title("title3").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location));
                MarkerOptions marker4 =new MarkerOptions().position(sydney3).title("title4").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location));

//the include method will calculate the min and max bound.
                builder.include(marker1.getPosition());
                builder.include(marker2.getPosition());
                builder.include(marker3.getPosition());
                builder.include(marker4.getPosition());

                LatLngBounds bounds = builder.build();

                int width = getResources().getDisplayMetrics().widthPixels;
                int height = getResources().getDisplayMetrics().heightPixels;
                int padding = (int) (width * 0.10); // offset from edges of the map 10% of screen

                CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);
                googleMap.addMarker(marker1);
                googleMap.addMarker(marker2);
                googleMap.addMarker(marker3);
                googleMap.addMarker(marker4);

                mMap.animateCamera(cu);
                // Position the map's camera near Sydney, Australia.
                //googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(28.5267345,77.5731743)));
                //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(markerLatLng, DEFAULT_ZOOM));
               // googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker Title").snippet("Marker Description"));
                //googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(getContext(), R.raw.style_json));



            }
        });

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
}