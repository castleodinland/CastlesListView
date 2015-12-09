package com.example.castle.castlelistview;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;


/**
 * Created by castle on 2015/12/8.
 */
public class ClientThread implements Runnable {
    private Socket mSocket;

    Handler sndHandler;
    Handler revHandler;

    BufferedReader br = null;
    OutputStream os = null;

    private static final String HOST = "192.168.8.101";
    private static final int PORT = 8899;
    private static final int TIMEOUT = 5000;
    final static String TAG = "ClientThread";

    private static boolean isConnected = false;

    public ClientThread(Handler handler) {
        this.sndHandler = handler;
    }

    private void sendOnOffLineMsg(boolean isOnline) {
        Message msg = new Message();
        Log.i(TAG, "sendOnOffLineMsg: " + isOnline);
        if (isOnline)
            msg.what = MainMsg.CLT_MUI_IND_CONNECT.ordinal();
        else
            msg.what = MainMsg.CLI_MUI_IND_DISCONNECT.ordinal();
        sndHandler.sendMessage(msg);
    }

    private void startConnect() {
        mSocket = new Socket();
        try {
            mSocket.connect(new InetSocketAddress(HOST, PORT), TIMEOUT);
            br = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));
            os = mSocket.getOutputStream();
            //connect ok here.
            sendOnOffLineMsg(true);
            new Thread() {
                @Override
                public void run() {
                    String content = null;
                    super.run();
                    char[] bytes = new char[128];
                    int readLen = 0;
                    try {
                        while (0 < (readLen = br.read(bytes, 0, 128))) {
                            if (readLen > 0) {
                                String display = new String(bytes, 0, readLen);
                                Log.i(TAG, display);
                            } else {
                                //maybe the client is disconnected by Server
                                sendOnOffLineMsg(false);
                                break;
                            }
                        }
                    } catch (Exception e1) {
                        Log.i(TAG, "Exception when brReading.");
                        e1.printStackTrace();
                    } finally {
                        stopConnect();
                    }
                    Log.i(TAG, "brReading Thread ending!.");
                }
            }.start();
        } catch (Exception e) {
            Log.i(TAG, "Exception when socket connecting.");
            e.printStackTrace();
            sendOnOffLineMsg(false);
        }
    }

    private void stopConnect() {
        sendOnOffLineMsg(false);
        try {
            mSocket.close();
            br.close();
            os.close();
            //Looper.quit();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            isConnected = false;
        }
    }

    @Override
    public void run() {
        try {
            Log.i(TAG, "ClientThread begin to run.");
            if(!isConnected){
                startConnect();
            }
            Looper.prepare();
            revHandler = new Handler() {

                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    Log.i(TAG, "ClientThread Rev message:" + msg.what);
                    if (msg.what == MainMsg.MUI_CLI_CMD_DISCONNECT.ordinal()) {
                        stopConnect();
                    }else if (msg.what == MainMsg.MUI_CLT_CMD_CONNECT.ordinal()){
                        startConnect();
                    }
                }
            };
            Looper.loop();

        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.i(TAG, "ClientThread end.");
    }
}
