package com.japhdroid.heizkosten;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements IBaseGpsListener {

    Status status = new Status();
    CostCalculator calculator = new CostCalculator();
    LocationManager locationManager;
    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        sharedPref = getPreferences(Context.MODE_PRIVATE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setAllowedSpeed(sharedPref.getInt("ALLOWED_SPEED", 50));
        setCity(sharedPref.getBoolean("IN_CITY", true));
        startLocationUpdates();
    }

    @Override
    protected void onStop() {
        stopLocationUpdates();
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("ALLOWED_SPEED", status.allowedSpeed);
        editor.putBoolean("IN_CITY", status.inCity);
        editor.commit();
        super.onStop();
    }

    public void startLocationUpdates() {
        LinearLayout costLayout = (LinearLayout) findViewById(R.id.costLayout);
        costLayout.setBackgroundColor(Color.parseColor("#CCCCCC")); // grey
        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        } catch (SecurityException e) {
            Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void stopLocationUpdates() {
        try {
            locationManager.removeUpdates(this);
        } catch (SecurityException e) {
            Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void increaseAllowedSpeed(View view) {
        TextView tv = (TextView) findViewById(R.id.allowedSpeed);
        tv.setText(String.valueOf(Integer.parseInt(tv.getText().toString()) + 10));
        status.allowedSpeed += 10;
    }

    public void decreaseAllowedSpeed(View view) {
        TextView tv = (TextView) findViewById(R.id.allowedSpeed);
        if (Integer.parseInt(tv.getText().toString()) > 0) {
            tv.setText(String.valueOf(Integer.parseInt(tv.getText().toString()) - 10));
            status.allowedSpeed -= 10;
        }
    }

    public void setAllowedSpeed(int speed) {
        TextView tv = (TextView) findViewById(R.id.allowedSpeed);
        tv.setText(String.valueOf(speed));
        status.allowedSpeed = speed;
    }

    public void toggleCity(View view) {
        setCity(!status.inCity);
    }

    public void setCity(boolean inCity) {
        status.inCity = inCity;
        TextView tvCity = (TextView) findViewById(R.id.inCity);
        tvCity.setText(status.inCity ? "Innerorts" : "Außerorts");
    }

    public void updateActualSpeed() {
        TextView tv = (TextView) findViewById(R.id.actualSpeed);
        tv.setText(String.valueOf(status.actualSpeed));
    }

    public void updateStatus(Speeding speeding) {
        TextView tvFee = (TextView) findViewById(R.id.fee);
        TextView tvPoints = (TextView) findViewById(R.id.points);
        TextView tvRevocation = (TextView) findViewById(R.id.revocation);
        tvFee.setText(speeding.getFeeStr());
        tvPoints.setText(speeding.getPointsStr());
        tvRevocation.setText(speeding.getRevocationStr());
        LinearLayout costLayout = (LinearLayout) findViewById(R.id.costLayout);
        if (speeding.getPoints() > 0)
            costLayout.setBackgroundColor(Color.parseColor("#FF6666")); // red
        else if (speeding.getFee() > 0)
            costLayout.setBackgroundColor(Color.parseColor("#FFFF66")); // yellow
        else
            costLayout.setBackgroundColor(Color.parseColor("#66B266")); // green
    }

    public void updateSpeed(CLocation location) {
        float nCurrentSpeed = 0;

        TextView tv = (TextView) findViewById(R.id.updateCounter);
        tv.setText(String.valueOf(Integer.parseInt(tv.getText().toString()) + 1));
        if (location != null) {
            tv.setTextColor(Color.BLACK);
            location.setUseMetricunits(true);
            nCurrentSpeed = location.getSpeed();
            status.actualSpeed = Math.round(nCurrentSpeed);
        } else {
            tv.setTextColor(Color.LTGRAY);
            status.actualSpeed = 0;
        }
        updateActualSpeed();
        updateStatus(calculator.speeding(status));
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            CLocation myLocation = new CLocation(location, true);
            this.updateSpeed(myLocation);
        }
    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onGpsStatusChanged(int event) {

    }
}
