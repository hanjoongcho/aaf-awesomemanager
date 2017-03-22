package me.blog.korn123.support;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import me.blog.korn123.awesomemanager.team.CustomMessageDao;
import me.blog.korn123.awesomemanager.team.CustomMessageDto;

/**
 * Created by CHO HANJOONG on 2016-10-10.
 */

public class AwesomeManagerSQLiteHelper extends SQLiteOpenHelper {

    public AwesomeManagerSQLiteHelper (Context context) {
        super(context, "AAF_AWESOME_MANAGER.DB", null, 2);
    }

    @Override
    public SQLiteDatabase getWritableDatabase() {
        SQLiteDatabase sqLiteDatabase = super.getWritableDatabase();
        sqLiteDatabase.setForeignKeyConstraintsEnabled(true);
        return sqLiteDatabase;
    }

    public void onCreate(SQLiteDatabase db) {
        String CREATE_MANAGEMENT_QUERY = "CREATE TABLE MANAGEMENT(" +
                "_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                " DVIC_ID TEXT NOT NULL);";

        String CREATE_BASIC_INFORMATION_QUERY = "CREATE TABLE BASIC_INFORMATION(" +
                "_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                " MNGMT_ID INTEGER," +
                " CATEGORY TEXT NOT NULL," +
                " BASIFMAT_VALUE TEXT NOT NULL," +
                " FOREIGN KEY(MNGMT_ID) REFERENCES MANAGEMENT(_ID));";

        String CREATE_PLAYGROUND_QUERY = "CREATE TABLE PLAYGROUND(" +
                "_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                " MNGMT_ID INTEGER," +
                " NAME TEXT NOT NULL," +
                " BRANCH TEXT NOT NULL," +
                " KIND TEXT NOT NULL," +
                " ADDRESS TEXT NOT NULL," +
                " ETC_INFO TEXT," +
                " ORGN_ID INTEGER," +
                " FOREIGN KEY(MNGMT_ID) REFERENCES MANAGEMENT(_ID));";

        String CREATE_TEAM_QUERY = "CREATE TABLE TEAM(" +
                "_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                " MNGMT_ID INTEGER," +
                " PLYGRUD_ID INTEGER ," +
                " NAME TEXT NOT NULL," +
                " COACH TEXT NOT NULL," +
                " SCHOOL TEXT NOT NULL," +
                " GRADE TEXT NOT NULL," +
                " BRANCH TEXT NOT NULL," +
                " EVENT TEXT NOT NULL," +
                " GENDER TEXT," +
                " MAXIMUM INTEGER," +
                " ETC_INFO TEXT," +
                " ORGN_ID INTEGER," +
                " FOREIGN KEY(MNGMT_ID) REFERENCES MANAGEMENT(_ID)," +
                " FOREIGN KEY(PLYGRUD_ID) REFERENCES PLAYGROUND(_ID));";

        String CREATE_MEMBER_QUERY = "CREATE TABLE MEMBER(" +
                "_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                " MNGMT_ID INTEGER," +
                " T_ID INTEGER NOT NULL," +
                " NAME TEXT ," +
                " BIRTHDAY TEXT," +
                " SCHOOL TEXT," +
                " GRADE TEXT," +
                " GENDER TEXT," +
                " AL_CELLPHONE TEXT," +
                " ETC_INFO TEXT," +
                " ORGN_ID INTEGER," +
                " FOREIGN KEY(MNGMT_ID) REFERENCES MANAGEMENT(_ID)," +
                " FOREIGN KEY(T_ID) REFERENCES TEAM(_ID));";

        String CREATE_ATTENDANCE_QUERY = "CREATE TABLE ATTENDANCE(" +
                "_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                " MNGMT_ID INTEGER," +
                " MB_ID INTEGER NOT NULL," +
                " T_ID INTEGER NOT NULL," +
                " RGST_DATE TEXT ," +
                " FLAG INTEGER, " +
                " ORGN_ID INTEGER," +
                " FOREIGN KEY(MNGMT_ID) REFERENCES MANAGEMENT(_ID)," +
                " FOREIGN KEY(T_ID) REFERENCES TEAM(_ID)," +
                " FOREIGN KEY(MB_ID) REFERENCES MEMBER(_ID));";

        String CREATE_CUSTOM_MESSAGE_QUERY = "CREATE TABLE CUSTOM_MESSAGE(" +
                "_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                " NAME TEXT NOT NULL," +
                " BODY TEXT NOT NULL);";

        db.execSQL(CREATE_MANAGEMENT_QUERY);
        db.execSQL(CREATE_BASIC_INFORMATION_QUERY);
        db.execSQL(CREATE_PLAYGROUND_QUERY);
        db.execSQL(CREATE_TEAM_QUERY);
        db.execSQL(CREATE_MEMBER_QUERY);
        db.execSQL(CREATE_ATTENDANCE_QUERY);
        db.execSQL(CREATE_CUSTOM_MESSAGE_QUERY);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            String CREATE_CUSTOM_MESSAGE_QUERY = "CREATE TABLE CUSTOM_MESSAGE(" +
                    "_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " NAME TEXT NOT NULL," +
                    " BODY TEXT NOT NULL);";

            db.execSQL(CREATE_CUSTOM_MESSAGE_QUERY);
        }
        if (oldVersion < 3) {
        }
    }
}
