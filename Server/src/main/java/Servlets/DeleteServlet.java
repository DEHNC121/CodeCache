package Servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;


import ServerRequests.RustAnswer;
import ServerRequests.ServerEngineSingleton;
import ServerRequests.ServerQuestion;
import ServerRequests.ServerAnswer;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.json.*;

@WebServlet("/delete")
public class DeleteServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        StringBuffer jb = new StringBuffer();
        String line = null;
        try {
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null)
                jb.append(line);
        } catch (Exception e) { /*report an error*/ }

        JSONObject jsonObject = null;
        try {
//            jsonObject =  HTTP.toJSONObject(jb.toString());
            jsonObject =  new JSONObject(jb.toString());
        } catch (JSONException e) {
            // crash and burn
            throw new IOException("Error parsing JSON request string");
        }

        System.out.println(jb.toString());
        System.out.println(jsonObject.toString());
        String question = jsonObject.getString("question");
        String text = jsonObject.getString("text");
        Long questionId = jsonObject.getLong("questionId");
        Long textId = jsonObject.getLong("textId");

        System.out.println(question);
        System.out.println(questionId);
        System.out.println(text);
        System.out.println(textId);


        ServerEngineSingleton.getInstance()
                .remove(new ServerQuestion(question, questionId), new ServerAnswer(text, textId));


        List<RustAnswer> answers = ServerEngineSingleton.getInstance().query(new ServerQuestion(question));
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