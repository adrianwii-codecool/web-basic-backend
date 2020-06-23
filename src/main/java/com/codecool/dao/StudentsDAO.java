package com.codecool.dao;

import com.codecool.model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentsDAO {

    public List<Student> getStudents() throws Exception {
        List<Student> students = new ArrayList<>();
        Connector connector = new Connector();
        Connection connection = connector.Connect();

        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM public.students;");
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                String email = rs.getString("email");

                Student student = new Student();
                student.setId(id)
                        .setName(name)
                        .setSurname(surname)
                        .setEmail(email);
                students.add(student);
            }
            rs.close();
            statement.close();
            connection.close();

            return students;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        throw new Exception("Data not found");
    }

    public void setStudent(Student student) throws ClassNotFoundException, SQLException {
        Connector connector = new Connector();
        Connection connection = connector.Connect();
        String query = "INSERT INTO students(name, surname, email) VALUES(?, ?, ?)";
        PreparedStatement pst = connection.prepareStatement(query);
        pst.setString(1, student.getName());
        pst.setString(2, student.getSurname());
        pst.setString(3, student.getEmail());
        pst.executeUpdate();
    }
}
