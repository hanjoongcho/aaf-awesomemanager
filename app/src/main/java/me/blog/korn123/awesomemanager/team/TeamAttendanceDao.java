package me.blog.korn123.awesomemanager.team;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import me.blog.korn123.log.AAFLogger;
import me.blog.korn123.support.AwesomeManagerSQLiteHelper;

/**
 * Created by CHO HANJOONG on 2016-10-13.
 */

public class TeamAttendanceDao {

    public static long insertAttendance(Context context, TeamAttendanceDto dto) {
        AwesomeManagerSQLiteHelper helper = new AwesomeManagerSQLiteHelper(context);
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("MNGMT_ID", dto.getManagementId());
        values.put("MB_ID", dto.getMemberId());
        values.put("T_ID", dto.getTeamId());
        values.put("RGST_DATE", dto.getRegisterDate());
        values.put("FLAG", dto.getFlag());
        values.put("ORGN_ID", dto.getOriginalId());
        long result = sqLiteDatabase.insert("ATTENDANCE", null, values);
        closeResource(null, sqLiteDatabase, helper);
        return result;
    }

    public static long deleteAll(Context context) {
        AwesomeManagerSQLiteHelper helper = new AwesomeManagerSQLiteHelper(context);
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();
        long result = sqLiteDatabase.delete("ATTENDANCE", null, null);
        closeResource(null, sqLiteDatabase, helper);
        return result;
    }

    public static List<TeamAttendanceDto> selectAttendancesBy(Context context, String memberName, String teamName, String startDate, String endDate) {
        AwesomeManagerSQLiteHelper helper = new AwesomeManagerSQLiteHelper(context);
        SQLiteDatabase sqLiteDatabase = helper.getReadableDatabase();
        StringBuilder query = new StringBuilder();
        // TODO sub query search
        if (StringUtils.isEmpty(memberName) && StringUtils.isEmpty(teamName) && StringUtils.isEmpty(startDate) && StringUtils.isEmpty(endDate)) {
            query.append("SELECT ATTENDANCE._ID, ATTENDANCE.MNGMT_ID, ATTENDANCE.MB_ID, ATTENDANCE.T_ID, ATTENDANCE.RGST_DATE, ATTENDANCE.FLAG, TEAM.NAME, MEMBER.NAME");
            query.append(" FROM ATTENDANCE INNER JOIN MEMBER ON ATTENDANCE.MB_ID = MEMBER._ID");
            query.append(" INNER JOIN TEAM ON ATTENDANCE.T_ID = TEAM._ID");
            query.append(" ORDER BY ATTENDANCE.RGST_DATE DESC");
        } else {
        // TODO filter search
            query.append("SELECT ATTENDANCE._ID, ATTENDANCE.MNGMT_ID, ATTENDANCE.MB_ID, ATTENDANCE.T_ID, ATTENDANCE.RGST_DATE, ATTENDANCE.FLAG, TEAM.NAME, MEMBER.NAME");
            query.append(" FROM ATTENDANCE INNER JOIN MEMBER ON ATTENDANCE.MB_ID = MEMBER._ID");
            query.append(" INNER JOIN TEAM ON ATTENDANCE.T_ID = TEAM._ID");
            query.append(" WHERE MEMBER.NAME LIKE '%" + memberName + "%'");
            query.append(" AND TEAM.NAME LIKE '%" + teamName + "%'");
            query.append(" AND Date(ATTENDANCE.RGST_DATE) BETWEEN " + "Date('" + startDate+ "')" +" AND " + "Date('" + endDate+ "')");
            query.append(" ORDER BY ATTENDANCE.RGST_DATE DESC");
        }
        AAFLogger.info("TeamAttendanceDao-selectAttendancesBy INFO: " + query.toString(), TeamAttendanceDao.class);
        Cursor cursor = sqLiteDatabase.rawQuery(query.toString(), null);
        List<TeamAttendanceDto> listDto = new ArrayList<>();
        int tempIndex = 1;
        while (cursor.moveToNext()) {
            TeamAttendanceDto dto = new TeamAttendanceDto(
                    cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getInt(2),
                    cursor.getInt(3),
                    cursor.getString(4),
                    cursor.getInt(5),
                    -1,
                    cursor.getString(6),
                    cursor.getString(7)
                    );
            dto.setTempNo(tempIndex++);
            listDto.add(dto);
        }
        closeResource(cursor, sqLiteDatabase, helper);
        return  listDto;
    }

    public static List<TeamAttendanceDto> selectAttendancesAll(Context context) {
        AwesomeManagerSQLiteHelper helper = new AwesomeManagerSQLiteHelper(context);
        SQLiteDatabase sqLiteDatabase = helper.getReadableDatabase();
        StringBuilder query = new StringBuilder();
        query.append("SELECT _ID, MNGMT_ID, MB_ID, T_ID, RGST_DATE, FLAG, ORGN_ID FROM ATTENDANCE");
        Cursor cursor = sqLiteDatabase.rawQuery(query.toString(), null);
        List<TeamAttendanceDto> listDto = new ArrayList<>();
//        TeamAttendanceDto(int attendanceId, int managementId, int memberId, int teamId, String registerDate, int flag, int originalId, String teamName, String memberName) {
        while (cursor.moveToNext()) {
            TeamAttendanceDto dto = new TeamAttendanceDto(
                    cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getInt(2),
                    cursor.getInt(3),
                    cursor.getString(4),
                    cursor.getInt(5),
                    cursor.getInt(6),
                    null,
                    null
            );
            listDto.add(dto);
        }
        closeResource(cursor, sqLiteDatabase, helper);
        return  listDto;
    }

    public static long updateAttendanceBy(Context context, TeamAttendanceDto dto) {
        AwesomeManagerSQLiteHelper helper = new AwesomeManagerSQLiteHelper(context);
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("RGST_DATE", dto.getRegisterDate());
        values.put("FLAG", dto.getFlag());
        long result = sqLiteDatabase.update("ATTENDANCE", values, "_ID= '" + dto.getAttendanceId() + "'", null);
        closeResource(null, sqLiteDatabase, helper);
        return result;
    }

    public static void closeResource(Cursor cursor, SQLiteDatabase sqLiteDatabase, SQLiteOpenHelper sqLiteOpenHelper) {
        if (cursor != null) cursor.close();
        if (sqLiteDatabase != null) sqLiteDatabase.close();
        if (sqLiteOpenHelper != null) sqLiteOpenHelper.close();
    }
}
