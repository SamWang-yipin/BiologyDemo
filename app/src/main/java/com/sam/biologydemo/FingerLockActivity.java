package com.sam.biologydemo;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import androidx.core.hardware.fingerprint.FingerprintManagerCompat;

public class FingerLockActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        DialogUtility.showSingleButtonDialog(this, "指纹登录", "取消登录", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                    setResult(RESULT_OK);
                    dialog.dismiss();
                    finish();
            }
        });
    }

    private void fingerUnlock() {
        if (BiometricUtils.getInstance(FingerLockActivity.this).judgePermission()) {
            BiometricUtils.getInstance(FingerLockActivity.this).setFingerPrintListener(new FingerprintManagerCompat.AuthenticationCallback() {
                @Override
                public void onAuthenticationError(int errMsgId, CharSequence errString) {
                    super.onAuthenticationError(errMsgId, errString);
                    Toast.makeText(FingerLockActivity.this, errString, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
                    super.onAuthenticationHelp(helpMsgId, helpString);
                }

                @Override
                public void onAuthenticationSucceeded(FingerprintManagerCompat.AuthenticationResult result) {
                    super.onAuthenticationSucceeded(result);
                    Toast.makeText(FingerLockActivity.this, "验证成功", Toast.LENGTH_SHORT).show();
                    finish();
                }

                @Override
                public void onAuthenticationFailed() {
                    super.onAuthenticationFailed();
                    Toast.makeText(FingerLockActivity.this, "验证失败，请重试", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        fingerUnlock();
    }

    @Override
    protected void onDestroy() {
        BiometricUtils.getInstance(FingerLockActivity.this).stopFingerPrintListener();
        super.onDestroy();
    }
}
