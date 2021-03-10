package com.example.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.sqlite.data.Copain;

/**
 * Classe CRUD (Create, Read, Update, Delete)
 */
public class DatabaseAdapter {
    private DatabaseHelper databaseHelper;
    private Context context;
    private SQLiteDatabase sqLiteDatabase;

    public DatabaseAdapter(Context context, String name) {
        this.context = context;
        this.databaseHelper = new DatabaseHelper(context, name);
    }

    /**
     * Méthode permettant l'ouverture de la base de données
     * @return databaseAdapter
     */
    public DatabaseAdapter open() {
        this.sqLiteDatabase = databaseHelper.getWritableDatabase();
        return this;
    }

    /**
     * Méthode permettant la fermeture de la base de données
     */
    public void close() {
        this.sqLiteDatabase.close();
    }

    /**
     * Méthode permettant la suppression de tous les copains en base
     */
    public void deleteAll() {
        int nbRow = this.sqLiteDatabase.delete("Copain", "1", null);
        System.out.println("Les " + nbRow + " lignes de la table Copain ont été supprimées.");
    }

    /**
     * Méthode permettant d'insérer un copain en base
     * @param copain nouveau copain
     * @return long
     */
    public long insertItem(Copain copain) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("nom", copain.getNom());
        contentValues.put("prenom", copain.getPrenom());
        return sqLiteDatabase.insert("Copain", null, contentValues);
    }

    /**
     * Méthode permettant de supprimer un copain
     * @param id identifiant du copain à supprimer
     * @return vrai ou faux
     */
    public boolean deleteItem(long id) {
        return sqLiteDatabase.delete("Copain", "_id = ?", new String[]{Long.toString(id)}) > 0;
    }

    /**
     * Méthode permetant d'obtenir la liste des copains
     * @return cursor
     */
    public Cursor retrieveItemsList() {
        return this.sqLiteDatabase.query("Copain", new String[]{"_id", "nom", "prenom"}, null, null, null, null, null);
    }
}
