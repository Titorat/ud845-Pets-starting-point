package com.example.android.pets;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.pets.data.PetContract;
import com.example.android.pets.data.PetContract.PetEntry;
import com.example.android.pets.data.PetDbHelper;

/**
 * Displays list of pets that were entered and stored in the app.
 */
public class CatalogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });



    }

    /**
     * Temporary helper method to display information in the onscreen TextView about the state of
     * the pets database.
     */
    private void displayDatabaseInfo() {


        Cursor cursor = getContentResolver().query(PetContract.CONTENT_URI,null,null,null,null);

        try {
            // Display the number of rows in the Cursor (which reflects the number of rows in the
            // pets table in the database).
            TextView displayView = (TextView) findViewById(R.id.text_view_pet);

            displayView.setText("The pets Table contains  " + cursor.getCount()+"pets\n\n");
            displayView.append(PetEntry._ID+" - "+PetEntry.COLUMN_PET_NAME+" - "
            +PetEntry.COLUMN_PET_BREED+" - "+PetEntry.COLUMN_PET_GENDER+" - " + PetEntry.COLUMN_PET_WEIGHT
            +"\n");
            int idColumnIndex = cursor.getColumnIndex(PetEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(PetEntry.COLUMN_PET_NAME);
            int breedColumnIndex = cursor.getColumnIndex(PetEntry.COLUMN_PET_BREED);
            int genderColumnIndex = cursor.getColumnIndex(PetEntry.COLUMN_PET_GENDER);
            int weightColumnIndex = cursor.getColumnIndex(PetEntry.COLUMN_PET_WEIGHT);

            while(cursor.moveToNext()){

                int    currentId     =   cursor.getInt(idColumnIndex);
                String currentName   =   cursor.getString(nameColumnIndex);
                String currentBreed  =   cursor.getString(breedColumnIndex);
                String currentGender =   cursor.getString(genderColumnIndex);
                String currentWeight =   cursor.getString(nameColumnIndex);

                displayView.append("\n"+currentId+" - "+currentName+" - "+currentBreed+" - "
                                       +currentGender+" - "+currentWeight);



            }

        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }
    }



    @Override
     protected void onStart() {
               super.onStart();
        displayDatabaseInfo();

    }

    public void  insertPet(){
        ContentValues values = new ContentValues();
        values.put(PetEntry.COLUMN_PET_NAME,"Toto");
        values.put(PetEntry.COLUMN_PET_BREED,"Terrier");
        values.put(PetEntry.COLUMN_PET_GENDER,PetEntry.GENDER_MALE);
        values.put(PetEntry.COLUMN_PET_WEIGHT,7);

       getContentResolver().insert(PetContract.CONTENT_URI,values);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
                insertPet();
                displayDatabaseInfo();

                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                // Do nothing for now
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}