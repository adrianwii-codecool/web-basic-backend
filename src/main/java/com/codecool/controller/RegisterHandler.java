package com.codecool;

import com.codecool.dao.StudentsDAO;
import com.codecool.model.Student;
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

            Map<String, String> data = parseFormData(br.readLine());

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

    private static Map<String, String> parseFormData(String formData) throws UnsupportedEncodingException {
        Map<String, String> map = new HashMap<>();
        String[] pairs = formData.split("&");
        for(String pair : pairs){
            String[] keyValue = pair.split("=");
            // We have to decode the value because it's urlencoded. see: https://en.wikipedia.org/wiki/POST_(HTTP)#Use_for_submitting_web_forms
            String value = new URLDecoder().decode(keyValue[1], "UTF-8");
            map.put(keyValue[0], value);
        }
        return map;
    }
}
