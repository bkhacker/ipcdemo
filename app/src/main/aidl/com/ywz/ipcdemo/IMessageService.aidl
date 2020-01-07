
package com.ywz.ipcdemo;
import com.ywz.ipcdemo.entity.Message;
import com.ywz.ipcdemo.MessageReceiveListener;
//消息服务
interface IMessageService {
void sendMessage(in Message message);

void registerMessageReceiveListener(MessageReceiveListener messageReceiveListener);
void unRegisterMessageReceiveListener(MessageReceiveListener messageReceiveListener);
}
