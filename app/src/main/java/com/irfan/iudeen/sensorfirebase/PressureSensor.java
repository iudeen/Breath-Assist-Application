package com.irfan.iudeen.sensorfirebase;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.icu.util.Calendar;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class PressureSensor extends AppCompatActivity implements SensorEventListener {
    SensorManager mSensorManager;
    Sensor mPressure;
    TextView sensorText;
    // Write a message to the database
    FirebaseDatabase mDatabase;
    DatabaseReference myRef;
    Handler handler = new Handler();
    float currentValue;
    Date currentTime;
    PopupWindow popupWindow;
    LayoutInflater layoutInflater;
    ConstraintLayout constraintLayout;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pressure_sensor);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mPressure = mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        sensorText = findViewById(R.id.sensorVal);
        mDatabase = FirebaseDatabase.getInstance();
        myRef = mDatabase.getReference();
        constraintLayout = findViewById(R.id.pressureLayout);
        handler.post(runnableCode);
    }

    private Runnable runnableCode = new Runnable() {
        @Override
        public void run() {
            currentTime = Calendar.getInstance().getTime();
            myRef.child("User").child("SensorVal").child(String.valueOf(currentTime)).setValue(String.valueOf(currentValue));
            Log.d("Handlers", "Called on main thread");
            handler.postDelayed(this, 5000);
        }
    };

    public void clearBtn(View view){

//        layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
//        ViewGroup container = (ViewGroup) layoutInflater.inflate(R.layout.popup_layout,null);
//        popupWindow = new PopupWindow(container, 700, 450, true);
//        popupWindow.showAtLocation(constraintLayout, Gravity.CENTER_VERTICAL, 700, 500);
//        container.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                popupWindow.dismiss();
//                return true;
//
//            }
//        });
        myRef.child("User").child("SensorVal").setValue(null);
    }
//    public void clearBtn1(View view){
//        myRef.child("User").child("SensorVal").setValue(null);
//        //clear();
//    }

   /* public void clear(){
        myRef.child("User").child("SensorVal").setValue(null);
    }*/

    @Override
    public void onSensorChanged(SensorEvent event) {
        currentValue = event.values[0];
        sensorText.setText((String.valueOf(currentValue)));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    @Override
    protected void onResume() {
        // Register a listener for the sensor.
        super.onResume();
        mSensorManager.registerListener(this, mPressure, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        // Be sure to unregister the sensor when the activity pauses.
        super.onPause();
        mSensorManager.unregisterListener(this);
    }
}
