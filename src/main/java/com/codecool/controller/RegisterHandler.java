package com.codecool.controller;

import com.codecool.dao.StudentsDAO;
import com.codecool.helpers.Parser;
import com.codecool.model.Student;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import javax.crypto.SecretKeyFactory;
import java.io.*;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.*;

public class RegisterHandler implements HttpHandler {
    private StudentsDAO studentsDao;

    public RegisterHandler() {
        studentsDao = new StudentsDAO();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        String response = "data not saved";
        String method = exchange.getRequestMethod(); // "POST", "GET"

        if(method.equals("POST")) {
            // retrieve data
            InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), "utf-8");
            BufferedReader br = new BufferedReader(isr);

            Map<String, String> data = Parser.parseFormData(br.readLine());

            Student student = new Student();
            student.setName(data.get("name"))
                    .setSurname(data.get("surname"))
                    .setEmail(data.get("email"));

            try {
                studentsDao.setStudent(student);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            response = "data saved";
        }

        exchange.sendResponseHeaders(200, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
