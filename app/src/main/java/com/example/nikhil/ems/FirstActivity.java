package com.example.nikhil.ems;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FirstActivity extends AppCompatActivity {

    Button logout;
    TextView appoint;
    TextView text;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    DatabaseReference dataReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        dataReference = FirebaseDatabase.getInstance().getReference("Dataout");
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss aa");
        final String checkout = simpleDateFormat.format(new Date());
        appoint = (TextView)findViewById(R.id.appointment);
        text = (TextView)findViewById(R.id.user_name);

        Intent in = getIntent();
        final String usercheck = in.getStringExtra("checkin");
        final String user = in.getStringExtra("userid");
        appoint.setText("You have successfully checked in at:\n"+usercheck+"");
        text.setText("WELCOME "+user+"");

        logout = (Button)findViewById(R.id.checkout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = dataReference.push().getKey();
                Dataout d = new Dataout(user,checkout);
                dataReference.child(id).setValue(d);
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(getApplication(),"Checked Out Successfully"+"\n"+"at"+checkout,Toast.LENGTH_SHORT).show();
                Intent i =  new Intent(FirstActivity.this,MainActivity.class);
                startActivity(i);
            }
        });


    }
}
