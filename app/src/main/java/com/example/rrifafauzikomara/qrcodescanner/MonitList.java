package com.example.rrifafauzikomara.qrcodescanner;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;

public class MonitList extends AppCompatActivity {
    DBHelper DBH;
   // Button btnAddTipM;
    ArrayList<String> plansListItem;
    ArrayAdapter adapter;
    String zapros;
    ListView userList;
    //SQLiteDatabase db;
    Cursor userCursor;
    @Override
    /**
     * Задаёт начальную установку параметров при инициализации активности
     *
     * @param savedInstanceState Сохраненное состояние
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monit_list);
        DBH=new DBHelper(this);
       // btnAddTipM= findViewById(R.id.btnTip);
        plansListItem = new ArrayList<>();
        Bundle arguments = getIntent().getExtras();
        zapros= arguments.get("zapros").toString();

        userList = findViewById(R.id.monit_list);
        userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String text = userList.getItemAtPosition(position).toString();
                String str[] = text.split(":");
                Intent intent = new Intent(getApplicationContext(), work_monit.class);
                intent.putExtra("id", str[0].trim());
                startActivity(intent);
            }
        });
    }
    /**
     * Отображение данных из бд
     */
    @Override
    protected void onStart() {
        super.onStart();
        selectList();
    }
    /**
     * Запрос на отображение
     */
    public void selectList() {
        plansListItem.clear();
       // db = DBH.getReadableDatabase();
        userCursor=DBH.selectTabl(zapros);
        //userCursor =  db.rawQuery("select * from firmmonit", null);
        while (userCursor.moveToNext()) {

            plansListItem.add(userCursor.getString(0) + " : " + userCursor.getString(1)+ "  " + userCursor.getString(2)+
                    "  " + userCursor.getString(3)+ " Цена: " + userCursor.getString(4) +" Количество: " + userCursor.getString(5)+
                    "    " + userCursor.getString(7));
        }
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, plansListItem);
        userList.setAdapter(adapter);
    }
}
