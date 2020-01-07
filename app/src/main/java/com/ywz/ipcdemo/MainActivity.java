package com.ywz.ipcdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * 客户端 主进程
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnConnect;
    Button btndisConnect;
    Button btnisConnected;
    private IConnectionService connectionServiceProxy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnConnect = findViewById(R.id.btnConnect);
        btndisConnect = findViewById(R.id.btndisConnect);
        btnisConnected = findViewById(R.id.btnisConnected);

        btnConnect.setOnClickListener(this);
        btndisConnect.setOnClickListener(this);
        btnisConnected.setOnClickListener(this);


        Intent intent = new Intent(this, RemoteService.class);
        bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                connectionServiceProxy = IConnectionService.Stub.asInterface(iBinder);
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {

            }
        }, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnConnect:
                try {
                    connectionServiceProxy.connect();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

                break;

            case R.id.btndisConnect:
                try {
                    connectionServiceProxy.disconnect();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;

            case R.id.btnisConnected:
                try {
                    boolean isConnected = connectionServiceProxy.isConnected();
                    Toast.makeText(MainActivity.this, String.valueOf(isConnected), Toast.LENGTH_SHORT).show();

                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;

        }
    }
}
