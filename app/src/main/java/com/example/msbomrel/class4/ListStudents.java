package com.example.msbomrel.class4;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ListView;


import com.example.msbomrel.class4.adapter.StudentListAdapter;
import com.example.msbomrel.class4.data.Student;
import com.example.msbomrel.class4.database.DatabaseHelper;

import java.util.List;

/**
 * Created by msbomrel on 7/13/17.
 */

public class ListStudents extends AppCompatActivity{
    DatabaseHelper databaseHelper;
    ListView listView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_main);

        listView = (ListView) findViewById(R.id.listviews);

        databaseHelper = new DatabaseHelper(this, null, null, 0);
        List<Student> studentList = databaseHelper.getStudentList();
        StudentListAdapter studentListAdapter = new StudentListAdapter(this,studentList, databaseHelper);
        listView.setAdapter(studentListAdapter);

    }
}
