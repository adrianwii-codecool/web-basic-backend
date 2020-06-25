package com.codecool;

import com.codecool.dao.StudentsDAO;
import com.codecool.model.Student;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class TemplateHandler implements HttpHandler {
    private StudentsDAO studentsDao;

    public TemplateHandler() {
        studentsDao = new StudentsDAO();
    }

    @Override
    public void handle(HttpExchange exchange) {

        try {
            List<Student> students = this.studentsDao.getStudents();
            JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/index.twig");

            JtwigModel model = JtwigModel.newModel();
            model.with("students", students);

            String response = template.render(model);
            exchange.sendResponseHeaders(200, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
