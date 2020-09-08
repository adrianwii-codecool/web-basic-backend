package com.codecool;

import com.codecool.controller.LoginHandler;
import com.codecool.controller.RegisterHandler;
import com.codecool.controller.TemplateHandler;
import com.codecool.controller.UserController;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class App {
    public static void main(String[] args) throws IOException {

        HttpServer server = HttpServer.create(new InetSocketAddress(8005), 0);

        server.createContext("/template", new TemplateHandler());
        server.createContext("/register", new RegisterHandler());
        server.createContext("/login", new LoginHandler());
        server.createContext("/users", new UserController());
        server.setExecutor(null);
        server.start();

        System.out.println("Server has started on port " + server.getAddress().getPort());
    }
}
