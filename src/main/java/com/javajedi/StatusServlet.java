package com.javajedi;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class StatusServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain");
        Runtime rt = Runtime.getRuntime();

        long total = rt.totalMemory();
        long free = rt.freeMemory();
        long used = total - free;

        resp.getWriter().write("Memory Status:\n");
        resp.getWriter().write("Total: " + (total / (1024 * 1024)) + " MB\n");
        resp.getWriter().write("Used: " + (used / (1024 * 1024)) + " MB\n");
    }
}