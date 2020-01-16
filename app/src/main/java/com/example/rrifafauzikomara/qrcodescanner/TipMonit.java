package com.example.rrifafauzikomara.qrcodescanner;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.DropBoxManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;

public class TipMonit extends AppCompatActivity {
   DBHelper DBH;
    Button btnAddTipM;
    EditText ET_name;
    ArrayList<String> plansListItem;
    ListView userList;
    SQLiteDatabase db;
    Cursor userCursor;
    ArrayAdapter adapter;
    @Override
    /**
     * Задаёт начальную установку параметров при инициализации активности
     *
     * @param savedInstanceState Сохраненное состояние
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip_monit);
        DBH=new DBHelper(this);
        btnAddTipM= findViewById(R.id.btnTip);
        plansListItem = new ArrayList<>();
        ET_name=findViewById(R.id.ET_Name);
        userList = findViewById(R.id.LV);
        userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String text = userList.getItemAtPosition(position).toString();
                String str[] = text.split(":");
                Intent intent = new Intent(getApplicationContext(), DelFirmmon.class);
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
     * Добавление новой компании
     */
    public void BTN_add_tipMon(View view) {
        String income_sum = ET_name.getText().toString();
        DBH.Otobr_TipMon(income_sum);
        selectList();
    }
    /**
     * вывод данных в лист
     */
    public void selectList() {
        plansListItem.clear();
       // db = DBH.getReadableDatabase();
        userCursor=DBH.selectTabl("select * from firmmonit");
        //userCursor =  db.rawQuery("select * from firmmonit", null);
        while (userCursor.moveToNext()) {

            plansListItem.add(userCursor.getString(0) + " : " + userCursor.getString(1) );
        }
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, plansListItem);
        userList.setAdapter(adapter);
        /*
        db = DBH.getReadableDatabase();
       // db.execSQL("DELETE FROM firmmonit WHERE _id = 6");
        userCursor =  db.rawQuery("select * from firmmonit", null);
        String[] headers = new String[] {"_id","monitor_name"};
         //создаем адаптер, передаем в него курсор
        userAdapter = new SimpleCursorAdapter(this, android.R.layout.two_line_list_item,
                userCursor, headers, new int[]{android.R.id.text1,android.R.id.text2}, 0);
      //  header.setText("Найдено элементов: " + String.valueOf(userCursor.getCount()));
        userList.setAdapter(userAdapter);

         */
    }

}
