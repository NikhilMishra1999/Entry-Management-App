package com.example.nikhil.ems;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class login extends AppCompatActivity {

    Button signin;
    EditText username;
    EditText password;
    CheckBox show;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    DatabaseReference dataReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        signin =(Button)findViewById(R.id.signin);
        show = (CheckBox)findViewById(R.id.show);

        show.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    // show password
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    // hide password
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        dataReference = FirebaseDatabase.getInstance().getReference("Datain");

        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss aa");
        final String checkin = simpleDateFormat.format(new Date());

        mFirebaseAuth = FirebaseAuth.getInstance();

        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.pass);

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                if (mFirebaseUser != null){
                    Toast.makeText(getApplication(),"Checked In Successfully",Toast.LENGTH_LONG).show();
                    Intent i  = new Intent(login.this,FirstActivity.class);
                    startActivity(i);
                }
            }
        };

        //SIGNIN Button---------
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String user = username.getText().toString();
                final String pas = password.getText().toString();

                //Checking if the EditText Fields are Empty------
                if (TextUtils.isEmpty(user)) {
                    username.requestFocus();
                    username.setError("This field can't be empty");
                    Toast.makeText(getApplication(),"Enter the required fields",Toast.LENGTH_LONG).show();
                    return;
                }

                if (TextUtils.isEmpty(pas)) {
                    password.setError("This field can't be empty");
                    password.requestFocus();
                    Toast.makeText(getApplication(),"Enter the required fields",Toast.LENGTH_LONG).show();
                    return;
                }

                else if (!(user.isEmpty() && pas.isEmpty())){
                    mFirebaseAuth.signInWithEmailAndPassword(user,pas).addOnCompleteListener(login.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){

                                String id = dataReference.push().getKey();
                                Datain d = new Datain(user,checkin);
                                dataReference.child(id).setValue(d);
                                Intent intent =  new Intent(login.this,FirstActivity.class);
                                intent.putExtra("userid",user+"");
                                intent.putExtra("checkin",checkin+"");
                                startActivity(intent);
                                Toast.makeText(getApplication(),"Checked In Successfully"+"\n"+"at "+checkin,
                                        Toast.LENGTH_SHORT).show();

                            }
                            else {
                                Toast.makeText(getApplication(),"Login Unsuccessful",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }
}
