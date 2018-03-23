package example.mathias.contactinformation.Controller;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.common.api.GoogleApiClient;
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

    private final String CURRENT_LOCATION = "Current location";
    private final String PERMISSION_DENIED = "Permission denied..!";
    private final int ZOOM_AREA = 11;
    public static final int REQUEST_LOCATION_CODE = 99;

    private GoogleMap mMap;
    private GoogleApiClient mClient;
    private LocationRequest mLocationRequest;
    private Location mLastLocation;
    private Button mBtnSearch;
    private EditText mTxtAddress, mTxtFriend;

    private Marker mCurrentLocationMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        // Checks the version of the googleMap.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    // Search for an address and adds a title to the marker.
    public void onClick(View view) {
        if (view.getId() == R.id.btnSearch) {

            mTxtAddress = findViewById(R.id.txtAddress);
            mTxtFriend = findViewById(R.id.txtFriend);

            String location = mTxtAddress.getText().toString();
            String friendMarker = mTxtFriend.getText().toString();

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

    // To handle request permissions. (If granted or not).
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
        // Gets the current location if permissions is granted.
        // Type of map
        googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }

        // Add a marker for Skovgaard's home and move the camera
        LatLng skovgaard = new LatLng(55.479509, 8.4635673);
        mMap.addMarker(new MarkerOptions().position(skovgaard).title("Skovgaard's crib"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(skovgaard));

    }
    // Creates a user.
    protected synchronized void buildGoogleApiClient() {
        mClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        mClient.connect();
    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;

        if (mCurrentLocationMarker != null) {
            mCurrentLocationMarker.remove();
        }
        // Gets the latitude and longitude of current position.
        LatLng latitudeAndLongitude = new LatLng(location.getLatitude(), location.getLongitude());

        MarkerOptions markerOptions = new MarkerOptions();

        // Sets the position on the marker.
        markerOptions.position(latitudeAndLongitude);
        // Info on the marker.
        markerOptions.title(CURRENT_LOCATION);
        // The color of the marker
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));

        // Marks the new position.
        mCurrentLocationMarker = mMap.addMarker(markerOptions);

        // Moves the camera to the new position.
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latitudeAndLongitude));
        mMap.animateCamera(CameraUpdateFactory.zoomBy(ZOOM_AREA));

        // If no location is set.
        if (mClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mClient, this);
        }
    }

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



    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
