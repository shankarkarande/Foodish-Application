package com.example.crunchmunch.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.crunchmunch.R;
import com.google.firebase.auth.FirebaseAuth;

public class ButtonActivity extends AppCompatActivity {

    Button button_udhar, button_yadi;
    ImageView logout_img, location_img,information_img;
    FirebaseAuth auth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button);

        button_udhar = findViewById(R.id.button_udhar);
        button_yadi = findViewById(R.id.button_yadi);
        logout_img = findViewById(R.id.logout_img);
        location_img = findViewById(R.id.location_img);
        information_img = findViewById(R.id.information_img);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);

        auth = FirebaseAuth.getInstance();

        button_udhar.setOnClickListener(v ->
        {
            Intent intent = new Intent(this, LoanActivity.class);
            startActivity(intent);
        });

        button_yadi.setOnClickListener(v ->
        {
            startActivity(new Intent(ButtonActivity.this, CustomerList.class));
        });

        logout_img.setOnClickListener(v ->
        {
            Dialog dialog = new Dialog(ButtonActivity.this, R.style.Dialoge);
            dialog.setContentView(R.layout.logout_dialog);

            TextView yesBtn, noBtn;

            yesBtn = dialog.findViewById(R.id.yesBtn);
            noBtn = dialog.findViewById(R.id.noBtn);

            yesBtn.setOnClickListener(v1 ->
            {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(ButtonActivity.this, LoginActivity.class));
                finishAffinity();
            });
            noBtn.setOnClickListener(v12 ->
            {
                dialog.dismiss();
            });
            dialog.show();
        });

        location_img.setOnClickListener(v->
        {
            Dialog dialog = new Dialog(ButtonActivity.this, R.style.Dialoge);
            dialog.setContentView(R.layout.location_dialog);

            TextView yesBtn, noBtn;

            yesBtn = dialog.findViewById(R.id.yesBtn);
            noBtn = dialog.findViewById(R.id.noBtn);

            yesBtn.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("NewApi")
                @Override
                public void onClick(View v)
                {
                    startActivity(new Intent(ButtonActivity.this,MapActivity.class));
                    finishAffinity();
                }
            });

            noBtn.setOnClickListener(v12 ->
            {
                dialog.dismiss();
            });
            dialog.show();
        });

        information_img.setOnClickListener(v->
        {
            Intent intent = new Intent(ButtonActivity.this, AboutActivity.class);
            startActivity(intent);
        });
    }
}