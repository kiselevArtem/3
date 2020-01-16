package com.example.rrifafauzikomara.qrcodescanner;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import info.vividcode.android.zxing.CaptureActivity;

public class ozyAdDel extends AppCompatActivity {
    TextView tv;
    DBHelper DBH;
    Spinner spiner,spiner2;
    Cursor userCursor;
    Button btnAdd,btnDel, btnIzm;
    EditText nazv, prise, kolvo;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ozy_ad_del);
        Bundle arguments = getIntent().getExtras();
        nazv= findViewById(R.id.name);
        prise= findViewById(R.id.prise);
        kolvo= findViewById(R.id.kolvo);
        spiner = findViewById(R.id.spimDDR);
        spiner2 = findViewById(R.id.spimobem);
        tv=findViewById(R.id.QR);
        btnAdd=findViewById(R.id.btnAdd);
        btnDel=findViewById(R.id.btnDell);
        btnIzm=findViewById(R.id.btnIzm);
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
        id= arguments.get("zapros").toString();

        if (id.equals("100"))
        {
           btnIzm.setVisibility(View.GONE);
           btnDel.setVisibility(View.GONE);
        }
        else {
            btnAdd.setVisibility(View.GONE);
            String zapros ="select * from ram where _id =" + id + "";
            userCursor=DBH.selectTabl(zapros);
            while (userCursor.moveToNext()) {
                //          db.execSQL("INSERT INTO ram(nazv_ram, _idramDDR, _idramObem,prise, kolvo,QR) VALUES ('R532G1601S1SL-U',2,4,3000,30,'30')");
                nazv.setText(userCursor.getString(1));
                spiner.setSelection(Integer.parseInt(userCursor.getString(2))-1);
                spiner2.setSelection(Integer.parseInt(userCursor.getString(3))-1);
                prise.setText(userCursor.getString(4));
                kolvo.setText(userCursor.getString(5));
                tv.setText(userCursor.getString(6));
            }
        }

    }


    /**
     * Получение qr кода
     *
     * @param view Параметр, отвечающий за отображение
     */
    public void onClick(View view) {
        Intent captureIntent = new Intent(ozyAdDel.this, CaptureActivity.class);
        startActivityForResult(captureIntent, 0);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                String value = data.getStringExtra("SCAN_RESULT");
                tv.setText(value);
            } else if (resultCode == Activity.RESULT_CANCELED) {
                tv.setText("qr");
            }
        } else {
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    /**
     * Добавление озу
     *
     * @param view Параметр, отвечающий за отображение
     */
    public void onClickAdd(View view) {
//db.execSQL("INSERT INTO ram(nazv_ram, _idramDDR, _idramObem,prise, kolvo,QR) VALUES ('R532G1601S1SL-U',2,4,3000,30,'30')");
        String zapros ="INSERT INTO ram(nazv_ram, _idramDDR, _idramObem,prise,kolvo, QR) VALUES " +
                "('"+nazv.getText().toString()+"',"+ (spiner.getSelectedItemPosition()+1)+","+(spiner2.getSelectedItemPosition()+1)+
                ","+ Integer.parseInt(prise.getText().toString())+","+Integer.parseInt(kolvo.getText().toString())+",'"+tv.getText().toString()+"')";

        DBH.Del_in(zapros);
        Toast.makeText(getApplicationContext(), "Данные добавлены", Toast.LENGTH_SHORT).show();
        super.onBackPressed();
    }
    /**
     * Удаление озу
     *
     * @param view Параметр, отвечающий за отображение
     */
    public void onClickDell(View view) {
        String zapros ="DELETE from ram where _id =" + id + "";

        DBH.Del_in(zapros);
        Toast.makeText(getApplicationContext(), "Данные обновлены", Toast.LENGTH_SHORT).show();
        super.onBackPressed();
    }
    /**
     * Изменение озу
     *
     * @param view Параметр, отвечающий за отображение
     */
    public void onClickIzmen(View view) {
//db.execSQL("INSERT INTO ram(nazv_ram, _idramDDR, _idramObem,prise, kolvo,QR) VALUES ('R532G1601S1SL-U',2,4,3000,30,'30')");
        String zapros ="UPDATE ram set nazv_ram='"+nazv.getText().toString()+"', _idramDDR="+ (spiner.getSelectedItemPosition()+1)+
                ", _idramObem="+(spiner2.getSelectedItemPosition()+1)+", prise="+ Integer.parseInt(prise.getText().toString())+
                ", kolvo="+Integer.parseInt(kolvo.getText().toString())+", QR='"+tv.getText().toString()+"' where _id="+id+"";

        DBH.Del_in(zapros);
        Toast.makeText(getApplicationContext(), "Данные обновлены", Toast.LENGTH_SHORT).show();
        super.onBackPressed();
    }
}
