package framework.servlet;

import java.io.*;
import java.io.IOException;
import java.util.List;

import main.java.utils.Utils;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import annotation.ControllerAnnotation;

public class FrontControllerServlet extends HttpServlet {
    private List<String> controllers;
    protected void processRequest(HttpServletRequest req , HttpServletResponse res) throws ServletException, IOException{
        res.getWriter().println("URL: " + req.getRequestURL());
        res.getWriter().println("URI: " + req.getRequestURI());
        res.getWriter().println("Context path: " + req.getContextPath());
        res.getWriter().println("Path :" + req.getRequestURI().substring(req.getContextPath().length()));
        res.getWriter().println("");
        res.getWriter().println("=== Controllers annotés ===");
        for (String controller : controllers) {
            res.getWriter().println(controller);
        }
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
        processRequest(req, res);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
        processRequest(req, res);
    }

    public void init() throws ServletException {

        try {
            String packageName =getServletContext().getInitParameter("controller-package");
            controllers = Utils.scanAnnotation(packageName, ControllerAnnotation.class);

            System.out.println("=== Controllers trouvés ===");

            for (String controller : controllers) {
                System.out.println(controller);
            }

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}