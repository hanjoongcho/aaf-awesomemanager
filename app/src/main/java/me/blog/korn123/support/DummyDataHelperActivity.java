package me.blog.korn123.support;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.blog.korn123.awesomemanager.R;
import me.blog.korn123.awesomemanager.management.ManagementDao;
import me.blog.korn123.awesomemanager.playground.PlaygroundDao;
import me.blog.korn123.awesomemanager.playground.PlaygroundDto;
import me.blog.korn123.awesomemanager.team.BasicInformationDao;
import me.blog.korn123.awesomemanager.team.BasicInformationDto;
import me.blog.korn123.awesomemanager.team.MemberDao;
import me.blog.korn123.awesomemanager.team.MemberDto;
import me.blog.korn123.awesomemanager.team.TeamAttendanceDao;
import me.blog.korn123.awesomemanager.team.TeamAttendanceDto;
import me.blog.korn123.awesomemanager.team.TeamDao;
import me.blog.korn123.awesomemanager.team.TeamDto;
import me.blog.korn123.constant.Constant;
import me.blog.korn123.utils.CommonUtils;

/**
 * Created by CHO HANJOONG on 2016-10-13.
 */

public class DummyDataHelperActivity extends AppCompatThemeActivity {

    private TextView mCurrentBasicInfo;
    private TextView mCurrentPlayground;
    private TextView mCurrentTeam;
    private TextView mCurrentMember;
    private TextView mCurrentAttendance;

