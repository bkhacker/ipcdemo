package com.ywz.ipcdemo;


import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.ywz.ipcdemo.entity.Message;

import java.util.ArrayList;

/**
 * 子进程
 * 管理和提供子进程的连接和消息服务
 */
public class RemoteService extends Service {

    private boolean isContected = false;
    private Handler handler = new Handler(Looper.getMainLooper());

    private ArrayList<MessageReceiveListener> messageReceiveListenerArrayList = new ArrayList<>();


    private IConnectionService connectionService = new IConnectionService.Stub() {
        @Override
        public void connect() throws RemoteException {
            try {
                Thread.sleep(5000);
                isContected = true;
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(RemoteService.this, "connect", Toast.LENGTH_SHORT).show();

                    }
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void disconnect() throws RemoteException {
            isContected = false;
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(RemoteService.this, "disconnect", Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public boolean isConnected() throws RemoteException {
            return isContected;
        }
    };

    private IMessageService messageServiceProxy = new IMessageService.Stub() {
        @Override
        public void sendMessage(Message message) throws RemoteException {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(RemoteService.this, message.getContent(), Toast.LENGTH_SHORT).show();

                }
            });

            if (isContected) {
                message.setSendSuccess(true);
            } else {
                message.setSendSuccess(true);
            }
        }

        @Override
        public void registerMessageReceiveListener(MessageReceiveListener messageReceiveListener) throws RemoteException {
            if (messageReceiveListener != null) {
                messageReceiveListenerArrayList.add(messageReceiveListener);
            }

        }

        @Override
        public void unRegisterMessageReceiveListener(MessageReceiveListener messageReceiveListener) throws RemoteException {
            if (messageReceiveListener != null) {
                messageReceiveListenerArrayList.remove(messageReceiveListener);
            }
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return connectionService.asBinder();
    }

}
