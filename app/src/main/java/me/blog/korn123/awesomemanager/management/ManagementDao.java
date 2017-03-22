package me.blog.korn123.awesomemanager.management;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import me.blog.korn123.support.AwesomeManagerSQLiteHelper;

/**
 * Created by CHO HANJOONG on 2016-10-17.
 */

public class ManagementDao {

    public static int getManagementId(Context context, String deviceId) {
        int managementId = -1;
        AwesomeManagerSQLiteHelper helper = new AwesomeManagerSQLiteHelper(context);
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        StringBuilder query = new StringBuilder();
        query.append("SELECT _ID FROM MANAGEMENT WHERE DVIC_ID = '" + deviceId + "'");
        Cursor cursor = sqLiteDatabase.rawQuery(query.toString(), null);
        if (cursor.getCount() == 1) {
            cursor.moveToFirst();
            managementId = cursor.getInt(0);
        } else {
            managementId =  (int) insertManagement(context, deviceId);
        }
        closeResource(null, sqLiteDatabase, helper);
        return managementId;
    }

    public static long insertManagement(Context context, String deviceId) {
        AwesomeManagerSQLiteHelper helper = new AwesomeManagerSQLiteHelper(context);
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("DVIC_ID", deviceId);
        long managementId = sqLiteDatabase.insert("MANAGEMENT", null, values);
        closeResource(null, sqLiteDatabase, helper);
        return managementId;
    }

    public static void closeResource(Cursor cursor, SQLiteDatabase sqLiteDatabase, SQLiteOpenHelper sqLiteOpenHelper) {
        if (cursor != null) cursor.close();
        if (sqLiteDatabase != null) sqLiteDatabase.close();
        if (sqLiteOpenHelper != null) sqLiteOpenHelper.close();
    }

}
