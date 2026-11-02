package com.tcet.session;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/** Technique 4: HttpSession - data lives on the server, browser only holds the session id. */
@WebServlet("/session")
public class SessionServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Create the session and store the username.
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String username = HtmlUtil.nameOrGuest(request.getParameter("username"));

        HttpSession session = request.getSession();
        session.setAttribute("username", username);

        String body = "<h2>HttpSession</h2>"
                + "Session created successfully.<br>"
                + "Welcome, " + HtmlUtil.escape((String) session.getAttribute("username")) + "<br>"
                + "Session ID: " + HtmlUtil.escape(session.getId()) + "<br><br>"
                + "<a href='session'>Open again (new GET request)</a>";
        out.println(HtmlUtil.page("HttpSession", body));
    }

    // A later request: data is read back from the server-side session.
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession(false);   // don't create a new one
        String username = (session == null) ? null : (String) session.getAttribute("username");

        StringBuilder body = new StringBuilder("<h2>HttpSession</h2>");
        if (username != null) {
            body.append("Session still active across requests.<br>")
                .append("Welcome, ").append(HtmlUtil.escape(username)).append("<br>")
                .append("Session ID: ").append(HtmlUtil.escape(session.getId()));
        } else {
            body.append("No active session. Go to the home page and use the HttpSession button first.");
        }
        out.println(HtmlUtil.page("HttpSession", body.toString()));
    }
}
