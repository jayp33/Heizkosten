package com.japhdroid.heizkosten;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    Status status = new Status();
    CostCalculator calculator = new CostCalculator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

    public void increaseActualSpeed(View view) {
        TextView tv = (TextView) findViewById(R.id.actualSpeed);
        tv.setText(String.valueOf(Integer.parseInt(tv.getText().toString()) + 1));
        status.actualSpeed += 1;
    }

    public void decreaseActualSpeed(View view) {
        TextView tv = (TextView) findViewById(R.id.actualSpeed);
        if (Integer.parseInt(tv.getText().toString()) > 0) {
            tv.setText(String.valueOf(Integer.parseInt(tv.getText().toString()) - 1));
            status.actualSpeed -= 1;
        }
    }

    public void matchActualSpeedWithAllowedSpeed(View view) {
        TextView tv = (TextView) findViewById(R.id.actualSpeed);
        TextView tv1 = (TextView) findViewById(R.id.allowedSpeed);
        tv.setText(tv1.getText());
        status.actualSpeed = status.allowedSpeed;
    }

    public void toggleCity(View view) {
        status.inCity = !status.inCity;
        TextView tvCity = (TextView) findViewById(R.id.inCity);
        String inCity = status.inCity ? "Innerorts" : "Außerorts";
        tvCity.setText(inCity);
    }

    public void pushStatus(View view) {
        Speeding speeding = calculator.speeding(status);
        updateStatus(speeding);
    }

    public void updateStatus(Speeding speeding) {
        TextView tvFee = (TextView) findViewById(R.id.fee);
        TextView tvPoints = (TextView) findViewById(R.id.points);
        TextView tvRevocation = (TextView) findViewById(R.id.revocation);
        tvFee.setText(speeding.getFeeStr());
        tvPoints.setText(speeding.getPointsStr());
        tvRevocation.setText(speeding.getRevocationStr());
    }
}
