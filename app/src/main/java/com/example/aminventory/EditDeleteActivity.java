package com.example.aminventory;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class EditDeleteActivity extends AppCompatActivity {

    //Variables
    EditText ET1, ET2;
    Button del, edit;
    DatabaseHelper databaseHelper;
    ItemModel itemToChange;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_delete);

        ET1 = (EditText)findViewById(R.id.editText1);
        ET2 = (EditText)findViewById(R.id.editText2);
        edit = (Button)findViewById(R.id.button4);
        del = (Button)findViewById(R.id.button5);

        databaseHelper = new DatabaseHelper(EditDeleteActivity.this);


        //Get item attributes from home activity
         final int ids = getIntent().getExtras().getInt("itemIdentity");
         String itemN = getIntent().getExtras().getString("itemName");
         int itemQ = getIntent().getExtras().getInt("itemQuantity");

         //recreate item to delete
         itemToChange = new ItemModel(ids, itemN, itemQ);

         //Set edit text views to item attributes
        String str = String.valueOf(itemQ);
        ET1.setText(itemN);
        ET2.setText(str);

        //Delete item button click
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                databaseHelper.removeData2(itemToChange);


                startActivity(new Intent(EditDeleteActivity.this, HomeActivity.class));
            }
        });

        //Edit item button click
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Get altered edit text input
                String nameChange = ET1.getText().toString();
                String quantityChange = ET2.getText().toString();

                //Create new item to send to update function
                int q = Integer.parseInt(quantityChange);

                ItemModel itemToEdit = new ItemModel(ids, nameChange, q);

                //Update the item and return to home activity
                databaseHelper.updateItem(itemToEdit);

                startActivity(new Intent(EditDeleteActivity.this, HomeActivity.class));

            }
        });
    }
}