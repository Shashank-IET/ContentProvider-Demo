package com.cocrux.m.pocketsql;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.constraint.solver.widgets.Snapshot;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.UUID;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final String BASE_STATUS = "TOTAL ENTRIES: ";
    private EditText t1_name, t1_roll, t1_agg, t2_roll, t2_class, t2_address;
    private TextView t1_status, t2_status;
    private ListView t1_list, t2_list;

    private String[] fName = {"Raj", "Varun", "Ajay", "Abhi", "Narayan", "Sidd", "Rocky"};
    private String[] lName = {"Kumar", "Roy", "Mehta", "Bond", "Lara", "Haka", "Kalra"};

    CursorAdapter adapterT1, adapterT2;
    LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        t1_name = findViewById(R.id.t1_name);
        t1_agg = findViewById(R.id.t1_agg);
        t1_roll = findViewById(R.id.t1_roll);
        t2_address = findViewById(R.id.t2_address);
        t2_class = findViewById(R.id.t2_class);
        t2_roll = findViewById(R.id.t2_roll);
        t1_status = findViewById(R.id.t1_status);
        t2_status = findViewById(R.id.t2_status);

        t1_status.setText(BASE_STATUS + "0");
        t2_status.setText(BASE_STATUS + "0");

        t1_list = findViewById(R.id.t1_list);
        t2_list = findViewById(R.id.t2_list);

        findViewById(R.id.t1_viewall).setOnClickListener(this);
        findViewById(R.id.t2_viewall).setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        PSqlSqliteHelper db = new PSqlSqliteHelper(this);
        SQLiteDatabase worker = db.getWritableDatabase();


        // for initiating db creation process
