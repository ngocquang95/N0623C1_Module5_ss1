package com.example.studentmanagement.repository.impl;

import com.example.studentmanagement.model.Clazz;
import com.example.studentmanagement.repository.IClazzRepository;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ClazzRepository implements IClazzRepository {
    @Override
    public List<Clazz> findAll() {
        // Bước 1: Mở phiên làm việc (Session) từ ConnectionUtil
        Session session = ConnectionUtil.sessionFactory.openSession();
        List<Clazz> clazzList = null;
        try {
            // HQL
            //My-SQL
            // Bước 2: Sử dụng HQL để lấy danh sách sinh viên
            clazzList = session.createQuery("FROM Clazz").getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close(); // Bước 3: Đóng phiên làm việc sau khi lấy danh sách xong
        }
        return clazzList;
    }
}
