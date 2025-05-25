package com.example.studentmanagementsystem;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.studentmanagementsystem.Adapter.StudentAdapter;
import com.example.studentmanagementsystem.Database.RoomDb;
import com.example.studentmanagementsystem.Interface.StudentClickListner;
import com.example.studentmanagementsystem.Model.Student;

import java.util.ArrayList;
import java.util.List;

public class ViewData extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    RecyclerView recyclerView;
    StudentAdapter studentAdapter;
    RoomDb database;
    SearchView searchView;
    List<Student> students = new ArrayList<>();
    Student selectedStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_data);

        recyclerView = findViewById(R.id.noteRv);
        searchView = findViewById(R.id.searchView);
        database = RoomDb.getInstance(this);


        students = database.mainDAO().getAll();
        updateRecycle(students);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Pass the search query to the adapter
                studentAdapter.filterList(newText);
                return true;
            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();
        students = database.mainDAO().getAll();
        updateRecycle(students);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101){
            if(resultCode == ViewData.RESULT_OK){
                Student new_student = (Student) data.getSerializableExtra("student");
                database.mainDAO().insert(new_student);
                students.clear();
                students.addAll(database.mainDAO().getAll());
                studentAdapter.notifyDataSetChanged();
            }
        }else if (requestCode == 102){
            //Update and Edit the notes on click of notes
            if(resultCode == ViewData.RESULT_OK){
                Student new_notes = (Student) data.getSerializableExtra("student");
                database.mainDAO().update(new_notes.getID(), new_notes.getName(), new_notes.getSurname(), new_notes.getClas(), new_notes.getRollNo(), new_notes.getMarks());
                students.clear();
                students.addAll(database.mainDAO().getAll());
                studentAdapter.notifyDataSetChanged();
            }
        }


    }

    private void updateRecycle(List<Student> students){
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        studentAdapter = new StudentAdapter(ViewData.this, students, studentClickListner);
        recyclerView.setAdapter(studentAdapter);
    }

    private final StudentClickListner studentClickListner = new StudentClickListner() {
        @Override
        public void onClick(Student student) {
            Intent intent = new Intent(ViewData.this, AddStudent.class);
            intent.putExtra("old_students",student);
            startActivityForResult(intent, 102);
        }

        @Override
        public void onLongPress(Student student, CardView cardView, int position) {
            // On Long press we have to pin unpin or delete the note
            selectedStudent = student;
            showPop(cardView, position);
        }
    };

    private void showPop(CardView cardView, int position) {
        // Lets create a popup menu
        PopupMenu popupMenu = new PopupMenu(this, cardView);
        popupMenu.inflate(R.menu.popup_menu);

        popupMenu.setOnMenuItemClickListener(item -> {
            if(item.getItemId() == R.id.delete){
                database.mainDAO().delete(selectedStudent);
                studentAdapter.removeItem(position);
                Toast.makeText(this, "Selected Student Deleted Successfully...", Toast.LENGTH_SHORT).show();
                return true;
            }
            return false;

        });
        popupMenu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }
}