package com.atoncorp.trustin.sample.fingerprint;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import com.atoncorp.trustin.sample.R;

public class BiometricAgent {
    private Context mContext;
    private BiometricCallback mCallback;
    private BiometricPrompt mBiometricPrompt;
    public static final String TAG = BiometricAgent.class.getSimpleName();

    public BiometricAgent(Context context, BiometricCallback callback){
        mContext = context;
        mCallback = callback;
    }

    public void init(){
        BiometricManager biometricManager = BiometricManager.from(mContext);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.P){
           if(mContext.checkSelfPermission(Manifest.permission.USE_BIOMETRIC) != PackageManager.PERMISSION_GRANTED){
               Toast.makeText(mContext, mContext.getString(R.string.bio_permission_msg), Toast.LENGTH_SHORT).show();
               return;
           }
        }

        int biometricConstant = biometricManager.canAuthenticate();
        switch (biometricConstant){
            //  하드웨어를 사용할 수 없습니다. 나중에 다시 시도하십시오.
            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                Toast.makeText(mContext, mContext.getString(R.string.bitmetric_error_msg1), Toast.LENGTH_SHORT).show();
                break;
            //  사용자에게 등록된 생체 인식이 없습니다.
            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                Toast.makeText(mContext, mContext.getString(R.string.bitmetric_error_msg2), Toast.LENGTH_SHORT).show();
                break;
            //  생체 인식 하드웨어가 없습니다.
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                Toast.makeText(mContext, mContext.getString(R.string.bitmetric_error_msg3), Toast.LENGTH_SHORT).show();
                break;
            case BiometricManager.BIOMETRIC_SUCCESS:
                break;
        }
    }

    /**
     *  finger authentication callback
     * @return
     */
    private BiometricPrompt.AuthenticationCallback getAuthenticationCallback(){
        return new BiometricPrompt.AuthenticationCallback(){
            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                mCallback.onSuccess();
            }

            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                mCallback.onFailWithErrorCode(errorCode, errString);
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                mCallback.onFailAuth();
            }
        };
    }

    /**
     * fingerpopup show
     */
    public void showAuthDialog() {
        try {
            BiometricPrompt.AuthenticationCallback authenticationCallback = getAuthenticationCallback();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                mBiometricPrompt = new BiometricPrompt((FragmentActivity) mContext, mContext.getMainExecutor(), authenticationCallback);
            } else {
                mBiometricPrompt = new BiometricPrompt((FragmentActivity) mContext, ContextCompat.getMainExecutor(mContext), authenticationCallback);
            }

            String title = mContext.getString(R.string.title);
            String subTilte = mContext.getString(R.string.subTitle);
            //  right bottom button name
            String btnName = mContext.getString(R.string.btn_cancel);
            String description = mContext.getString(R.string.description);
            BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                    .setDescription(description)
                    .setTitle(title)
                    .setSubtitle(subTilte)
                    .setNegativeButtonText(btnName)
                    .build();
            mBiometricPrompt.authenticate(promptInfo);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public interface BiometricCallback{
        void onSuccess();

        void onFailAuth();

        void onFailWithErrorCode(int errorCode, CharSequence errString);
    }
}