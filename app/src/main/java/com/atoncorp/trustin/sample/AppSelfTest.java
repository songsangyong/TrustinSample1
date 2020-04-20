package com.atoncorp.trustin.sample;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.fragment.app.FragmentActivity;
import com.atoncorp.trustin.sample.fingerprint.BiometricAgent;
import com.atoncorp.trustin.sample.fingerprint.BiometricError;

public class AppSelfTest extends FragmentActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //        trustin OTP의 경우 HTI (거래연동) 기반 임.
        //        - HTI 요청 to 더미서버
        //        - OTP 번호 생성 요청 to SDK
        //        등 으로 끊어서 기능 구형
        //        HTI 값은 Req.의 Rsp 값으로 화면 출력

        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_self_test);

        initRes();
    }

    private void initRes(){
        Button btnRegOtpGen = (Button)findViewById(R.id.btn_reg_otp_gen);
        btnRegOtpGen.setOnClickListener(this);

        Button btnSetBio = (Button)findViewById(R.id.btn_set_bio);
        btnSetBio.setOnClickListener(this);

        Button btnDelBio = (Button)findViewById(R.id.btn_del_bio);
        btnDelBio.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_reg_otp_gen:
                String fingerRegYn = PrefUtil.getInstance(AppSelfTest.this).getPreferences(PrefUtil.FINGER_REG_YN);

                //  fingerpopup show
                if(!TextUtils.isEmpty(fingerRegYn)){
                    verifyBiometric();
                }else{

                }

                break;
            //  set bio
            case R.id.btn_set_bio:
                setBiometric();
                break;
            //  delete bio
            case R.id.btn_del_bio:
                discardBiometric();
                break;
        }
    }

    /**
     * finger reg
     */
    private void setBiometric() {
       BiometricAgent biometricAgent = new BiometricAgent(this, new BiometricAgent.BiometricCallback() {
           @Override
           public void onSuccess() {
               PrefUtil.getInstance(AppSelfTest.this).savePreferences(PrefUtil.FINGER_REG_YN, "Y");
               Toast.makeText(AppSelfTest.this, getString(R.string.success), Toast.LENGTH_SHORT).show();
           }

           @Override
           public void onFailAuth() {

           }

           @Override
           public void onFailWithErrorCode(int errorCode, CharSequence errString) {
               String errorMsg = BiometricError.getBiometricErrorMessage(errorCode, errString.toString());
               Toast.makeText(AppSelfTest.this, errorMsg, Toast.LENGTH_SHORT).show();
           }
       });

        biometricAgent.init();
        biometricAgent.showAuthDialog();
    }

    /**
     * finger verify
     */
    private void verifyBiometric(){
        BiometricAgent biometricAgent = new BiometricAgent(this, new BiometricAgent.BiometricCallback() {
            @Override
            public void onSuccess() {
                Toast.makeText(AppSelfTest.this, getString(R.string.success), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailAuth() {

            }

            @Override
            public void onFailWithErrorCode(int errorCode, CharSequence errString) {
                String errorMsg = BiometricError.getBiometricErrorMessage(errorCode, errString.toString());
                Toast.makeText(AppSelfTest.this, errorMsg, Toast.LENGTH_SHORT).show();
            }
        });

        biometricAgent.init();
        biometricAgent.showAuthDialog();
    }

    /**
     * finger delete
     */
    private void discardBiometric(){
        PrefUtil.getInstance(AppSelfTest.this).removePreference(PrefUtil.FINGER_REG_YN);
    }
}
