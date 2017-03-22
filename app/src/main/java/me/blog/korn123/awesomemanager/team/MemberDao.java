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
 * Created by CHO HANJOONG on 2016-10-04.
 */

public class MemberDao {

    public static long insertMember(Context context, MemberDto dto) {
        AwesomeManagerSQLiteHelper helper = new AwesomeManagerSQLiteHelper(context);
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("MNGMT_ID", dto.getManagementId());
        values.put("T_ID", dto.getTeamId());
        values.put("NAME", dto.getName());
        values.put("BIRTHDAY", dto.getBirth());
        values.put("SCHOOL", dto.getSchool());
        values.put("GRADE", dto.getGrade());
        values.put("GENDER", dto.getGender());
        values.put("AL_CELLPHONE", dto.getAlarmCellphone());
        values.put("ORGN_ID", dto.getOriginalId());
        long result = sqLiteDatabase.insert("MEMBER", null, values);
        closeResource(null, sqLiteDatabase, helper);
        return result;
    }

    public static long deleteAll(Context context) {
        AwesomeManagerSQLiteHelper helper = new AwesomeManagerSQLiteHelper(context);
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();
        long result = -1;
        try {
            result = sqLiteDatabase.delete("MEMBER", null, null);
        } catch (Exception e) {
            AAFLogger.error("MemberDao-deleteAll ERROR: " + e.getMessage(), MemberDao.class);
            CommonUtils.makeToast(context, e.getMessage());
        }
        closeResource(null, sqLiteDatabase, helper);
        return result;
    }

    public static int countMemberBy(Context context, int teamId) {
        AwesomeManagerSQLiteHelper helper = new AwesomeManagerSQLiteHelper(context);
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();
        int count = 0;
        StringBuilder query = new StringBuilder();
        query.append("SELECT COUNT(_ID) FROM MEMBER WHERE T_ID = " + teamId);
//        AAFLogger.info("MemberDao-countMemberBy INFO: " + query, MemberDao.class);
        Cursor cursor = sqLiteDatabase.rawQuery(query.toString(), null);
        cursor.moveToFirst();
        count = cursor.getInt(0);
        closeResource(null, sqLiteDatabase, helper);
        return count;
    }

    // TODO 리턴값 확인
    public static long updateMemberBy(Context context, MemberDto dto) {
        AwesomeManagerSQLiteHelper helper = new AwesomeManagerSQLiteHelper(context);
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("T_ID", dto.getTeamId());
        values.put("NAME", dto.getName());
        values.put("BIRTHDAY", dto.getBirth());
        values.put("SCHOOL", dto.getSchool());
        values.put("GRADE", dto.getGrade());
        values.put("GENDER", dto.getGender());
        values.put("AL_CELLPHONE", dto.getAlarmCellphone());
        long result = sqLiteDatabase.update("MEMBER", values, "_ID= '" + dto.getMemberId() + "'", null);
        closeResource(null, sqLiteDatabase, helper);
        return result;
    }

