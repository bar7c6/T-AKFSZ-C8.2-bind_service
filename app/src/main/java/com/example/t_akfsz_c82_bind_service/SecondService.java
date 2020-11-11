package com.example.t_akfsz_c82_bind_service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import javax.security.auth.login.LoginException;

public class SecondService extends Service {

    private String message;
    private boolean isRunning = true;
    private IBinder binder = new MyBinder();
    private SecondService.ServiceThread serviceThread;
    private Thread thread;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i("service","onBind");
        serviceThread = new ServiceThread();
        thread = new Thread(serviceThread);
        thread.start();
        return binder;
    }

    @Override
    public void onCreate() {

        Log.i("service","onCreate");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("service","onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        serviceThread.flag = false;
        Log.i("service","onDestroy");
    }

    class ServiceThread implements Runnable{
        volatile boolean flag = true;

        @Override
        public void run() {
            Log.i("service","thread start");
            int i =  1;
            while(flag){
                if (mOnDataCallback != null){
                    mOnDataCallback.onDataChange(message + i);

                }
                i++;
                try{
                    Thread.sleep(1000);

                }catch (InterruptedException e){
                    e.printStackTrace();
                }

            }
        }
    }

    public class MyBinder extends Binder{

        public void setData(String message){

            SecondService.this.message = message;
        }

        public SecondService getService(){
            return SecondService.this;
        }
    }

    public interface OnDataCallback{
        void onDataChange(String message);
    }//定义一个回调接口

    private OnDataCallback mOnDataCallback =null;//创建回调接口对象

    public void setmOnDataCallback(OnDataCallback mOnDataCallback){
        this.mOnDataCallback = mOnDataCallback;

    }//设置回调函数


}
