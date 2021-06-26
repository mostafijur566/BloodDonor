package com.example.blooddonor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApiNotAvailableException;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class AddDonor extends AppCompatActivity {

    private EditText txt_name;
    private EditText txt_number;
    private EditText txt_address;
    private EditText txt_blood;
    private EditText txt_email;

    private Button join;

    private DatabaseReference databaseReference;

    private FirebaseAuth mAuth;

    private String codeSent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_donor);

        txt_name = (EditText)findViewById(R.id.txt_name);
        txt_number = (EditText)findViewById(R.id.txt_number);
        txt_address = (EditText)findViewById(R.id.txt_address);
        txt_blood = (EditText)findViewById(R.id.txt_blood);
        txt_email = (EditText)findViewById(R.id.txt_email);

        join = (Button)findViewById(R.id.join);

        databaseReference = FirebaseDatabase.getInstance().getReference("Donors");

        mAuth = FirebaseAuth.getInstance();

        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveData();
            }
        });
    }

    private void saveData()
    {
        String name = txt_name.getText().toString().trim();
        String number = txt_number.getText().toString().trim();
        String address = txt_address.getText().toString().trim();
        String blood = txt_blood.getText().toString().trim();
        String email = txt_email.getText().toString().trim();

        if(name.isEmpty())
        {
            txt_name.setError("Enter your name");
            txt_name.requestFocus();
            return;
        }

        if(number.isEmpty())
        {
            txt_number.setError("Enter your phone number");
            txt_number.requestFocus();
            return;
        }

        if(number.length() != 11)
        {
            txt_number.setError("Number length should be 11");
            txt_number.requestFocus();
            return;
        }

        if(email.isEmpty())
        {
            txt_email.setError("Enter an email address");
            txt_email.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            txt_email.setError("Enter a valid email address");
            txt_email.requestFocus();
            return;
        }

        if(address.isEmpty())
        {
            txt_address.setError("Enter your address");
            txt_address.requestFocus();
            return;
        }

        if(blood.isEmpty())
        {
            txt_blood.setError("Enter your Blood Group");
            txt_blood.requestFocus();
            return;
        }

       mAuth.createUserWithEmailAndPassword(email, number)
               .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task) {
                       if(task.isSuccessful())
                       {
                           Donor donor = new Donor(name, number, address, blood);
                           String key = databaseReference.push().getKey();
                           databaseReference.child(key).setValue(donor);
                           Toast.makeText(AddDonor.this, "You have successfully registered", Toast.LENGTH_SHORT).show();
                           txt_name.setText("");
                           txt_address.setText("");
                           txt_blood.setText("");
                           txt_email.setText("");
                           txt_number.setText("");
                       }
                       else
                       {
                           if(task.getException() instanceof FirebaseAuthUserCollisionException)
                           {
                               Toast.makeText(AddDonor.this,"You have already registered your information", Toast.LENGTH_SHORT).show();
                           }
                           else
                           {
                               Toast.makeText(AddDonor.this, "No internet connection", Toast.LENGTH_SHORT).show();
                           }
                       }
                   }
               });
    }

}
