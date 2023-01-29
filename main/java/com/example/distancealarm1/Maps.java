package com.example.distancealarm1;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Maps extends AppCompatActivity implements OnMapReadyCallback {
    boolean isPermissionGranter = true;
    GoogleMap googleMap;
    ImageView imageViewSearch;
    EditText inputLocation;
    MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        getSupportActionBar().hide();
        imageViewSearch = findViewById(R.id.imageViewSearch);
        inputLocation = findViewById(R.id.inputLocation);

        //checkPermission();
        if (isPermissionGranter) {
            if (checkGooglePlaServices()) {
//
                SupportMapFragment supportMapFragment = SupportMapFragment.newInstance();
                getSupportFragmentManager().beginTransaction().add(R.id.container, supportMapFragment).commit();
                supportMapFragment.getMapAsync(this);


            } else {
                Toast.makeText(this, "Google Playservices Not Available ", Toast.LENGTH_SHORT).show();
            }
        }



//        imageViewSearch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
////                String location = inputLocation.getText().toString();
////                    Geocoder geocoder = new Geocoder(Maps.this, Locale.getDefault());
////                    try {
////                        List<Address> listAddress = geocoder.getFromLocationName(location, 1);
////                        if (listAddress.size() > 0) {
////                            LatLng latLng = new LatLng(listAddress.get(0).getLatitude(), listAddress.get(0).getLongitude());
////                            MarkerOptions markerOptions = new MarkerOptions();
////                            markerOptions.title("My Search Position");
////                            markerOptions.position(latLng);
////                            googleMap.addMarker(markerOptions);
////                            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 5);
////                            googleMap.animateCamera(cameraUpdate);
////                        }
////                    } catch (IOException e) {
////                        e.printStackTrace();
////                    }
//
//                Toast.makeText(Maps.this,"Hey there!",Toast.LENGTH_SHORT).show();
//                LatLng latLng = new LatLng(0, 0);
//                MarkerOptions markerOptions = new MarkerOptions();
//                markerOptions.title("My position");
//                markerOptions.position(latLng);
//                googleMap.addMarker(markerOptions);
//                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLng(latLng);
//                googleMap.moveCamera(cameraUpdate);
//
//            }
//        });

    }


    private void checkPermission() {
        Dexter.withContext(this).withPermission(Manifest.permission.ACCESS_FINE_LOCATION).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                isPermissionGranter = true;
                Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), "");
                intent.setData(uri);
                startActivity(intent);
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).check();
    }

    private boolean checkGooglePlaServices() {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int result = googleApiAvailability.isGooglePlayServicesAvailable(this);
        if (result == ConnectionResult.SUCCESS) {
            return true;
        } else if (googleApiAvailability.isUserResolvableError(result)) {
            Dialog dialog = googleApiAvailability.getErrorDialog(this, result, 201, new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    Toast.makeText(Maps.this, "User Cancelled Dialoge", Toast.LENGTH_SHORT).show();
                }
            });
            dialog.show();
        }
        return false;
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        mapView.onStart();
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        mapView.onResume();
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        mapView.onStop();
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        mapView.onPause();
//    }
//
//    @Override
//    protected void onSaveInstanceState(@NonNull Bundle outState) {
//        super.onSaveInstanceState(outState);
//        mapView.onSaveInstanceState(outState);
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        mapView.onDestroy();
//    }
//
//    @Override
//    public void onLowMemory() {
//        super.onLowMemory();
//        mapView.onLowMemory();
//    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        googleMap = googleMap;
        LatLng latLng = new LatLng(21.8, 78.9);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.title("My Position");
        markerOptions.position(latLng);
        googleMap.addMarker(markerOptions);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 5);
        googleMap.moveCamera(cameraUpdate);
        googleMap.getUiSettings().setZoomControlsEnabled(true);


        GoogleMap finalGoogleMap = googleMap;
        GoogleMap finalGoogleMap1 = googleMap;
        imageViewSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Toast.makeText(Maps.this,"Successful!",Toast.LENGTH_SHORT).show();
//                LatLng latLng = new LatLng(0, 0);
//                MarkerOptions markerOptions = new MarkerOptions();
//                markerOptions.title("My position");
//                markerOptions.position(latLng);
//                finalGoogleMap.addMarker(markerOptions);
//                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLng(latLng);
//                finalGoogleMap.moveCamera(cameraUpdate);


                String location;
                location = inputLocation.getText().toString();
                Geocoder geocoder = new Geocoder(Maps.this, Locale.getDefault());
                    try {
                        List<Address> listAddress = geocoder.getFromLocationName(location, 1);
                        if (listAddress.size() > 0) {
                            LatLng latLng = new LatLng(listAddress.get(0).getLatitude(), listAddress.get(0).getLongitude());
                            MarkerOptions markerOptions = new MarkerOptions();
                            markerOptions.title("My Search Position");
                            markerOptions.position(latLng);
                            finalGoogleMap1.addMarker(markerOptions);
                            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 10);
                            finalGoogleMap1.animateCamera(cameraUpdate);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }



            }
        });


    }
}