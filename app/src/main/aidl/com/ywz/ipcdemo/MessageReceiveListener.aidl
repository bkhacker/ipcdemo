package com.ywz.ipcdemo;
import com.ywz.ipcdemo.entity.Message;

interface MessageReceiveListener {
void onReceiveMessage(in Message message);

}
