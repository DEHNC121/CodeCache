package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import SQLEngine.SQLEngineSingleton;
import ServerRequests.RustAnswer;
import ServerRequests.ServerQuestion;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/search")
public class SearchServlet extends HttpServlet{

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String question = request.getParameter("q");
        sendJSON(response, question);
    }

    static void sendJSON(HttpServletResponse response, String question) throws IOException {
        List<RustAnswer> answers = SQLEngineSingleton.getInstance().query(new ServerQuestion(question));
        int size = answers.size();


        String json = "{\n";
        json += "\"answers\" : [\n";
        for(int i = 0; i < size; i++) {
            json += "{ \"question\": \"" + answers.get(i).getQuestion().getValue() + "\", ";
            json += "\"questionId\": " + answers.get(i).getQuestion().getId() + ", ";
            json += "\"text\": \"" + answers.get(i).getAnswer().getValue() + "\", ";
            json += "\"textId\": " + answers.get(i).getAnswer().getId() + " }";
            if(i < size-1)
                json += ",\n";
            else
                json += "\n";
        }
        json += "]\n";
        json += "}\n";

        PrintWriter out = response.getWriter();
        out.println(json);
    }
}
