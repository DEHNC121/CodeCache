package Servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import SQLEngine.SQLEngineSingleton;
import ServerRequests.RustAnswer;
import ServerRequests.ServerQuestion;
import ServerRequests.ServerAnswer;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.json.*;

@WebServlet("/add")
public class AddServlet extends HttpServlet {
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

        System.out.println(question);
        System.out.println(text);

        SQLEngineSingleton.getInstance().add(new ServerQuestion(question), new ServerAnswer(text));


        SearchServlet.sendJSON(response, question);
    }
}
