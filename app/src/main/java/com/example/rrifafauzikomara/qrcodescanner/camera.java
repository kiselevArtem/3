package com.example.rrifafauzikomara.qrcodescanner;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Camera;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import info.vividcode.android.zxing.CaptureActivity;
import info.vividcode.android.zxing.CaptureActivityIntents;

public class camera extends AppCompatActivity {
    TextView tvScanResult;
    Button btScan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        tvScanResult = findViewById(R.id.TV_QR);
        btScan = findViewById(R.id.bt_scan);

    }
    public void onClick(View view) {
        Intent captureIntent = new Intent(camera.this, CaptureActivity.class);
        startActivityForResult(captureIntent, 0);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                String value = data.getStringExtra("Результат");
                tvScanResult.setText(value);
            } else if (resultCode == Activity.RESULT_CANCELED) {
                tvScanResult.setText("Сканирование не удалось.");
            }
        } else {
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
