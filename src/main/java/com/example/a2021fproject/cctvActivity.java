package com.example.a2021fproject;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

public class cctvActivity extends AppCompatActivity {
    final String TAG = "TAG+CCTVFragment";
    Button cctvOnButton, cctvOffButton, cctvCenterButton, cctvLeftButton, cctvRightButton;
    WebView webView;
    WebSettings webSettings;
    TextView callText;

    @SuppressLint({"ClickableViewAccessibility", "SetJavaScriptEnabled"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cctv);

        Log.d(TAG,"Create CCTV Fragment");

        webView = (WebView) findViewById(R.id.cctvWeb);
        callText = (TextView) findViewById(R.id.callText);
        cctvOnButton = (Button) findViewById(R.id.cctvOnButton);
        cctvOffButton = (Button) findViewById(R.id.cctvOffButton);
        cctvCenterButton = (Button) findViewById(R.id.cctvCenterButton);
        cctvLeftButton = (Button) findViewById(R.id.cctvLeftButton);
        cctvRightButton = (Button) findViewById(R.id.cctvRightButton);

        webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webView.loadData("<html><head><style type='text/css'>body{margin:auto auto;text-align:center;} " +
                        "img{width:100%25;} div{overflow: hidden;} </style></head>" +
                        "<body><div><img src='http://" + ((MainActivity)MainActivity.context).tcpThread.ip + ":8082/'/></div></body></html>",
                "text/html", "UTF-8");
        // WebView 에 CCTV 화면 띄움
        webView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    webView.reload();
                }
                return true;
            }
        }); // WebView 터치 시 새로고침


        cctvCenterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            ((MainActivity)MainActivity.context).tcpThread.cctv_center();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        }); // Center 버튼 클릭 시 cctv 위치 중앙으로
        cctvLeftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            ((MainActivity)MainActivity.context).tcpThread.cctv_left();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        }); // left 버튼 클릭 시 cctv 위치 왼쪽으로
        cctvRightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            ((MainActivity)MainActivity.context).tcpThread.cctv_right();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        }); // Right 버튼 클릭 시 cctv 위치 오른쪽으로
        cctvOnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            ((MainActivity)MainActivity.context).tcpThread.cctvOn();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        }); // On 버튼 클릭 시 cctv On 명령어 전송
        cctvOffButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            ((MainActivity)MainActivity.context).tcpThread.cctvOff();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        }); // Off 버튼 클릭 시 cctv Off 명령어 전송
        callText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(cctvActivity.this);
                builder.setTitle("신고");
                builder.setMessage("신고하시겠습니까?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:112"));
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        }); // 신고하기 버튼 클릭 시 112 전화걸기로 이동
    }
}
