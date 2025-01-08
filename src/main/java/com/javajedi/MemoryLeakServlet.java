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
@WebServlet("/memoryleak")
public class MemoryLeakServlet extends HttpServlet {
    private static final List<byte[]> memoryLeak = new ArrayList<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        resp.setHeader("Java-Developer", "mertugral");
        for (int i = 0; i < 1000; i++)
        {
            memoryLeak.add(new byte[1024*10024]); // döngüde 1MB ekliyorum
        }
        resp.getWriter().write("Memory leak test ongoing... Current size: " + memoryLeak.size() + " MB");
    }


}
