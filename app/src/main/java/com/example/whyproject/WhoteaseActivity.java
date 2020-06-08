package com.example.whyproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;


public class WhoteaseActivity extends AppCompatActivity {

    MaterialCalendarView calendar;
    private final MainActivity ma = new MainActivity();
    private final OneDayDecorator oneDayDecorator = new OneDayDecorator();
    private ImageButton chart;
    private ListView stress_list;
    private Button add;

    static DBHelper mHelper;
    static SQLiteDatabase db;
    static Cursor cursor;
    static MyCursorAdapter myAdapter;

    static CalendarDay selectedDay = null;
    static boolean Selected;
    String DATE;
    String check_d, check_d2;
    int year, month, day, s_value;

    final static String querySelectAll = String.format("SELECT * FROM STRESSTB");
    static String check_date;
    static String s_kinds = "";
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

        add.setVisibility(View.INVISIBLE);

        cursor = db.rawQuery(querySelectAll, null);
        myAdapter = new MyCursorAdapter(this, cursor);
        //countwordDB();
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

                add.setVisibility(View.VISIBLE);

                selectDB();

                int count = cursor.getCount();
                if(count != 0) {
                    stress_list.setVisibility(View.VISIBLE);
                } else if(count == 0) {
                    //Toast.makeText(getApplicationContext(), "아무 내용이 없습니다!", Toast.LENGTH_SHORT).show();
                    stress_list.setVisibility(View.INVISIBLE);
                }
            }
        });


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Toast.makeText(getApplicationContext(), year + "-" + month + "-" + day, Toast.LENGTH_SHORT).show();
                check_d = year + "년 " + month + "월 " + day + "일";
                check_d2 = year + "-" + month + "-" + day;


                LinearLayout layout = new LinearLayout(WhoteaseActivity.this);
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.setGravity(Gravity.CENTER);

                LinearLayout layout2 = new LinearLayout(WhoteaseActivity.this);
                layout2.setOrientation(LinearLayout.HORIZONTAL);
                layout2.setGravity(Gravity.CENTER);

                LinearLayout layout3 = new LinearLayout(WhoteaseActivity.this);
                layout3.setOrientation(LinearLayout.VERTICAL);

                LinearLayout lay1 = new LinearLayout(WhoteaseActivity.this);
                lay1.setOrientation(LinearLayout.VERTICAL);
                lay1.setGravity(Gravity.CENTER);

                LinearLayout lay2 = new LinearLayout(WhoteaseActivity.this);
                lay2.setOrientation(LinearLayout.HORIZONTAL);

                LinearLayout lay3 = new LinearLayout(WhoteaseActivity.this);
                lay3.setOrientation(LinearLayout.VERTICAL);
                lay3.setGravity(Gravity.CENTER);

                LinearLayout lay4 = new LinearLayout(WhoteaseActivity.this);
                lay4.setOrientation(LinearLayout.VERTICAL);
                lay4.setGravity(Gravity.CENTER);

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
                parammargin5.setMargins(0, 40, 0, 10);

                LinearLayout.LayoutParams parammargin6 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
                parammargin6.setMargins(0, 20, 0, 10);


                final TextView k = new TextView(WhoteaseActivity.this);
                k.setText("스트레스 원인");
                k.setTextSize(17);
                k.setTextColor(Color.BLACK);

                String kind_arr[] = getResources().getStringArray(R.array.stress_kind);
                String company_arr[] = getResources().getStringArray(R.array.company);
                String environment_arr[] = getResources().getStringArray(R.array.environment);
                String relation_arr[] = getResources().getStringArray(R.array.relation);
                String school_arr[] = getResources().getStringArray(R.array.school);

                final Spinner kinds1, kinds2;
                kinds1 = new Spinner(WhoteaseActivity.this);
                kinds2 = new Spinner(WhoteaseActivity.this);

                final ArrayAdapter<String> array, array2, array3, array4, array5;
                array = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, kind_arr);
                array2 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, company_arr);
                array3 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, school_arr);
                array4 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, environment_arr);
                array5 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, relation_arr);


                kinds1.setAdapter(array);

                kinds1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        if(adapterView.getItemIdAtPosition(i) == 0) {
                            kinds2.setAdapter(array2);
                        } else if(adapterView.getItemIdAtPosition(i) == 1) {
                            kinds2.setAdapter(array3);
                        } else if(adapterView.getItemIdAtPosition(i) == 2) {
                            kinds2.setAdapter(array4);
                        } else if(adapterView.getItemIdAtPosition(i) == 3) {
                            kinds2.setAdapter(array5);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                kinds2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        s_kinds = adapterView.getItemAtPosition(i).toString();
                        Log.d("스트레스 원인", s_kinds);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                LinearLayout.LayoutParams parammargin = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
                parammargin.setMargins(0, 20, 0, 0);

                final AutoCompleteTextView add_stress = new AutoCompleteTextView(WhoteaseActivity.this);
                add_stress.setHint("스트레스 내용");
                add_stress.setThreshold(1);
                add_stress.setTextSize(20);
                add_stress.setTextColor(Color.BLACK);
                LinearLayout.LayoutParams parammargin1 = new LinearLayout.LayoutParams(600, LinearLayout.LayoutParams.MATCH_PARENT);
                parammargin1.setMargins(0, 20, 0, 0);

                String queryAuto = String.format("SELECT DISTINCT S_CONTENT FROM STRESSTB;");
                cursor = db.rawQuery(queryAuto, null);
                cursor.moveToFirst();
                int alength = cursor.getCount();

                String arrcontent[] = new String[alength];
                for(int i=0; i < arrcontent.length; i++) {
                    arrcontent[i] = cursor.getString(0);
                    cursor.moveToNext();
                }

                ArrayAdapter<String> contentadapter = new ArrayAdapter<String>(WhoteaseActivity.this, android.R.layout.simple_dropdown_item_1line, arrcontent);
                add_stress.setAdapter(contentadapter);

                final TextView st = new TextView(WhoteaseActivity.this);
                st.setText("어느정도?");
                st.setTextSize(20);
                st.setTextColor(Color.BLACK);
                LinearLayout.LayoutParams parammargin2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
                parammargin2.setMargins(180, 35, 0, 10);

                final TextView show_sv = new TextView(WhoteaseActivity.this);
                show_sv.setText("1");
                show_sv.setTextSize(20);
                show_sv.setTextColor(Color.BLACK);
                LinearLayout.LayoutParams parammargin3 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
                parammargin3.setMargins(20, 35, 0, 10);

                final SeekBar stress_value = new SeekBar(WhoteaseActivity.this);
                s_value = 0;
                stress_value.setMax(100);
                LinearLayout.LayoutParams parammargin4 = new LinearLayout.LayoutParams(650, LinearLayout.LayoutParams.MATCH_PARENT);
                parammargin4.setMargins(0, 20, 0, 0);

                stress_value.setProgressDrawable(getResources().getDrawable(R.drawable.seekbar2));
                stress_value.setThumb(getResources().getDrawable(R.drawable.seekbar1_thumb));

                stress_value.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                        s_value = stress_value.getProgress();
                        update();
                    }
                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                        s_value = stress_value.getProgress();
                        if(s_value > 70) {
                            show_sv.setTextColor(Color.RED);
                        }
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
                layout3.addView(k, parammargin2);
                layout2.addView(kinds1, parammargin6);
                layout2.addView(kinds2, parammargin6);
                lay2.addView(st, parammargin2);
                lay2.addView(show_sv, parammargin3);
                lay3.addView(stress_value, parammargin4);
                lay4.addView(date, parammargin5);

                layout.addView(lay4);
                layout.addView(layout3);
                layout.addView(layout2);
                layout.addView(lay1);
                layout.addView(lay2);
                layout.addView(lay3);

                AlertDialog.Builder dialog = new AlertDialog.Builder(WhoteaseActivity.this);
                dialog.setIcon(R.drawable.emotion);
                dialog.setTitle("누가 그랬어!!").setView(layout).setPositiveButton("등록", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        try {
                            System.out.println("s_kinds " +  s_kinds);
                            System.out.println("s_date " + check_d2);

                            String s_date = check_d2;

                           // String s_date = date.getText().toString();
                            String stress_con = add_stress.getText().toString();
                            String a = show_sv.getText().toString();
                            int db_stress = Integer.parseInt(a);

                            String ins_q1 = String.format("INSERT INTO STRESSTB VALUES (null, '%s', '%s', '%s', '%d');", s_date, s_kinds, stress_con, db_stress);
                            db.execSQL(ins_q1);
                            System.out.println("날짜 : " + s_date + ", 스트레스 내용 : " + stress_con + ", 스트레스 수치 : " + db_stress);
                            selectDB();
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
                String qq = String.format("SELECT * FROM STRESSTB WHERE S_DATE = '%s';", check_date);
                cursor = db.rawQuery(qq, null);
                myAdapter.changeCursor(cursor);
                cursor.moveToFirst();
                cursor.moveToPosition(i);
                final String ss = cursor.getString(cursor.getColumnIndex("S_DATE"));
                final String kk = cursor.getString(cursor.getColumnIndex("S_KINDS"));
                final String cc = cursor.getString(cursor.getColumnIndex("S_CONTENT"));
                final int vv = cursor.getInt(cursor.getColumnIndex("S_VALUE"));

                System.out.println("ss : " + ss);
                System.out.println("cc : " + cc);
                System.out.println("vv : " + vv);

                AlertDialog.Builder build = new AlertDialog.Builder(WhoteaseActivity.this);

                build.setTitle("항목 삭제").setMessage("해당 항목을 삭제하시겠습니까?");
                build.setIcon(R.drawable.trashcan);
                build.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            System.out.println("ss : " + ss);
                            String query = String.format("DELETE FROM STRESSTB WHERE S_DATE = '%s' and S_KINDS = '%s' and S_CONTENT = '%s' and S_VALUE = '%d';", ss, kk, cc, vv);
                            db.execSQL(query);
                            selectDB();
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "오류가 발생했습니다!", Toast.LENGTH_SHORT).show();
                        }
                        int count = cursor.getCount();
                        System.out.println("delete : " + count);

                        if(count == 0) {
                            stress_list.setVisibility(View.INVISIBLE);
                        }
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

   //     countwordDB();

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

    @Override
    public void onBackPressed() {

    }
}


