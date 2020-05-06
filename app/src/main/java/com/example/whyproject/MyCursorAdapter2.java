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
//        final TextView s_content = (TextView) view.findViewById(R.id.list_content);
//        final TextView s_value = (TextView) view.findViewById(R.id.list_value);
        int setv = cursor.getInt(cursor.getColumnIndex("SET_VALUE"));
        int pwd = cursor.getInt(cursor.getColumnIndex("PASSWORD"));
        //String convertsv = Integer.toString(sv);
        //Log.d("스트링 확인", sc + ", " + convertsv);
        // System.out.println("convertbm : " + convertbm);
//        s_content.setText(sc);
//        s_value.setText(convertsv);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.stres_list, parent, false);
        return v;
    }

}