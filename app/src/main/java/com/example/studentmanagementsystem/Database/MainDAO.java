package com.example.studentmanagementsystem.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.studentmanagementsystem.Model.Student;

import java.util.List;

@Dao
public interface MainDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Student student);

    @Query("SELECT * FROM students ORDER BY id DESC")
    List<Student> getAll();

    @Query("UPDATE students SET name = :name, surname = :surname, clas = :clas, rollNo = :rollNo, marks = :marks WHERE ID = :id")
    void update(int id, String name, String surname,String clas, String rollNo, String marks);

    @Delete
    void delete(Student student);

    @Query("SELECT * FROM students WHERE name LIKE '%' || :query || '%' OR surname LIKE '%' || :query || '%' OR clas LIKE '%' || :query || '%' OR rollNo LIKE '%' || :query || '%' OR marks LIKE '%' || :query || '%'")
    List<Student> searchStudents(String query);
}
