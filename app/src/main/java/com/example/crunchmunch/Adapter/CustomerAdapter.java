package com.example.crunchmunch.Adapter;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crunchmunch.R;

import java.util.ArrayList;
import java.util.List;

import com.example.crunchmunch.Activities.CustomerList;
import com.example.crunchmunch.Modale.Customers;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.MyViewHolder> {

    Context context;
    ArrayList<Customers> list;
    final int SEND_SMS_CODE = 1;
    final int REQUEST_CALL = 1;
    private List<CustomerList> exampleList;
    private List<CustomerList> exampleListFull;

    public CustomerAdapter(Context context, ArrayList<Customers> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.activity_customerlist, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerAdapter.MyViewHolder holder, int position) {
        Customers customers = list.get(position);
        holder.customer_name.setText(customers.getName());
        holder.customer_amount.setText(customers.getAmount());
        holder.customer_date.setText(customers.getDate());

        if (checkPermission(Manifest.permission.SEND_SMS)) {

        } else {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.SEND_SMS}, 1);
        }

        holder.customer_message.setOnClickListener(v ->
        {

            Dialog dialog = new Dialog(context, R.style.Dialoge);
            dialog.setContentView(R.layout.message_dialog);

            TextView yesBtn, noBtn;
            yesBtn = dialog.findViewById(R.id.yesBtn);
            noBtn = dialog.findViewById(R.id.noBtn);

            yesBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkPermission(Manifest.permission.SEND_SMS)) {
                        SmsManager smsManager = SmsManager.getDefault();
                        ArrayList<String> message = smsManager.divideMessage("प्रिय " + customers.name + " आपली उधारी रक्कम\n₹ " + customers.amount + " बाकी आहे. आपण या रकमेचे सामान दिनांक " + customers.date + " या दिवशी घेतले होते.\nकृपया कारंडे चुरमुरे स्टॉल यांना संपर्क करा.\nधन्यवाद!");
                        smsManager.sendMultipartTextMessage(customers.phone, null, message, null, null);
                        Toast.makeText(context, "Message Sent", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    } else {
                        Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            noBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        });

        holder.customer_phone.setOnClickListener(v ->
        {

            Dialog dialog = new Dialog(context, R.style.Dialoge);
            dialog.setContentView(R.layout.phone_dialog);

            TextView yesBtn, noBtn;
            yesBtn = dialog.findViewById(R.id.yesBtn);
            noBtn = dialog.findViewById(R.id.noBtn);

            yesBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intcall = new Intent(Intent.ACTION_CALL);
                    intcall.setData(Uri.parse("tel:" + customers.phone));
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(context, "Please Grant Permission", Toast.LENGTH_SHORT).show();
                        requestingPermission();
                    } else {
                        context.startActivity(intcall);
                    }
                    dialog.dismiss();
                }
            });
            noBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.show();

        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public boolean checkPermission(String permission) {
        int check = ContextCompat.checkSelfPermission(context, permission);
        return (check == PackageManager.PERMISSION_GRANTED);
    }

    public void requestingPermission() {
        ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CALL_PHONE}, 1);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView customer_name, customer_amount, customer_date;
        public ImageView customer_message, customer_phone, customer_delete;
        public TextView yesBtn, noBtn;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            customer_name = itemView.findViewById(R.id.customer_name);
            customer_amount = itemView.findViewById(R.id.customer_amount);
            customer_date = itemView.findViewById(R.id.customer_date);

            customer_message = itemView.findViewById(R.id.customer_message);
            customer_phone = itemView.findViewById(R.id.customer_phone);
            customer_delete = itemView.findViewById(R.id.customer_delete);
            yesBtn = itemView.findViewById(R.id.yesBtn);
            noBtn = itemView.findViewById(R.id.noBtn);

        }
    }
}