package me.blog.korn123.awesomemanager.playground;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import me.blog.korn123.awesomemanager.R;
import me.blog.korn123.awesomemanager.team.TeamDto;

/**
 * Created by CHO HANJOONG on 2016-10-04.
 */

public class PlaygroundArrayAdapter extends ArrayAdapter<PlaygroundDto> {
    private final Activity activity;
    private final Context context;
    private final List<PlaygroundDto> listDto;
    private final int layoutResourceId;

    public PlaygroundArrayAdapter(Activity activity, Context context, int layoutResourceId, List<PlaygroundDto> listDto) {
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
            row.setTag(holder);
        } else {
            holder = (ViewHolder)row.getTag();
        }
        PlaygroundDto dto = listDto.get(position);
        holder.textView1.setText(dto.getName());
        holder.textView2.setText(dto.getAddress());
        return row;
    }

    private static class ViewHolder {
        TextView textView1;
        TextView textView2;
    }
}
