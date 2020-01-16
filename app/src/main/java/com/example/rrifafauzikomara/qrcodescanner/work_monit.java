package com.example.rrifafauzikomara.qrcodescanner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class work_monit extends AppCompatActivity {
String id;
    DBHelper DBH;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_monit);
        Bundle arguments = getIntent().getExtras();
        id= arguments.get("id").toString();
        DBH=new DBHelper(this);
    }
    public void DelFirmMon(View view) {
        DBH.Del_in("DELETE from monit where _id =" + id + "");
        Toast.makeText(getApplicationContext(), "Данные удалены", Toast.LENGTH_SHORT).show();
        super.onBackPressed();
    }
}
