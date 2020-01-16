package com.example.rrifafauzikomara.qrcodescanner;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import info.vividcode.android.zxing.CaptureActivity;
import info.vividcode.android.zxing.CaptureActivityIntents;


public class monitor extends AppCompatActivity {
    DBHelper DBH;
    Spinner spiner;
    Cursor userCursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor);
        spiner = findViewById(R.id.spinnerFirm);
        DBH=new DBHelper(this);
        userCursor=DBH.selectTabl("select * from firmmonit");
        List<String> labels= new ArrayList<String>();
        if (userCursor.moveToFirst()) {
            do {

                labels.add(userCursor.getString(1));
            } while (userCursor.moveToNext());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, labels);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spiner.setAdapter(dataAdapter);
    }
    public void onTipMon(View view) {
        String name="";
        userCursor= DBH.selectTabl("select _id from firmmonit where monitor_name='"+spiner.getSelectedItem().toString()+"'");
        if (userCursor.moveToFirst()) {
            do {

                name=(userCursor.getString(0));
            } while (userCursor.moveToNext());
        }

        Intent intent = new Intent(monitor.this, MonitList.class);
        intent.putExtra("zapros", "select _id,nazv_mon, color, monitor_diag,prise,kolvo,QR, " +
                "(select monitor_name from firmmonit where monit.id_tipmon=firmmonit._id)  from monit where id_tipmon="+name+"");
        startActivity(intent);
    }

    public void addList(View view) {
        Intent intent = new Intent(monitor.this, Monit_add.class);
        intent.putExtra("zapros", "");
        startActivity(intent);
    }

    public void MonList(View view) {
        Intent intent = new Intent(monitor.this,MonitList.class);
        intent.putExtra("zapros", "select _id,nazv_mon, color, monitor_diag,prise,kolvo,QR, " +
                "(select monitor_name from firmmonit where monit.id_tipmon=firmmonit._id)  from monit");
        startActivity(intent);
    }

    public void TipMonit(View view) {
        Intent intent = new Intent(monitor.this, TipMonit.class);
        startActivity(intent);
    }
    public void onClick(View view) {
        Intent captureIntent = new Intent(monitor.this, CaptureActivity.class);
        startActivityForResult(captureIntent, 0);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                String value = data.getStringExtra("SCAN_RESULT");
                Intent intent = new Intent(monitor.this, MonitList.class);
                intent.putExtra("zapros", "select _id,nazv_mon, color, monitor_diag,prise,kolvo,QR, " +
                        "(select monitor_name from firmmonit where monit.id_tipmon=firmmonit._id)  from monit where QR='"+value+"'");
                startActivity(intent);
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(), "Сканирование не удалось", Toast.LENGTH_SHORT).show();
            }
        } else {
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
