package com.tcet.session;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/** Technique 2: Hidden form field - data is carried inside an invisible &lt;input&gt;. */
@WebServlet("/HiddenFieldServlet")
public class HiddenFieldServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Step 1: receive the username and emit a form that carries it as a hidden field.
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String username = HtmlUtil.nameOrGuest(request.getParameter("username"));

        StringBuilder body = new StringBuilder();
        body.append("<h2>Hidden Form Field</h2>");
        body.append("Username received: ").append(HtmlUtil.escape(username)).append("<br><br>");
        body.append("<form action='HiddenFieldServlet' method='post'>");
        body.append("<input type='hidden' name='username' value='")
            .append(HtmlUtil.escape(username)).append("'>");
        body.append("<input type='submit' value='Continue'>");
        body.append("</form>");

        out.println(HtmlUtil.page("Hidden Form Field", body.toString()));
    }

    // Step 2: the hidden value comes back with the POST.
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String username = HtmlUtil.nameOrGuest(request.getParameter("username"));

        String body = "<h2>Hidden Form Field</h2><h3>Welcome, " + HtmlUtil.escape(username) + "</h3>";
        out.println(HtmlUtil.page("Hidden Form Field", body));
    }
}
