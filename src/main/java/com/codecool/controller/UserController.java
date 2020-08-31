package com.codecool;

import com.codecool.dao.StudentsDAO;
import com.codecool.model.Student;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class UserController implements HttpHandler {
    private StudentsDAO studentsDao;

    public UserController() {
        studentsDao = new StudentsDAO();
    }

    private void sendResponse(String response, HttpExchange exchange, int status) throws IOException {
        if (status == 200) {
            exchange.getResponseHeaders().put("Content-type", Collections.singletonList("application/json"));
            exchange.getResponseHeaders().put("Access-Control-Allow-Origin", Collections.singletonList("*"));
        }

        exchange.sendResponseHeaders(status, response.length());

        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String url = exchange.getRequestURI().getRawPath();
        String[] actions = url.split("/");
        String action = actions.length == 2 ? "" : actions[2].matches("\\d+") ? "details" : actions[2];
        ObjectMapper mapper = new ObjectMapper();
        String response = "";

        try {
            switch (action) {
                case "add":
                    //todo  add new user -> POST
                    break;
                case "details":
                    //np. http://localhost:8005/users/details/1
                    Student student = this.studentsDao.getStudent(Integer.parseInt(actions[3]));
                    response = mapper.writeValueAsString(student);
                    break;
                default:
                    //np. http://localhost:8005/users
                    List<Student> students = this.studentsDao.getStudents();
                    response = mapper.writeValueAsString(students);
            }
            sendResponse(response, exchange, 200);

        } catch (Exception error) {
            sendResponse(response, exchange, 404);
        }
    }
}
