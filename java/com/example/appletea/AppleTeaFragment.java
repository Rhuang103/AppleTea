package com.example.appletea;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.ContextCompat;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Map;

public class AppleTeaFragment extends SupportMapFragment {
    private static final String TAG = "AppleTeaFragment";


    //new way to request permissions


    //array that lists all the permissions needed
    private static final String[] LOCATION_PERMISSIONS = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
    };

    private static final int REQUEST_LOCATION_PERMISSIONS = 0;

    //Google map object that can do google map features
    private GoogleMap mMap;

    //Variable to store current location
    private Location mCurrentLocation;

    private GoogleApiClient mClient; //client to use google play services

    public static AppleTeaFragment newInstance() {
        return new AppleTeaFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        /**
         * configure client instance with specific API's to be used
         */
        mClient = new GoogleApiClient.Builder(getActivity())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(Bundle bundle) {
                        getActivity().invalidateOptionsMenu();
                        /**
                         * Once we are connected, attempt to get current location
                         */
                        if(hasLocationPermission()) {
                            findLocation();
                        } else {
                            //requestPermissions(LOCATION_PERMISSIONS,REQUEST_LOCATION_PERMISSIONS);
                            requestPermissionLauncher.launch(
                                    LOCATION_PERMISSIONS);
                            //Manifest.permission.ACCESS_FINE_LOCATION);
                        }
                    }

                    @Override
                    public void onConnectionSuspended(int i) {

                    }
                })
                .build();

        getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;
                updateUI();
            }
        });
    }

    /**
     * Connect to the google client on start
     */
    @Override
    public void onStart() {
        super.onStart();

        //shuts down the menu button until we know client is connected
        getActivity().invalidateOptionsMenu();
        mClient.connect();
    }

    /**
     * Disconnect the google client on stop
     */
    @Override
    public void onStop() {
        super.onStop();

        mClient.disconnect();
    }

    //Creates the menu items
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragement_appletea, menu);

        MenuItem searchItem = menu.findItem(R.id.action_locate);

        //enables action_locate buttons if client is connected
        searchItem.setEnabled(mClient.isConnected());
    }

    //Hooks up the search button to execute a function
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_locate:
                if(hasLocationPermission()) {
                    findLocation();
                } else {
                    requestPermissionLauncher.launch(
                            LOCATION_PERMISSIONS);
                }
                return true;
            default :
                return super.onOptionsItemSelected(item);
        }
    }



    /**
     * Updated callback to check whether you obtained the permission or not
     */
    private ActivityResultLauncher<String[]> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(),
                    new ActivityResultCallback<Map<String, Boolean>>() {
                @Override
                public void onActivityResult(Map<String, Boolean> result) {
                    if (result.get(Manifest.permission.ACCESS_COARSE_LOCATION) != null &&
                        result.get(Manifest.permission.ACCESS_FINE_LOCATION) != null){
                        findLocation();
                    }
                }
            });

    /**
     * Callback to check whether you obtained the permission or not

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        switch(requestCode) {
            case REQUEST_LOCATION_PERMISSIONS:
                if(hasLocationPermission()) {
                    findLocation();
                }
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    */



    /**
     * Function to get your location fix
     * Set to a single high precise location fix
     *
     * Will need to alter for AppleTea
     */
    private void findLocation() {
        LocationRequest request = LocationRequest.create();
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY); //request high accuracy
        request.setNumUpdates(1); //how often to update
        request.setInterval(0); //get location fix ASAP



        /**
         * Send request to location services an wait for a reply
         *
         * If it were a longer request with more updates, would need removeLocationsUpdates()
         */
        LocationServices.FusedLocationApi
                .requestLocationUpdates(mClient, request, new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        Log.i(TAG, "Got a fix" + location);
                        mCurrentLocation = location;
                        updateUI();
                    }
                });


    }

    /**
     * Function to check if you have the required permissions
     */
    private boolean hasLocationPermission() {
        int result = ContextCompat
                .checkSelfPermission(getActivity(), LOCATION_PERMISSIONS[0]);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * Function that performs google map stuff like zoom
     */
    private void updateUI() {
        if(mMap == null || mCurrentLocation == null){
            return;
        }

        LatLng myPoint = new LatLng(
                mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());


        MarkerOptions myMarker = new MarkerOptions().position(myPoint);

        mMap.clear();
        mMap.addMarker(myMarker);

        LatLngBounds bounds = new LatLngBounds.Builder()
                .include(myPoint)
                .build();

        int margin = getResources().getDimensionPixelSize(R.dimen.map_inset_margin);
        CameraUpdate update = CameraUpdateFactory.newLatLngBounds(bounds, 100);
        mMap.animateCamera(update);
    }




}
