package com.example.mylostandfoundapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //Initializing variables
    Button createNewAdvertButton, showListButton;
    FragmentContainerView myFragmentContainerView;
    EditText enterName, enterPhone, enterItemDesc, enterDate, enterLocation;
    RadioGroup myRadioGroup;
    DatabaseHelper myDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homefragment);
        myDatabaseHelper = new DatabaseHelper(getApplicationContext());

        //FInd view by ID
        createNewAdvertButton = findViewById(R.id.createAdvertButton);
        showListButton = findViewById(R.id.lostAndFoundListButton);
        myFragmentContainerView = findViewById(R.id.fragmentContainerView);
        enterName = findViewById(R.id.nameEditText);
        enterPhone = findViewById(R.id.phoneEditText);
        enterItemDesc = findViewById(R.id.descriptionEditText);
        enterDate = findViewById(R.id.dateEditText);
        enterLocation = findViewById(R.id.locationEditText);

        setContentView(R.layout.activity_main);
    }
    public void selectArticleFragment(int position){
        Bundle bundle = new Bundle();
        bundle.putInt("key", position);
        Fragment removeAdvert = new RemoveAdvert();
        removeAdvert.setArguments(bundle);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainerView, removeAdvert).commit();
    }

    public void selectFragment(View view){
        Fragment fragment = null;
        Fragment createAdvertFragment = new CreateAdvert();
        Fragment lostAndFoundFragment = new LostAndFoundList();
        Fragment homeFragment = new HomeFragment();

        //Redirect to the correct fragment
        switch (view.getId()){
            case R.id.createAdvertButton:
                fragment = createAdvertFragment;
                break;
            case R.id.lostAndFoundListButton:
                fragment = lostAndFoundFragment;
                break;
            default:
                fragment = homeFragment;
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainerView, fragment).commit();

    }

    public List<Item> dbGetAll() {
        SQLiteDatabase myDB = myDatabaseHelper.getReadableDatabase();
        Cursor cursor = myDB.rawQuery("select * from " + Util.TABLE_NAME, null);

        ArrayList<Item> itemTable = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                itemTable.add(new Item(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6)));
            } while (cursor.moveToNext());
        }
        return itemTable;
    }

    public Item dbGetItem(Integer findID){
        SQLiteDatabase myDB = myDatabaseHelper.getReadableDatabase();
        String[] args = {findID.toString()};
        Cursor cursor = myDB.rawQuery("select * from " + Util.TABLE_NAME + " where " + Util.ID + "= ?", args, null);

        ArrayList<Item> itemTable = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                itemTable.add(new Item(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6)));
            } while (cursor.moveToNext());
        }
        return itemTable.get(0);
    }

    public void updateID(){
        List<Item> itemTable = new ArrayList<>();
        itemTable = dbGetAll();
        SQLiteDatabase db = myDatabaseHelper.getWritableDatabase();
        db.delete(Util.TABLE_NAME, null, null);
        int len = itemTable.size();

        for(int i = 0; i<len; i++){
            Item newEntry = itemTable.get(i);
            newEntry.ID=i+1;
            myDatabaseHelper.insertNew(newEntry);
        }
        db.close();
    }

    public void dbInsert(View view){
        enterName = findViewById(R.id.nameEditText);
        enterPhone = findViewById(R.id.phoneEditText);
        enterItemDesc = findViewById(R.id.descriptionEditText);
        enterDate = findViewById(R.id.dateEditText);
        enterLocation = findViewById(R.id.locationEditText);
        String status = "Lost";

        myRadioGroup = findViewById(R.id.lostAndFoundRadioGroup);
        if(myRadioGroup.getCheckedRadioButtonId() == R.id.foundRadioButton){
            status = "Found";
        }

        String name, phone, itemdesc, date, location;

        name = enterName.getText().toString();
        phone = enterPhone.getText().toString();
        itemdesc = enterItemDesc.getText().toString();
        date = enterDate.getText().toString();
        location = enterLocation.getText().toString();

        Integer dbSize = dbGetAll().size();

        //Make sure no fields are empty
        if(name.length()>0 && phone.length()>0 && itemdesc.length()>0 && date.length()>0 && location.length()>0){
            Item newLostArticle = new Item(dbSize, status, name, phone, itemdesc, date, location);
            myDatabaseHelper.insertNew(newLostArticle);
            selectFragment(view);
        }
        else{
            Toast.makeText(getApplicationContext(), "All fields are required.", Toast.LENGTH_LONG).show();
        }
    }

    public void dbDelete(Integer delID){
        SQLiteDatabase db = myDatabaseHelper.getWritableDatabase();
        db.delete(Util.TABLE_NAME, Util.ID + "=?", new String[]{delID.toString()});
        db.close();
        updateID();
    }

    public void deleteButton(View view){
        int pass = (int) view.getTag();
        dbDelete(pass);
        selectFragment(view);
    }
}
