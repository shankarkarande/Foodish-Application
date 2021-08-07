package com.example.crunchmunch.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.crunchmunch.R;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText email,password;
    Button submit;
    ProgressDialog pg;
    FirebaseAuth auth;
    String email_pattern = "^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        pg = new ProgressDialog(this);
        pg.setMessage("Please Wait...");
        pg.setCancelable(false);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        submit = findViewById(R.id.submit);
        auth = FirebaseAuth.getInstance();

        if(auth.getCurrentUser()!=null)
        {
            startActivity(new Intent(LoginActivity.this,ButtonActivity.class));
            finishAffinity();
        }

        submit.setOnClickListener(v ->
        {
            pg.show();
            String login_email = email.getText().toString();
            String login_pass = password.getText().toString();

            if(TextUtils.isEmpty(login_email) || TextUtils.isEmpty(login_pass))
            {
                pg.dismiss();
                Toast.makeText(LoginActivity.this, "Enter Valid Data", Toast.LENGTH_SHORT).show();
            }
            else if(!login_email.matches(email_pattern))
            {
                pg.dismiss();
                email.setError("Invalid Email");
                Toast.makeText(LoginActivity.this,"Enter Valid Email",Toast.LENGTH_SHORT).show();
            }
            else if(password.length()<6)
            {
                pg.dismiss();
                password.setError("Invalid Password");
                Toast.makeText(LoginActivity.this,"Password must be greater than 6 characters",Toast.LENGTH_SHORT).show();
            }
            else
            {
                auth.signInWithEmailAndPassword(login_email,login_pass).addOnCompleteListener(task ->
                {
                    if(task.isSuccessful())
                    {
                        pg.dismiss();
                        startActivity(new Intent(LoginActivity.this, ButtonActivity.class));
                        finish();
                    }
                    else
                    {
                        pg.dismiss();
                        Toast.makeText(LoginActivity.this,"Login Failed",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}