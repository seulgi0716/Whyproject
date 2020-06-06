package com.example.whyproject;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.data.PieData;

import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import com.github.mikephil.charting.utils.ColorTemplate;

import org.snu.ids.kkma.index.Keyword;
import org.snu.ids.kkma.index.KeywordExtractor;
import org.snu.ids.kkma.index.KeywordList;

import java.util.ArrayList;

public class Chart_bar extends AppCompatActivity {

    static DBHelper mHelper;
    static SQLiteDatabase db;
    static Cursor cursor;
    static MyCursorAdapter myAdapter;
    final static String querySelectAll = String.format("SELECT * FROM STRESSTB");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_bar);

        PieChart pchart = findViewById(R.id.piechart2);

        mHelper = new DBHelper(this);
        db = mHelper.getWritableDatabase();
        cursor = db.rawQuery(querySelectAll, null);
        myAdapter = new MyCursorAdapter(this, cursor);

        String qq = String.format("SELECT _id, S_CONTENT FROM STRESSTB");
        cursor = db.rawQuery(qq, null);
        myAdapter.changeCursor(cursor);

        ArrayList<String> scontent = new ArrayList<>();

        // ArrayList<String> bag = new ArrayList<>();
        //     String match = "[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s,!%$&.]";
        //String[] stopword = new String[]{"이", "ㅇ"};
        //String stopword = "[이, 그리고, 와, 가]"
        ArrayList<String> s_con = new ArrayList<>();
        ArrayList<Integer> s_count = new ArrayList<>();
        try {
            cursor.moveToFirst();

            int count = cursor.getCount();
            System.out.println("count : " + count);

            if (cursor.getCount() > 0) {
                startManagingCursor(cursor);
                while (count > 0) {
                    String a = cursor.getString(cursor.getColumnIndex("S_CONTENT"));

                    scontent.add(a);
                    count--;
                    cursor.moveToNext();
                }
            }

            for (int i = 0; i < scontent.size(); i++) {
                String aaaa = scontent.get(i);
                System.out.println("aaaa" + aaaa);
                KeywordExtractor ke = new KeywordExtractor();
                KeywordList kl = ke.extractKeyword(aaaa, true);
                System.out.println(kl);

                for (int j = 0; j < kl.size(); j++) {
                    Keyword kwrd = kl.get(j);
                    System.out.println(kwrd.getString() + "\t" + kwrd.getCnt());
                    if(!(s_con.contains(kwrd.getString()))) {
                        s_con.add(kwrd.getString());
                        s_count.add(1);
                    } else {
                        int index = s_con.indexOf(kwrd.getString());
                        int count_value = s_count.get(index);
                        s_count.set(index, count_value+1);
                    }
                 //   s_con.add(kwrd.getString());
                 //   s_count.add(kwrd.getCnt());
                 //   System.out.println(s_con.get(j));
                 //   System.out.println(s_count.get(j));

                }
            }

            for(int k = 0; k < s_con.size(); k++) {
                System.out.println(s_con.get(k));
                System.out.println(s_count.get(k));
            }

        } catch (Exception e) {
        }

    //    pchart.setUsePercentValues(true);
        pchart.getDescription().setEnabled(false);
        pchart.setExtraOffsets(5, 10, 5, 20);

        pchart.setDragDecelerationFrictionCoef(0.95f);

        pchart.setDrawHoleEnabled(false);
        pchart.setHoleColor(Color.WHITE);
        pchart.setTransparentCircleRadius(61f);
        pchart.setEntryLabelColor(Color.BLACK);

        ArrayList<PieEntry> yValues = new ArrayList<PieEntry>();

        if (s_con.size() >= 10) {
            int c = cursor.getCount();
            System.out.println("c : " + c);
            c = 10;

            for (int i = 0; i < c; i++) {
                yValues.add(new PieEntry((s_count.get(i)), s_con.get(i)));
            }

        } else {
            for (int i = 0; i < cursor.getCount(); i++) {
                yValues.add(new PieEntry((s_count.get(i)), s_con.get(i)));
            }
        }

        PieDataSet dataSet = new PieDataSet(yValues, "wordcount");
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        PieData data = new PieData((dataSet));
        data.setValueTextSize(20f);
        data.setValueTextColor(Color.BLACK);

        pchart.setData(data);


    }

//                private void countwordDB () {
//        String qq = String.format("SELECT S_CONTENT FROM STRESSTB;");
//        cursor = db.rawQuery(qq, null);
//        ArrayList<String> scontent = new ArrayList<>();
//        ArrayList<String> s_con = new ArrayList<>();
//        ArrayList<Integer> s_count = new ArrayList<>();
//        // ArrayList<String> bag = new ArrayList<>();
//        //     String match = "[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s,!%$&.]";
//        //String[] stopword = new String[]{"이", "ㅇ"};
//        //String stopword = "[이, 그리고, 와, 가]"
//
//
//        try {
//            cursor.moveToFirst();
//
//            int count = cursor.getCount();
//            System.out.println("count : " + count);
//
//            if (cursor.getCount() > 0) {
//                startManagingCursor(cursor);
//                while (count > 0) {
//                    String a = cursor.getString(cursor.getColumnIndex("S_CONTENT"));
//                    // System.out.println("a" + a);
//                    scontent.add(a);
//                    count--;
//                    cursor.moveToNext();
//                }
//            }
//
//            for (int i = 0; i < scontent.size(); i++) {
//                String aaaa = scontent.get(i);
//                //System.out.println(scontent.get(i));
//                KeywordExtractor ke = new KeywordExtractor();
//                KeywordList kl = ke.extractKeyword(aaaa, true);
//
//                for (int j = 0; j < kl.size(); j++) {
//                    Keyword kwrd = kl.get(j);
//                    s_con.add(kwrd.getString());
//                    s_count.add(kwrd.getCnt());
//                    System.out.println(kwrd.getString() + "\t" + kwrd.getCnt());
//
//                    //          bag[j] = bag[j].replaceAll(match, "");
//                    //        System.out.println(bag[j]);
//                    // }
//
//
//                    // }
//                }
//            }
//        }
//        catch (Exception e) {
//            Toast.makeText(getApplicationContext(), "아무 내용이 없습니다!", Toast.LENGTH_SHORT).show();
//        }


    @Override
    public void onBackPressed() {

    }
}