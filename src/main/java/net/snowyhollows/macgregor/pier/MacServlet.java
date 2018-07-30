package net.snowyhollows.macgregor.pier;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.snowyhollows.mcgregor.Event;
import net.snowyhollows.mcgregor.tag.Component;
import net.snowyhollows.mcgregor.tag.GenericTag;
import net.snowyhollows.mcgregor.tag.helper.WriterHtmlWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class MacServlet<T extends Component> extends HttpServlet {

    private static final GsonBuilder gsonBuilder = new GsonBuilder();

    public abstract T createComponent();

    private T getComponent(HttpServletRequest req) {
        if (req.getSession().getAttribute(req.getServletPath()) == null) {
            req.getSession().setAttribute(req.getServletPath(), createComponent());
        }
        return (T)req.getSession().getAttribute(req.getServletPath());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        T component = getComponent(req);
        render(resp, component);
    }

    private void render(HttpServletResponse resp, T component) throws IOException {
        resp.setContentType("text/html;charset=UTF-8");
        component.render(new WriterHtmlWriter(resp.getWriter()));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = gsonBuilder.create();
        MacEvent macEvent = gson.fromJson(req.getReader(), MacEvent.class);

        T component = getComponent(req);
        Event e = new Event(macEvent.source, macEvent.value);
        component.visit(c -> {
            if (e.sourceKey.equals(c.getKey())) {
                switch (macEvent.type) {
                    case CLICK: ((GenericTag)c).getOnclick().onEvent(e); break;
                    case CHANGE: ((GenericTag)c).getOnchange().onEvent(e); break;
                }
            }
        });
        render(resp, component);
    }
}
