package com.sam.biologydemo;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import androidx.core.hardware.fingerprint.FingerprintManagerCompat;

/**
 * 面部识别需要第三方提供的AI工具去检测人脸
 * 原生只能做到检测出，某个位置是个人脸，而且人脸必须向上
 */
public class FaceLockActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        DialogUtility.showSingleButtonDialog(this, "面部登录", "取消登录", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setResult(RESULT_OK);
                dialog.dismiss();
                finish();
            }
        });
    }

    private void fingerUnlock() {
    }

    @Override
    protected void onResume() {
        super.onResume();
        fingerUnlock();
    }

    @Override
    protected void onDestroy() {
        BiometricUtils.getInstance(FaceLockActivity.this).stopFingerPrintListener();
        super.onDestroy();
    }
}
