package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import ServerRequests.RustAnswer;
import ServerRequests.ServerEngineSingleton;
import ServerRequests.ServerQuestion;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;

@WebServlet("/search")
public class SearchServlet extends HttpServlet{

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String question = request.getParameter("q");
        answerJSON(question, response);
    }

    public static void answerJSON(String question, HttpServletResponse response) throws IOException {
        if(question == null) {
            System.out.println("question in null");
            return;
        }

        List<RustAnswer> answers = ServerEngineSingleton.getInstance().query(new ServerQuestion(question));

        System.out.println("Search list");
        System.out.println(answers);
        System.out.println("question - " + question);
        int size = answers.size();


        String json = "{\n";
        json += "\"answers\" : [\n";
        for(int i = 0; i < size; i++) {
            json += "{ \"question\": " + JSONObject.quote(answers.get(i).getQuestion().getValue()) + ", ";
            json += "\"questionId\": " + answers.get(i).getQuestion().getId() + ", ";
            json += "\"text\": " + JSONObject.quote(answers.get(i).getAnswer().getValue()) + ", ";
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
