package com.example.whyproject;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class MyCursorAdapter extends CursorAdapter {
    public MyCursorAdapter(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        final TextView s_content = (TextView) view.findViewById(R.id.tname);
        String bn = cursor.getString(cursor.getColumnIndex("borrowname"));
        int bm = cursor.getInt(cursor.getColumnIndex("borrowmoney"));
        String convertbm = Integer.toString(bm);
        Log.d("스트링 확인", bn + ", " + bm);
        System.out.println("convertbm : " + convertbm);
        tvbname.setText(bn);
        tvbmoney.setText(convertbm);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.listitem, parent, false);
        return v;
    }

}