    public static List<MemberDto> selectMembersBy(Context context, int teamId) {
        AwesomeManagerSQLiteHelper helper = new AwesomeManagerSQLiteHelper(context);
        SQLiteDatabase sqLiteDatabase = helper.getReadableDatabase();
        StringBuilder query = new StringBuilder();
        query.append("SELECT _ID, MNGMT_ID, T_ID, NAME, BIRTHDAY, SCHOOL, GRADE, GENDER, AL_CELLPHONE, ORGN_ID FROM MEMBER");
        query.append(" WHERE T_ID = " + teamId);
        query.append(" ORDER BY NAME ASC");
        Cursor cursor = sqLiteDatabase.rawQuery(query.toString(), null);
        List<MemberDto> listDto = new ArrayList<>();
        int tempIndex = 1;
        while (cursor.moveToNext()) {
            MemberDto dto = new MemberDto(
                    cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getInt(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getString(7),
                    cursor.getString(8),
                    cursor.getInt(9)
            );
            dto.setTempNo(tempIndex++);
            listDto.add(dto);
        }
        closeResource(cursor, sqLiteDatabase, helper);
        return  listDto;
    }

    public static List<MemberDto> selectMembersBy(Context context, String keyword) {
        AwesomeManagerSQLiteHelper helper = new AwesomeManagerSQLiteHelper(context);
        SQLiteDatabase sqLiteDatabase = helper.getReadableDatabase();
        StringBuilder query = new StringBuilder();
        if (keyword == null) {
            query.append("SELECT _ID, MNGMT_ID, T_ID, NAME, BIRTHDAY, SCHOOL, GRADE, GENDER, AL_CELLPHONE, ORGN_ID FROM MEMBER ORDER BY NAME ASC");
        } else {
            query.append("SELECT _ID, MNGMT_ID, T_ID, NAME, BIRTHDAY, SCHOOL, GRADE, GENDER, AL_CELLPHONE, ORGN_ID FROM MEMBER");
            query.append(" WHERE NAME LIKE '%"+ keyword + "%'");
            query.append(" ORDER BY NAME ASC");
        }
        Cursor cursor = sqLiteDatabase.rawQuery(query.toString(), null);
        List<MemberDto> listDto = new ArrayList<>();
        int tempIndex = 1;
        while (cursor.moveToNext()) {
            MemberDto dto = new MemberDto(
                    cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getInt(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getString(7),
                    cursor.getString(8),
                    cursor.getInt(9)
            );
            dto.setTempNo(tempIndex++);
            listDto.add(dto);
        }
        closeResource(cursor, sqLiteDatabase, helper);
        return  listDto;
    }

    public static int selectNewIdBy(Context context, int originalId, int managementId) {
        int newId = -1;
        AwesomeManagerSQLiteHelper helper = new AwesomeManagerSQLiteHelper(context);
        SQLiteDatabase sqLiteDatabase = helper.getReadableDatabase();
        StringBuilder query = new StringBuilder();
        query.append("SELECT _ID FROM MEMBER WHERE ORGN_ID = " + originalId);
        query.append(" AND MNGMT_ID = " + managementId);
        AAFLogger.info("MemberDao-selectNewIdBy INFO: " + query.toString(), PlaygroundDao.class);
        Cursor cursor = sqLiteDatabase.rawQuery(query.toString(), null);
        cursor.moveToNext();
        newId = cursor.getInt(0);
        closeResource(cursor, sqLiteDatabase, helper);
        return newId;
    }

    public static MemberDto selectMemberBy(Context context, int memberId) {
        AwesomeManagerSQLiteHelper helper = new AwesomeManagerSQLiteHelper(context);
        SQLiteDatabase sqLiteDatabase = helper.getReadableDatabase();
        StringBuilder query = new StringBuilder();
        query.append("SELECT _ID, MNGMT_ID, T_ID, NAME, BIRTHDAY, SCHOOL, GRADE, GENDER, AL_CELLPHONE, ORGN_ID FROM MEMBER");
        query.append(" WHERE _ID = '"+ memberId + "'");
        Cursor cursor = sqLiteDatabase.rawQuery(query.toString(), null);
        cursor.moveToFirst();
        MemberDto dto = new MemberDto(
                cursor.getInt(0),
                cursor.getInt(1),
                cursor.getInt(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getString(5),
                cursor.getString(6),
                cursor.getString(7),
                cursor.getString(8),
                cursor.getInt(9)
        );
        closeResource(cursor, sqLiteDatabase, helper);
        return  dto;
    }

    public static void closeResource(Cursor cursor, SQLiteDatabase sqLiteDatabase, SQLiteOpenHelper sqLiteOpenHelper) {
        if (cursor != null) cursor.close();
        if (sqLiteDatabase != null) sqLiteDatabase.close();
        if (sqLiteOpenHelper != null) sqLiteOpenHelper.close();
    }

}
