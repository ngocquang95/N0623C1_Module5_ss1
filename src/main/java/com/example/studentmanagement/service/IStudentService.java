package com.example.studentmanagement.service;

import com.example.dto.StudentCreateDTO;
import com.example.dto.StudentDTO;
import com.example.dto.StudentSearchDTO;
import com.example.studentmanagement.model.Student;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface IStudentService {
    List<StudentDTO> search(StudentSearchDTO studentSearchDTO);

    Student findById(int id);

    void create(Student student);

    void validate(StudentCreateDTO studentCreateDTO, Map<String, String> messageError);
}
