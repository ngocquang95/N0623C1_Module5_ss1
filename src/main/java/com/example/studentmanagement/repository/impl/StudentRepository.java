package com.example.studentmanagement.repository.impl;

import com.example.studentmanagement.dto.StudentDTO;
import com.example.studentmanagement.dto.StudentSearchDTO;
import com.example.studentmanagement.model.Clazz;
import com.example.studentmanagement.model.Student;
import com.example.studentmanagement.repository.IStudentRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class StudentRepository implements IStudentRepository {

    @Override
    public List<Student> search(StudentSearchDTO studentSearchDTO) {
//            // Tìm kiếm theo điểm
        String scoreQuery = "";
            if (studentSearchDTO.getFromScore() != null || studentSearchDTO.getToScore() != null) {
                if (studentSearchDTO.getFromScore() == null) { // Không nhập from => Nhập to
                    scoreQuery = String.format("and s.score <= %s ", studentSearchDTO.getToScore());
                } else if (studentSearchDTO.getToScore() == null) {// Không nhập to => Nhập from
                    scoreQuery = String.format("and s.score >= %s ", studentSearchDTO.getFromScore());
                } else { // Nhập cả 2
                    scoreQuery = String.format(" and (s.score between %s and %s) ", studentSearchDTO.getFromScore(), studentSearchDTO.getToScore());
                }
            }
           // Tìm kiếm theo lớp
        String classIdQuery = "";
        if (studentSearchDTO.getClazzId() != null && !"" .equals(studentSearchDTO.getClazzId())) {
            classIdQuery = String.format("and s.clazz.id = %s ", studentSearchDTO.getClazzId());
        }

        // Bước 1: Mở phiên làm việc (Session) từ ConnectionUtil
        Session session = ConnectionUtil.sessionFactory.openSession();
        List<Student> studentList = null;
        try {
            // HQL
            //My-SQL
            // Bước 2: Sử dụng HQL để lấy danh sách sinh viên
            studentList = session.createQuery("FROM Student s where" +
                            " s.name like concat('%', :namexxx, '%') " + classIdQuery + scoreQuery)
                    .setParameter("namexxx", studentSearchDTO.getName())
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close(); // Bước 3: Đóng phiên làm việc sau khi lấy danh sách xong
        }
        return studentList;
    }

    @Override
    public Student findById(int id) {
//        try {
//            PreparedStatement preparedStatement = BaseRepository.getConnection().prepareStatement(
//                    "select id, name, score from student where id = ?"
//            );
//
//            preparedStatement.setInt(1, id);
//            ResultSet resultSet = preparedStatement.executeQuery();
//
//            Student student;
//            if (resultSet.next()) {
//                student = new Student();
//                student.setId(resultSet.getInt("id"));
//                student.setName(resultSet.getString("name"));
//                student.setScore(resultSet.getDouble("score"));
//
//                return student;
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        return null;
    }

    @Override
    public void create(Student student) {
        Session session = ConnectionUtil.sessionFactory.openSession(); // Bước 1: Mở phiên làm việc (Session)
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction(); // Bước 2: Bắt đầu giao dịch (Transaction)

            session.save(student); // Bước 3: Lưu đối tượng sinh viên vào cơ sở dữ liệu

            transaction.commit(); // Bước 4: Xác nhận giao dịch nếu thành công
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback(); // Bước 5: Lưu ý nếu có lỗi, giao dịch bị quay lại (rollback)
            }
            e.printStackTrace();
        } finally {
            session.close(); // Bước 6: Đóng phiên làm việc khi hoàn tất công việc
        }
    }
}
