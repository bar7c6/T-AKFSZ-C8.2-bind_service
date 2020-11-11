package com.example.t_akfsz_c82_bind_service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button bindServiceButton;
    private Button unbindServiceButton;
    private TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intView();
    }

    private void intView(){

        bindServiceButton = findViewById(R.id.bind_service);
        unbindServiceButton = findViewById(R.id.unbind_service);
        textView = findViewById(R.id.text);
        bindServiceButton.setOnClickListener(this);
        unbindServiceButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.bind_service:
                Intent bindIntent = new Intent(this,SecondService.class);
                bindService(bindIntent,serviceConnection, Context.BIND_AUTO_CREATE);
                break;

            case R.id.unbind_service:

              unbindService(serviceConnection);
                break;

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private SecondService.MyBinder binder;
    private SecondService secondService;
    public ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            binder = (SecondService.MyBinder)service;

            binder.setData("MainActivity: ");
            secondService = binder.getService();
            secondService.setmOnDataCallback(new SecondService.OnDataCallback() {
                @Override
                public void onDataChange(final String message) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText(message);
                        }
                    });
                }
            });
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };


}