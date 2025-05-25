package com.example.studentmanagementsystem.Interface;

import androidx.cardview.widget.CardView;
import com.example.studentmanagementsystem.Model.Student;

public interface StudentClickListner {

    void onClick(Student studentInfo);
    void onLongPress(Student studentInfo, CardView cardView, int Position);


}
