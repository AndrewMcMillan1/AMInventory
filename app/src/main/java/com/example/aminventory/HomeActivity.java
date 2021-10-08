package com.example.aminventory;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class HomeActivity extends AppCompatActivity {

    //Grid view and button;
    private GridView grid;
    private Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        button = findViewById(R.id.addActButton);
        grid = findViewById(R.id.grid);

        int userNum = getIntent().getExtras().getInt("userID");


        //Initialize DB helper, get array of items, and set array adapter to view
        final DatabaseHelper databaseHelper = new DatabaseHelper(HomeActivity.this);
        final List<ItemModel> allItems = databaseHelper.getItems(userNum);
        final ArrayAdapter itemAA = new ArrayAdapter<ItemModel>(HomeActivity.this, android.R.layout.simple_list_item_1, allItems);
        grid.setAdapter(itemAA);


        //Add button launches Add activity
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(new Intent(HomeActivity.this, AddActivity.class));
            }
        });

        //Click grid view item to launch EditDelete activity
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Get position of clicked item;
                ItemModel itemSelected = (ItemModel) parent.getItemAtPosition(position);


                //Get item attributes to send to EditDelete activity
                Intent intent = new Intent(HomeActivity.this, EditDeleteActivity.class);
                int idNum = itemSelected.getId();
                String item = itemSelected.getName();
                int quant = itemSelected.getQuantity();

                //Send item data to EditDelete activity
                intent.putExtra("itemIdentity", idNum);
                intent.putExtra("itemName", item);
                intent.putExtra("itemQuantity", quant);

                startActivity(intent);

            }
        });


    }
}