package framework.servlet;

import java.io.*;
import java.util.List;

import main.java.utils.Utils;
import framework.UrlMethod;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import annotation.ControllerAnnotation;

public class FrontControllerServlet extends HttpServlet {
    private List<UrlMethod> mappings;
    private List<String> controllers;
    
    protected void processRequest(HttpServletRequest req , HttpServletResponse res) throws ServletException, IOException{
        PrintWriter out = res.getWriter();
        String path = req.getRequestURI().substring(req.getContextPath().length());

        out.println("URL: " + req.getRequestURL());
        out.println("URI: " + req.getRequestURI());
        out.println("Context path: " + req.getContextPath());
        out.println("Path: " + path);
        out.println("");
        out.println("=== Controllers annotes ===");
        for (String controller : controllers) {
            out.println(controller);
        }
        out.println("");

        UrlMethod found = null;
        for (UrlMethod um : mappings) {
            if (um.getUrl().equals(path)) {
                found = um;
                break;
            }
        }

        if (found != null) {
            out.println("=> Lien trouve !");
            out.println(found.getUrl() + " : dans Controller (" + found.getControllerClass().getSimpleName() + ") de " + found.getMethod().getName() + "() no methode associe aminy");
        } else {
            out.println("=> Le lien \"" + path + "\" n'est pas supporte.");
        }

        out.println("");
        out.println("=== Liens supportes ===");
        for (UrlMethod um : mappings) {
            out.println(um.getUrl() + " : dans Controller (" + um.getControllerClass().getSimpleName() + ") de " + um.getMethod().getName() + "()");
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
            String packageName = getServletContext().getInitParameter("controller-package");

            controllers = Utils.scanAnnotation(packageName, ControllerAnnotation.class);
            System.out.println("=== Controllers trouves ===");
            for (String controller : controllers) {
                System.out.println(controller);
            }

            mappings = Utils.scanUrlMappings(packageName, ControllerAnnotation.class);
            System.out.println("=== Mappings trouves ===");
            for (UrlMethod um : mappings) {
                System.out.println(um.getUrl() + " -> " + um.getControllerClass().getSimpleName() + "." + um.getMethod().getName() + "()");
            }

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}