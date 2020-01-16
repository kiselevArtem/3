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

public class ozy extends AppCompatActivity {
    DBHelper DBH;
    Spinner spiner,spiner2;
    Cursor userCursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ozy);
        spiner = findViewById(R.id.spimDDR);
        spiner2 = findViewById(R.id.spimobem);
        DBH=new DBHelper(this);
        userCursor=DBH.selectTabl("select * from ddr");
        List<String> labels= new ArrayList<String>();
        if (userCursor.moveToFirst()) {
            do {

                labels.add(userCursor.getString(1));
            } while (userCursor.moveToNext());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, labels);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spiner.setAdapter(dataAdapter);
        DBH=new DBHelper(this);
        userCursor=DBH.selectTabl("select * from obem");
         labels= new ArrayList<String>();
        if (userCursor.moveToFirst()) {
            do {

                labels.add(userCursor.getString(1));
            } while (userCursor.moveToNext());
        }
         dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, labels);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spiner2.setAdapter(dataAdapter);

    }
    //    db.execSQL("INSERT INTO ram(nazv_ram, _idramDDR, _idramObem, prise, kolvo,QR) VALUES ('R532G1601S1SL-U',2,4,3000,30,'30')");
    public void ozyList(View view) {
        Intent intent = new Intent(ozy.this,ozyList.class);
       String zapros="select _id,nazv_ram,(select ddr_name from ddr where ram._idramDDR=ddr._id),(select obem_name from obem where ram._idramObem=obem._id),prise,kolvo,QR from ram ";
        intent.putExtra("zapros", zapros);
        startActivity(intent);
    }
    public void onClick(View view) {
        Intent captureIntent = new Intent(ozy.this, CaptureActivity.class);
        startActivityForResult(captureIntent, 0);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                String value = data.getStringExtra("SCAN_RESULT");
                Intent intent = new Intent(ozy.this, ozyList.class);
                String zapros="select _id,nazv_ram,(select ddr_name from ddr where ram._idramDDR=ddr._id),(select obem_name from obem where ram._idramObem=obem._id),prise,kolvo,QR from ram where QR='"+value+"'";
                intent.putExtra("zapros", zapros);
                startActivity(intent);
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(), "Сканирование не удалось", Toast.LENGTH_SHORT).show();
            }
        } else {
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void ddrPoisk(View view) {
        Intent intent = new Intent(ozy.this,ozyList.class);
        String zn=spiner.getSelectedItem().toString();
        String zapros="select _id,nazv_ram,(select ddr_name from ddr where ram._idramDDR=ddr._id)," +
                "(select obem_name from obem where ram._idramObem=obem._id),prise,kolvo,QR from ram where _idramDDR=(select _id from ddr where ddr_name='"+zn+"') ";
        intent.putExtra("zapros", zapros);
        startActivity(intent);
    }

    public void obemPoisk(View view) {
        Intent intent = new Intent(ozy.this,ozyList.class);
        String zn=spiner2.getSelectedItem().toString();
        String zapros="select _id,nazv_ram,(select ddr_name from ddr where ram._idramDDR=ddr._id)," +
                "(select obem_name from obem where ram._idramObem=obem._id),prise,kolvo,QR from ram where _idramObem=(select _id from obem where obem_name='"+zn+"') ";
        intent.putExtra("zapros", zapros);
        startActivity(intent);
    }

    public void addOzy(View view) {
        Intent intent = new Intent(ozy.this,ozyAdDel.class);
        intent.putExtra("zapros", 100);
        startActivity(intent);
    }
}
