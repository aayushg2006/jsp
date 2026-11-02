package com.tcet.session;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/** Technique 1: URL Rewriting - data travels in the URL query string. */
@WebServlet("/URLRewriteServlet")
public class URLRewriteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        // Make sure a session exists so encodeURL() has a session id to append
        // (Tomcat only appends ;jsessionid=... until it knows the browser accepts cookies).
        request.getSession(true);

        String username = HtmlUtil.nameOrGuest(request.getParameter("username"));

        // Encode the value for use inside a URL, then let the container add the session id.
        String encoded = URLEncoder.encode(username, StandardCharsets.UTF_8);
        String url = response.encodeURL("URLRewriteServlet?username=" + encoded);

        StringBuilder body = new StringBuilder();
        body.append("<h2>URL Rewriting</h2>");
        body.append("Welcome, ").append(HtmlUtil.escape(username)).append("<br>");
        body.append("Generated link: ")
            .append(HtmlUtil.escape(request.getContextPath() + "/" + url)).append("<br><br>");
        body.append("<a href=\"").append(HtmlUtil.escape(url)).append("\">Continue</a>");

        out.println(HtmlUtil.page("URL Rewriting", body.toString()));
    }
}
