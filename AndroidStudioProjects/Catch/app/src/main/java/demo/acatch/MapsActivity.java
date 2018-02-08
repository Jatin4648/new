package demo.acatch;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
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

import static com.google.android.gms.analytics.internal.zzy.e;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener,LocationListener
{

    private GoogleMap mMap;
    double latitude, longitude;
    String lati,longi,country,add,pincode;
     EditText editText;
    String location;
    Geocoder geocoder;
    String testJ;
    StringBuilder googlePlaceUrl;

    int PROXIMITY_RADIUS=10000;
    public static final int REQUEST_LOCATION_CODE = 99;
    Object dataTransfer[]=new Object[2];

    LocationRequest locationRequest;
    GoogleApiClient client;
    Location lastlocation;
    Marker currentLocationmMarker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        lati = getIntent().getExtras().getString("latitude");
        longi = getIntent().getExtras().getString("longitude" );
        country = getIntent().getExtras().getString("country");
        add = getIntent().getExtras().getString("add");
        pincode=getIntent().getExtras().getString("pincode");
    }
    public void onClick(View view) {
        GetNearbyPlacesData getNearbyPlacesData=new GetNearbyPlacesData();
        switch (view.getId())
        {

            case R.id.srch:
                List<Address> addressList;
        editText = (EditText) findViewById(R.id.sarch);
        location = editText.getText().toString();
        if (location != null || !location.equals("")) {
            geocoder = new Geocoder(this);
            try {
                addressList = geocoder.getFromLocationName(location, 5);

            if (addressList != null) {

                for (int i = 0; i < addressList.size(); i++) {
                    android.location.Address address = addressList.get(i);
                    LatLng india = new LatLng(address.getLatitude(), address.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(india));
                }
            }
            }
            catch (IOException e) {
                e.printStackTrace();
        }}

       break;
            case R.id.hospi:
                mMap.clear();// put a toast here so that we check if the flow pointer coming here
                Toast.makeText(this, "hey", Toast.LENGTH_SHORT).show();
                String hospital="hospital";
                String url=getUrl(latitude,longitude,hospital);
                dataTransfer[0]=mMap;
                dataTransfer[1]=url;
                testJ="its me app";
                getNearbyPlacesData.execute(dataTransfer);
              if(getNearbyPlacesData.getStatus()== AsyncTask.Status.PENDING) {
                   getNearbyPlacesData.execute(dataTransfer);
                   Toast.makeText(MapsActivity.this, "Nearby hospitals", Toast.LENGTH_SHORT).show();
               }
               else {
                   Toast.makeText(MapsActivity.this, "Nearby hospitals", Toast.LENGTH_SHORT).show();
               }
                break;
            case R.id.food:
                mMap.clear();
                String restaurant="restaurant";
                url=getUrl(latitude,longitude,restaurant);
                dataTransfer[0]=mMap;
                dataTransfer[1]=url;
                getNearbyPlacesData.execute(dataTransfer);
                Toast.makeText(MapsActivity.this, "Nearby restaurants", Toast.LENGTH_SHORT).show();
                break;
            case R.id.college:
                mMap.clear();
                String college="university";
                url=getUrl(latitude,longitude,college);
                dataTransfer[0]=mMap;
                dataTransfer[1]=url;
                getNearbyPlacesData.execute(dataTransfer);
                Toast.makeText(MapsActivity.this, "Nearby college", Toast.LENGTH_SHORT).show();
                break;

        }
    }



    private String getUrl(double latitude,double longitude,String nearbyPlaces){
         googlePlaceUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
      googlePlaceUrl.append("location="+latitude+","+longitude);
        googlePlaceUrl.append("&radius="+PROXIMITY_RADIUS);
        googlePlaceUrl.append("&type="+nearbyPlaces);
        googlePlaceUrl.append("&sensor=true");
        googlePlaceUrl.append("&key="+"AIzaSyD_JmzeX2ItDPmufpbV6_0RQQXUokc8dIQ");

        Log.d("MapsActivity", "url = "+googlePlaceUrl.toString());

        return googlePlaceUrl.toString();
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

        latitude = Double.parseDouble(lati);
        longitude = Double.parseDouble(longi);

        LatLng india = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(india).title("Marker"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(india));
        // Add a marker in Sydney and move the camera

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().isCompassEnabled();
        mMap.getUiSettings().isZoomControlsEnabled();
        mMap.getUiSettings().isZoomGesturesEnabled();
    }
    protected synchronized void bulidGoogleApiClient() {
        client = new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
        client.connect();

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
         locationRequest = new LocationRequest();
        locationRequest.setInterval(100);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);


        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            LocationServices.FusedLocationApi.requestLocationUpdates(client, locationRequest, (com.google.android.gms.location.LocationListener) this);
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        lastlocation = location;
        if(currentLocationmMarker != null)
        {
            currentLocationmMarker.remove();

        }
        Log.d("lat = ",""+latitude);
        LatLng latLng = new LatLng(location.getLatitude() , location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Location");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        currentLocationmMarker = mMap.addMarker(markerOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomBy(10));

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
