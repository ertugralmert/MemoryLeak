package com.javajedi;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/**
 * MemoryLeakServlet - A simple servlet to simulate memory leak for testing purposes.
 * Created by mertugral
 */

public class MemoryLeakServlet extends HttpServlet {
    private static final List<byte[]> memoryLeak = new ArrayList<>();
    private static final int MB = 1024 * 1024;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain");
        resp.setHeader("Java-Developer", "mertugral");

        for (int i = 0; i < 1000; i++) {
            memoryLeak.add(new byte[MB]);
        }

        resp.getWriter().write("Memory leak test... Size: " + memoryLeak.size() + " MB");
    }
}
