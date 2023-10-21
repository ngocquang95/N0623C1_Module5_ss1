package com.example.studentmanagement.service;

import com.example.dto.StudentDTO;
import com.example.dto.StudentSearchDTO;
import com.example.studentmanagement.model.Student;

import java.util.List;

public interface IStudentService {
    List<StudentDTO> search(StudentSearchDTO studentSearchDTO);

    Student findById(int id);

    void create(Student student);
}
