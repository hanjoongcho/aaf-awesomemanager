package me.blog.korn123.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import me.blog.korn123.awesomemanager.LoginActivity;
import me.blog.korn123.awesomemanager.R;
import me.blog.korn123.awesomemanager.setting.SettingsActivity;
import me.blog.korn123.awesomemanager.team.MemberDto;
import me.blog.korn123.awesomemanager.team.TeamAttendanceDto;
import me.blog.korn123.googledrive.GoogleDriveDownloader;
import me.blog.korn123.googledrive.GoogleDriveUploader;

/**
 * Created by CHO HANJOONG on 2016-10-06.
 */

public class DialogUtils {

    /**
     * 구글드라이브 업로드 다이얼로그
     * @param context
     * @param activity
     * @param title
     * @param message
     */
    public static void showGoogleDriveUploaderDialog(final Context context, final Activity activity, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        DialogInterface.OnClickListener positiveListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent googleDriveIntent = new Intent(context, GoogleDriveUploader.class);
                context.startActivity(googleDriveIntent);
                return;
            }
        };
        DialogInterface.OnClickListener negativeListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Snackbar.make(activity.findViewById(android.R.id.content), "구글드라이브 업로드 취소", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        };
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton(context.getString(R.string.confirm), positiveListener);
        builder.setNegativeButton(context.getString(R.string.cancel), negativeListener);
        AlertDialog alert = builder.create();
        alert.show();
    }

    /**
     * 구글드라이드 다운로드 다이얼로그
     * @param context
     * @param activity
     * @param title
     * @param message
     */
    public static void showGoogleDriveDownloaderDialog(final Context context, final Activity activity, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        DialogInterface.OnClickListener positiveListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent googleDriveIntent = new Intent(context, GoogleDriveDownloader.class);
                context.startActivity(googleDriveIntent);
                return;
            }
        };
        DialogInterface.OnClickListener negativeListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Snackbar.make(activity.findViewById(android.R.id.content), "구글드라이브 다운로드 취소", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        };
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton(context.getString(R.string.confirm), positiveListener);
        builder.setNegativeButton(context.getString(R.string.cancel), negativeListener);
        AlertDialog alert = builder.create();
        alert.show();
    }

    /**
     * 어플리케이션 데이터 초기화확인 다이얼로그
     * @param context
     * @param title
     * @param message
     */
    public static void initDataPreferenceConfirm(final Context context, final SettingsActivity.InitDataHandler initDataHandler, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        DialogInterface.OnClickListener positiveListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                initDataHandler.start();
            }
        };
        DialogInterface.OnClickListener negativeListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        };
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton(context.getString(R.string.confirm), positiveListener);
        builder.setNegativeButton(context.getString(R.string.cancel), negativeListener);
        AlertDialog alert = builder.create();
        alert.show();
    }

    /**
     * 잠금번호 변경확인 다이얼로그
     * @param context
     * @param activity
     * @param title
     * @param message
     */
    public static void passwordChangeConfirm(final Context context, final Activity activity, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        DialogInterface.OnClickListener positiveListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent login = new Intent(context, LoginActivity.class);
                CommonUtils.sessionClear(login);
                activity.startActivity(login);
                return;
            }
        };
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton(context.getString(R.string.confirm), positiveListener);
        AlertDialog alert = builder.create();
        alert.show();
    }

    public static void showAlertDialogOnlyPositive(final Context context, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        DialogInterface.OnClickListener positiveListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        };
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton(context.getString(R.string.confirm), positiveListener);
        AlertDialog alert = builder.create();
        alert.show();
    }

    public static void showAlertDialogOnlyPositive(final Context context, final Activity activity, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        DialogInterface.OnClickListener positiveListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                activity.finish();
                return;
            }
        };
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton(context.getString(R.string.confirm), positiveListener);
        AlertDialog alert = builder.create();
        alert.show();
    }

    public static void showAlertDialog(final Context context, final Activity activity, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        DialogInterface.OnClickListener positiveListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        };
        DialogInterface.OnClickListener negativeListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        };
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton(context.getString(R.string.confirm), positiveListener);
        builder.setNegativeButton(context.getString(R.string.cancel), negativeListener);
        AlertDialog alert = builder.create();
        alert.show();
    }

    public static void showAlertDialog(Context context,
                                       String message,
                                       DialogInterface.OnClickListener positiveListener,
                                       DialogInterface.OnClickListener negativeListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setIcon(R.drawable.ic_launcher);
//        builder.setTitle("일기삭제");
        builder.setMessage(message);
        builder.setCancelable(true);
        builder.setNegativeButton("취소", negativeListener);
        builder.setPositiveButton("확인", positiveListener);
        AlertDialog alert = builder.create();
        alert.show();
    }

    public static void showAlertDialogInAttendanceEdit(final Context context, final Activity activity, String title, String message, final ArrayAdapter<TeamAttendanceDto> mAttendanceAdapter) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        DialogInterface.OnClickListener positiveListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mAttendanceAdapter.notifyDataSetChanged();
                return;
            }
        };
        DialogInterface.OnClickListener negativeListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mAttendanceAdapter.notifyDataSetChanged();
                return;
            }
        };
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton(context.getString(R.string.confirm), positiveListener);
        builder.setNegativeButton(context.getString(R.string.cancel), negativeListener);
        AlertDialog alert = builder.create();
        alert.show();
    }
}
