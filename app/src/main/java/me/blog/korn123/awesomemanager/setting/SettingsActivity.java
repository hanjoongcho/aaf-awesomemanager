package me.blog.korn123.awesomemanager.setting;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import org.apache.commons.lang3.StringUtils;

import me.blog.korn123.awesomemanager.LoginActivity;
import me.blog.korn123.awesomemanager.R;
import me.blog.korn123.awesomemanager.playground.PlaygroundDao;
import me.blog.korn123.awesomemanager.team.BasicInformationDao;
import me.blog.korn123.awesomemanager.team.MemberDao;
import me.blog.korn123.awesomemanager.team.TeamAttendanceDao;
import me.blog.korn123.awesomemanager.team.TeamDao;
import me.blog.korn123.log.AAFLogger;
import me.blog.korn123.utils.CommonUtils;
import me.blog.korn123.utils.DialogUtils;

/**
 * A {@link PreferenceActivity} that presents a set of application settings. On
 * handset devices, settings are presented as a single list. On tablets,
 * settings are split by category, with category headers shown to the left of
 * the list of settings.
 * <p/>
 * See <a href="http://developer.android.com/design/patterns/settings.html">
 * Android Design: Settings</a> for design guidelines and the <a
 * href="http://developer.android.com/guide/topics/ui/settings.html">Settings
 * API Guide</a> for more information on developing a Settings UI.
 */
public class SettingsActivity extends AppCompatPreferenceActivity {

    @Override
    protected void onPause() {
        super.onPause();
        CommonUtils.saveLongPreference(this, "pause_millis", System.currentTimeMillis());
//        CommonUtils.makeToast(this, "onPause");
    }

