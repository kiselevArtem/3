package com.example.rrifafauzikomara.qrcodescanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class glavnaya extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glavnaya);
    }
    /**
     * Переход на вкладку для работы с мониторами
     */
    public void MonitorOpenClick(View view) {
        Intent intent = new Intent(glavnaya.this, monitor.class);
        startActivity(intent);
    }
    public void OzyOpenClick(View view) {
        Intent intent = new Intent(glavnaya.this, ozy.class);
        startActivity(intent);
    }
}
