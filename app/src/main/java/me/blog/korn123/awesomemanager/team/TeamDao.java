package me.blog.korn123.awesomemanager.team;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
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

public class TeamDao {

    public static long insertTeam(Context context, TeamDto dto) {
        AwesomeManagerSQLiteHelper helper = new AwesomeManagerSQLiteHelper(context);
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("MNGMT_ID", dto.getManagementId());
        values.put("PLYGRUD_ID", dto.getPlaygroundId());
        values.put("NAME", dto.getName());
        values.put("COACH", dto.getCoach());
        values.put("SCHOOL", dto.getSchool());
        values.put("GRADE", dto.getGrade());
        values.put("BRANCH", dto.getBranch());
//        values.put("PLAYGROUND", dto.getPlayground());
        values.put("EVENT", dto.getEvent());
        values.put("GENDER", dto.getGender());
        values.put("MAXIMUM", dto.getMaximum());
        values.put("ETC_INFO", dto.getEtcInfo());
        values.put("ORGN_ID", dto.getOriginalId());
        long result = sqLiteDatabase.insert("TEAM", null, values);
        closeResource(null, sqLiteDatabase, helper);
        return result;
    }

    public static int selectNewIdBy(Context context, int originalId, int managementId) {
        int newId = -1;
        AwesomeManagerSQLiteHelper helper = new AwesomeManagerSQLiteHelper(context);
        SQLiteDatabase sqLiteDatabase = helper.getReadableDatabase();
        StringBuilder query = new StringBuilder();
        query.append("SELECT _ID FROM TEAM WHERE ORGN_ID = " + originalId);
        query.append(" AND MNGMT_ID = " + managementId);
        AAFLogger.info("TeamDao-selectNewIdBy INFO: " + query.toString(), PlaygroundDao.class);
        Cursor cursor = sqLiteDatabase.rawQuery(query.toString(), null);
        cursor.moveToNext();
        newId = cursor.getInt(0);
        closeResource(cursor, sqLiteDatabase, helper);
        return newId;
    }

    public static TeamDto selectTeamBy(Context context, int id) {
        AwesomeManagerSQLiteHelper helper = new AwesomeManagerSQLiteHelper(context);
        SQLiteDatabase sqLiteDatabase = helper.getReadableDatabase();
        String query = "SELECT _ID, MNGMT_ID, PLYGRUD_ID, NAME, COACH, SCHOOL, GRADE, BRANCH, EVENT, GENDER, MAXIMUM, ETC_INFO, ORGN_ID FROM TEAM" +
                " WHERE _ID = '"+ id + "' ORDER BY NAME ASC";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        List<TeamDto> listDto = new ArrayList<>();
        cursor.moveToFirst();
        TeamDto dto = new TeamDto(
                cursor.getInt(0),
                cursor.getInt(1),
                cursor.getInt(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getString(5),
                cursor.getString(6),
                cursor.getString(7),
                cursor.getString(8),
                cursor.getString(9),
                cursor.getInt(10),
                cursor.getString(11),
                cursor.getInt(12)
        );
        closeResource(cursor, sqLiteDatabase, helper);
        return  dto;
    }

    public static List<TeamDto> selectTeamsBy(Context context, String keyword) {
        AwesomeManagerSQLiteHelper helper = new AwesomeManagerSQLiteHelper(context);
        SQLiteDatabase sqLiteDatabase = helper.getReadableDatabase();
        StringBuilder query = new StringBuilder();
        if (keyword == null) {
            query.append("SELECT _ID, MNGMT_ID, PLYGRUD_ID, NAME, COACH, SCHOOL, GRADE, BRANCH, EVENT, GENDER, MAXIMUM, ETC_INFO, ORGN_ID FROM TEAM ORDER BY NAME ASC");
        } else {
            query.append("SELECT _ID, MNGMT_ID, PLYGRUD_ID, NAME, COACH, SCHOOL, GRADE, BRANCH, EVENT, GENDER, MAXIMUM, ETC_INFO, ORGN_ID FROM TEAM");
            query.append(" WHERE NAME LIKE '%"+ keyword + "%'");
            query.append(" OR SCHOOL LIKE '%"+ keyword + "%'");
            query.append(" ORDER BY NAME ASC");
        }
        Cursor cursor = sqLiteDatabase.rawQuery(query.toString(), null);
        List<TeamDto> listDto = new ArrayList<>();
        while (cursor.moveToNext()) {
            TeamDto dto = new TeamDto(
                    cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getInt(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getString(7),
                    cursor.getString(8),
                    cursor.getString(9),
                    cursor.getInt(10),
                    cursor.getString(11),
                    cursor.getInt(12)
            );
            listDto.add(dto);
        }
        closeResource(cursor, sqLiteDatabase, helper);
        return  listDto;
    }

    // TODO 리턴값 확인
    public static long updateTeamBy(Context context, TeamDto dto) {
        AwesomeManagerSQLiteHelper helper = new AwesomeManagerSQLiteHelper(context);
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("PLYGRUD_ID", dto.getPlaygroundId());
        values.put("NAME", dto.getName());
        values.put("COACH", dto.getCoach());
        values.put("SCHOOL", dto.getSchool());
        values.put("GRADE", dto.getGrade());
        values.put("BRANCH", dto.getBranch());
        values.put("EVENT", dto.getEvent());
        values.put("GENDER", dto.getGender());
        values.put("MAXIMUM", dto.getMaximum());
        values.put("ETC_INFO", dto.getEtcInfo());
        long result = sqLiteDatabase.update("TEAM", values, "_ID= '" + dto.getId() + "'", null);
        closeResource(null, sqLiteDatabase, helper);
        return result;
    }

    public static long deleteAll(Context context) {
        AwesomeManagerSQLiteHelper helper = new AwesomeManagerSQLiteHelper(context);
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();
        long result = -1;
        try {
            result = sqLiteDatabase.delete("TEAM", null, null);
        } catch (Exception e) {
            AAFLogger.error("TeamDao-deleteAll ERROR: " + e.getMessage(), TeamDao.class);
            CommonUtils.makeToast(context, e.getMessage());
        }
        closeResource(null, sqLiteDatabase, helper);
        return result;
    }

    public static void closeResource(Cursor cursor, SQLiteDatabase sqLiteDatabase, SQLiteOpenHelper sqLiteOpenHelper) {
        if (cursor != null) cursor.close();
        if (sqLiteDatabase != null) sqLiteDatabase.close();
        if (sqLiteOpenHelper != null) sqLiteOpenHelper.close();
    }

}
