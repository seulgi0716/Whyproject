package com.example.whyproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class WhoteaseActivity extends AppCompatActivity {

    MaterialCalendarView calendar;
    private final MainActivity ma = new MainActivity();
    private final OneDayDecorator oneDayDecorator = new OneDayDecorator();
    private Button add;
    private ImageButton chart;
    private ListView stress_list;

    static DBHelper mHelper;
    static SQLiteDatabase db;
    static Cursor cursor;
    static MyCursorAdapter myAdapter;

    static CalendarDay selectedDay = null;
    static boolean Selected;
    String DATE;
    String check_d;
    int year, month, day, s_value;

    final static String querySelectAll = String.format("SELECT * FROM STRESSTB");
    static String check_date;
    static String aa, bb, cc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whotease);
        setTitle("누가 괴롭혔어?!");

        mHelper = new DBHelper(this);
        db = mHelper.getWritableDatabase();

        add = findViewById(R.id.add_btn);
        chart = findViewById(R.id.piechart);
        calendar = findViewById(R.id.calendarView);
        stress_list = findViewById(R.id.stress_list);


        //cursor = db.rawQuery(querySelectAll, null);
        //myAdapter = new MyCursorAdapter(this, cursor);
        //stress_list.setAdapter(myAdapter);

        calendar.addDecorators(
                new SundayDecorator(),
                new SaturdayDecorator(),
                oneDayDecorator);

        calendar.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {

                if (selectedDay == date) {
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
                month = Integer.parseInt(parsedDATA[1]) + 1;
                day = Integer.parseInt(parsedDATA[2]);
                check_date = year + "-" + month + "-" + day;

//                String qq = String.format("SELECT S_CONTENT, S_VALUE FROM STRESSTB WHERE S_DATE = '%s';", check_date);
//                cursor = db.rawQuery(qq, null);
//
//                System.out.println(qq);
//                cursor.moveToFirst();

                selectDB();

                int count = cursor.getCount();
                if(count != 0) {
                    stress_list.setVisibility(View.VISIBLE);
                } else if(count == 0) {
                    Toast.makeText(getApplicationContext(), "아무 내용이 없습니다!", Toast.LENGTH_SHORT).show();
                    stress_list.setVisibility(View.INVISIBLE);
                }

            }


        });


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getApplicationContext(), year + "-" + month + "-" + day, Toast.LENGTH_SHORT).show();
                check_d = year + "-" + month + "-" + day;

                LinearLayout layout = new LinearLayout(WhoteaseActivity.this);
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.setGravity(Gravity.CENTER);

                LinearLayout lay1 = new LinearLayout(WhoteaseActivity.this);
                lay1.setOrientation(LinearLayout.VERTICAL);
                lay1.setGravity(Gravity.CENTER);

                LinearLayout lay2 = new LinearLayout(WhoteaseActivity.this);
                lay2.setOrientation(LinearLayout.HORIZONTAL);
                ;

                LinearLayout lay3 = new LinearLayout(WhoteaseActivity.this);
                lay3.setOrientation(LinearLayout.VERTICAL);
                lay3.setGravity(Gravity.CENTER);

                LinearLayout lay4 = new LinearLayout(WhoteaseActivity.this);
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.setGravity(Gravity.CENTER);

                final TextView date = new TextView(WhoteaseActivity.this);

                if (check_d.equals("0-0-0")) {
                    date.setText(ma.today);
                    System.out.println("equal0 : " + ma.today);
                } else {
                    date.setText(check_d);
                    System.out.println("else : " + check_d);
                }
                date.setTextSize(20);
                LinearLayout.LayoutParams parammargin5 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
                parammargin5.setMargins(180, 40, 0, 10);

                final AutoCompleteTextView add_stress = new AutoCompleteTextView(WhoteaseActivity.this);
                add_stress.setHint("스트레스 항목");
                add_stress.setThreshold(1);
                add_stress.setTextSize(20);
                LinearLayout.LayoutParams parammargin1 = new LinearLayout.LayoutParams(600, LinearLayout.LayoutParams.MATCH_PARENT);
                parammargin1.setMargins(0, 20, 0, 0);

                final TextView st = new TextView(WhoteaseActivity.this);
                st.setText("어느정도야?");
                st.setTextSize(20);
                LinearLayout.LayoutParams parammargin2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
                parammargin2.setMargins(180, 35, 0, 10);

                final TextView show_sv = new TextView(WhoteaseActivity.this);
                show_sv.setText("0");
                show_sv.setTextSize(20);
                LinearLayout.LayoutParams parammargin3 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
                parammargin3.setMargins(20, 35, 0, 10);

                final SeekBar stress_value = new SeekBar(WhoteaseActivity.this);
                s_value = 0;
                stress_value.setMax(100);
                LinearLayout.LayoutParams parammargin4 = new LinearLayout.LayoutParams(650, LinearLayout.LayoutParams.MATCH_PARENT);
                parammargin4.setMargins(0, 20, 0, 0);

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
                lay4.addView(date, parammargin5);

                layout.addView(lay4);
                layout.addView(lay1);
                layout.addView(lay2);
                layout.addView(lay3);

                AlertDialog.Builder dialog = new AlertDialog.Builder(WhoteaseActivity.this);
                dialog.setIcon(R.drawable.emotion);
                dialog.setTitle("누가 그랬어!!").setView(layout).setPositiveButton("등록", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        try {

                            String s_date = date.getText().toString();
                            String stress_con = add_stress.getText().toString();
                            String a = show_sv.getText().toString();
                            int db_stress = Integer.parseInt(a);

                            String ins_q1 = String.format("INSERT INTO STRESSTB VALUES (null, '%s', '%s', '%d');", s_date, stress_con, db_stress);
                            db.execSQL(ins_q1);
                            Toast.makeText(getApplicationContext(), "날짜 : " + s_date + ", 스트레스 내용 : " + stress_con + ", 스트레스 수치 : " + db_stress, Toast.LENGTH_SHORT).show();
                            cursor = db.rawQuery(querySelectAll, null);
                            myAdapter.changeCursor(cursor);

                            String viewq = String.format("SELECT S_CONTENT, S_VALUE FROM STRESSTB WHERE S_DATE = '%s';", s_date);
                            cursor = db.rawQuery(viewq, null);
                            cursor.moveToFirst();
                            stress_list.setAdapter(myAdapter);
                            stress_list.setVisibility(View.VISIBLE);


                        } catch (Exception e) {
                        }
                    }
                }).setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                }).create().show();
            }
        });


        stress_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                cursor = db.rawQuery(querySelectAll, null);
                myAdapter.changeCursor(cursor);
                cursor.moveToPosition(i);
                final String ss = cursor.getString(cursor.getColumnIndex("S_DATE"));
                final String cc = cursor.getString(cursor.getColumnIndex("S_CONTENT"));
                final int vv = cursor.getInt(cursor.getColumnIndex("S_VALUE"));
