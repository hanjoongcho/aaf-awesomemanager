package me.blog.korn123.googledrive;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;

import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.DriveContents;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.MetadataChangeSet;
import com.google.android.gms.drive.OpenFileActivityBuilder;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import me.blog.korn123.awesomemanager.LoginActivity;
import me.blog.korn123.constant.Constant;
import me.blog.korn123.log.AAFLogger;
import me.blog.korn123.utils.CommonUtils;
import me.blog.korn123.utils.DateUtils;

/**
 * Created by CHO HANJOONG on 2016-09-26.
 */
public class GoogleDriveUploader extends GoogleDriveUtils {

    private static final String TAG = "CreateFileWithCreatorActivity";

    protected static final int REQUEST_CODE_CREATOR = 1;

    @Override
    public void onConnected(Bundle connectionHint) {
        super.onConnected(connectionHint);
        Drive.DriveApi.newDriveContents(getGoogleApiClient())
                .setResultCallback(driveContentsCallback);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_CREATOR:
                if ((resultCode == RESULT_OK) && (data != null)) {
                    DriveId driveId = (DriveId) data.getParcelableExtra(OpenFileActivityBuilder.EXTRA_RESPONSE_DRIVE_ID);
//                    showMessage("File created with ID: " + driveId);
                } else {
                    super.onActivityResult(requestCode, resultCode, data);
                }
                finish();
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }

    final ResultCallback<DriveApi.DriveContentsResult> driveContentsCallback =
            new ResultCallback<DriveApi.DriveContentsResult>() {
                @Override
                public void onResult(DriveApi.DriveContentsResult result) {

                    final DriveContents driveContents = result.getDriveContents();
                    OutputStream outputStream = driveContents.getOutputStream();
                    File backupFile = new File(Constant.AAF_AWESOME_MANAGER_DATABASE_PATH + Constant.AAF_AWESOME_MANAGER_DATABASE_FILENAME);
                    try {
                        File tempFile = new File(Constant.WORKING_DIRECTORY + "tempDB");
                        FileUtils.copyFile(backupFile, tempFile);
                        String deviceId = CommonUtils.loadStringPreference(GoogleDriveUploader.this, "login_device_id", "");
                        String loginHash = CommonUtils.loadStringPreference(GoogleDriveUploader.this, "login_hash", "");
                        FileUtils.write(tempFile, deviceId + loginHash, "UTF-8", true);
                        FileUtils.copyFile(tempFile, outputStream);
                        FileUtils.forceDelete(tempFile);
                    } catch (IOException e) {
                        AAFLogger.error("ResultCallback-onResult ERROR: " + e.getMessage(), getClass());
                    }

                    MetadataChangeSet metadataChangeSet = new MetadataChangeSet.Builder()
                            .setTitle(FilenameUtils.getBaseName(Constant.AAF_AWESOME_MANAGER_DATABASE_FILENAME) + "_" + DateUtils.getCurrentDateTime("yyyyMMdd_HHmmss") + ".DB")
                            .setMimeType("text/html").build();
                    IntentSender intentSender = Drive.DriveApi
                            .newCreateFileActivityBuilder()
                            .setInitialMetadata(metadataChangeSet)
                            .setInitialDriveContents(result.getDriveContents())
                            .build(getGoogleApiClient());
                    try {
                        startIntentSenderForResult(
                                intentSender, REQUEST_CODE_CREATOR, null, 0, 0, 0);
                    } catch (IntentSender.SendIntentException e) {
                        AAFLogger.error("ResultCallback-onResult ERROR: " + e.getMessage(), getClass());
                    }
                }
            };

}
