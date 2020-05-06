package com.example.whyproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

public class WhoteaseActivity extends AppCompatActivity {

    MaterialCalendarView calendar;
    private final OneDayDecorator oneDayDecorator = new OneDayDecorator();
    private Button add;
    private ListView stress_list;

    static DBHelper mHelper;
    static SQLiteDatabase db;
    static Cursor cursor;
    static MyCursorAdapter myAdapter;

    static CalendarDay selectedDay = null;
    static boolean Selected;
    String DATE;
    int year, month, day, s_value;

    final static String querySelectAll = String.format("SELECT * FROM STRESSTB");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whotease);
        setTitle("누가 괴롭혔어?!");

        mHelper = new DBHelper(this);
        db = mHelper.getWritableDatabase();

        add = (Button) findViewById(R.id.add_btn);
        calendar = (MaterialCalendarView)findViewById(R.id.calendarView);
        stress_list = (ListView)findViewById(R.id.stress_list);

        cursor = db.rawQuery(querySelectAll, null);
        myAdapter = new MyCursorAdapter(this, cursor);
        //stress_list.setAdapter(myAdapter);

        calendar.addDecorators(
                new SundayDecorator(),
                new SaturdayDecorator(),
                oneDayDecorator);

        calendar.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {

                if(selectedDay == date) {
                    selected = false;
                    Selected = selected;
                } else {
                    selected = true;
                    Selected = selected;
                }
                selectedDay = date;
                DATE = selectedDay.toString();
                String[] parsedDATA = DATE.split("[{]");
                parsedDATA = parsedDATA[1].split("[}]");
                parsedDATA = parsedDATA[0].split("-");
                year = Integer.parseInt(parsedDATA[0]);
                month = Integer.parseInt(parsedDATA[1])+1;
                day = Integer.parseInt(parsedDATA[2]);
                String check_date = year+"-"+month+"-"+day;
                //Toast.makeText(getApplicationContext(), year+"-"+month+"-"+day, Toast.LENGTH_SHORT).show();
                //myAdapter = new MyCursorAdapter(this, cursor);
                String qq = String.format("SELECT S_CONTENT, S_VALUE FROM STRESSTB WHERE S_DATE = '%s';", check_date);
                cursor = db.rawQuery(qq, null);
                cursor.moveToFirst();
                try {
                    String cd = cursor.getString(0);
                    stress_list.setVisibility(View.VISIBLE);
                    stress_list.setAdapter(myAdapter);
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(),"아무 내용이 없습니다!", Toast.LENGTH_SHORT).show();
                    stress_list.setVisibility(View.INVISIBLE);
                }
            }
        });


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout layout = new LinearLayout(WhoteaseActivity.this);
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.setGravity(Gravity.CENTER);

                LinearLayout lay1 = new LinearLayout(WhoteaseActivity.this);
                lay1.setOrientation(LinearLayout.VERTICAL);
                lay1.setGravity(Gravity.CENTER);

                LinearLayout lay2 = new LinearLayout(WhoteaseActivity.this);
                lay2.setOrientation(LinearLayout.HORIZONTAL);
               // lay2.setGravity(Gravity.CENTER);

                LinearLayout lay3 = new LinearLayout(WhoteaseActivity.this);
                lay3.setOrientation(LinearLayout.VERTICAL);
                lay3.setGravity(Gravity.CENTER);

                final AutoCompleteTextView add_stress = new AutoCompleteTextView(WhoteaseActivity.this);
                add_stress.setHint("스트레스 항목");
                add_stress.setThreshold(1);
                add_stress.setTextSize(20);
                LinearLayout.LayoutParams parammargin1 = new LinearLayout.LayoutParams(600, LinearLayout.LayoutParams.MATCH_PARENT);
                parammargin1.setMargins(0, 20, 0, 0);
                //add_stress.setLayoutParams(new LinearLayout.LayoutParams(600, ViewGroup.LayoutParams.WRAP_CONTENT));

                final TextView st = new TextView(WhoteaseActivity.this);
                st.setText("어느정도야?");
                st.setTextSize(20);
                LinearLayout.LayoutParams parammargin2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
                parammargin2.setMargins(180, 35, 0, 10);
                //st.setLayoutParams(new LinearLayout.LayoutParams(600, ViewGroup.LayoutParams.WRAP_CONTENT));

                final TextView show_sv = new TextView(WhoteaseActivity.this);
                show_sv.setText("0");
                show_sv.setTextSize(20);
                LinearLayout.LayoutParams parammargin3 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
                parammargin3.setMargins(20, 30, 0, 10);
                //show_sv.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                final SeekBar stress_value = new SeekBar(WhoteaseActivity.this);
                s_value = 0;
                stress_value.setMax(100);
                LinearLayout.LayoutParams parammargin4 = new LinearLayout.LayoutParams(650, LinearLayout.LayoutParams.MATCH_PARENT);
                parammargin4.setMargins(0, 20, 0, 0);
                //stress_value.setLayoutParams(new LinearLayout.LayoutParams(500, ViewGroup.LayoutParams.WRAP_CONTENT));
                stress_value.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                        s_value = stress_value.getProgress();
                        update();
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                        s_value = stress_value.getProgress();
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        s_value = stress_value.getProgress();
                    }

                    public void update() {
                        show_sv.setText(new StringBuilder().append(s_value));
                    }
                });

                lay1.addView(add_stress, parammargin1);
                lay2.addView(st, parammargin2);
                lay2.addView(show_sv, parammargin3);
                lay3.addView(stress_value, parammargin4);

                layout.addView(lay1);
                layout.addView(lay2);
                layout.addView(lay3);

                AlertDialog.Builder dialog = new AlertDialog.Builder(WhoteaseActivity.this);
                dialog.setIcon(R.drawable.emotion);
                dialog.setTitle("누가 그랬어!!").setView(layout).setPositiveButton("등록", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        try {
                            String s_date = year+"-"+month+"-"+day;
                            String stress_con = add_stress.getText().toString();
                            String a = show_sv.getText().toString();
                            int db_stress = Integer.parseInt(a);

                            String ins_q1 = String.format("INSERT INTO STRESSTB VALUES (null, '%s', '%s', '%d');", s_date, stress_con, db_stress);
                            db.execSQL(ins_q1);
                            Toast.makeText(getApplicationContext(), "날짜 : " + s_date + ", 스트레스 내용 : " + stress_con + ", 스트레스 수치 : " + db_stress, Toast.LENGTH_SHORT).show();
                            cursor = db.rawQuery(querySelectAll, null);
                            myAdapter.changeCursor(cursor);


                        } catch (Exception e) {}
                    }
                }).setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                }).create().show();
            }
        });

    }
//    public String Searchdate(String check_date) {
//        String sda = "";
//        try {
//            String queryFinddate = String.format("SELECT S_CONTENT FROM STRESSTB WHERE S_DATE = '%s';", check_date);
//            cursor = db.rawQuery(queryFineSname, null);
//            cursor.moveToFirst();
//            samename = cursor.getString(0);
//
//            if (samename != null) {
//                System.out.println("sanename is exist : " + samename);
//            } else {
//                System.out.println("samename is not exist");
//            }
//        } catch (Exception e) {
//        }
//        return sda;
//    }
}


