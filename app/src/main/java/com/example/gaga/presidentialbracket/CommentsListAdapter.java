package com.example.gaga.presidentialbracket;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;


public class CommentsListAdapter extends ArrayAdapter<Comment> {
    private final ArrayList<Comment> commentData;
    private final int layoutResourceId;
    Context context;



    public CommentsListAdapter(Context context, int layoutResourceId, ArrayList<Comment> data) {
        super(context, layoutResourceId, data);
        this.context = context;
        this.commentData = data;
        this.layoutResourceId = layoutResourceId;
    }

    @Override
    public int getCount() {
        // Number of times getView is called
        return commentData.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    // getView is called for each item
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = null;
        if(row == null) {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, null);
            holder = new ViewHolder();
            holder.posterNameH = (TextView)row.findViewById(R.id.posterName);
            holder.commentContentH = (TextView)row.findViewById(R.id.commentContent);

            row.setTag(holder);
        } else {
            holder = (ViewHolder)row.getTag();
        }

        Comment targetComment;
        targetComment = commentData.get(position);
        holder.posterNameH.setText(targetComment.get_Name());
        holder.commentContentH.setText(targetComment.get_Comment());

        return row;
    }

    static class ViewHolder {
        TextView posterNameH;
        TextView commentContentH;
    }

}
