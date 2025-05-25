package com.example.studentmanagementsystem.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentmanagementsystem.R;
import com.example.studentmanagementsystem.Interface.StudentClickListner;
import com.example.studentmanagementsystem.Model.Student;
import com.example.studentmanagementsystem.Database.RoomDb;

import java.util.ArrayList;
import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentViewHolder> {

    Context context;
    List<Student> studentList = new ArrayList<>();
    List<Student> originalStudentList = new ArrayList<>(); // Store the original list
    StudentClickListner listner;
    RoomDb database; // Database reference

    public StudentAdapter(Context context, List<Student> studentList, StudentClickListner listner) {
        this.context = context;
        this.studentList = studentList != null ? studentList : new ArrayList<>();
        this.originalStudentList = new ArrayList<>(studentList); // Store the original list
        this.listner = listner;
        this.database = RoomDb.getInstance(context); // Initialize the database
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StudentViewHolder(LayoutInflater.from(context).inflate(R.layout.item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        Student currentStudent = studentList.get(position);
        holder.nameTxt.setText(currentStudent.getName());
        holder.surnameTxt.setText(currentStudent.getSurname());
        holder.classTxt.setText(currentStudent.getClas());
        holder.rollNoTxt.setText(currentStudent.getRollNo());
        holder.marksTxt.setText(currentStudent.getMarks());
        holder.textId.setText(String.valueOf(currentStudent.getID()));

        holder.cardView.setOnClickListener(v -> listner.onClick(studentList.get(holder.getAdapterPosition())));

        holder.cardView.setOnLongClickListener(v -> {
            listner.onLongPress(studentList.get(holder.getAdapterPosition()), holder.cardView, position);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    // Method to filter the list based on the search query
    public void filterList(String query) {
        if (query.isEmpty()) {
            // Restore the original list when search is cleared
            studentList.clear();
            studentList.addAll(database.mainDAO().getAll());
        } else {
            // Query the database for filtered results based on name, surname, etc.
            List<Student> filteredList = database.mainDAO().searchStudents(query);
            studentList.clear();
            studentList.addAll(filteredList);
        }
        notifyDataSetChanged(); // Notify adapter of the changes
    }

    // Method to remove an item from the list at the given position
    public void removeItem(int position) {
        Student studentToRemove = studentList.get(position);

        // Remove from the database
        database.mainDAO().delete(studentToRemove);

        // Remove from the list
        studentList.remove(position);

        // Notify adapter of the item removal
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, studentList.size()); // Adjust positions of the remaining items
    }
}

class StudentViewHolder extends RecyclerView.ViewHolder {

    CardView cardView;
    TextView nameTxt, surnameTxt, classTxt, rollNoTxt, marksTxt, textId;

    public StudentViewHolder(@NonNull View itemView) {
        super(itemView);
        cardView = itemView.findViewById(R.id.record_Container);
        nameTxt = itemView.findViewById(R.id.nameTxt);
        surnameTxt = itemView.findViewById(R.id.surnameTxt);
        classTxt = itemView.findViewById(R.id.classTxt);
        rollNoTxt = itemView.findViewById(R.id.rollNoTxt);
        marksTxt = itemView.findViewById(R.id.marksTxt);
        textId = itemView.findViewById(R.id.textId);
    }
}
