package com.example.connectt;

import static com.example.connectt.R.id.nameBox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUpActivity extends AppCompatActivity {
    FirebaseAuth auth;
    EditText emailBox, PasswordBox,nameBox;
    Button loginbutton ,createbutton;
    FirebaseFirestore database;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.connectt.R.layout.activity_sign_up);

        database=FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();

        emailBox=findViewById(com.example.connectt.R.id.emailBox);
        nameBox=findViewById(R.id.nameBox);
        PasswordBox=findViewById(com.example.connectt.R.id.PasswordBox);
        loginbutton=findViewById(com.example.connectt.R.id.loginbutton);
        createbutton=findViewById(R.id.createbutton);

        createbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email,pass,name;
                email=emailBox.getText().toString();
                pass=PasswordBox.getText().toString();
                name=nameBox.getText().toString();

                User user=new User();
                user.setEmail(email);
                user.setName(name);
                user.setPass(pass);//

                auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            database.collection("Users")
                                    .document().set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            startActivity(new Intent(SignUpActivity.this,LoginActivity.class));
                                        }
                                    });
                            Toast.makeText(SignUpActivity.this,"Account is Created",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(SignUpActivity.this,task.getException().getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}