package com.nish.ahs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.firebase.iid.FirebaseInstanceId;



public class MainScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        //IDs
        WebView view = (WebView) this.findViewById(R.id.webView);
        String url = "http://192.168.29.194:81/stream"; //Stream Address
        String blankpage = "http://www.e-try.com/black.htm"; //Address for blank page

        String token = FirebaseInstanceId.getInstance().getToken();
        TextView tokenTextView = (TextView) findViewById(R.id.textView2);
        tokenTextView.setText(token);

        //Stream
        view.getSettings().setLoadWithOverviewMode(true);
        view.getSettings().setUseWideViewPort(true);
        view.loadUrl(blankpage); //Shows a black screen
        //Start Stream Button
        Button start = (Button) findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.loadUrl(url); //Loads the stream url

            }
        });


        //Stop Stream Button
        Button stop = (Button) findViewById(R.id.stop);
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.stopLoading(); //Stops loading the stream
                view.loadUrl("http://www.e-try.com/black.htm"); //Shows a black screen
            }
        });
        //Snapshot Button
        Button snapshot = (Button) findViewById(R.id.snapshot);
        snapshot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String token = FirebaseInstanceId.getInstance().getToken();
                tokenTextView.setText(token);

                //Email token to ourselves
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"nishathumaira23@gmail.com"});
                intent.putExtra(Intent.EXTRA_SUBJECT, "AHS - Device ID");
                intent.putExtra(Intent.EXTRA_TEXT, token);
                intent.setType("message/rfc822");
                startActivity(Intent.createChooser(intent, "Select Email Sending App :"));
            }
        });



    }

