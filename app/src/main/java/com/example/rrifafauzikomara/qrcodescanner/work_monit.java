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

public class work_monit extends AppCompatActivity {
String id;
    DBHelper DBH;
    Cursor userCursor;
    Spinner spinner;
    EditText nazv, zvet,diag,prise, kolvo;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_monit);
        nazv=findViewById(R.id.ETname2);
        zvet=findViewById(R.id.ETColor2);
        diag=findViewById(R.id.ETdiag2);
        prise=findViewById(R.id.ETprise2);
        kolvo=findViewById(R.id.ETkolvo2);
        tv=findViewById(R.id.tvQR2);
        spinner=findViewById(R.id.spinnerFirm2);
        Bundle arguments = getIntent().getExtras();
        id= arguments.get("id").toString();
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
        spinner.setAdapter(dataAdapter);
        String zapros ="select * from monit where _id =" + id + "";
        userCursor=DBH.selectTabl(zapros);
        while (userCursor.moveToNext()) {
            //        db.execSQL("INSERT INTO monit(nazv_mon, color, monitor_diag,prise,kolvo, QR, id_tipmon) VALUES ('Samsung S24D300H','чёрный','24\"',10000,10,'1',1)");
             nazv.setText(userCursor.getString(1));
             zvet.setText(userCursor.getString(2));
             diag.setText(userCursor.getString(3));
             prise.setText(userCursor.getString(4));
             kolvo.setText(userCursor.getString(5));
             spinner.setSelection(Integer.parseInt(userCursor.getString(7))-1);
             tv.setText(userCursor.getString(6));
        }

    }
    /**
     * Удаление монитора
     */
    public void DelFirmMon(View view) {
        DBH.Del_in("DELETE from monit where _id =" + id + "");
        Toast.makeText(getApplicationContext(), "Данные удалены", Toast.LENGTH_SHORT).show();
        super.onBackPressed();
    }
    /**
     * Получение qr кода
     */
    public void onClick(View view) {
        Intent captureIntent = new Intent(work_monit.this, CaptureActivity.class);
        startActivityForResult(captureIntent, 0);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                String value = data.getStringExtra("SCAN_RESULT");
                tv.setText(value);
            } else if (resultCode == Activity.RESULT_CANCELED) {
                tv.setText("Сканирование не удалось.");
            }
        } else {
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    /**
     * добавление монитора
     */
    public void onClickIzmMon(View view) {// Integer.parseInt(prise.getText().toString())   Integer.parseInt(kolvo.getText().toString())
        String i="";
        userCursor= DBH.selectTabl("select _id from firmmonit where monitor_name='"+spinner.getSelectedItem().toString()+"'");
        if (userCursor.moveToFirst()) {
            do {

                i=(userCursor.getString(0));
            } while (userCursor.moveToNext());
        }
        String  zapros="UPDATE monit set nazv_mon='"+nazv.getText().toString()+"',color='"+zvet.getText().toString()+"',monitor_diag='"+diag.getText().toString()+
                  "',prise="+prise.getText().toString()+",kolvo="+kolvo.getText().toString()+",QR='"+tv.getText().toString()+
                  "',id_tipmon="+i+" where _id="+id+"";
        DBH.Del_in(zapros);
        Toast.makeText(getApplicationContext(), "Монитор изменён", Toast.LENGTH_SHORT).show();
        super.onBackPressed();
    }
}
