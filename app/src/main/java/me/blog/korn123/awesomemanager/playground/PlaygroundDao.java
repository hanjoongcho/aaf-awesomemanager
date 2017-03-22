package me.blog.korn123.awesomemanager.playground;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import me.blog.korn123.awesomemanager.team.MemberDto;
import me.blog.korn123.log.AAFLogger;
import me.blog.korn123.support.AwesomeManagerSQLiteHelper;
import me.blog.korn123.utils.CommonUtils;

/**
 * Created by CHO HANJOONG on 2016-10-05.
 */

public class PlaygroundDao {

    public static long deleteAll(Context context) {
        AwesomeManagerSQLiteHelper helper = new AwesomeManagerSQLiteHelper(context);
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();
        long result = -1;
        try {
            result = sqLiteDatabase.delete("PLAYGROUND", null, null);
        } catch (Exception e) {
            AAFLogger.error("PlaygroundDao-deleteAll ERROR: " + e.getMessage(), PlaygroundDao.class);
            CommonUtils.makeToast(context, e.getMessage());
        }
        closeResource(null, sqLiteDatabase, helper);
        return result;
    }

    public static void deletePlaygroundBy(Context context, int id) {
        AwesomeManagerSQLiteHelper helper = new AwesomeManagerSQLiteHelper(context);
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();
        try {
            sqLiteDatabase.delete("PLAYGROUND", "_ID= '" + id + "'", null);
        } catch (Exception e) {
            AAFLogger.error("PlaygroundDao-deletePlaygroundBy ERROR: " + e.getMessage(), PlaygroundDao.class);
            CommonUtils.makeToast(context, e.getMessage());
        }
        closeResource(null, sqLiteDatabase, helper);
    }

    public static long insertPlayground(Context context, PlaygroundDto dto) {
        AwesomeManagerSQLiteHelper helper = new AwesomeManagerSQLiteHelper(context);
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("MNGMT_ID", dto.getManagementId());
        values.put("NAME", dto.getName());
        values.put("BRANCH", dto.getBranch());
        values.put("KIND", dto.getKind());
        values.put("ADDRESS", dto.getAddress());
        values.put("ETC_INFO", dto.getEtcInfo());
        values.put("ORGN_ID", dto.getOriginalId());
        long result = sqLiteDatabase.insert("PLAYGROUND", null, values);
        closeResource(null, sqLiteDatabase, helper);
        return result;
    }

    // TODO 리턴값 확인
    public static long updatePlaygroundBy(Context context, PlaygroundDto dto) {
        AwesomeManagerSQLiteHelper helper = new AwesomeManagerSQLiteHelper(context);
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("NAME", dto.getName());
        values.put("BRANCH", dto.getBranch());
        values.put("KIND", dto.getKind());
        values.put("ADDRESS", dto.getAddress());
        values.put("ETC_INFO", dto.getEtcInfo());
        long result = sqLiteDatabase.update("PLAYGROUND", values, "_ID= '" + dto.getId() + "'", null);
        closeResource(null, sqLiteDatabase, helper);
        return result;
    }

    public static int selectNewIdBy(Context context, int originalId, int managementId) {
        int newId = -1;
        AwesomeManagerSQLiteHelper helper = new AwesomeManagerSQLiteHelper(context);
        SQLiteDatabase sqLiteDatabase = helper.getReadableDatabase();
        StringBuilder query = new StringBuilder();
        query.append("SELECT _ID FROM PLAYGROUND WHERE ORGN_ID = " + originalId);
        query.append(" AND MNGMT_ID = " + managementId);
        AAFLogger.info("PlaygroundDao-selectNewIdBy INFO: " + query.toString(), PlaygroundDao.class);
        Cursor cursor = sqLiteDatabase.rawQuery(query.toString(), null);
        cursor.moveToNext();
        newId = cursor.getInt(0);
        closeResource(cursor, sqLiteDatabase, helper);
        return newId;
    }

    public static PlaygroundDto selectPlaygroundBy(Context context, int id) {
        AwesomeManagerSQLiteHelper helper = new AwesomeManagerSQLiteHelper(context);
        SQLiteDatabase sqLiteDatabase = helper.getReadableDatabase();
        String query = "SELECT _ID, MNGMT_ID, NAME, BRANCH, KIND, ADDRESS, ETC_INFO, ORGN_ID FROM PLAYGROUND WHERE _ID = '" + id + "'";
        AAFLogger.info("PlaygroundDao-selectPlaygroundBy INFO: " + query, PlaygroundDao.class);
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        cursor.moveToNext();
        PlaygroundDto dto = new PlaygroundDto(
                cursor.getInt(0),
                cursor.getInt(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getString(5),
                cursor.getString(6),
                cursor.getInt(7)
        );
        closeResource(cursor, sqLiteDatabase, helper);
        return  dto;
    }

    public static PlaygroundDto selectPlaygroundBy(Context context, MemberDto memberDto) {
        AwesomeManagerSQLiteHelper helper = new AwesomeManagerSQLiteHelper(context);
        SQLiteDatabase sqLiteDatabase = helper.getReadableDatabase();
        String query = "SELECT _ID, MNGMT_ID, NAME, BRANCH, KIND, ADDRESS, ETC_INFO, ORGN_ID FROM PLAYGROUND WHERE _ID = (SELECT PLYGRUD_ID FROM TEAM WHERE _ID = " + memberDto.getTeamId() + ")";
        AAFLogger.info("PlaygroundDao-selectPlaygroundBy INFO: " + query, PlaygroundDao.class);
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        cursor.moveToNext();
        PlaygroundDto dto = new PlaygroundDto(
                cursor.getInt(0),
                cursor.getInt(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getString(5),
                cursor.getString(6),
                cursor.getInt(7)
        );
        closeResource(cursor, sqLiteDatabase, helper);
        return  dto;
    }

    public static List<PlaygroundDto> selectPlaygrounds(Context context, String keyword) {
        AwesomeManagerSQLiteHelper helper = new AwesomeManagerSQLiteHelper(context);
        SQLiteDatabase sqLiteDatabase = helper.getReadableDatabase();
        String query;
        if (keyword == null) {
            query = "SELECT _ID, MNGMT_ID, NAME, BRANCH, KIND, ADDRESS, ETC_INFO, ORGN_ID FROM PLAYGROUND ORDER BY NAME ASC";
        } else {
            query = "SELECT _ID, MNGMT_ID, NAME, BRANCH, KIND, ADDRESS, ETC_INFO, ORGN_ID FROM PLAYGROUND" +
                    " WHERE NAME LIKE '%"+ keyword + "%' OR" +
                    " BRANCH LIKE '%"+ keyword + "%' OR" +
                    " ADDRESS LIKE '%"+ keyword + "%'" +
                    " ORDER BY NAME ASC";
        }
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        List<PlaygroundDto> listDto = new ArrayList<>();
        while (cursor.moveToNext()) {
            PlaygroundDto dto = new PlaygroundDto(
                    cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getInt(7)
            );
            listDto.add(dto);
        }
        closeResource(cursor, sqLiteDatabase, helper);
        return  listDto;
    }

    public static void closeResource(Cursor cursor, SQLiteDatabase sqLiteDatabase, SQLiteOpenHelper sqLiteOpenHelper) {
        if (cursor != null) cursor.close();
        if (sqLiteDatabase != null) sqLiteDatabase.close();
        if (sqLiteOpenHelper != null) sqLiteOpenHelper.close();
    }
}
