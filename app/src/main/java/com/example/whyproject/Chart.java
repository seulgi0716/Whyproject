package com.example.whyproject;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
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

        String chart_query = String.format("SELECT _id, S_CONTENT, COUNT(S_CONTENT) as COUNT FROM STRESSTB GROUP BY S_CONTENT;");
        cursor = db.rawQuery(chart_query, null);
        myAdapter.changeCursor(cursor);
        //cursor.moveToFirst();

        System.out.println("count : "+ cursor.getCount());

        if (cursor != null && cursor.getCount() != 0){
            cursor.moveToFirst();

            do {
                String cc = cursor.getString(cursor.getColumnIndex("S_CONTENT"));
                System.out.println("abcd : " + cc);
                chart_content.add(cc);
                int cv = cursor.getInt(cursor.getColumnIndex("COUNT"));
                chart_value.add(cv);
            } while (cursor.moveToNext());

        }else{

        }

        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5,10,5,5);

        pieChart.setDragDecelerationFrictionCoef(0.95f);

        pieChart.setDrawHoleEnabled(false);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(61f);

        ArrayList<PieEntry> yValues = new ArrayList<PieEntry>();

        for (int i=0; i<cursor.getCount(); i++) {
            yValues.add(new PieEntry((chart_value.get(i)),chart_content.get(i)));
        }

        Description description = new Description();
        description.setText("스트레스 요인"); //라벨
        description.setTextSize(15);
        pieChart.setDescription(description);

        pieChart.animateXY(1000, 5000); //애니메이션

        PieDataSet dataSet = new PieDataSet(yValues,"stress");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);

        PieData data = new PieData((dataSet));
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.YELLOW);

        pieChart.setData(data);



      //  while () {
//            String cc = cursor.getString(cursor.getColumnIndex("S_CONTENT"));
//            System.out.println("abcd : " + cc);
//            chart_content.add(cc);
//            int cv = cursor.getInt(cursor.getColumnIndex("COUNT"));
//            chart_value.add(cv);
      //  }

//        if(cursor.getCount() > 0){
//            startManagingCursor(cursor);
//          //  MyCursorAdapter myadapter = new MyCursorAdapter(this, cursor);
//            chart_content.add(cursor.getString(cursor.getColumnIndex("S_CONTENT")));
//            //int b = cursor.getInt(cursor.getColumnIndex("COUNT"));
//
//            for(int index=0; index < cursor.getCount(); index++) {
//                System.out.println("CC" + chart_content.get(index));
//                System.out.println("CV" + chart_value.get(index));
//            }
        }

    }
//    private void selectDB(){
//        String qq = String.format("SELECT * FROM STRESSTB WHERE S_DATE = '%s';", check_date);
//        System.out.println("check_date : " + check_date.getClass().getName());
//        cursor = db.rawQuery(qq, null);
//
//        try {
//            cursor.moveToFirst();
//            String bb = cursor.getString(cursor.getColumnIndex("S_DATE"));
//            System.out.println(bb.getClass().getName());
//            System.out.println("t/f : " + check_date.equals(bb));
//
//            int count = cursor.getCount();
//            System.out.println("count : " + count);
//            if(cursor.getCount() > 0){
//                startManagingCursor(cursor);
//                MyCursorAdapter myadapter = new MyCursorAdapter(this, cursor);
//                stress_list.setAdapter(myadapter);
//            }
//        } catch (Exception e) {
//            Toast.makeText(getApplicationContext(), "아무 내용이 없습니다!", Toast.LENGTH_SHORT).show();
//        }
//    }
//}
