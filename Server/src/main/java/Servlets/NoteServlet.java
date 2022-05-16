package Servlets;

import java.io.IOException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/note*")
public class NoteServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

//        long unixTimeSeconds = System.currentTimeMillis() / 1000;
//
//        response.setContentType("text/html;");
//        response.getWriter().println(unixTimeSeconds);

        String json = "{\n";
        json += "\"answers\" : [{\n";
        for(int i = 0; i < 3; i++) {
            json += "\"text\": \"Our random number is " + Double.toString(Math.random()) + "\"\n";
            if(i < 2)
                json += "}, {\n";
            else
                json += "}]\n";
        }
        json += "}\n";

        response.setContentType("text/html;");
        response.getWriter().println(json);
    }
}
