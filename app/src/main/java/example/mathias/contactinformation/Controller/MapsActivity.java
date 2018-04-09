package example.mathias.contactinformation.Controller;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

import example.mathias.contactinformation.R;


public class MapsActivity extends FragmentActivity
        implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    // Gets the current location of the user.
    private static final String CURRENT_LOCATION = "My location";
    // If user don't agree to permission is denied text will appear.
    private static final String PERMISSION_DENIED = "Permission denied..!";
    // The radius of the map shown for the user.
    private static final int ZOOM_AREA = 14;
    public static final int REQUEST_LOCATION_CODE = 99;

    // Intent specific EXTRAS
    public static final String EXTRA_CONTACT_NAME = "example.mathias.contactinformation.contact.name";
    public static final String EXTRA_CONTACT_ADDRESS = "example.mathias.contactinformation.contact.address";

    private GoogleMap mMap;
    private GoogleApiClient mClient;
    private LocationRequest mLocationRequest;
    private Marker mCurrentLocationMarker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        // Checks the Android build version.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Search for an address and adds a title to the marker.
     * @param view
     */
    public void onClick(View view) {
        if (view.getId() == R.id.btnSearch) {

            EditText txtAddress = findViewById(R.id.txtAddress);
            EditText txtFriend = findViewById(R.id.txtFriend);


            String location = txtAddress.getText().toString();
            String friendMarker = txtFriend.getText().toString();

            MarkerOptions markerOptions = new MarkerOptions();

            List<Address> addressList = null;

            // Don't search if nothing is in the string.
            if (!location.equals("")) {
                Geocoder geocoder = new Geocoder(this);

                // Takes two attributes locationName and maxResults.
                try {
                    addressList = geocoder.getFromLocationName(location, 5);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // Checks the size of the addressList.
                for (int i = 0; i < addressList.size() ; i++) {
                    Address myAddress = addressList.get(i);
                    // For each address i want to get the LatLng.
                    LatLng latLng = new LatLng(myAddress.getLatitude(), myAddress.getLongitude());
                    // Sets the marker.
                    markerOptions.position(latLng);

                    // Sets a title on the marker. Either the FriendName or Address.
                    if (friendMarker.equals("")) {
                        markerOptions.title(location);
                    } else {
                        markerOptions.title(friendMarker);
                    }
                    mMap.addMarker(markerOptions);
                    mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                }
            }
        }
    }

    /**
     * To handle request permissions. (If granted or not).
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_LOCATION_CODE:
                // Checks if the permission is granted or not.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission is granted
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        // If the client == null, then create a client.
                        if (mClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }
                } else {
                    Toast.makeText(this, PERMISSION_DENIED, Toast.LENGTH_LONG).show();
                }
                return;
        }
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

        // Type of map
        googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

        // Gets the current location if permissions is granted.
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }
    }

    /**
     * adds the marker of the selected contact.
     */
    private void contactMarker() {
        // Get the intent.
        Intent intent = getIntent();
        String contactName = intent.getStringExtra(EXTRA_CONTACT_NAME);
        String contactAddress = intent.getStringExtra(EXTRA_CONTACT_ADDRESS);


        if (contactName != null) {
            List<Address> addressList = null;

            Geocoder geocoder = new Geocoder(this);

            try {
                // there will only be one.
                addressList = geocoder.getFromLocationName(contactAddress, 1);
                // gets the first.
                Address myAddress = addressList.get(0);

                // For each address i want to get the LatLng.
                LatLng latLng = new LatLng(myAddress.getLatitude(), myAddress.getLongitude());

                // sets the contactName on the marker.
                Marker marker = mMap.addMarker(new MarkerOptions().position(latLng)
                        .title(contactName));

                // shows the marker when googleMap is opening.
                marker.showInfoWindow();

                // Moves to the contacts marker.
                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        mMap.animateCamera(CameraUpdateFactory.zoomTo(ZOOM_AREA));
    }

    /**
     * Creates a user in the map.
     */
    protected synchronized void buildGoogleApiClient() {
        mClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        mClient.connect();
    }

    /**
     * Checks if the user have changed location.
     * @param location
     */
    @Override
    public void onLocationChanged(Location location) {
        if (mCurrentLocationMarker != null) {
            mCurrentLocationMarker.remove();
        }
        // Gets the latitude and longitude of current position.
        LatLng currentPosition = new LatLng(location.getLatitude(), location.getLongitude());

        MarkerOptions markerOptions = new MarkerOptions();

        // Sets the position on the marker.
        markerOptions.position(currentPosition);
        // Info on the marker.
        markerOptions.title(CURRENT_LOCATION);
        // The color of the marker
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));

        // Marks the new position.
        mCurrentLocationMarker = mMap.addMarker(markerOptions);

        // If no location is set.
        if (mClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mClient, this);
        }
        // Moves the camera to your current position.
        mMap.moveCamera(CameraUpdateFactory.newLatLng(currentPosition));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(ZOOM_AREA));

        contactMarker();
    }

    /**
     * When to update and accuracy + permission check.
     * @param bundle
     */
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new LocationRequest();

        // 1000 is ms => 1sec.
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        //Balanced so it wont end up drying the phone from battery.
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        // Checks if you have agreed on the permission.
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mClient, mLocationRequest, this);
        }
    }

    /**
     * Permission check.
     * @return
     */
    public boolean checkLocationPermission() {
        // Checks the permission is granted.
        // If the app is NOT granted permission ask the user to grant permission.
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Checks ff the user already granted permission to use the location.
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.ACCESS_FINE_LOCATION }, REQUEST_LOCATION_CODE);
            } else {
                ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.ACCESS_FINE_LOCATION }, REQUEST_LOCATION_CODE);
            }
            // Returns false if the user don't grant the app to use location.
            return false;
        } else {
            // Returns false if the user don't grant the app to use location.
            return false;
        }
    }

    /**
     * Gets the map intent.
     *
     * @param content
     * @param contactName
     * @param contactAddress
     * @return MapActivity intent
     */
    public static Intent newIntent(Context content, String contactName, String contactAddress) {
        Intent intent = new Intent(content, MapsActivity.class);
        intent.putExtra(EXTRA_CONTACT_NAME, contactName);
        intent.putExtra(EXTRA_CONTACT_ADDRESS, contactAddress);
        return intent;
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
