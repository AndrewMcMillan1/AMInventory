package com.example.aminventory;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {

    private EditText item, quantity;
    private TextView message;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        item = findViewById(R.id.itemAdd);
        quantity = findViewById(R.id.quantityAdd);

        button = findViewById(R.id.addButton);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ItemModel itemModel;

                //Test input
                try {
                    itemModel = new ItemModel(-1, item.getText().toString(), Integer.parseInt(quantity.getText().toString()));
                    Toast.makeText(AddActivity.this, itemModel.toString(), Toast.LENGTH_SHORT).show();


                }
                catch (Exception e){
                    Toast.makeText(AddActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    itemModel = new ItemModel(-1, "error", 0);
                }

                //Add item to database and return to home activity
                DatabaseHelper databaseHelper = new DatabaseHelper(AddActivity.this);
                boolean success = databaseHelper.addItem(itemModel);

                startActivity(new Intent(AddActivity.this, HomeActivity.class));
            }
        });
    }
}