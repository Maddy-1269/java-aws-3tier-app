
package com.app;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class UserServlet extends HttpServlet {

    // Read DB config from environment variables (.env)
    private static final String DB_HOST = System.getenv("DB_HOST");
    private static final String DB_PORT = System.getenv("DB_PORT");
    private static final String DB_NAME = System.getenv("DB_NAME");
    private static final String DB_USER = System.getenv("DB_USER");
    private static final String DB_PASS = System.getenv("DB_PASSWORD");

    private static final String DB_URL =
            "jdbc:mysql://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME +
            "?useSSL=false&allowPublicKeyRetrieval=true";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws IOException {

        res.setContentType("text/plain");

        try {
            // Explicit driver loading (best practice in containers)
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
                 Statement st = con.createStatement();
                 ResultSet rs = st.executeQuery("SELECT name FROM users")) {

                StringBuilder output = new StringBuilder();
                while (rs.next()) {
                    output.append(rs.getString("name")).append("\n");
                }
                res.getWriter().write(output.toString());
            }

        } catch (Exception e) {
            res.getWriter().write("DB Error: " + e.getMessage());
        }
    }
}
