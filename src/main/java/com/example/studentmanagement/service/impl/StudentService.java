package com.example.studentmanagement.service.impl;

import com.example.studentmanagement.dto.StudentDTO;
import com.example.studentmanagement.dto.StudentSearchDTO;
import com.example.studentmanagement.model.Student;
import com.example.studentmanagement.repository.IStudentRepository;
import com.example.studentmanagement.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("studentService")
public class StudentService implements IStudentService {
    @Autowired
    private IStudentRepository studentRepository;

    @Override
    public List<StudentDTO> search(StudentSearchDTO studentSearchDTO) {
        // Tránh trường hợp người dùng đi vào màn hình list (Chưa search)
        if(studentSearchDTO.getName() == null) {
            studentSearchDTO.setName("");
        }

        if("".equals(studentSearchDTO.getFromScore())) {
            studentSearchDTO.setFromScore(null);
        }

        if("".equals(studentSearchDTO.getToScore())) {
            studentSearchDTO.setToScore(null);
        }

        return studentRepository.search(studentSearchDTO);
    }

    @Override
    public Student findById(int id) {
        return studentRepository.findById(id);
    }

    @Override
    public void create(Student student) {
        studentRepository.create(student);
    }
}
