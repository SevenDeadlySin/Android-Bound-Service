package com.example.raksa.boundservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by Raksa on 5/13/2017.
 */

public class MyBoundService extends Service {


    public class MyLocalBinder extends Binder {

        MyBoundService getService (){
            return MyBoundService.this;
        }
    }

    MyLocalBinder myLocalBinder = new MyLocalBinder();


    public int add(int a , int b){
        return a + b;
    }


    public int subtract(int a , int b){
        return a - b;
    }

    public int multiply(int a , int b){
        return a * b;
    }

    public float divide(int a , int b){
        return (float) a / (float) b;
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return myLocalBinder;
    }
}
