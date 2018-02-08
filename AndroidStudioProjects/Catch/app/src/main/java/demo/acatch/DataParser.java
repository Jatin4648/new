package demo.acatch;

import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ASUS on 20-10-2017.
 */
public class DataParser {
    private HashMap<String,String>getPlace(JSONObject googlePlaceJason)
    {
        Log.d("DataParser", "i m in parser");
        HashMap<String,String>googlePlaceMap=new HashMap<>();
        String placeName="-NA-";
        String vicinity="-NA-";
        String latitude="";
        String longitude="";
        String reference="";
        Log.d("DataParser", "jsonObject="+googlePlaceJason.toString() );
        try{
        if(!googlePlaceJason.isNull("name"))
        {
            Log.d("DataParser", "Hello I am ok");
                placeName=googlePlaceJason.getString("name");
            }
            if(!googlePlaceJason.isNull("vicinity")){
                vicinity=googlePlaceJason.getString("vicinity");
            }
           // latitude = "45.5016889";
            //longitude="-73.567256";
           // longitude = googlePlaceJason.getJSONObject("geometry").getJSONObject("location").getString("lng");
            //reference = googlePlaceJason.getString("reference");
            latitude = googlePlaceJason.getJSONObject("geometry").getJSONObject("location").getString("lat");
            longitude = googlePlaceJason.getJSONObject("geometry").getJSONObject("location").getString("lng");
            reference = googlePlaceJason.getString("reference");
            googlePlaceMap.put("place_name",placeName);
            googlePlaceMap.put("vicinity",vicinity);
            googlePlaceMap.put("latitude",latitude);
            googlePlaceMap.put("longitude",longitude);
            googlePlaceMap.put("reference",reference);
        }
        catch (JSONException e) {
                e.printStackTrace();
            }
        return googlePlaceMap;
        }
    private List<HashMap<String,String>> getPlaces(JSONArray jsonArray){
        int count=jsonArray.length();
        List<HashMap<String,String>>placesList=new ArrayList<>();
        HashMap<String,String>placeMap=null;
        for (int i=0;i<count;i++){
            try {
                placeMap=getPlace((JSONObject)jsonArray.get(i));
                placesList.add(placeMap);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return placesList;

    }
    public List<HashMap<String,String>> parse(String jsonData){
        JSONArray jsonArray=null;
        JSONObject jsonObject;
        try {
            jsonObject=new JSONObject(jsonData);
            jsonArray=jsonObject.getJSONArray("results");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return getPlaces(jsonArray);

    }
}
