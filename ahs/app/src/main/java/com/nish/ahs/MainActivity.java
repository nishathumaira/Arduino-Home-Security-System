package com.nish.ahs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.View;
import android.webkit.WebView;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessagingService;

public class MainActivity extends AppCompatActivity {

    private EditText eName;
    private EditText ePassword;
    private Button eLogin;
    private TextView eAttemptsInfo;


    private int counter=3;

    class Credentials {
        private String Username = "Admin";
        private String Password = "2323";
    }

    Boolean isValid = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //set to load the content of activity_main
        Intent intentBackgroundService = new Intent(this, MyFirebaseMessagingService.class);
        startService(intentBackgroundService);
        eName = findViewById(R.id.etName); //connects the xml layout with the variables
        ePassword = findViewById(R.id.etPassword);
        eLogin = findViewById(R.id.btnLogin);
        eAttemptsInfo = findViewById(R.id.tvAttemptsInfo);

        eLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){

                String inputName = eName.getText().toString();
                String inputPassword = ePassword.getText().toString();

                if(inputName.isEmpty() || inputPassword.isEmpty())
                {
                    Toast.makeText(MainActivity.this, "Enter correct username and password", Toast.LENGTH_SHORT).show();
                }else{

                    isValid = validate(inputName, inputPassword);

                    if(!isValid){

                        counter--;

                        eAttemptsInfo.setText("No. of attempts remaining:" + counter);

                        if (counter == 0){
                            eLogin.setEnabled(false); //disables the login button
                            Toast.makeText(MainActivity.this, "You have used all your attempts try again later", Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(MainActivity.this, "Incorrect username/password", Toast.LENGTH_SHORT).show();
                        }
                    }

                    else {

                        Toast.makeText(MainActivity.this, "Login success", Toast.LENGTH_SHORT).show();

                        //next activity
                        startActivity(new Intent(MainActivity.this, MainScreen.class));
                    }


                }

            }
        });

    }

    private boolean validate(String Username, String Password)
    {
        /* Get the object of Credentials class */
        Credentials credentials = new Credentials();

        /* Check the credentials */
        if(Username.equals(credentials.Username) && Password.equals(credentials.Password))
        {
            return true;
        }

        return false;
    }
}