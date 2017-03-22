package me.blog.korn123.googledrive;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;

import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.OpenFileActivityBuilder;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import me.blog.korn123.awesomemanager.LoginActivity;
import me.blog.korn123.constant.Constant;
import me.blog.korn123.log.AAFLogger;
import me.blog.korn123.utils.CommonUtils;

/**
 * Created by CHO HANJOONG on 2016-09-28.
 */
public class GoogleDriveDownloader extends GoogleDriveUtils {

    /**
     * File that is selected with the open file activity.
     */
    private DriveId mSelectedFileDriveId;

    private static final int REQUEST_CODE_OPENER = 1;

    @Override
    public void onConnected(Bundle connectionHint) {
        super.onConnected(connectionHint);
        // If there is a selected file, open its contents.
        if (mSelectedFileDriveId != null) {
            open();
            return;
        }

        IntentSender intentSender = Drive.DriveApi
                .newOpenFileActivityBuilder()
//                .setMimeType(new String[] { "text/plain", "text/html" })
                .build(getGoogleApiClient());
        try {
            startIntentSenderForResult(
                    intentSender, REQUEST_CODE_OPENER, null, 0, 0, 0);
        } catch (IntentSender.SendIntentException e) {
            AAFLogger.error("GoogleDriveDownloader-onConnected ERROR: " + e.getMessage(), getClass());
        }
    }

    private void open() {
        DriveFile driveFile = mSelectedFileDriveId.asDriveFile();
        DriveFile.DownloadProgressListener listener = new DriveFile.DownloadProgressListener() {
            @Override
            public void onProgress(long bytesDownloaded, long bytesExpected) {
                // Update progress dialog with the latest progress.
                int progress = (int)(bytesDownloaded*100/bytesExpected);
                AAFLogger.info("GoogleDriveDownloader-open INFO: " + progress, getClass());
            }
        };
        driveFile.open(getGoogleApiClient(), DriveFile.MODE_READ_ONLY, listener)
                .setResultCallback(driveContentsCallback);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_OPENER && resultCode == RESULT_OK && data != null) { // 계정선택 후 최초 진입 시 data가 null 임
            mSelectedFileDriveId = (DriveId) data.getParcelableExtra(OpenFileActivityBuilder.EXTRA_RESPONSE_DRIVE_ID);
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private ResultCallback<DriveApi.DriveContentsResult> driveContentsCallback =
        new ResultCallback<DriveApi.DriveContentsResult>() {
            @Override
            public void onResult(DriveApi.DriveContentsResult result) {
                if (!result.getStatus().isSuccess()) {
                    showMessage("Error while opening the file contents");
                    return;
                }

                long fileSize = 0;
                try {
                    InputStream driveContents = result.getDriveContents().getInputStream();
                    fileSize = driveContents.available();
                    OutputStream outputStream = new FileOutputStream(Constant.WORKING_DIRECTORY + "tempDB");
                    IOUtils.copy(driveContents, outputStream);
                    IOUtils.closeQuietly(outputStream);
                    CommonUtils.saveManageInfo(GoogleDriveDownloader.this, Constant.WORKING_DIRECTORY + "tempDB");
                    File inFile = new File(Constant.WORKING_DIRECTORY + "tempDB");
                    IOUtils.copyLarge(new FileInputStream(inFile), new FileOutputStream(Constant.AAF_AWESOME_MANAGER_DATABASE_PATH + Constant.AAF_AWESOME_MANAGER_DATABASE_FILENAME), 0, inFile.length() - 76);
                } catch (Exception e) {
                    AAFLogger.error("GoogleDriveDownloader.ResultCallback-onResult INFO: " + e.getMessage(), getClass());
                }
//                CommonUtils.makeToast(GoogleDriveDownloader.this, "fileSize is " + fileSize);
                Intent login = new Intent(GoogleDriveDownloader.this, LoginActivity.class);
                CommonUtils.sessionClear(login);
                startActivity(login);
                finish();
            }
        };

}
