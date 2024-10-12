package com.fit2081.fit2081assignment3;

import androidx.fragment.app.FragmentActivity;

import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.fit2081.fit2081assignment3.databinding.ActivityGoogleMapBinding;

import java.util.Locale;

public class GoogleMapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityGoogleMapBinding binding;
    private String locationToFocus;
    private Geocoder geocoder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityGoogleMapBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        geocoder = new Geocoder(this, Locale.getDefault());

        locationToFocus = getIntent().getExtras().getString("location",null);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        findCountryMoveCamera();
    }

    private void findCountryMoveCamera() {
//        Toast.makeText(this, "API Check: " + locationToFocus, Toast.LENGTH_SHORT).show();

        // getFromLocationName method works for API 33 and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

            if (locationToFocus != null) {
                /**
                 * countryToFocus: String value, any string we want to search
                 * maxResults: how many results to return if search was successful
                 * successCallback method: if results are found, this method will be executed
                 *                          runs in a background thread
                 */
                geocoder.getFromLocationName(locationToFocus, 1, addresses -> {
                    // if there are results, this condition would return true
                    if (!addresses.isEmpty() && addresses != null) {
                        // run on UI thread as the user interface will update once set map location
                        runOnUiThread(() -> {
                            // define new LatLng variable using the first address from list of addresses
                            LatLng newAddressLocation = new LatLng(
                                    addresses.get(0).getLatitude(),
                                    addresses.get(0).getLongitude()
                            );

                            // repositions the camera according to newAddressLocation
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(newAddressLocation));

                            // just for reference add a new Marker
                            mMap.addMarker(
                                    new MarkerOptions()
                                            .position(newAddressLocation)
                                            .title(locationToFocus)
                            );

                            // set zoom level to 8.5f or any number between range of 2.0 to 21.0
                            mMap.animateCamera(CameraUpdateFactory.zoomTo(10.0f));
                        });
                    } else {
                        runOnUiThread(() -> {
                            LatLng defaultLocation = new LatLng(3.84997932752601, 102.04767013974775);
                            mMap.addMarker(new MarkerOptions().position(defaultLocation).title("Malaysia"));
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(defaultLocation));
                            mMap.animateCamera(CameraUpdateFactory.zoomTo(10.0f));
                            Toast.makeText(this, "Category address not found, displaying default", Toast.LENGTH_SHORT).show();
                        });
                    }
                });
            } else {
                System.out.println("happened2");
                runOnUiThread(() -> {
                    LatLng defaultLocation = new LatLng(3.84997932752601, 102.04767013974775);
                    mMap.addMarker(new MarkerOptions().position(defaultLocation).title("Malaysia"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(defaultLocation));
                    mMap.animateCamera(CameraUpdateFactory.zoomTo(10.0f));
                    Toast.makeText(this, "Displaying default (no location is given)", Toast.LENGTH_SHORT).show();
                });
            }
        }
    }
}