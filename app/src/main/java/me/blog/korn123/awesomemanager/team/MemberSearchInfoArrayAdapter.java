package me.blog.korn123.awesomemanager.team;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import me.blog.korn123.awesomemanager.R;

/**
 * Created by CHO HANJOONG on 2016-10-04.
 */

public class MemberSearchInfoArrayAdapter extends ArrayAdapter<MemberDto> {
    private final Activity activity;
    private final Context context;
    private final List<MemberDto> listDto;
    private final int layoutResourceId;

    public MemberSearchInfoArrayAdapter(Activity activity, Context context, int layoutResourceId, List<MemberDto> listDto) {
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
            holder.textView1 = (TextView)row.findViewById(R.id.text1);
            holder.textView2 = (TextView)row.findViewById(R.id.text2);
            holder.textView3 = (TextView)row.findViewById(R.id.text3);
            row.setTag(holder);
        } else {
            holder = (ViewHolder)row.getTag();
        }
        MemberDto dto = listDto.get(position);
        holder.textView1.setText(String.valueOf(dto.getTempNo()));
        holder.textView2.setText(dto.getName());
        TeamDto teamDto = TeamDao.selectTeamBy(context, dto.getTeamId());
        holder.textView3.setText(teamDto.getSchool() + " " + teamDto.getName());
        return row;
    }

    private static class ViewHolder {
        TextView textView1;
        TextView textView2;
        TextView textView3;
    }
}
