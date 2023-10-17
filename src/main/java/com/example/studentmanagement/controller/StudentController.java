package com.example.studentmanagement.controller;

import com.example.dto.StudentCreateDTO;
import com.example.dto.StudentSearchDTO;
import com.example.studentmanagement.model.Student;
import com.example.studentmanagement.service.IClazzService;
import com.example.studentmanagement.service.IStudentService;
import com.example.studentmanagement.service.impl.ClazzService;
import com.example.studentmanagement.service.impl.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/student")
public class StudentController {
    private IStudentService studentService = new StudentService();
    private IClazzService clazzService = new ClazzService();

    @GetMapping("/create")
    public String showCreate(Model model) {
        model.addAttribute("clazzList", clazzService.findAll());
        return "student/create";
    }

    @GetMapping("/edit")
    public String showEdit(Model model, int id) {
        Student student = studentService.findById(id);

        model.addAttribute("student", student);
        return "student/edit";
    }

    @GetMapping("")
    public String showList(Model model,  StudentSearchDTO studentSearchDTO) {
        model.addAttribute("clazzList", clazzService.findAll());
        model.addAttribute("studentList", studentService.search(studentSearchDTO));
        return "student/list";
    }

    @PostMapping("/create")
    public String create(Model model, StudentCreateDTO studentCreateDTO, RedirectAttributes redirectAttributes) {
        Map<String, String> messageError = new HashMap<>();
        studentService.validate(studentCreateDTO, messageError);

        if (!messageError.isEmpty()) {
            // Trường hợp bị lỗi thì gửi lại clazzList
            model.addAttribute("clazzList", clazzService.findAll());
            model.addAttribute("studentCreateDTO", studentCreateDTO);
            model.addAttribute("messageError", messageError);
            return "student/create";
        }

        Student student = new Student();
        student.setName(studentCreateDTO.getName());
        student.setScore(Double.parseDouble(studentCreateDTO.getScore()));
        student.setClazzId(Integer.parseInt(studentCreateDTO.getClazzId()));

        studentService.create(student);
        redirectAttributes.addFlashAttribute("message", "Thêm mới thành công");
        return "redirect:/student";
    }

//    private void editStudent(HttpServletRequest request, HttpServletResponse response) {
//        int id = Integer.parseInt(request.getParameter("id"));
//        Student student = studentService.findById(id);
//        student.setName(request.getParameter("name"));
//        student.setScore(Double.parseDouble(request.getParameter("score")));
//    }
}
