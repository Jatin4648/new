package demo.acatch;

import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import demo.acatch.DataParser;
import demo.acatch.DownloadUrl;

/**
 * Created by ASUS on 20-10-2017.
 */
public class GetNearbyPlacesData extends AsyncTask<Object,String,String> {
    String googlePlacesData;
    GoogleMap mMap;
    String url;

    @Override
    protected String doInBackground(Object... objects){
        mMap= (GoogleMap)objects[0];
        url=(String)objects[1];
        DownloadUrl downloadUrl=new DownloadUrl();
        try {
            googlePlacesData=downloadUrl.readUrl(url);
        }catch (NullPointerException e){

        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return googlePlacesData;
    }

    @Override
    protected void onPostExecute(String s) {
        List<HashMap<String,String>>nearbyPlaceList;
        DataParser parser=new DataParser();
        nearbyPlaceList=parser.parse(s);

        Log.d("nearby place data", "called parse method ");
        showNearByPlaces(nearbyPlaceList);
    }
    public void showNearByPlaces(List<HashMap<String,String>>nearbyPlaceList){
        for (int i=0;i<nearbyPlaceList.size();i++){

            MarkerOptions markerOptions=new MarkerOptions();
            HashMap<String,String>googlePlace=nearbyPlaceList.get(i);

            String placeName=googlePlace.get("place_name");
            Log.d("placeName",""+placeName);
            String vicinity=googlePlace.get("vicinity");
            Log.d("vicinity",""+vicinity);

            double latitude=Double.parseDouble(googlePlace.get("latitude"));
            double longitude=Double.parseDouble(googlePlace.get("longitude"));
            Log.d("latitude",""+latitude + " " +longitude);

            LatLng india=new LatLng( latitude,longitude);

            markerOptions.position(india);
            markerOptions.title(placeName + ":"+vicinity);
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
            mMap.addMarker(markerOptions);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(india));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
        }
    }
}
