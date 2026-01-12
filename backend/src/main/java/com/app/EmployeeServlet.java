package com.app;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class EmployeeServlet extends HttpServlet {

    private static final String DB_HOST = System.getenv("DB_HOST");
    private static final String DB_PORT = System.getenv("DB_PORT");
    private static final String DB_NAME = System.getenv("DB_NAME");
    private static final String DB_USER = System.getenv("DB_USER");
    private static final String DB_PASS = System.getenv("DB_PASSWORD");

    private static final String DB_URL =
        "jdbc:mysql://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME +
        "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

    // GET all employees
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws IOException {

        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");

        try {
            // Ensure MySQL driver is loaded
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
                 Statement st = con.createStatement();
                 ResultSet rs = st.executeQuery("SELECT * FROM employees")) {

                StringBuilder json = new StringBuilder("[");
                while (rs.next()) {
                    json.append("{")
                        .append("\"id\":").append(rs.getInt("id")).append(",")
                        .append("\"name\":\"").append(rs.getString("name")).append("\",")
                        .append("\"email\":\"").append(rs.getString("email")).append("\",")
                        .append("\"department\":\"").append(rs.getString("department")).append("\"")
                        .append("},");
                }

                if (json.length() > 1 && json.charAt(json.length() - 1) == ',') {
                    json.deleteCharAt(json.length() - 1);
                }
                json.append("]");

                res.getWriter().write(json.toString());
            }

        } catch (Exception e) {
            res.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    // POST add employee
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException {

        res.setContentType("text/plain");
        res.setCharacterEncoding("UTF-8");

        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String dept = req.getParameter("department");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
                 PreparedStatement ps = con.prepareStatement(
                     "INSERT INTO employees(name,email,department) VALUES (?,?,?)")) {

                ps.setString(1, name);
                ps.setString(2, email);
                ps.setString(3, dept);
                ps.executeUpdate();

                res.getWriter().write("Employee added successfully");
            }

        } catch (Exception e) {
            res.getWriter().write("DB Error: " + e.getMessage());
        }
    }
}
