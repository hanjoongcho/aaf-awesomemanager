package me.blog.korn123.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Point;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.view.Display;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import me.blog.korn123.awesomemanager.R;
import me.blog.korn123.log.AAFLogger;

/**
 * Created by CHO HANJOONG on 2016-10-04.
 */

public class CommonUtils {
    public static String getDeviceId(Context context) {
        String deviceId = loadStringPreference(context, "login_device_id", "");
        if (StringUtils.isEmpty(deviceId)) {
            deviceId = UUID.randomUUID().toString();
        }
        return deviceId;
    }

    public static String loadStringPreference(Context context, String key, String defaultValue) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String value = preferences.getString(key, defaultValue);
        return value;
    }

    public static void saveStringPreference(Context context, String key, String value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor edit = preferences.edit();
        edit.putString(key, value);
        edit.commit();
    }

    public static void saveLongPreference(Context context, String key, Long value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor edit = preferences.edit();
        edit.putLong(key, value);
        edit.commit();
    }

    public static Long loadLongPreference(Context context, String key) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        Long value = preferences.getLong(key, System.currentTimeMillis());
        return value;
    }

    public static Point getDefaultDisplay(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size;
    }

    public static void makeToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void makeSnackBar(Activity activity, String message) {
        Snackbar.make(activity.findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }

    public static List<String> getStringList(Context context, int id) {
        List<String> list = null;
        Resources res = context.getResources();
        String[] array = res.getStringArray(id);
        list = Arrays.asList(array);
        return list;
    }

    public static String[] getStringArray(Context context, int id) {
        List<String> list = null;
        Resources res = context.getResources();
        String[] array = res.getStringArray(id);
        return array;
    }

    public static boolean verifyInputData(Context context, Activity activity, View view, String message) {
        boolean result = false;
        String inputData = null;
        if (view instanceof Spinner) {
            Spinner spinner = (Spinner) view;
            if (spinner.getCount() > 0) {
                inputData = spinner.getSelectedItem().toString();
            }
        } else if (view instanceof EditText) {
            EditText editText = (EditText) view;
            inputData = String.valueOf(editText.getText());
        }
        if (StringUtils.isEmpty(inputData)) {
            DialogUtils.showAlertDialogOnlyPositive(context, "", message);
        } else {
            result = true;
        }
        return result;
    }

    public static String appendPaddingToDate(String data) {
        String resultDate = StringUtils.stripToNull(data);
        String[] tempArr = StringUtils.split(resultDate, "-");
        if (tempArr.length == 3) {
            tempArr[1] = me.blog.korn123.utils.StringUtils.leftPad(tempArr[1], 2, '0');
            tempArr[2] = me.blog.korn123.utils.StringUtils.leftPad(tempArr[2], 2, '0');
            resultDate = tempArr[0] + "-" + tempArr[1] + "-" + tempArr[2];
        }
        return resultDate;
    }

    public static String getYearBy(String date) {
        return date.substring(0, date.indexOf("년") + 1);
    }

    public static String getMonthBy(String date) {
        return date.substring(date.indexOf("년") + 1, date.indexOf("월") + 1);
    }

    public static String getDayBy(String date) {
        return date.substring(date.indexOf("월") + 1);
    }

    public static String getLoginHash(String deviceId, String password) {
        String loginHash = DigestUtils.getHashCode(deviceId + password, "SHA-1");
        return loginHash;
    }

    public static void saveManageInfo(Context context, String filePath) {
        RandomAccessFile file = null;
        try {
            file = new RandomAccessFile(new File(filePath), "r");
            int startPosition = ((int) file.length())  - 76;
            file.skipBytes(startPosition);
            byte[] deviceId = new byte[36];
            byte[] loginHash = new byte[40];
            file.read(deviceId);
            file.read(loginHash);
            saveStringPreference(context, "login_device_id", new String(deviceId));
            saveStringPreference(context, "login_hash", new String(loginHash));
        } catch (Exception e) {
            AAFLogger.error("CommonUtils-saveManageInfo ERROR: " + e.getMessage(), CommonUtils.class);
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(file);
        }
    }

    public static void sessionClear(Intent login) {
        login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
    }

}
