package com.example.msbomrel.class4;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.example.msbomrel.class4.data.Student;
import com.example.msbomrel.class4.database.DatabaseHelper;

/**
 * Created by msbomrel on 7/8/17.
 */

public class Home extends AppCompatActivity {
    EditText username, address, rollno;
    Button save, reset, listView;
    PopupWindow pwindo;
    Activity activity;

    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        username = (EditText) findViewById(R.id.username);
        address = (EditText) findViewById(R.id.address);
        rollno = (EditText) findViewById(R.id.rollno);
        databaseHelper = new DatabaseHelper(this, null, null, 0);
        listView = (Button) findViewById(R.id.listview);

        save = (Button) findViewById(R.id.save);
        reset = (Button) findViewById(R.id.reset);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username.setText("");
                address.setText("");
                rollno.setText("");
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Student student = new Student(
                        1,
                        username.getText().toString(),
                        address.getText().toString(),
                        Integer.parseInt(rollno.getText().toString())
                );
                Boolean status = databaseHelper.insertStudent(student);
                System.out.println(status);

                if (status) {
                    Toast.makeText(Home.this, "Inserted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Home.this, "Not Inserted", Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(Home.this, ListStudents.class);
                startActivity(intent);
            }
        });

        listView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, ListStudents.class);
                startActivity(intent);
            }
        });

    }
}
