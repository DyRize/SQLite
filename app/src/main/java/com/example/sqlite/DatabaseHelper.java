package com.example.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Classe permettant de créer physiquement la base de données SQLite sur l'appareil
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private Context context;

    public DatabaseHelper(Context context, String databaseName) {
        super(context, databaseName, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE Copain (_id INTEGER PRIMARY KEY AUTOINCREMENT, nom TEXT, prenom TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        Toast.makeText(context, "Mise à jour de la BBD Version " + oldVersion + " vers " + newVersion, Toast.LENGTH_LONG).show();
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Copain");
        onCreate(sqLiteDatabase);
    }
}
