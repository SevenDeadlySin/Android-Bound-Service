package com.example.raksa.boundservice;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    boolean isBound = false;

    TextView textViewResult;
    EditText editTextValue1;
    EditText editTextValue2;

    MyBoundService myBoundService;

    ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MyBoundService.MyLocalBinder myLocalBinder = (MyBoundService.MyLocalBinder) service;
            myBoundService = myLocalBinder.getService();

            isBound = true;

            int value1 =Integer.valueOf(editTextValue1.getText().toString());
            int value2 =Integer.valueOf(editTextValue2.getText().toString());

            /// Example.......
            myLocalBinder.add(value1,value2);

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBound = false;
        }
    };


    @Override
    protected void onStart() {
        super.onStart();
        Intent boundToServerIntent = new Intent(MainActivity.this,MyBoundService.class);
        bindService(boundToServerIntent,mServiceConnection,BIND_AUTO_CREATE);

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isBound){
            unbindService(mServiceConnection);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        editTextValue1 = (EditText) findViewById(R.id.editTextValue1);
        editTextValue2 = (EditText) findViewById(R.id.editTextValue2);

        textViewResult = (TextView) findViewById(R.id.textViewResult);

    }

    public void onCalculateButton(View view) {

        int value1 = Integer.valueOf(editTextValue1.getText().toString());
        int value2 = Integer.valueOf(editTextValue2.getText().toString());

        float result = 0;


        if (isBound){
            switch (view.getId()){
                case R.id.buttonSum :
                    result = myBoundService.addService(value1,value2);
                    break;
                case R.id.buttonSub :
                    result = myBoundService.subtract(value1,value2);
                    break;
                case R.id.buttonDivide:
                    result = myBoundService.divide(value1,value2);
                    break;
                case R.id.buttonMultiply:
                    result = myBoundService.multiply(value1,value2);
                    break;
            }
        }

        textViewResult.setText(String.valueOf(result));


    }
}
