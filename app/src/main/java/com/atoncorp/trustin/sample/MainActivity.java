package com.atoncorp.trustin.sample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        TrustinService service = TrustinService.getInstance(this);
//        service.trustinInfo(new TrustinCallback() {
//            @Override
//            public void onResult(Bundle data, Boolean isSuccess) {
//                if(isSuccess) {
//                    String version = data.getString(TrustinConstants.BUNDLE_EXTRA_SDK_VERSION);
//                    ArrayList<String> otpUids = data.getStringArrayList(TrustinConstants.BUNDLE_EXTRA_OTP_UIDS);
//                    Log.d("ATg", "a");
//                }
//            }
//        });



        findViewById(R.id.btn_local).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AppSelfTest.class));
            }
        });

        findViewById(R.id.btn_server).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TrustinServerConnectTest.class));
            }
        });
    }
}
