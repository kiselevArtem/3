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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import info.vividcode.android.zxing.CaptureActivity;
import info.vividcode.android.zxing.CaptureActivityIntents;
public class Monit_add extends AppCompatActivity {
    TextView tvQr;
    DBHelper DBH;
    EditText nazv, zvet,diag,prise, kolvo;
    Spinner spiner;
    Cursor userCursor;
    @Override
    /**
     * Задаёт начальную установку параметров при инициализации активности
     *
     * @param savedInstanceState Сохраненное состояние
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monit_add);

        nazv=findViewById(R.id.ETname);
        zvet=findViewById(R.id.ET_Color);
        diag=findViewById(R.id.ETdiag);
        tvQr=findViewById(R.id.tvQR);
        prise=findViewById(R.id.ETprise);
        kolvo=findViewById(R.id.ETkolvo);
        spiner = findViewById(R.id.spinnerFirm);
        DBH=new DBHelper(this);
        userCursor=DBH.selectTabl("select * from firmmonit");
        List<String> labels= new ArrayList<String>();
        if (userCursor.moveToFirst()) {
            do {

                labels.add(userCursor.getString(1));
            } while (userCursor.moveToNext());
        }
       // String a = diag.getText().toString();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, labels);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spiner.setAdapter(dataAdapter);
    }
    /**
     * Получение qr кода
     *
     * @param view Параметр, отвечающий за отображение
     */
    public void onClick(View view) {
        Intent captureIntent = new Intent(Monit_add.this, CaptureActivity.class);
        startActivityForResult(captureIntent, 0);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                String value = data.getStringExtra("SCAN_RESULT");
                tvQr.setText(value);
            } else if (resultCode == Activity.RESULT_CANCELED) {
                tvQr.setText("Сканирование не удалось.");
            }
        } else {
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    /**
     * Добавление нового монитора в БД
     *
     * @param view Параметр, отвечающий за отображение
     */
    public void onClickAddMon(View view) {// Integer.parseInt(prise.getText().toString())   Integer.parseInt(kolvo.getText().toString())
        String i="";
        String firm=spiner.getSelectedItem().toString();
        String a=nazv.getText().toString();
       userCursor= DBH.selectTabl("select _id from firmmonit where monitor_name='"+spiner.getSelectedItem().toString()+"'");
        if (userCursor.moveToFirst()) {
            do {

                i=(userCursor.getString(0));
            } while (userCursor.moveToNext());
        }

        String zapros ="INSERT INTO monit(nazv_mon, color, monitor_diag,prise,kolvo, QR, id_tipmon) VALUES " +
                "('"+a+"','"+zvet.getText().toString()+"','"+diag.getText().toString()+
                "\"',"+ Integer.parseInt(prise.getText().toString())+","+Integer.parseInt(kolvo.getText().toString())+
                ",'"+tvQr.getText().toString()+"',1)";

        DBH.Del_in(zapros);
        Toast.makeText(getApplicationContext(), "Монитор добавлен", Toast.LENGTH_SHORT).show();
        super.onBackPressed();
    }

}