//                Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
                System.out.println("ss : " + ss);
                System.out.println("cc : " + cc);
                System.out.println("vv : " + vv);

                AlertDialog.Builder build = new AlertDialog.Builder(WhoteaseActivity.this);

                build.setTitle("항목 삭제").setMessage("해당 항목을 삭제하시겠습니까?");
                build.setIcon(R.drawable.soso);
                build.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            System.out.println("ss : " + ss);
                            String query = String.format("DELETE FROM STRESSTB WHERE S_DATE = '%s' and S_CONTENT = '%s' and S_VALUE = '%d';", ss, cc, vv);
                            db.execSQL(query);
                            cursor = db.rawQuery(querySelectAll, null);
                            myAdapter.changeCursor(cursor);
                        } catch (Exception e) {
                        }
                        //selectDB();
                        Toast.makeText(getApplicationContext(), "삭제되었습니다!", Toast.LENGTH_SHORT).show();
                    }
                });

                build.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                AlertDialog alertDialog = build.create();
                alertDialog.show();

                return true;
            }
        });


        chart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Chart.class);
                startActivity(intent);
            }
        });

    }

    private void selectDB(){
        String qq = String.format("SELECT * FROM STRESSTB WHERE S_DATE = '%s';", check_date);
        System.out.println("check_date : " + check_date.getClass().getName());
        cursor = db.rawQuery(qq, null);

        try {
            cursor.moveToFirst();
            String bb = cursor.getString(cursor.getColumnIndex("S_DATE"));
            System.out.println(bb.getClass().getName());
            System.out.println("t/f : " + check_date.equals(bb));

            int count = cursor.getCount();
            System.out.println("count : " + count);
            if(cursor.getCount() > 0){
                startManagingCursor(cursor);
                MyCursorAdapter myadapter = new MyCursorAdapter(this, cursor);
                stress_list.setAdapter(myadapter);
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "아무 내용이 없습니다!", Toast.LENGTH_SHORT).show();
        }
    }
}


