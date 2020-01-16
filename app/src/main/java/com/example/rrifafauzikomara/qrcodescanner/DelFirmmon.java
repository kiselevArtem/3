package com.example.rrifafauzikomara.qrcodescanner;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class DelFirmmon extends AppCompatActivity {
    EditText TB_mon;
    Button delButton;
    Button saveButton;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_del_firmmon);
        TB_mon = findViewById(R.id.TB_firmMon);
        delButton = findViewById(R.id.deleteButton);
        saveButton =  findViewById(R.id.saveButton);
        Bundle arguments = getIntent().getExtras();
         id = arguments.get("id").toString();
        DBH=new DBHelper(this);
        selectList(id);
    }
    ListView userList;
    SQLiteDatabase db;
    Cursor userCursor;
    ArrayAdapter adapter;
    DBHelper DBH;
    public void selectList(String id) {
        //db = DBH.getReadableDatabase();
       userCursor=DBH.selectTabl("select monitor_name from firmmonit where _id="+id+"");
        while (userCursor.moveToNext()) {
            TB_mon.setText(userCursor.getString(0));
        }
    }
    public void DelFirmMon(View view) {
        DBH.Del_in("DELETE from firmmonit where _id =" + id + "");
        Toast.makeText(getApplicationContext(), "Данные удалены", Toast.LENGTH_SHORT).show();
        super.onBackPressed();
    }
    //
    public void izmen(View view) {
        DBH.Del_in("UPDATE  firmmonit set monitor_name='"+TB_mon.getText().toString()+"' where _id =" + id + "");
        Toast.makeText(getApplicationContext(), "Данные изменены", Toast.LENGTH_SHORT).show();
        super.onBackPressed();
    }

}
