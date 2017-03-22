package me.blog.korn123.awesomemanager.team;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.lang.reflect.Member;
import java.util.HashMap;
import java.util.List;

import me.blog.korn123.awesomemanager.R;

/**
 * Created by CHO HANJOONG on 2016-10-04.
 */

public class MemberAttendanceAddArrayAdapter extends ArrayAdapter<MemberDto> {
    private final Activity activity;
    private final Context context;
    private final List<MemberDto> listDto;
    private final int layoutResourceId;

    public MemberAttendanceAddArrayAdapter(Activity activity, Context context, int layoutResourceId, List<MemberDto> listDto) {
        super(context, layoutResourceId, listDto);
        this.activity = activity;
        this.context = context;
        this.listDto = listDto;
        this.layoutResourceId = layoutResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = null;

        if(row == null) {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.textView1 = (TextView) row.findViewById(R.id.text1);
            holder.textView2 = (TextView) row.findViewById(R.id.text2);
            holder.textView3 = (TextView) row.findViewById(R.id.text3);
            holder.textView4 = (TextView) row.findViewById(R.id.text4);
            holder.spinner1 = (Spinner) row.findViewById(R.id.spinner);
            row.setTag(holder);

        } else {
            holder = (ViewHolder)row.getTag();
        }

        // FIXME workaround
        // view 재활용 이슈가 있음
        // item이 호출될때마다 listener를 새로등록하여 처리함
        final int fPosition = position;
        holder.spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int index, long id) {
                long flag = parent.getAdapter().getItemId(index);
                if (flag == 1) {
                    listDto.get(fPosition).setAttendance(false);
                } else {
                    listDto.get(fPosition).setAttendance(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        MemberDto dto = listDto.get(position);
        holder.textView1.setText(String.valueOf(dto.getTempNo()));
        holder.textView2.setText(dto.getName());
        holder.textView3.setText(dto.getBirth());
        holder.textView4.setText(dto.getAlarmCellphone());

        // spinner customizing
        ArrayAdapter adapter = ArrayAdapter.createFromResource(context, R.array.attendance_arrays, R.layout.spinner_attendance_item);
        adapter.setDropDownViewResource(R.layout.spinner_attendance_dropdown_item);
        holder.spinner1.setAdapter(adapter);
        if(dto.isAttendance()) {
            holder.spinner1.setSelection(0);
        } else {
            holder.spinner1.setSelection(1);
        }
        return row;
    }

    private static class ViewHolder {
        TextView textView1;
        TextView textView2;
        TextView textView3;
        TextView textView4;
        Spinner spinner1;
    }
}
