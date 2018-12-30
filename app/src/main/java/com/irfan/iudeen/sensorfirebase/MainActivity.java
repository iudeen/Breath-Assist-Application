package com.irfan.iudeen.sensorfirebase;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    SensorManager mSensorManager;
    Button pressureBtn;
    TextView kidsVal, adultVal, agedVal;
    LinearLayout kidLayout, adultLayout, agedLayout;
    Sensor pressure;
    float currentValue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        kidsVal = findViewById(R.id.kidsVal);
        adultVal = findViewById(R.id.adultVal);
        agedVal = findViewById(R.id.agedVal);
        kidLayout = findViewById(R.id.kidLayout);
        adultLayout = findViewById(R.id.adultLayout);
        agedLayout = findViewById(R.id.agedLayout);
        pressure = mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);


    }
    @Override
    public void onSensorChanged(SensorEvent event) {
        currentValue = event.values[0];
        changeVal(currentValue);

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.viewPressure){
            Intent intent = new Intent(this, PressureSensor.class);
            startActivity(intent);
        }
        else{
            return super.onOptionsItemSelected(item);
        }
        return true;
    }

    public void changeVal(float sensorVal){
        if((sensorVal < 1008) && (sensorVal > 1003)){
            kidsVal.setText("SAFE");
            kidLayout.setBackgroundColor(getColor(R.color.colorGreen));
            adultVal.setText("SAFE");
            adultLayout.setBackgroundColor(getColor(R.color.colorGreen));
            agedVal.setText("SAFE");
            agedLayout.setBackgroundColor(getColor(R.color.colorGreen));
        }
        else if((sensorVal < 1003) && (sensorVal > 999)){
            kidsVal.setText("NOT SAFE");
            kidLayout.setBackgroundColor(getColor(R.color.colorRed));
            adultVal.setText("SAFE");
            adultLayout.setBackgroundColor(getColor(R.color.colorGreen));
            agedVal.setText("SAFE");
            agedLayout.setBackgroundColor(getColor(R.color.colorGreen));
        }
        else if((sensorVal < 999) && (sensorVal > 997)){
            kidsVal.setText("NOT SAFE");
            kidLayout.setBackgroundColor(getColor(R.color.colorRed));
            adultVal.setText("SAFE");
            adultLayout.setBackgroundColor(getColor(R.color.colorGreen));
            agedVal.setText("NOT SAFE");
            agedLayout.setBackgroundColor(getColor(R.color.colorRed));
        }
        else if(sensorVal < 996){
            kidsVal.setText("NOT SAFE");
            kidLayout.setBackgroundColor(getColor(R.color.colorRed));
            adultVal.setText("NOT SAFE");
            adultLayout.setBackgroundColor(getColor(R.color.colorRed));
            agedVal.setText("NOT SAFE");
            agedLayout.setBackgroundColor(getColor(R.color.colorRed));
        }

    }
    @Override
    protected void onResume() {
        // Register a listener for the sensor.
        super.onResume();
        mSensorManager.registerListener(this, pressure, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        // Be sure to unregister the sensor when the activity pauses.
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

   /* public void pressureBtn(View view){
        Intent intent = new Intent(this, PressureSensor.class);
        startActivity(intent);
    }*/

}
