package com.example.aminventory;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText nameET, passwordET;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Edit text views
        nameET = findViewById(R.id.username);
        passwordET = findViewById(R.id.password);

        //login button
        loginButton = findViewById(R.id.login);

        //login button click
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UserModel userModel;

                //Test user input
                try {
                    userModel = new UserModel(-1, nameET.getText().toString(), passwordET.getText().toString());
                    Toast.makeText(MainActivity.this, userModel.toString(), Toast.LENGTH_SHORT).show();


                }
                catch (Exception e){
                    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    userModel = new UserModel(-1, "error", "error");
                }

                ///////
                //////
                //////
                DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this);
                boolean success = true;
                boolean validation = databaseHelper.isUserRegistered(userModel.getUsername(), userModel.getPassword());
                if (!validation) {
                    //Add user to user table
                    success = databaseHelper.addUser(userModel);
                }


                ///////
                ///////
                //////




                // added message
                ///////
                /////////
                if (!success) {
                    Toast.makeText(MainActivity.this, "User not added", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Welcome", Toast.LENGTH_SHORT).show();
                }
                 ////////
                 // /////





                /////////////
                    //////////
                    //////////
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                int userID = databaseHelper.idQuery(userModel.getUsername(), userModel.getPassword());


                intent.putExtra("userID", userID);


                //////////
                    /////////
                    ///////////

                //Start main activity
                startActivity(intent);
            }
        });


    }
}