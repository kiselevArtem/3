package com.example.rrifafauzikomara.qrcodescanner;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.widget.Button;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "DB.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_FirmMonitor = "firmmonit";
    public static final String Firm_monit_ID = "_id";
    public static final String Firm_monit_Name = "monitor_name";

    public static final String TABLE_Monitor = "monit";
    public static final String monit_ID = "_id";
    public static final String Nazv_monit = "nazv_mon";
    public static final String monit_Diag = "monitor_diag";
    public static final String monit_firm = "id_tipmon";
    public static final String Color_mon = "color";
    public static final String monit_qr = "QR";
    public static final String Prise = "prise";
    public static final String kolvo = "kolvo";

    public static final String TABLE_ram= "ram";
    public static final String ram_ID = "_id";
    public static final String Nazv_ram = "nazv_ram";
    public static final String ram_ddr = "_idramDDR";
    public static final String ram_obem = "_idramObem";
    public static final String Prise_ram = "prise";
    public static final String kolvo_ram = "kolvo";

    public static final String TABLE_ddr= "ddr";
    public static final String ddr_ID = "_id";
    public static final String Nazv_ddr = "ddr_name";

    public static final String TABLE_obem= "obem";
    public static final String obem_ID = "_id";
    public static final String Nazv_obem = "obem_name";
    /**
     * конструктор класса DBHelper
     */
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
//
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_obem + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, obem_name TEXT)");
        db.execSQL("INSERT INTO obem(obem_name) VALUES ('2GB')");
        db.execSQL("INSERT INTO obem(obem_name) VALUES ('4GB')");
        db.execSQL("INSERT INTO obem(obem_name) VALUES ('8GB')");
        db.execSQL("INSERT INTO obem(obem_name) VALUES ('16GB')");

        db.execSQL("CREATE TABLE " + TABLE_ddr + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, ddr_name TEXT)");
        db.execSQL("INSERT INTO ddr(ddr_name) VALUES ('DDR3')");
        db.execSQL("INSERT INTO ddr(ddr_name) VALUES ('DDR4')");

        db.execSQL("CREATE TABLE " + TABLE_ram + " (_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " nazv_ram TEXT, _idramDDR INTEGER, _idramObem INTEGER ,prise  INTEGER ,kolvo  INTEGER, QR TEXT," +
                "FOREIGN KEY(_idramDDR) REFERENCES ddr(_id) ,FOREIGN KEY(_idramObem) REFERENCES obem(_id))");

        db.execSQL("INSERT INTO ram(nazv_ram, _idramDDR, _idramObem,prise,kolvo, QR) VALUES ('R532G1601S1S-U',1,2,10000,10,'10')");
        db.execSQL("INSERT INTO ram(nazv_ram, _idramDDR, _idramObem,prise, kolvo,QR) VALUES ('R532G1601S1SL-U',2,3,20000,20,'20')");
        db.execSQL("INSERT INTO ram(nazv_ram, _idramDDR, _idramObem,prise, kolvo,QR) VALUES ('R532G1601S1SL-U',2,4,3000,30,'30')");

        db.execSQL("CREATE TABLE " + TABLE_FirmMonitor + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, monitor_name TEXT)");
        db.execSQL("INSERT INTO firmmonit(monitor_name) VALUES ('Samsung')");
        db.execSQL("INSERT INTO firmmonit(monitor_name) VALUES ('Lenovo')");
        db.execSQL("INSERT INTO firmmonit(monitor_name) VALUES ('LG')");

        db.execSQL("CREATE TABLE " + TABLE_Monitor + " (_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " nazv_mon TEXT, color TEXT, monitor_diag TEXT,prise  INTEGER ,kolvo  INTEGER, QR TEXT, id_tipmon INTEGER," +
                "FOREIGN KEY (id_tipmon) REFERENCES TABLE_FirmMonitor(_id) ON DELETE CASCADE)");

// заполнение таблицы мониторов
        db.execSQL("INSERT INTO monit(nazv_mon, color, monitor_diag,prise,kolvo, QR, id_tipmon) VALUES ('Samsung S24D300H','чёрный','24\"',10000,10,'1',1)");
        db.execSQL("INSERT INTO monit(nazv_mon, color, monitor_diag,prise, kolvo,QR, id_tipmon) VALUES ('Lenovo S22E-19 61C9KAT1EU','белый','21.5\"',20000,20,'2',2)");
        db.execSQL("INSERT INTO monit(nazv_mon, color, monitor_diag,prise, kolvo,QR, id_tipmon) VALUES ('LG 22MK400A-B','чёрный','21.5\"',3000,30,'3',3)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int j) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FirmMonitor);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Monitor);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ram);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ddr);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_obem);
        onCreate(db);
    }
    /**
     * Добавление названия новой фирмы в таблицу
     */
    public void Otobr_TipMon(String label) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO firmmonit(monitor_name) VALUES ('" + label + "')");
        db.close();
    }
    public Cursor selectTabl(String zapros) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = zapros;
        return db.rawQuery(query, null);
    }
    public void Del_in(String zapros) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = zapros;
         db.execSQL(query);
        db.close();
    }
}
