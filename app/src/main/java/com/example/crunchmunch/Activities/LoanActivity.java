package com.example.crunchmunch.Activities;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.crunchmunch.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

import com.example.crunchmunch.Modale.Customers;

public class LoanActivity extends AppCompatActivity {

    EditText name1, phone1, amount1;
    TextView date1;
    int year, month, day;
    final Calendar calendar = Calendar.getInstance();
    Button btn;
    FirebaseDatabase database;
    ProgressDialog pg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan);

        pg = new ProgressDialog(this);
        pg.setMessage("Please Wait...");
        pg.setCancelable(false);

        btn = findViewById(R.id.btn);
        name1 = findViewById(R.id.edit1);
        phone1 = findViewById(R.id.edit2);
        amount1 = findViewById(R.id.edit3);
        date1 = findViewById(R.id.date);
        database = FirebaseDatabase.getInstance();

        date1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date1.setTextColor(getResources().getColor(R.color.white));
                Calendar calendar = Calendar.getInstance();
                day = calendar.get(Calendar.DAY_OF_MONTH);
                month = calendar.get(Calendar.MONTH);
                year = calendar.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(LoanActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        date1.setText(dayOfMonth + "/" + month + "/" + year);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });


        btn.setOnClickListener(v ->
        {
            pg.show();
            String name = name1.getText().toString() ;
            String phone = phone1.getText().toString();
            String amount = amount1.getText().toString();
            String date = date1.getText().toString();

            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(amount) || TextUtils.isEmpty(date))
            {
                pg.dismiss();
                Toast.makeText(this, "माहिती निट भरा ", Toast.LENGTH_SHORT).show();
            }
            else if (phone.length() != 10)
            {
                phone1.setError("Phone number must be 10 Digit");
            }
            else
            {
                DatabaseReference reference = database.getReference().child("Customers").child(name);
                Customers customers = new Customers(name,phone,amount,date);
                reference.setValue(customers).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if (task.isSuccessful())
                        {
                            pg.dismiss();
                            Toast.makeText(LoanActivity.this, "माहिती यशस्वी रीत्या जतन झाली ", Toast.LENGTH_SHORT).show();

                            name1.setText("");
                            phone1.setText("");
                            amount1.setText("");
                            date1.setText("तारीख निवडा");
                        }
                        else
                        {
                            pg.dismiss();
                            Toast.makeText(LoanActivity.this, "माहिती जतन करण्यात अयशस्वी ", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }

        });
    }
}