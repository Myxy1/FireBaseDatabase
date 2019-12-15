package com.example.petrik.firebasedatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private Button buttonSubmit;
    private EditText editRealName, editUserName, editPassword, editEmail;
    private DatabaseReference databaseReference;

    private Member member;

    private long maxid = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!editRealName.getText().toString().equals("") && !editEmail.getText().toString().equals("") &&
                        !editUserName.getText().toString().equals("") && !editPassword.getText().toString().equals("")) {
                    member.setRealName(editRealName.getText().toString().trim());
                    member.setUsername(editUserName.getText().toString().trim());
                    member.setPassowrd(editPassword.getText().toString().trim());
                    member.setEmail(editEmail.getText().toString().trim());
                    databaseReference.child(String.valueOf(maxid+1)).setValue(member);
                } else
                    Toast.makeText(MainActivity.this, "Minden mezőt ki kell tölteni!", Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void init() {
        buttonSubmit = findViewById(R.id.buttonSubmit);
        editRealName = findViewById(R.id.editRealName);
        editUserName = findViewById(R.id.editUserName);
        editPassword = findViewById(R.id.editPassword);
        editEmail = findViewById(R.id.editEmail);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Felhasználók");
        member = new Member();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                    maxid = (dataSnapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
