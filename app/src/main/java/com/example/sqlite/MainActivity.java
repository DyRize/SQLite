package com.example.sqlite;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.sqlite.data.Copain;

public class MainActivity extends ListActivity {

    private DatabaseAdapter databaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseAdapter = new DatabaseAdapter(this, "BaseDeCopains");

        // Ouverture de la connection
        databaseAdapter.open();

        // Récupérations des items en base
        Cursor cursor = databaseAdapter.retrieveItemsList();

        // Insertion de nouveaux copains si il n'y a pas de données en base
        if (cursor.getCount() == 0) {
            databaseAdapter.insertItem(new Copain("Mothé", "Aceline"));
            databaseAdapter.insertItem(new Copain("Thibodeau", "Auriville"));
            databaseAdapter.insertItem(new Copain("Leduc", "Christine"));
            databaseAdapter.insertItem(new Copain("Jardine", "Madelene"));
            databaseAdapter.insertItem(new Copain("Boulanger", "Langley"));
            databaseAdapter.insertItem(new Copain("Paradis", "Arianne"));
            databaseAdapter.insertItem(new Copain("Bouvier", "Fifine"));
            databaseAdapter.insertItem(new Copain("Marois", "Granville"));
            databaseAdapter.insertItem(new Copain("Saindon", "Fleur"));
            Toast.makeText(this, "Vous avez obtenu de nouveaux copains", Toast.LENGTH_LONG).show();
        }

        dataBind();

        // Liaison du menu contextuel
        ListView listView = (ListView) findViewById(android.R.id.list);
        registerForContextMenu(listView);
    }

    @Override
    protected void onDestroy() {
        databaseAdapter.close();
        super.onDestroy();
    }

    /**
     * Méthode permettant le chargement de l'IHM avec les données en base
     */
    public void dataBind() {
        Cursor cursor = databaseAdapter.retrieveItemsList();
        startManagingCursor(cursor);
        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(
                this,
                R.layout.copain_item,
                cursor,
                new String[]{"nom", "prenom"},
                new int[]{R.id.nom, R.id.prenom});
        setListAdapter(simpleCursorAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 0, 0, "Ajouter un copain");
        menu.add(0, 1, 1, "Supprimer tous les copains");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case 0 :
                databaseAdapter.insertItem(new Copain("Guertin", "Gérard"));
                dataBind();
                Toast.makeText(this, "Un copain a rejoint ton entourage !", Toast.LENGTH_LONG).show();
                break;
            case 1 :
                databaseAdapter.deleteAll();
                dataBind();
                Toast.makeText(this, "Vous avez perdu tous vos copains...", Toast.LENGTH_LONG).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        contextMenu.add(0, 0, 1, "Supprimer ce copain");
        contextMenu.add(0, 1, 1, "Modifier ce copain");
        super.onCreateContextMenu(contextMenu, view, contextMenuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo adapterContextMenuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Object object = getListAdapter().getItem(adapterContextMenuInfo.position);
        Cursor cursor = (Cursor)object;
        switch (item.getItemId()) {
            case 0 :
                databaseAdapter.deleteItem(cursor.getLong(0));
                dataBind();
                Toast.makeText(this, "Cette personne n'est plus ton copain", Toast.LENGTH_LONG).show();
                break;
            case 1 :
                Toast.makeText(this, "La modification d'un copain n'est pas encore disponible", Toast.LENGTH_LONG).show();
                break;
        }
        return super.onContextItemSelected(item);
    }
}