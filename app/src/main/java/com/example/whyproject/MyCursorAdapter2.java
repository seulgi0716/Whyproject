package com.example.whyproject;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class MyCursorAdapter2 extends CursorAdapter {
    public MyCursorAdapter2(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        final TextView targettv = (TextView) view.findViewById(R.id.targettv);
        final TextView hitvalue = (TextView) view.findViewById(R.id.hitvalue);
        String tn = cursor.getString(cursor.getColumnIndex("TARGET_NAME"));
        int c = cursor.getInt(cursor.getColumnIndex("COUNT"));
        String convertsv = Integer.toString(c);
        targettv.setText(tn);
        hitvalue.setText(convertsv);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.rank_list, parent, false);
        return v;
    }

}