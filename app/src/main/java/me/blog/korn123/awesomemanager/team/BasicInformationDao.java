package me.blog.korn123.awesomemanager.team;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import me.blog.korn123.log.AAFLogger;
import me.blog.korn123.support.AwesomeManagerSQLiteHelper;

/**
 * Created by CHO HANJOONG on 2016-10-08.
 */

public class BasicInformationDao {

    public static long deleteAll(Context context) {
        AwesomeManagerSQLiteHelper helper = new AwesomeManagerSQLiteHelper(context);
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();
        long result = sqLiteDatabase.delete("BASIC_INFORMATION", null, null);
        closeResource(null, sqLiteDatabase, helper);
        return result;
    }

    public static long insertCategoryValue(Context context, int managementId, String category, String basicInformationValue) {
        AwesomeManagerSQLiteHelper helper = new AwesomeManagerSQLiteHelper(context);
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("MNGMT_ID", managementId);
        values.put("CATEGORY", category);
        values.put("BASIFMAT_VALUE", basicInformationValue);
        long rowId = sqLiteDatabase.insert("BASIC_INFORMATION", null, values);
        AAFLogger.info("BasicInformationDao-insertCategoryValue INFO: rowId is " + rowId, BasicInformationDao.class);
        closeResource(null, sqLiteDatabase, helper);
        return rowId;
    }

    public static void deleteCategoryValueBy(Context context, String basicInformationValue) {
        AwesomeManagerSQLiteHelper helper = new AwesomeManagerSQLiteHelper(context);
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();
//        String query = "DELETE FROM BASIC_INFORMATION WHERE BASIFMAT_VALUE = '" + basicInformationValue + "'";
//        Log.i("query", query);
//        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        int result = sqLiteDatabase.delete("BASIC_INFORMATION", "BASIFMAT_VALUE= '" + basicInformationValue + "'", null);
        closeResource(null, sqLiteDatabase, helper);
    }

    public static List<String> selectCategoryValuesBy(Context context, String category) {
        AwesomeManagerSQLiteHelper helper = new AwesomeManagerSQLiteHelper(context);
        SQLiteDatabase sqLiteDatabase = helper.getReadableDatabase();
        StringBuilder query = new StringBuilder();
        if (StringUtils.isEmpty(category)) {
            query.append("SELECT BASIFMAT_VALUE FROM BASIC_INFORMATION ORDER BY CATEGORY ASC");
        } else {
            query.append("SELECT BASIFMAT_VALUE FROM BASIC_INFORMATION WHERE CATEGORY = '" + category + "' ORDER BY BASIFMAT_VALUE ASC");
        }
        Cursor cursor = sqLiteDatabase.rawQuery(query.toString(), null);
        List<String> listCategoryValue = new ArrayList<String>();
        while (cursor.moveToNext()) {
            listCategoryValue.add(cursor.getString(0));
        }
        closeResource(cursor, sqLiteDatabase, helper);
        return listCategoryValue;
    }

    public static List<BasicInformationDto> selectCategoryValuesBy(Context context) {
        AwesomeManagerSQLiteHelper helper = new AwesomeManagerSQLiteHelper(context);
        SQLiteDatabase sqLiteDatabase = helper.getReadableDatabase();
        StringBuilder query = new StringBuilder();
        query.append("SELECT _ID, MNGMT_ID, CATEGORY, BASIFMAT_VALUE FROM BASIC_INFORMATION ORDER BY CATEGORY ASC");
        Cursor cursor = sqLiteDatabase.rawQuery(query.toString(), null);
        List<BasicInformationDto> listBasicInformationDto = new ArrayList<BasicInformationDto>();
        while (cursor.moveToNext()) {
            BasicInformationDto dto = new BasicInformationDto(
                cursor.getInt(0),
                cursor.getInt(1),
                cursor.getString(2),
                cursor.getString(3)
            );
            listBasicInformationDto.add(dto);
        }
        closeResource(cursor, sqLiteDatabase, helper);
        return listBasicInformationDto;
    }

    public static void closeResource(Cursor cursor, SQLiteDatabase sqLiteDatabase, SQLiteOpenHelper sqLiteOpenHelper) {
        if (cursor != null) cursor.close();
        if (sqLiteDatabase != null) sqLiteDatabase.close();
        if (sqLiteOpenHelper != null) sqLiteOpenHelper.close();
    }
}
