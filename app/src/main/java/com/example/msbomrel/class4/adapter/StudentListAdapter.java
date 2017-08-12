package com.example.msbomrel.class4.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.msbomrel.class4.ListStudents;

import com.example.msbomrel.class4.R;
import com.example.msbomrel.class4.data.Student;
import com.example.msbomrel.class4.database.DatabaseHelper;

import java.util.List;

/**
 * Created by msbomrel on 7/8/17.
 */

public class StudentListAdapter extends BaseAdapter{
    private Context context;
    private List<Student> students;
    private int layoutId;
    PopupWindow pwindo;
    Activity activity;
    DatabaseHelper databaseHelper;

    public StudentListAdapter(Context context, List<Student> students, DatabaseHelper databaseHelper){
        this.context = context;
        this.students = students;
        this.databaseHelper = databaseHelper;
        layoutId  = R.layout.customlist;
    }

    @Override
    public int getCount() {
        return students.size();
    }

    @Override
    public Object getItem(int i) {
        return students.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public static class ViewHolder{
        TextView name;
        TextView address;
        TextView roll;
        Button edit;
        Button delete;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
       View result = view;
       ViewHolder viewHolder;
        if (result == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            result = inflater.inflate(layoutId, null, true);

            viewHolder.name = (TextView) result.findViewById(R.id.username);
            viewHolder.address = (TextView) result.findViewById(R.id.address);
            viewHolder.roll = (TextView) result.findViewById(R.id.rollno);
            viewHolder.edit = (Button) result.findViewById(R.id.editstudent);
            viewHolder.delete = (Button) result.findViewById(R.id.deletestudent);

            result.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.name.setText(students.get(i).getUsername());
        viewHolder.address.setText(students.get(i).getAddress());
        viewHolder.roll.setText(students.get(i).getRoll()+ " ");

        final int positionPopUp = i;

        viewHolder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d("Save: ", "" + positionPopUp);
                editPopup(positionPopUp);

            }
        });
        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Last Index", "" + positionPopUp);
                boolean status = databaseHelper.deleteStudent(students.get(positionPopUp));
                if (status) {
                    Toast.makeText(context, "Student record was deleted.", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(context, "Student record was not deleted.", Toast.LENGTH_SHORT).show();
                }
                notifyDataSetChanged();
                Intent intent= new Intent(context, ListStudents.class);
                context.startActivity(intent);
            }
        });
        return  result;
    }

    public void editPopup(final int positionPopup)
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.edit_popup,null,true);
        pwindo = new PopupWindow(layout, 700, 800, true);
        pwindo.showAtLocation(layout, Gravity.CENTER, 0, 0);
        final EditText nameEdit = (EditText) layout.findViewById(R.id.editTextName);
        final EditText addressEdit = (EditText) layout.findViewById(R.id.editTextAddress);
        final EditText rollnoEdit = (EditText) layout.findViewById(R.id.editTextRollNo);

        nameEdit.setText(students.get(positionPopup).getUsername());
        addressEdit.setText("" + students.get(positionPopup).getAddress());
        rollnoEdit.setText("" + students.get(positionPopup).getRoll());

        Log.d("Name: ", "" + students.get(positionPopup).getUsername());
        Button save = (Button) layout.findViewById(R.id.save_popup);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameEdit.getText().toString();
                String address = addressEdit.getText().toString();
                String rollno = rollnoEdit.getText().toString();
                Student student = students.get(positionPopup);
                student.setUsername(name);
                student.setAddress(address);
                student.setRoll(Integer.parseInt(rollno));
                databaseHelper.updateStudent(student);
                notifyDataSetChanged();
                pwindo.dismiss();
            }
        });
    }
}
