package com.sam.biologydemo;

import android.Manifest;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.FaceDetector;
import android.os.Build;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.hardware.fingerprint.FingerprintManagerCompat;
import androidx.core.os.CancellationSignal;

/**
 * Created by samwang on 2019/12/23.
 */

public class BiometricUtils {
    private Context context;
    private static Object sync = new Object();

    private FingerprintManagerCompat fingerprintManager;
    private CancellationSignal cancellationSignal;
    private KeyguardManager keyguardManager;

    private FaceDetector faceDetector;

    private BiometricUtils (Context context){
        this.context = context.getApplicationContext();
        fingerprintManager = FingerprintManagerCompat.from(context);
        keyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
//        faceDetector = new FaceDetector();
    }

    private static BiometricUtils biometricUtils = null;
    public static BiometricUtils getInstance(Context context) {
        if (biometricUtils == null)
        {
            synchronized (sync)
            {
                if (biometricUtils == null)
                {
                    biometricUtils = new BiometricUtils(context);
                }
            }
        }
        return biometricUtils;
    }

    public boolean isAbove23() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }
    public boolean judgeFingerPermission(){
        if (!isAbove23()) {
            Toast.makeText(context, "设备不支持指纹识别", Toast.LENGTH_SHORT).show();
            return false;
        }
        //硬件是否支持指纹识别
        if (!fingerprintManager.isHardwareDetected()){
            Toast.makeText(context, "设备不支持指纹识别", Toast.LENGTH_LONG).show();
            return false;
        }
        //权限
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(context, "请开启指纹识别权限", Toast.LENGTH_LONG).show();
            return false;
        }
        //是否已经录入指纹
        if (!fingerprintManager.hasEnrolledFingerprints()){
            Toast.makeText(context, "请先在设备添加指纹信息", Toast.LENGTH_LONG).show();
            return false;
        }
        //手机是否开启锁屏密码
        if (!keyguardManager.isKeyguardSecure()){
            Toast.makeText(context, "请先在设备添加指纹信息", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    public boolean judgeFacePermission(){
        if (!isAbove23()) {
            Toast.makeText(context, "设备不支持面部识别", Toast.LENGTH_SHORT).show();
            return false;
        }
        //硬件是否支持面部识别
        if (!fingerprintManager.isHardwareDetected()){
            Toast.makeText(context, "设备不支持面部识别", Toast.LENGTH_LONG).show();
            return false;
        }
        //权限
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(context, "请开启面部识别权限", Toast.LENGTH_LONG).show();
            return false;
        }
        //是否已经录入面部信息
        if (!fingerprintManager.hasEnrolledFingerprints()){
            Toast.makeText(context, "请先在设备添加面部信息", Toast.LENGTH_LONG).show();
            return false;
        }
        //手机是否开启锁屏密码
        if (!keyguardManager.isKeyguardSecure()){
            Toast.makeText(context, "请先在设备添加面部信息", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    public void setFingerPrintListener(FingerprintManagerCompat.AuthenticationCallback callback) {
        if (cancellationSignal == null || cancellationSignal.isCanceled()) {
            cancellationSignal = new CancellationSignal();
        }
        fingerprintManager.authenticate(null, 0, cancellationSignal, callback, null);
    }

    public void stopFingerPrintListener() {
        if (cancellationSignal != null) {
            cancellationSignal.cancel();
            cancellationSignal = null;
        }
    }
}
