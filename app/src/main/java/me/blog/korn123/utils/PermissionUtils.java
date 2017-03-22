package me.blog.korn123.utils;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by CHO HANJOONG on 2016-10-09.
 */

public class PermissionUtils {

    final static int WRITE_EXTERNAL_STORAGE_CODE = 0;
    final static int PERMISSION_ALL = 1;
    final static String[] PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.SEND_SMS, Manifest.permission.READ_CONTACTS};
    public static void confirmPermission(Context context, final Activity activity) {
        // 권한이 있는지 확인함
        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
                || ContextCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_DENIED
                || ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_DENIED) {
            // 권한 취득에 대한 설명이 필요한지 확인
            // 처음 권한을 요청하는경우 false 리턴
            // 사용자가 '다시 묻지 않기'를 체크하지 않고, 1번이상 권한요청에 대해 거부한 경우에만 true 리턴
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    || ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.SEND_SMS)) {
                new AlertDialog.Builder(context)
                        .setMessage("Awesome Manger 사용을 위해서는 권한승인이 필요합니다.")
                        .setTitle("권한승인 요청")
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                ActivityCompat.requestPermissions(activity, PERMISSIONS, PERMISSION_ALL);
                            }
                        })
//                        .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int whichButton) {
//                            }
//                        })
                        .show();
            } else {
                ActivityCompat.requestPermissions(activity, PERMISSIONS, PERMISSION_ALL);
            }
        }
    }

    public static boolean checkPermission(Context context) {
        boolean isValid = true;
        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
                || ContextCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_DENIED
                || ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_DENIED) {
            isValid = false;
        }
        return isValid;
    }

}
