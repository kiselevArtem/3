package com.example.rrifafauzikomara.qrcodescanner;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ozyList extends AppCompatActivity {
    DBHelper DBH;
    // Button btnAddTipM;
    ArrayList<String> plansListItem;
    ArrayAdapter adapter;
    String zapros;
    ListView userList;
    //SQLiteDatabase db;
    Cursor userCursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ozy_list);
        DBH=new DBHelper(this);
        // btnAddTipM= findViewById(R.id.btnTip);
        plansListItem = new ArrayList<>();
        Bundle arguments = getIntent().getExtras();
        zapros= arguments.get("zapros").toString();

        userList = findViewById(R.id.LV);
        userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String text = userList.getItemAtPosition(position).toString();
                String str[] = text.split(":");
                Intent intent = new Intent(getApplicationContext(), ozyAdDel.class);
                intent.putExtra("zapros", str[0].trim());
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
     //   zapros="select _id,nazv_ram,(select ddr_name from ddr where ram._idramDDR=ddr._id),(select obem_name from obem where ram._idramObem=obem._id),prise,kolvo,QR from ram";
        userCursor=DBH.selectTabl(zapros);
       // db.execSQL("INSERT INTO ram(nazv_ram, _idramDDR, _idramObem, prise, kolvo,QR) VALUES ('R532G1601S1SL-U',2,4,3000,30,'30')");
        while (userCursor.moveToNext()) {


            plansListItem.add(userCursor.getString(0) +
                    " : " + userCursor.getString(1)+
                    "  " + userCursor.getString(2)+
                    "  " + userCursor.getString(3)+
                    " Цена: " + userCursor.getString(4) +
                    " Количество: " + userCursor.getString(5));
        }
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, plansListItem);
        userList.setAdapter(adapter);
    }
}
