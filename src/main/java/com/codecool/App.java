package com.codecool;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class App {
    public static void main(String[] args) throws IOException {

        HttpServer server = HttpServer.create(new InetSocketAddress(8005), 0);

        server.createContext("/students", new StudentHandler());
        server.createContext("/mentors", new MentorHandler());
        server.createContext("/template", new TemplateHandler());
        server.createContext("/register", new RegisterHandler());
        server.setExecutor(null);
        server.start();

        System.out.println("Server has started on port " + server.getAddress().getPort());
    }
}
