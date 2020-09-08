package com.codecool.controller;

import com.codecool.helpers.Parser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpCookie;
import java.util.Collections;
import java.util.Map;

public class LoginHandler implements HttpHandler {

    private void sendResponse(String response, HttpExchange exchange, int status) throws IOException {
        if (status == 200) {
            exchange.getResponseHeaders().put("Content-type", Collections.singletonList("application/json"));
        }
        exchange.getResponseHeaders().put("Access-Control-Allow-Origin", Collections.singletonList("*"));
        exchange.sendResponseHeaders(status, response.length());

        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String response = "";
        try {
            InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), "utf-8");
            BufferedReader br = new BufferedReader(isr);

            Map<String, String> data = Parser.parseFormData(br.readLine());
            String email = data.get("email");
            String password = data.get("password");

            //  TODO here we need to check if user with given password and email exist in database
            //  In cookie we should set json with user token and role instead of email
            HttpCookie cookie = new HttpCookie("user", email);
            exchange.getResponseHeaders().add("Set-Cookie", cookie.toString());

            response = "User authenticated";
            sendResponse(response, exchange, 200);
        } catch (Exception e) {
            e.printStackTrace();
            response = e.getMessage();
            sendResponse(response, exchange, 500);
        }
    }
}
