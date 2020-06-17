package com.example.whyproject;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class Chart extends AppCompatActivity {

    private PieChart pieChart;

    static DBHelper mHelper;
    static SQLiteDatabase db;
    static Cursor cursor;
    static MyCursorAdapter myAdapter;


    final static String querySelectAll = String.format("SELECT * FROM STRESSTB");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        setTitle("스트레스 중요 요인들!!");

        mHelper = new DBHelper(this);
        db = mHelper.getWritableDatabase();
        cursor = db.rawQuery(querySelectAll, null);
        myAdapter = new MyCursorAdapter(this, cursor);

        pieChart = findViewById(R.id.piechart);

        ArrayList<String> chart_content = new ArrayList<String>();
        ArrayList<Integer> chart_value = new ArrayList<Integer>();

        String chart_query = String.format("SELECT _id, S_KINDS, COUNT(S_KINDS) as COUNT FROM STRESSTB GROUP BY S_KINDS;");
        cursor = db.rawQuery(chart_query, null);
        myAdapter.changeCursor(cursor);
        //cursor.moveToFirst();

        System.out.println("count : " + cursor.getCount());

        if (cursor != null && cursor.getCount() != 0) {
            cursor.moveToFirst();

            do {
                String kk = cursor.getString(cursor.getColumnIndex("S_KINDS"));
                System.out.println("abcd : " + kk);
                chart_content.add(kk);
                int cv = cursor.getInt(cursor.getColumnIndex("COUNT"));
                chart_value.add(cv);
            } while (cursor.moveToNext());

        } else {

        }

        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 10, 5, 20);

        pieChart.setDragDecelerationFrictionCoef(0.95f);

        pieChart.setDrawHoleEnabled(false);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(61f);
        pieChart.setEntryLabelColor(Color.BLACK);

        ArrayList<PieEntry> yValues = new ArrayList<PieEntry>();

        if (chart_value.size() >= 10 && chart_content.size() >= 10) {
            int c = cursor.getCount();
            System.out.println("c : " + c);
            c = 10;

            for (int i = 0; i < c; i++) {
                yValues.add(new PieEntry((chart_value.get(i)), chart_content.get(i)));
            }

        } else {
            for (int i = 0; i < cursor.getCount(); i++) {
                yValues.add(new PieEntry((chart_value.get(i)), chart_content.get(i)));
            }
        }

        PieDataSet dataSet = new PieDataSet(yValues, "stress");
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setColors(ColorTemplate.LIBERTY_COLORS);

        PieData data = new PieData((dataSet));
        data.setValueTextSize(20f);
        data.setValueTextColor(Color.BLACK);

        pieChart.setData(data);


    }

    @Override
    public void onBackPressed() {

    }
}