//        fillRandomT1(null);
//        insertT1(null);
//        fillRandomT2(null);
//        insertInT2(null);
//
//        t1_status.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Cursor cs = getContentResolver().query(PSqlContentProvider.CONTENT_URI_2,
//                        new String[]{PSqlSqliteHelper.T1_COL_ROLL},
//                        null,
//                        null,
//                        null);
//                t2_status.setText(BASE_STATUS + cs.getCount());
//                cs = getContentResolver().query(PSqlContentProvider.CONTENT_URI_1,
//                        new String[]{PSqlSqliteHelper.T1_COL_ROLL},
//                        null,
//                        null,
//                        null);
//                t1_status.setText(BASE_STATUS + cs.getCount());
//                cs.close();
//            }
//        }, 3000);
    }

    public void fillRandomT1(View view) {
        String name = fName[(int) ((Math.random() * 10) % fName.length)] + " " +
                lName[(int) ((Math.random() * 10) % fName.length)];
        String agg = String.format("%.2f %%", Math.random() * 100);
        int roll = (int) (Math.random() * 100) + 1;
        t1_name.setText(name);
        t1_agg.setText(agg);
        t1_roll.setText(String.valueOf(roll));
    }

    public void insertT1(View view) {
        if (t1_name.getText().length() > 0
                && t1_roll.getText().length() > 0
                && t1_agg.getText().length() > 0) {
            try {
                ContentValues cv = new ContentValues();
                cv.put(PSqlSqliteHelper.T1_COL_NAME, t1_name.getText().toString());
                cv.put(PSqlSqliteHelper.T1_COL_AGGREGATE, t1_agg.getText().toString());
                cv.put(PSqlSqliteHelper.T1_COL_ROLL, Integer.parseInt(t1_roll.getText().toString()));

                Uri resUri = getContentResolver().insert(PSqlContentProvider.CONTENT_URI_1, cv);
                Toast.makeText(this, "Inserted!\n" + resUri, Toast.LENGTH_SHORT).show();
                t1_name.setText("");
                t1_agg.setText("");
                t1_roll.setText("");

                Cursor cs = getContentResolver().query(PSqlContentProvider.CONTENT_URI_1,
                        new String[]{PSqlSqliteHelper.T1_COL_ROLL},
                        null,
                        null,
                        null);
                t1_status.setText(BASE_STATUS + cs.getCount());
                cs.close();
                expandForT1(view, true);
            } catch (Exception e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, "Fill Essentials!", Toast.LENGTH_SHORT).show();
        }
    }

    public void fillRandomT2(View view) {
        String address = UUID.randomUUID().toString();
        int class_ = (int) (Math.random() * 12) + 1;
        int roll = (int) (Math.random() * 100) + 1;
        t2_address.setText(address.substring(0, 20));
        t2_class.setText(String.valueOf(class_) + "th Standard");
        t2_roll.setText(String.valueOf(roll));
    }

    public void insertInT2(View view) {
        if (t2_address.getText().length() > 0
                && t2_roll.getText().length() > 0
                && t2_class.getText().length() > 0) {
            try {

                ContentValues cv = new ContentValues();
                cv.put(PSqlSqliteHelper.T2_COL_ADDRESS, t2_address.getText().toString());
                cv.put(PSqlSqliteHelper.T2_COL_CLASS, t2_class.getText().toString());
                cv.put(PSqlSqliteHelper.T2_COL_ROLL, Integer.parseInt(t2_roll.getText().toString()));
                Uri resUri = getContentResolver().insert(PSqlContentProvider.CONTENT_URI_2, cv);

                Toast.makeText(this, "Inserted!\n" + resUri, Toast.LENGTH_SHORT).show();
                t2_roll.setText("");
                t2_class.setText("");
                t2_address.setText("");

                Cursor cs = getContentResolver().query(PSqlContentProvider.CONTENT_URI_2,
                        new String[]{PSqlSqliteHelper.T2_COL_ROLL},
                        null,
                        null,
                        null);
                t2_status.setText(BASE_STATUS + cs.getCount());
                expandForT2(view, true);
                cs.close();
            } catch (Exception e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, "Fill Essentials!", Toast.LENGTH_SHORT).show();
        }
    }

    public void expandForT1(View view, boolean... arg) {
        ViewGroup parentView = findViewById(R.id.t1_listContainer);
        int vis = parentView.getVisibility();
        if(arg.length > 0 && !arg[0])
            vis = View.GONE;
        switch (vis) {
            case View.VISIBLE:
                findViewById(R.id.t1_listContainer).setVisibility(View.GONE);
                ((ViewGroup) (findViewById(R.id.t1_listContainer))).removeAllViews();
                break;
            case View.INVISIBLE:
            case View.GONE:
                parentView.removeAllViews();
                parentView.setVisibility(View.VISIBLE);
                Cursor cs = getContentResolver().query(PSqlContentProvider.CONTENT_URI_1,
                        new String[]{PSqlSqliteHelper.COL_ID, PSqlSqliteHelper.T1_COL_NAME, PSqlSqliteHelper.T1_COL_ROLL, PSqlSqliteHelper.T1_COL_AGGREGATE},
                        null,
                        null,
                        PSqlSqliteHelper.T1_COL_ROLL + " asc");
//                adapterT1 = new SimpleCursorAdapter(this,
//                        R.layout.row_list_t1,
//                        cs,
//                        new String[]{PSqlSqliteHelper.T1_COL_NAME, PSqlSqliteHelper.T1_COL_ROLL, PSqlSqliteHelper.T1_COL_AGGREGATE},
//                        new int[]{R.id.name, R.id.roll, R.id.agg});
//                t1_list.setAdapter(adapterT1);
                while (cs.moveToNext()) {
                    View v = inflater.inflate(R.layout.row_list_t1, parentView, false);
                    ((TextView) v.findViewById(R.id.name)).setText(cs.getString(cs.getColumnIndex(PSqlSqliteHelper.T1_COL_NAME)));
                    ((TextView) v.findViewById(R.id.roll)).setText(cs.getInt(cs.getColumnIndex(PSqlSqliteHelper.T1_COL_ROLL)) + "");
                    ((TextView) v.findViewById(R.id.agg)).setText(cs.getString(cs.getColumnIndex(PSqlSqliteHelper.T1_COL_AGGREGATE)));
                    parentView.addView(v);
                }
        }
    }

    public void expandForT2(View view, boolean... arg) {
        ViewGroup parentView = findViewById(R.id.t2_listContainer);
        int vis = parentView.getVisibility();
        if(arg.length > 0 && !arg[0])
            vis = View.GONE;

        switch (vis) {
            case View.VISIBLE:
                findViewById(R.id.t2_listContainer).setVisibility(View.GONE);
                break;
            case View.INVISIBLE:
            case View.GONE:
                parentView.removeAllViews();
                parentView.setVisibility(View.VISIBLE);
                Cursor cs = getContentResolver().query(PSqlContentProvider.CONTENT_URI_2,
                        new String[]{PSqlSqliteHelper.COL_ID, PSqlSqliteHelper.T2_COL_ADDRESS, PSqlSqliteHelper.T2_COL_ROLL, PSqlSqliteHelper.T2_COL_CLASS},
                        null,
                        null,
                        PSqlSqliteHelper.T2_COL_ROLL + " asc");
//                adapterT2 = new SimpleCursorAdapter(this,
//                        R.layout.row_list_t2,
//                        cs,
//                        new String[]{PSqlSqliteHelper.T2_COL_ADDRESS, PSqlSqliteHelper.T2_COL_ROLL, PSqlSqliteHelper.T2_COL_CLASS},
//                        new int[]{R.id.address, R.id.roll, R.id.class_});
//                t2_list.setAdapter(adapterT2);

                while (cs.moveToNext()) {
                    View v = inflater.inflate(R.layout.row_list_t2, parentView, false);
                    ((TextView) v.findViewById(R.id.roll)).setText(cs.getString(cs.getColumnIndex(PSqlSqliteHelper.T2_COL_ROLL)));
                    ((TextView) v.findViewById(R.id.class_)).setText(cs.getInt(cs.getColumnIndex(PSqlSqliteHelper.T2_COL_CLASS)) + "");
                    ((TextView) v.findViewById(R.id.address)).setText(cs.getString(cs.getColumnIndex(PSqlSqliteHelper.T2_COL_ADDRESS)));
                    parentView.addView(v);
                }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.t1_viewall:
                expandForT1(v);
                break;
            case R.id.t2_viewall:
                expandForT2(v);
                break;
        }
    }
}