    protected void onResume() {
        super.onResume();
        long currentMillis = System.currentTimeMillis();
        long pauseMillis = CommonUtils.loadLongPreference(this, "pause_millis") + (1000 * 60);
//        CommonUtils.makeToast(this, "onResume, " + ((currentMillis-CommonUtils.loadLongPreference(this, "pause_millis"))/1000));
        if (currentMillis > pauseMillis) { // activity pause 후 1분이 지난경우
            Intent login = new Intent(this, LoginActivity.class);
            login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(login);
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(listener);
    }

    SharedPreferences.OnSharedPreferenceChangeListener listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
//            TODO 스타일 설정방법, 액션바처리 등..
            if (StringUtils.equals(key, "app_theme")) {
                ListPreference listPreference = (ListPreference) findPreference("app_theme");
                CommonUtils.makeToast(SettingsActivity.this, listPreference.getValue());
//                CommonUtils.saveStringPreference(SettingsActivity.this, "app_theme", listPreference.getValue());
                startActivity(new Intent(SettingsActivity.this, SettingsActivity.class));
                overridePendingTransition(0, 0);
                finish();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (StringUtils.equals("purple", CommonUtils.loadStringPreference(this, "app_theme", "basic"))) {
            setTheme(R.style.CustomPurpleWithActionBar);
        }
        super.onCreate(savedInstanceState);
        setupActionBar();
        addPreferencesFromResource(R.xml.pref_general);
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(listener);
        initPreference();
        bindClickEvent();
    }

    Preference mAppVersionPreference;
    Preference mInitDataPreference;
//    Preference mAAFAppLinkPreference;
    Preference mInfoMessagePreference;
    Preference mExportGoogleDrive;
    Preference mImportGoogleDrive;
    Preference mOpenSourceLicensesInfo;
    private void initPreference() {
        mAppVersionPreference = findPreference("aaf_app_version");
        mInitDataPreference = findPreference("init_data");
//        mAAFAppLinkPreference = findPreference("aaf_app_link");
        mInfoMessagePreference = findPreference("info_message");
        mExportGoogleDrive= findPreference("export_google_drive");
        mImportGoogleDrive= findPreference("import_google_drive");
        mOpenSourceLicensesInfo = findPreference("open_source_licenses");
        PackageInfo pInfo = null;
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            AAFLogger.error("SettingsActivity-initPreference ERROR: " + e.getMessage(), getClass());
        }
        String version = pInfo.versionName;
        mAppVersionPreference.setTitle("Awesome Manager Version");
        mAppVersionPreference.setSummary("v " + version);

        mOpenSourceLicensesInfo.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent intent = new Intent(SettingsActivity.this, LicensesActivity.class);
                startActivity(intent);
                return false;
            }
        });
    }

    Handler settingHandler = new Handler(new Handler.Callback() {
        public boolean handleMessage(Message msg) {
            if (msg.obj instanceof String) {
                if (StringUtils.equals((String) msg.obj, "InitDataThread")) {
                    mProgressDialog.dismiss();
                }
            }
            return true;
        }
    });

    public class InitDataHandler {
        public void start() {
            Thread initDataThread = new InitDataThread();
            initDataThread.start();
            mProgressDialog = ProgressDialog.show(SettingsActivity.this, "데이터 초기화", "데이터 삭제중...");
        }
    }

    class InitDataThread extends Thread {
        @Override
        public void run() {
            // 작업시작
            TeamAttendanceDao.deleteAll(SettingsActivity.this);
            MemberDao.deleteAll(SettingsActivity.this);
            TeamDao.deleteAll(SettingsActivity.this);
            PlaygroundDao.deleteAll(SettingsActivity.this);
            BasicInformationDao.deleteAll(SettingsActivity.this);
            // 작업종료
            Message completeMsg = settingHandler.obtainMessage();
            completeMsg.obj = "InitDataThread";
            settingHandler.sendMessage(completeMsg);
        }
    }

    private ProgressDialog mProgressDialog;
    private void bindClickEvent() {
        mInfoMessagePreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                StringBuilder sb = new StringBuilder();
                sb.append("'핸드폰 변경'시 아래 순서에 따라 데이터를 백업하셔야 합니다.");
                sb.append("\n\n[이전 핸드폰에서 백업]");
                sb.append("\n환경설정(우측 상단 공구 아이콘) - 구글 계정으로 백업 - 로그인 후 백업파일 저장");
                sb.append("\n\n[새 핸드폰에서 복구]");
                sb.append("\n환경설정(우측 상단 공구 아이콘) - 구글 계정으로 복구 - 로그인 후 위에서 저장한 파일 선택");
                DialogUtils.showAlertDialogOnlyPositive(SettingsActivity.this, "주의사항", sb.toString());
                return false;
            }
        });

        mInitDataPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                InitDataHandler initDataHandler = new InitDataHandler();
                StringBuilder sb = new StringBuilder();
                sb.append("그 동안 입력했던 데이터가 모두 삭제됩니다. 한 번 초기화된 데이터는 복구가 불가능합니다. 계속하시겠습니까?");
                DialogUtils.initDataPreferenceConfirm(SettingsActivity.this, initDataHandler, "데이터 초기화", sb.toString());
                return false;
            }
        });

        mExportGoogleDrive.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                StringBuilder sb = new StringBuilder();
                sb.append("Awesome Manager 어플리케이션의 모든데이터는 사용자기기에 저장이 됩니다.\n\n");
                sb.append("따라서 데이터를 백업하거나 복구하기 위해서는 구글드라이브 클라우드 서비스를 이용해야 합니다. ");
                sb.append("구글 OAuth 기능을 통하여 구글 계정과 연동되므로 안전하게 자료를 보관하실 수 있습니다.\n\n");
                sb.append("기기에 로그인된 계정중 개인 구글개정을 선택하시고 '확인'을 누르시면 됩니다.");
                DialogUtils.showGoogleDriveUploaderDialog(SettingsActivity.this, SettingsActivity.this, "구글 계정으로 백업", sb.toString());
                return false;
            }
        });

        mImportGoogleDrive.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                StringBuilder sb = new StringBuilder();
                sb.append("Awesome Manager 어플리케이션의 모든데이터는 사용자기기에 저장이 됩니다.\n\n");
                sb.append("따라서 데이터를 백업하거나 복구하기 위해서는 구글드라이브 클라우드 서비스를 이용해야 합니다. ");
                sb.append("구글 OAuth 기능을 통하여 구글 계정과 연동되므로 안전하게 자료를 보관하실 수 있습니다.\n\n");
                sb.append("기기에 로그인된 계정중 개인 구글개정을 선택하시고 '확인'을 누르시면 됩니다.");
                DialogUtils.showGoogleDriveDownloaderDialog(SettingsActivity.this, SettingsActivity.this, "구글 계정에서 복구", sb.toString());
                return false;
            }
        });

//        mAAFAppLinkPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
//            public boolean onPreferenceClick(Preference preference) {
////                final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
//                final String appPackageName = "me.blog.korn123.easyphotomap";
//                try {
//                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
//                } catch (android.content.ActivityNotFoundException anfe) {
//                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
//                }
//
//                return false;
//            }
//        });
    }

    /**
     * Set up the {@link android.app.ActionBar}, if the API is available.
     */
    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return true;
    }

}
