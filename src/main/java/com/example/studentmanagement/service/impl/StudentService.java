package com.example.studentmanagement.service.impl;

import com.example.dto.StudentCreateDTO;
import com.example.dto.StudentDTO;
import com.example.dto.StudentSearchDTO;
import com.example.studentmanagement.model.Student;
import com.example.studentmanagement.repository.IStudentRepository;
import com.example.studentmanagement.repository.impl.StudentRepository;
import com.example.studentmanagement.service.IStudentService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public class StudentService implements IStudentService {
    private IStudentRepository studentRepository = new StudentRepository();

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

    @Override
    public void validate(StudentCreateDTO studentCreateDTO, Map<String, String> messageError) {
        if(studentCreateDTO.getName().trim().equals("")) {
            messageError.put("name", "Tên bắt buộc nhập");
        } else if(!studentCreateDTO.getName().matches("[a-zA-ZÀ-ỹ\\s]+")) {
            messageError.put("name", "Tên chỉ chứa khoảng cách hoặc chữ cái");
        }

        if(studentCreateDTO.getScore().trim().equals("")) {
            messageError.put("score", "Điểm bắt buộc nhập");
        }

        if(studentCreateDTO.getClazzId().trim().equals("")) {
            messageError.put("clazzId", "Bắt buộc chọn lớp");
        }
    }
}
