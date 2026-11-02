package com.tcet.session;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/** Technique 3 (write side): store the username in a browser cookie. */
@WebServlet("/CookieServlet")
public class CookieServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String username = HtmlUtil.nameOrGuest(request.getParameter("username"));

        // Cookie values can't contain spaces/special chars, so URL-encode them.
        Cookie cookie = new Cookie("username", URLEncoder.encode(username, StandardCharsets.UTF_8));
        cookie.setMaxAge(60 * 60);          // keep for 1 hour
        cookie.setHttpOnly(true);           // not readable from JavaScript
        String ctx = request.getContextPath();
        cookie.setPath(ctx.isEmpty() ? "/" : ctx);
        response.addCookie(cookie);

        String body = "<h2>Cookie Session Handling</h2>"
                + "Cookie created successfully.<br>"
                + "<a href='ReadCookieServlet'>Read Cookie</a>";
        out.println(HtmlUtil.page("Cookie Session Handling", body));
    }
}
