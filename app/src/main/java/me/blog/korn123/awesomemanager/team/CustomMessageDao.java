package me.blog.korn123.awesomemanager.team;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import me.blog.korn123.awesomemanager.playground.PlaygroundDao;
import me.blog.korn123.log.AAFLogger;
import me.blog.korn123.support.AwesomeManagerSQLiteHelper;
import me.blog.korn123.utils.CommonUtils;

/**
 * Created by CHO HANJOONG on 2016-10-25.
 */

public class CustomMessageDao {

    public static List<CustomMessageDto> selectCustomMessages(Context context) {
        AwesomeManagerSQLiteHelper helper = new AwesomeManagerSQLiteHelper(context);
        SQLiteDatabase sqLiteDatabase = helper.getReadableDatabase();
        StringBuilder query = new StringBuilder();
        query.append("SELECT _ID, NAME, BODY FROM CUSTOM_MESSAGE");
        query.append(" ORDER BY _ID DESC");
        Cursor cursor = sqLiteDatabase.rawQuery(query.toString(), null);
        List<CustomMessageDto> listDto = new ArrayList<>();
        while (cursor.moveToNext()) {
            CustomMessageDto dto = new CustomMessageDto(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2)
            );
            listDto.add(dto);
        }
        closeResource(cursor, sqLiteDatabase, helper);
        return  listDto;
    }

    public static CustomMessageDto selectCustomMessageBy(Context context, int messageId) {
        AwesomeManagerSQLiteHelper helper = new AwesomeManagerSQLiteHelper(context);
        SQLiteDatabase sqLiteDatabase = helper.getReadableDatabase();
        StringBuilder query = new StringBuilder();
        query.append("SELECT _ID, NAME, BODY FROM CUSTOM_MESSAGE WHERE _ID = '" + messageId + "'");
        Cursor cursor = sqLiteDatabase.rawQuery(query.toString(), null);
        cursor.moveToFirst();
        CustomMessageDto dto = new CustomMessageDto(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2)
        );
        closeResource(cursor, sqLiteDatabase, helper);
        return  dto;
    }

    public static long insertCustomMessage(Context context, CustomMessageDto dto) {
        AwesomeManagerSQLiteHelper helper = new AwesomeManagerSQLiteHelper(context);
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("NAME", dto.getCustomMessageName());
        values.put("BODY", dto.getCustomMessageBody());
        long rowId = sqLiteDatabase.insert("CUSTOM_MESSAGE", null, values);
        AAFLogger.info("CustomMessageDao-insertCustomMessage INFO: rowId is " + rowId, CustomMessageDao.class);
        closeResource(null, sqLiteDatabase, helper);
        return rowId;
    }

    public static void deleteCustomMessageBy(Context context, int id) {
        AwesomeManagerSQLiteHelper helper = new AwesomeManagerSQLiteHelper(context);
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();
        try {
            sqLiteDatabase.delete("CUSTOM_MESSAGE", "_ID= '" + id + "'", null);
        } catch (Exception e) {
            AAFLogger.error("CustomMessageDao-deleteCustomMessageBy ERROR: " + e.getMessage(), CustomMessageDao.class);
            CommonUtils.makeToast(context, e.getMessage());
        }
        closeResource(null, sqLiteDatabase, helper);
    }

    public static void closeResource(Cursor cursor, SQLiteDatabase sqLiteDatabase, SQLiteOpenHelper sqLiteOpenHelper) {
        if (cursor != null) cursor.close();
        if (sqLiteDatabase != null) sqLiteDatabase.close();
        if (sqLiteOpenHelper != null) sqLiteOpenHelper.close();
    }

}