    @Override
    public void refresh() {}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dummy_data_helper_main);
        mProgressBar = (ProgressBar)findViewById(R.id.progressBar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupActionBar();
        initData();
    }

    private void initData() {
        StringBuilder sb = new StringBuilder();

        mCurrentBasicInfo = (TextView) findViewById(R.id.currentBasicInfo);
        for (BasicInformationDto basicInformationDto : BasicInformationDao.selectCategoryValuesBy(this)) {
            sb.append("mng:" + basicInformationDto.getManagementId() + ", value: " + basicInformationDto.getBasicInformationValue() + "\n");
        }
        mCurrentBasicInfo.setText(sb.toString());

        sb.setLength(0);
        mCurrentPlayground = (TextView) findViewById(R.id.currentPlayground);
        for (PlaygroundDto playgroundDto : PlaygroundDao.selectPlaygrounds(this, null)) {
            sb.append("mng:" + playgroundDto.getManagementId() + ", seq: " + playgroundDto.getId() + ", oriseq: " + playgroundDto.getOriginalId() + "\n");
        }
        mCurrentPlayground.setText(sb.toString());

        sb.setLength(0);
        mCurrentTeam = (TextView) findViewById(R.id.currentTeam);
        for (TeamDto teamDto : TeamDao.selectTeamsBy(this, null)) {
            sb.append("mng:" + teamDto.getManagementId() + ", seq: " + teamDto.getId() + ", oriseq: " + teamDto.getOriginalId() + ", " + teamDto.getName() + "\n");
        }
        mCurrentTeam.setText(sb.toString());

        sb.setLength(0);
        mCurrentMember = (TextView) findViewById(R.id.currentMember);
        for (MemberDto memberDto : MemberDao.selectMembersBy(this, null)) {
            sb.append("mng:" + memberDto.getManagementId() + ", seq:" + memberDto.getMemberId() + ", oriseq: " + memberDto.getOriginalId() + ", " + memberDto.getName() + "\n");
        }
        mCurrentMember.setText(sb.toString());

        sb.setLength(0);
        mCurrentAttendance = (TextView) findViewById(R.id.currentAttendance);
        for (TeamAttendanceDto teamAttendanceDto : TeamAttendanceDao.selectAttendancesAll(this)) {
            sb.append("mng:" + teamAttendanceDto.getManagementId() + ", seq: " + teamAttendanceDto.getAttendanceId() + ", oriseq: " + teamAttendanceDto.getOriginalId()  + ", " + teamAttendanceDto.getMemberName() + ", " + teamAttendanceDto.getTeamName() + "\n");
        }
        mCurrentAttendance.setText(sb.toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public int doWork() {
        try {
            BasicInformationDao.insertCategoryValue(DummyDataHelperActivity.this, Constant.MASTER_DEVICE_MANAGEMENT_ID, mListBasicInformation.get(mProgressStatus).getCategory(), mListBasicInformation.get(mProgressStatus).getValue());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ++mProgressStatus;
    }

    class BasicInformationHelper {
        String category;
        String value;

        public BasicInformationHelper(String category, String value) {
            this.category = category;
            this.value = value;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    private ProgressBar mProgressBar;
    private int mProgressStatus = 0;
    private Handler mHandler = new Handler();
    List<BasicInformationHelper> mListBasicInformation = new ArrayList<>();
    public void onClick(View view) {
//        findViewById(R.id.mainScrollView).scrollTo(0, 0);
        OutputStream outputStream = null;
        InputStream inputStream = null;
        StringBuilder stringBuilder = null;
        switch (view.getId()) {
            case R.id.insertAllBasicInfo:
                mListBasicInformation.clear();
                mProgressStatus = 0;
                mProgressBar.setVisibility(View.VISIBLE);

                BasicInformationHelper basicInformationHelper = null;
                // 종목정보 등록
                List<String> listTemp = CommonUtils.getStringList(DummyDataHelperActivity.this, R.array.event_arrays);
                for (String event : listTemp) {
                    basicInformationHelper = new BasicInformationHelper(Constant.CATEGORY_EVENT, event);
                    mListBasicInformation.add(basicInformationHelper);
                }
                // 학년정보 등록
                listTemp = CommonUtils.getStringList(DummyDataHelperActivity.this, R.array.school_grade_arrays);
                for (String grade : listTemp) {
                    BasicInformationDao.insertCategoryValue(DummyDataHelperActivity.this, Constant.MASTER_DEVICE_MANAGEMENT_ID, Constant.CATEGORY_GRADE, grade);
                }
                // 지점정보 등록
                String[] arr = {"모란점", "판교점", "야탑점", "서현점", "이매점", "오리점", "영통점", "수지점"};
                listTemp = Arrays.asList(arr);
                for (String grade : listTemp) {
                    BasicInformationDao.insertCategoryValue(DummyDataHelperActivity.this, Constant.MASTER_DEVICE_MANAGEMENT_ID, Constant.CATEGORY_BRANCH, grade);
                }
                // 학교정보 등록
                String[] schoolArray = {"모란초등학교", "판교초등학교", "야탑초등학교", "서현초등학교", "이매초등학교", "오리초등학교", "영통초등학교", "수지초등학교"};
                listTemp = Arrays.asList(schoolArray);
                for (String grade : listTemp) {
                    BasicInformationDao.insertCategoryValue(DummyDataHelperActivity.this, Constant.MASTER_DEVICE_MANAGEMENT_ID, Constant.CATEGORY_SCHOOL, grade);
                }
                // 코치정보 등록
                String[] coachArray = {"조한중 코치", "강병곤 코치", "손병훈 코치", "이승철 코치", "윤영록 코치", "서동우 코치"};
                listTemp = Arrays.asList(coachArray);
                for (String grade : listTemp) {
                    BasicInformationDao.insertCategoryValue(DummyDataHelperActivity.this, Constant.MASTER_DEVICE_MANAGEMENT_ID, Constant.CATEGORY_COACH, grade);
                }
                new Thread(new Runnable() {
                    public void run() {
                        while (mProgressStatus < mListBasicInformation.size()) {
                            mProgressStatus = doWork();

                            // Update the progress bar
                            mHandler.post(new Runnable() {
                                public void run() {
                                    mProgressBar.setProgress(mProgressStatus);
                                }
                            });
                        }
                        mHandler.post(new Runnable() {
                            public void run() {
                                mProgressBar.setVisibility(View.INVISIBLE);
                                initData();
                            }
                        });
                    }
                }).start();

                break;
            case R.id.deleteAllBasicInfo:
                BasicInformationDao.deleteAll(this);
                break;
            case R.id.insertAllPlayground:
                PlaygroundDto dto = new PlaygroundDto(
                        -1,
                        Constant.MASTER_DEVICE_MANAGEMENT_ID,
                        "모란점 운동장 A",
                        "모란점",
                        "지점 운동장",
                        "성남시 모란 123",
                        "",
                        -1
                );
                PlaygroundDao.insertPlayground(this, dto);
                dto = new PlaygroundDto(
                        -1,
                        Constant.MASTER_DEVICE_MANAGEMENT_ID,
                        "모란점 운동장 B",
                        "모란점",
                        "지점 운동장",
                        "성남시 모란 123",
                        "",
                        -1
                );
                PlaygroundDao.insertPlayground(this, dto);
                dto = new PlaygroundDto(
                        -1,
                        Constant.MASTER_DEVICE_MANAGEMENT_ID,
                        "판교초등학교 운동장",
                        "판교점",
                        "학교 운동장",
                        "성남시 판교동 313",
                        "",
                        -1
                );
                PlaygroundDao.insertPlayground(this, dto);
                dto = new PlaygroundDto(
                        -1,
                        Constant.MASTER_DEVICE_MANAGEMENT_ID,
                        "야탑점 체육관",
                        "야탑점",
                        "지점 체육관",
                        "성남시 야탑동 981",
                        "",
                        -1
                );
                PlaygroundDao.insertPlayground(this, dto);
                dto = new PlaygroundDto(
                        -1,
                        Constant.MASTER_DEVICE_MANAGEMENT_ID,
                        "서현점 체육관",
                        "서현점",
                        "학교 체육관",
                        "성남시 서현 910",
                        "",
                        -1
                );
                PlaygroundDao.insertPlayground(this, dto);
                dto = new PlaygroundDto(
                        -1,
                        Constant.MASTER_DEVICE_MANAGEMENT_ID,
                        "영통점 체육관",
                        "영통점",
                        "학교 체육관",
                        "수원시 영통 910",
                        "",
                        -1
                );
                PlaygroundDao.insertPlayground(this, dto);

                break;
            case R.id.deleteAllPlayground:
                PlaygroundDao.deleteAll(this);
                break;
            case R.id.insertAllTeam:
                TeamDto teamDto = new TeamDto(
                        -1,
                        Constant.MASTER_DEVICE_MANAGEMENT_ID,
                        3,
                        "블랙쉐도우",
                        "조한중 코치",
                        "판교초등학교",
                        "1학년",
                        "판교점",
                        "축구",
                        "남성",
                        15,
                        "",
                        -1);
                TeamDao.insertTeam(this, teamDto);
                teamDto = new TeamDto(
                        -1,
                        Constant.MASTER_DEVICE_MANAGEMENT_ID,
                        3,
                        "레전드오브사커",
                        "조한중 코치",
                        "판교초등학교",
                        "2학년",
                        "판교점",
                        "축구",
                        "남성",
                        17,
                        "",
                        -1);
                TeamDao.insertTeam(this, teamDto);
                teamDto = new TeamDto(
                        -1,
                        Constant.MASTER_DEVICE_MANAGEMENT_ID,
                        3,
                        "판교슛돌이",
                        "조한중 코치",
                        "판교초등학교",
                        "2학년",
                        "판교점",
                        "축구",
                        "남성",
                        15,
                        "",
                        -1);
                TeamDao.insertTeam(this, teamDto);
                teamDto = new TeamDto(
                        -1,
                        Constant.MASTER_DEVICE_MANAGEMENT_ID,
                        3,
                        "리틀히어로",
                        "조한중 코치",
                        "판교초등학교",
                        "2학년",
                        "판교점",
                        "축구",
                        "남성",
                        15,
                        "",
                        -1);
                TeamDao.insertTeam(this, teamDto);
                teamDto = new TeamDto(
                        -1,
                        Constant.MASTER_DEVICE_MANAGEMENT_ID,
                        3,
                        "판교유나이티드",
                        "조한중 코치",
                        "판교초등학교",
                        "2학년",
                        "판교점",
                        "축구",
                        "남성",
                        15,
                        "",
                        -1);
                TeamDao.insertTeam(this, teamDto);
                break;
            case R.id.deleteAllTeam:
                TeamDao.deleteAll(this);
                break;
            case R.id.insertAllMember:
                MemberDto memberDto = new MemberDto(
                        -1,
                        Constant.MASTER_DEVICE_MANAGEMENT_ID,
                        1,
                        "정재곤",
                        "2006년" + "1월" + "1일",
                        "판교초등학교",
                        "1학년",
                        "남성",
                        "01031201547",
                        -1
                );
                MemberDao.insertMember(this, memberDto);
                memberDto = new MemberDto(
                        -1,
                        Constant.MASTER_DEVICE_MANAGEMENT_ID,
                        1,
                        "김상형",
                        "2006년" + "2월" + "2일",
                        "판교초등학교",
                        "1학년",
                        "남성",
                        "",
                        -1
                );
                MemberDao.insertMember(this, memberDto);
                memberDto = new MemberDto(
                        -1,
                        Constant.MASTER_DEVICE_MANAGEMENT_ID,
                        1,
                        "우재남",
                        "2006년" + "3월" + "3일",
                        "판교초등학교",
                        "1학년",
                        "남성",
                        "",
                        -1
                );
                MemberDao.insertMember(this, memberDto);
                memberDto = new MemberDto(
                        -1,
                        Constant.MASTER_DEVICE_MANAGEMENT_ID,
                        1,
                        "남진하",
                        "2006년" + "4월" + "3일",
                        "판교초등학교",
                        "1학년",
                        "남성",
                        "",
                        -1
                );
                MemberDao.insertMember(this, memberDto);
                memberDto = new MemberDto(
                        -1,
                        Constant.MASTER_DEVICE_MANAGEMENT_ID,
                        1,
                        "박상길",
                        "2006년" + "5월" + "5일",
                        "판교초등학교",
                        "1학년",
                        "남성",
                        "",
                        -1
                );
                MemberDao.insertMember(this, memberDto);
                memberDto = new MemberDto(
                        -1,
                        Constant.MASTER_DEVICE_MANAGEMENT_ID,
                        1,
                        "천인국",
                        "2006년" + "6월" + "6일",
                        "판교초등학교",
                        "1학년",
                        "남성",
                        "",
                        -1
                );
                MemberDao.insertMember(this, memberDto);
                memberDto = new MemberDto(
                        -1,
                        Constant.MASTER_DEVICE_MANAGEMENT_ID,
                        1,
                        "이준형",
                        "2006년" + "7월" + "7일",
                        "판교초등학교",
                        "1학년",
                        "남성",
                        "",
                        -1
                );
                MemberDao.insertMember(this, memberDto);
                memberDto = new MemberDto(
                        -1,
                        Constant.MASTER_DEVICE_MANAGEMENT_ID,
                        1,
                        "유동환",
                        "2006년" + "8월" + "8일",
                        "판교초등학교",
                        "1학년",
                        "남성",
                        "",
                        -1
                );
                MemberDao.insertMember(this, memberDto);
                break;
            case R.id.deleteAllMember:
                MemberDao.deleteAll(this);
                break;
            case R.id.insertAllAttendance:
                TeamAttendanceDto teamAttendanceDto = new TeamAttendanceDto(
                        -1,
                        Constant.MASTER_DEVICE_MANAGEMENT_ID,
                        1,  // memberId
                        1,  // teamId
                        "2016-10-12 15:00:00",
                        1,   // 출결
                        -1,
                        null,
                        null
                );
                TeamAttendanceDao.insertAttendance(this, teamAttendanceDto);
                teamAttendanceDto = new TeamAttendanceDto(
                        -1,
                        Constant.MASTER_DEVICE_MANAGEMENT_ID,
                        1,
                        1,
                        "2016-10-13 15:00:00",
                        1,
                        -1,
                        null,
                        null
                );
                TeamAttendanceDao.insertAttendance(this, teamAttendanceDto);
                teamAttendanceDto = new TeamAttendanceDto(
                        -1,
                        Constant.MASTER_DEVICE_MANAGEMENT_ID,
                        2,
                        1,
                        "2016-10-12 15:00:00",
                        1,
                        -1,
                        null,
                        null
                );
                TeamAttendanceDao.insertAttendance(this, teamAttendanceDto);
                teamAttendanceDto = new TeamAttendanceDto(
                        -1,
                        Constant.MASTER_DEVICE_MANAGEMENT_ID,
                        2,
                        1,
                        "2016-10-13 15:00:00",
                        1,
                        -1,
                        null,
                        null
                );
                TeamAttendanceDao.insertAttendance(this, teamAttendanceDto);
                break;
            case R.id.deleteAllAttendance:
                TeamAttendanceDao.deleteAll(this);
                break;
            case R.id.exportBasicInfo:
                List<BasicInformationDto> listBasicInformationDto = BasicInformationDao.selectCategoryValuesBy(this);
                stringBuilder = new StringBuilder(CommonUtils.loadStringPreference(DummyDataHelperActivity.this, "login_device_id", "") + "\n");
                for (BasicInformationDto basicInformation : listBasicInformationDto) {
                    stringBuilder.append(basicInformation.getCategory() + "|");
                    stringBuilder.append(basicInformation.getBasicInformationValue() + "\n");
                }
                try {
                    outputStream = FileUtils.openOutputStream(new File(Constant.WORKING_DIRECTORY + Constant.DATA_FILE_BASIC_INFORMATION));
                    IOUtils.write(stringBuilder.toString(), outputStream, "UTF-8");
                    IOUtils.closeQuietly(outputStream);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                CommonUtils.makeSnackBar(this, "export row " + listBasicInformationDto.size());
                break;
            case R.id.importBasicInfo:
                try {
                    inputStream = FileUtils.openInputStream(new File(Constant.WORKING_DIRECTORY + Constant.DATA_FILE_BASIC_INFORMATION));
                    List<String> listLine = IOUtils.readLines(inputStream, "UTF-8");
                    String deviceId = null;
                    int managementId = -1;
                    for (String line : listLine) {
                        if (deviceId == null) {
                            deviceId = line;
                            managementId  = ManagementDao.getManagementId(this, deviceId);
                            continue;
                        }
                        String[] tempArr = org.apache.commons.lang3.StringUtils.split(line, "|");
                        BasicInformationDao.insertCategoryValue(this, managementId, tempArr[0], tempArr[1]);
                    }
                    CommonUtils.makeSnackBar(this, "import row " + (listLine.size() - 1));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.importPlayground:
                try {
                    inputStream = FileUtils.openInputStream(new File(Constant.WORKING_DIRECTORY + Constant.DATA_FILE_PLAYGROUND));
                    List<String> listLine = IOUtils.readLines(inputStream, "UTF-8");
                    String deviceId = null;
                    int managementId = -1;
                    for (String line : listLine) {
                        if (deviceId == null) {
                            deviceId = line;
                            managementId  = ManagementDao.getManagementId(this, deviceId);
                            continue;
                        }
                        String[] tempArr = org.apache.commons.lang3.StringUtils.split(line, "|");
                        PlaygroundDto playgroundDto = new PlaygroundDto(
                            -1,
                            managementId,
                                tempArr[1],
                                tempArr[2],
                                tempArr[3],
                                tempArr[4],
                                tempArr[5],
                                Integer.parseInt(tempArr[0])

                        );
                        PlaygroundDao.insertPlayground(this, playgroundDto);
                    }
                    CommonUtils.makeSnackBar(this, "import row " + (listLine.size() - 1));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.exportPlayground:
                List<PlaygroundDto> listPlaygroundDto = PlaygroundDao.selectPlaygrounds(this, null);
                stringBuilder = new StringBuilder(CommonUtils.loadStringPreference(DummyDataHelperActivity.this, "login_device_id", "") + "\n");
                for (PlaygroundDto playgroundDto : listPlaygroundDto) {
                    stringBuilder.append(playgroundDto.getId() + "|");
                    stringBuilder.append(playgroundDto.getName() + "|");
                    stringBuilder.append(playgroundDto.getBranch() + "|");
                    stringBuilder.append(playgroundDto.getKind() + "|");
                    stringBuilder.append(playgroundDto.getAddress() + "|");
                    stringBuilder.append(playgroundDto.getEtcInfo() + "\n");
                }
                try {
                    outputStream = FileUtils.openOutputStream(new File(Constant.WORKING_DIRECTORY + Constant.DATA_FILE_PLAYGROUND));
                    IOUtils.write(stringBuilder.toString(), outputStream, "UTF-8");
                    IOUtils.closeQuietly(outputStream);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                CommonUtils.makeSnackBar(this, "export row " + listPlaygroundDto.size());
                break;
            case R.id.importTeam:
                try {
                    inputStream = FileUtils.openInputStream(new File(Constant.WORKING_DIRECTORY + Constant.DATA_FILE_TEAM));
                    List<String> listLine = IOUtils.readLines(inputStream, "UTF-8");
                    String deviceId = null;
                    int managementId = -1;
                    for (String line : listLine) {
                        if (deviceId == null) {
                            deviceId = line;
                            managementId  = ManagementDao.getManagementId(this, deviceId);
                            continue;
                        }
                        String[] tempArr = org.apache.commons.lang3.StringUtils.split(line, "|");
                        TeamDto teamDto1 = new TeamDto(
                                -1,
                                managementId,
                                PlaygroundDao.selectNewIdBy(this, Integer.valueOf(tempArr[1]), managementId),
                                tempArr[2],
                                tempArr[3],
                                tempArr[4],
                                tempArr[5],
                                tempArr[6],
                                tempArr[7],
                                tempArr[8],
                                Integer.valueOf(tempArr[9]),
                                tempArr[10],
                                Integer.parseInt(tempArr[0])
                        );
                        TeamDao.insertTeam(this, teamDto1);
                    }
                    CommonUtils.makeSnackBar(this, "import row " + (listLine.size() - 1));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.exportTeam:
                List<TeamDto> listTeamDto = TeamDao.selectTeamsBy(this, null);
                stringBuilder = new StringBuilder(CommonUtils.loadStringPreference(DummyDataHelperActivity.this, "login_device_id", "") + "\n");
                for (TeamDto teamDto2 : listTeamDto) {
                    stringBuilder.append(teamDto2.getId() + "|");
                    stringBuilder.append(teamDto2.getPlaygroundId() + "|");
                    stringBuilder.append(teamDto2.getName() + "|");
                    stringBuilder.append(teamDto2.getCoach() + "|");
                    stringBuilder.append(teamDto2.getSchool() + "|");
                    stringBuilder.append(teamDto2.getGrade() + "|");
                    stringBuilder.append(teamDto2.getBranch() + "|");
                    stringBuilder.append(teamDto2.getEvent() + "|");
                    stringBuilder.append(teamDto2.getGender() + "|");
                    stringBuilder.append(teamDto2.getMaximum() + "|");
                    stringBuilder.append(teamDto2.getEtcInfo() + "|");
                    stringBuilder.append(teamDto2.getOriginalId() + "\n");
                }
                try {
                    outputStream = FileUtils.openOutputStream(new File(Constant.WORKING_DIRECTORY + Constant.DATA_FILE_TEAM));
                    IOUtils.write(stringBuilder.toString(), outputStream, "UTF-8");
                    IOUtils.closeQuietly(outputStream);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                CommonUtils.makeSnackBar(this, "export row " + listTeamDto.size());
                break;
            case R.id.importMember:
                try {
                    inputStream = FileUtils.openInputStream(new File(Constant.WORKING_DIRECTORY + Constant.DATA_FILE_MEMBER));
                    List<String> listLine = IOUtils.readLines(inputStream, "UTF-8");
                    String deviceId = null;
                    int managementId = -1;
                    for (String line : listLine) {
                        if (deviceId == null) {
                            deviceId = line;
                            managementId  = ManagementDao.getManagementId(this, deviceId);
                            continue;
                        }
                        String[] tempArr = org.apache.commons.lang3.StringUtils.split(line, "|");
                        MemberDto memberDto1 = new MemberDto(
                                -1,
                                managementId,
                                TeamDao.selectNewIdBy(this, Integer.valueOf(tempArr[1]), managementId),
                                tempArr[2],
                                tempArr[3],
                                tempArr[4],
                                tempArr[5],
                                tempArr[6],
                                tempArr[7],
                                Integer.parseInt(tempArr[0])
                        );
                        MemberDao.insertMember(this, memberDto1);
                    }
                    CommonUtils.makeSnackBar(this, "import row " + (listLine.size() - 1));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            case R.id.exportMember:
                List<MemberDto> listMemberDto = MemberDao.selectMembersBy(this, null);
                stringBuilder = new StringBuilder(CommonUtils.loadStringPreference(DummyDataHelperActivity.this, "login_device_id", "") + "\n");
                for (MemberDto memberDto1 : listMemberDto) {
                    stringBuilder.append(memberDto1.getMemberId() + "|");
                    stringBuilder.append(memberDto1.getTeamId() + "|");
                    stringBuilder.append(memberDto1.getName() + "|");
                    stringBuilder.append(memberDto1.getBirth() + "|");
                    stringBuilder.append(memberDto1.getSchool() + "|");
                    stringBuilder.append(memberDto1.getGrade() + "|");
                    stringBuilder.append(memberDto1.getGender() + "|");
                    stringBuilder.append(memberDto1.getAlarmCellphone() + "|");
                    stringBuilder.append(memberDto1.getOriginalId() + "\n");
                }
                try {
                    outputStream = FileUtils.openOutputStream(new File(Constant.WORKING_DIRECTORY + Constant.DATA_FILE_MEMBER));
                    IOUtils.write(stringBuilder.toString(), outputStream, "UTF-8");
                    IOUtils.closeQuietly(outputStream);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                CommonUtils.makeSnackBar(this, "export row " + listMemberDto.size());
                break;
            case R.id.importAttendance:
                try {
                    inputStream = FileUtils.openInputStream(new File(Constant.WORKING_DIRECTORY + Constant.DATA_FILE_ATTENDANCE));
                    List<String> listLine = IOUtils.readLines(inputStream, "UTF-8");
                    String deviceId = null;
                    int managementId = -1;
                    for (String line : listLine) {
                        if (deviceId == null) {
                            deviceId = line;
                            managementId  = ManagementDao.getManagementId(this, deviceId);
                            continue;
                        }
                        String[] tempArr = org.apache.commons.lang3.StringUtils.split(line, "|");
                        TeamAttendanceDto teamAttendanceDto1 = new TeamAttendanceDto(
                                -1,
                                managementId,
                                MemberDao.selectNewIdBy(this, Integer.valueOf(tempArr[1]), managementId),
                                TeamDao.selectNewIdBy(this, Integer.valueOf(tempArr[2]), managementId),
                                tempArr[3],
                                Integer.parseInt(tempArr[4]),
                                Integer.parseInt(tempArr[0]),
                                null,
                                null
                        );
                        TeamAttendanceDao.insertAttendance(this, teamAttendanceDto1);
                    }
                    CommonUtils.makeSnackBar(this, "import row " + (listLine.size() - 1));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.exportAttendance:
                List<TeamAttendanceDto> listAttendanceDto = TeamAttendanceDao.selectAttendancesAll(this);
                stringBuilder = new StringBuilder(CommonUtils.loadStringPreference(DummyDataHelperActivity.this, "login_device_id", "") + "\n");
                for (TeamAttendanceDto teamAttendanceDto1 : listAttendanceDto) {
                    stringBuilder.append(teamAttendanceDto1.getAttendanceId() + "|");
                    stringBuilder.append(teamAttendanceDto1.getMemberId() + "|");
                    stringBuilder.append(teamAttendanceDto1.getTeamId() + "|");
                    stringBuilder.append(teamAttendanceDto1.getRegisterDate() + "|");
                    stringBuilder.append(teamAttendanceDto1.getFlag() + "|");
                    stringBuilder.append(teamAttendanceDto1.getOriginalId() + "\n");
                }
                try {
                    outputStream = FileUtils.openOutputStream(new File(Constant.WORKING_DIRECTORY + Constant.DATA_FILE_ATTENDANCE));
                    IOUtils.write(stringBuilder.toString(), outputStream, "UTF-8");
                    IOUtils.closeQuietly(outputStream);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                CommonUtils.makeSnackBar(this, "export row " + listAttendanceDto.size());
                break;
            default:
                break;
        }
        initData();
    }

}
