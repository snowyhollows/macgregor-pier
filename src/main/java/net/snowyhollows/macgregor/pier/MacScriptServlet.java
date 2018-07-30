package net.snowyhollows.macgregor.pier;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStreamReader;

@WebServlet(urlPatterns = "/macscript.js")
public class MacScriptServlet extends HttpServlet {

    private static String script;

    {
        try {
            char[] scriptChars = new char[8192];
            InputStreamReader isr = new InputStreamReader(MacScriptServlet.class.getResourceAsStream("/macscript.js"));
            int length = isr.read(scriptChars);
            script = new String(scriptChars, 0, length);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().append(script);
    }
}
