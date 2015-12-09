package com.example.castle.castlelistview;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class MPlayerActivity extends AppCompatActivity {

    private FloatingActionButton bPlay;
    private FloatingActionButton bStop;
    private NetworkImageView ivIconDisplay;
    private TextView tvPlayStatus, tvTrackTitle;
    private ImageLoader mImageLoader;

    private ToggleButton tbLineStatus;
    final static String TAG = "MPlayerActivity";

    //private MyTaskMPlayer bkSocketClient;

    private Socket socket;
    private BufferedReader in = null;
    private PrintWriter out = null;
    private static final String HOST = "192.168.8.101";
    private static final int PORT = 8899;

    private Boolean need_return = false;


    //2rd method to handler client thread
    ClientThread clientThread;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mplayer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ivIconDisplay = (NetworkImageView) this.findViewById(R.id.urlImage1);
        tvPlayStatus = (TextView) this.findViewById(R.id.TVplayState);
        tvTrackTitle = (TextView) this.findViewById(R.id.TVTrackTitle);
        tbLineStatus = (ToggleButton) this.findViewById(R.id.toggleButton);
        mImageLoader = VolleySingleton.getVolleySingleton(this.getApplicationContext()).getImageLoader();

        FloatingActionButton bPlay = (FloatingActionButton) findViewById(R.id.md_play);
        bPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Press Play button", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        FloatingActionButton bStop = (FloatingActionButton) findViewById(R.id.md_stop);
        bStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Press Stop button", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        tbLineStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "tbLineStatus Clicked: " + tbLineStatus.isChecked());
                if(tbLineStatus.isChecked())
                    sendMsg2ClientThread(MainMsg.MUI_CLT_CMD_CONNECT);
                else
                    sendMsg2ClientThread(MainMsg.MUI_CLI_CMD_DISCONNECT);
            }
        });

        Intent intent = getIntent();
        String cverl_url = intent.getStringExtra("cverl_url");
        String trkTitle = intent.getStringExtra("title");
        tvTrackTitle.setText(trkTitle.toCharArray(), 0, trkTitle.length());
        ivIconDisplay.setImageUrl(cverl_url, mImageLoader);

        handler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                //recevie the msg from ClientThread and display on Activity
                Log.i(TAG, "Main UI receive msg: " + msg.what);
                if (msg.what == MainMsg.CLT_MUI_IND_CONNECT.ordinal()) {
                    tbLineStatus.setChecked(true);
                }

                if (msg.what == MainMsg.CLI_MUI_IND_DISCONNECT.ordinal()) {
                    tbLineStatus.setChecked(false);
                }
            }
        };

        clientThread = new ClientThread(handler);
        new Thread(clientThread).start();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        sendMsg2ClientThread(MainMsg.MUI_CLI_CMD_DISCONNECT);
    }

    private void sendMsg2ClientThread(MainMsg msgID) {
        try {
            Message msg = new Message();
            msg.what = msgID.ordinal();
            clientThread.revHandler.sendMessage(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
/*
    public class MyTaskMPlayer extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            Log.i(TAG, "Go into doInBackground");

            try {
                socket = new Socket(HOST, PORT);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
            } catch (Exception e) {
                Log.i(TAG, "socket failed here.");
                e.printStackTrace();
            }

            need_return = false;
            while (!need_return) {
                char[] bytes = new char[128];
                int readLen = 0;
                try {
                    if (socket.isConnected()) {
                        if (!socket.isInputShutdown()) {
                            readLen = in.read(bytes, 0, 128);//block here!!!
                            String display = new String(bytes, 0, readLen);
                            Log.i(TAG, "Service rsp: " + display);
                        }
                    }
                } catch (Exception e) {
                    //can stop by bStop
                    Log.i(TAG, "Socket is shutdown or exception.");
                    e.printStackTrace();
                    try {
                        Log.i(TAG, "Finally do some clean work.");
                        in.close();
                        out.close();
                        socket.close();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }

            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.i(TAG, "Go into onPreExecute");
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.i(TAG, "Go into onPostExecute");
        }
    }
    */
}

