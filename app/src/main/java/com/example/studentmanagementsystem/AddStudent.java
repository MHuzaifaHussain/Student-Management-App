package com.example.studentmanagementsystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.studentmanagementsystem.Database.MainDAO;
import com.example.studentmanagementsystem.Database.RoomDb;
import com.example.studentmanagementsystem.Model.Student;


public class AddStudent extends AppCompatActivity {

    EditText nameTxt, surnameTxt, classTxt, rollNoTxt, marksTxt;
    ImageView saveBtn;
    Student student;


    boolean isOldStudent = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_student);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        saveBtn = findViewById(R.id.saveBtn);
        nameTxt = findViewById(R.id.nameTxt);
        surnameTxt = findViewById(R.id.surnameTxt);
        classTxt = findViewById(R.id.classTxt);
        rollNoTxt = findViewById(R.id.rollNoTxt);
        marksTxt = findViewById(R.id.marksTxt);


        student = new Student();
        try {
            student = (Student) getIntent().getSerializableExtra("old_students");
            nameTxt.setText(student.getName());
            surnameTxt.setText(student.getSurname());
            classTxt.setText(student.getClas());
            rollNoTxt.setText(student.getRollNo());
            marksTxt.setText(student.getMarks());
            student.setID(student.getID());


            isOldStudent = true;
        }catch (Exception e){
            e.printStackTrace();
        }

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!isOldStudent){
                    student = new Student();
                }

                String name = nameTxt.getText().toString();
                String surname = surnameTxt.getText().toString();
                String clas = classTxt.getText().toString();
                String rollNo = rollNoTxt.getText().toString();
                String marks = marksTxt.getText().toString();


                if (name.isEmpty()){
                    Toast.makeText(AddStudent.this,"Please Enter the Name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (surname.isEmpty()){
                    Toast.makeText(AddStudent.this,"Please Enter the Surname", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (clas.isEmpty()){
                    Toast.makeText(AddStudent.this,"Please Enter the Class", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (rollNo.isEmpty()){
                    Toast.makeText(AddStudent.this,"Please Enter the Roll No", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (marks.isEmpty()){
                    Toast.makeText(AddStudent.this,"Please Enter the Marks", Toast.LENGTH_SHORT).show();
                    return;
                }


                student.setName(name);
                student.setSurname(surname);
                student.setClas(clas);
                student.setRollNo(rollNo);
                student.setMarks(marks);



                new Thread(() -> {
                    RoomDb db = RoomDb.getInstance(AddStudent.this);
                    MainDAO dao = db.mainDAO();

                    if (isOldStudent) {
                        dao.update(student.getID(), name, surname, clas, rollNo, marks);
                    } else {
                        dao.insert(student);
                    }

                    runOnUiThread(() -> {
//                        Toast.makeText(AddStudent.this, "Student saved successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    });
                }).start();


                Intent intent = new Intent();
                intent.putExtra("student", student);
                setResult(AddStudent.RESULT_OK, intent);
                finish();

            }
        });
    }
}