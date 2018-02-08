package demo.acatch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    GPSTracker gpsTracker;
    TextView textView,textView1,textView2,textView3,textView4;
    String latitude,longitude,add,country,pincode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView=(TextView)findViewById(R.id.lat);
        textView1=(TextView)findViewById(R.id.lat);
        textView2=(TextView)findViewById(R.id.count);
        textView3=(TextView)findViewById(R.id.add);
        textView4=(TextView)findViewById(R.id.pin);




    }
    public void My_Location(View view)
    {
        gpsTracker=new GPSTracker(this);
        double lati=gpsTracker.getLatitude();
        double longi=gpsTracker.getLongitude();
        latitude=Double.toString(lati);
        longitude=Double.toString(longi);
        textView.setText("Latitude= " + latitude);
        textView1.setText("Longitude=" + longitude);
        country = gpsTracker.getCountryName(this);
        textView2.setText("Country=" + country);
        add=gpsTracker.getAddressLine(this);
        textView3.setText("Address=" + add);
        pincode=gpsTracker.getPostalCode(this);
        textView4.setText("Postalcode=" + pincode);

    }
public void Map(View view){
    Intent intent=new Intent(MainActivity.this,MapsActivity.class);
    intent.putExtra("latitude",latitude);
    intent.putExtra("longitude",longitude);
    intent.putExtra("add",add);
    intent.putExtra("country",country);
    intent.putExtra("pincode",pincode);
    startActivity(intent);
}
}
