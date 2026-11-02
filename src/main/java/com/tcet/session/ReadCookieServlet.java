package com.tcet.session;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/** Technique 3 (read side): read the username cookie on a later request. */
@WebServlet("/ReadCookieServlet")
public class ReadCookieServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String username = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if ("username".equals(c.getName())) {
                    username = URLDecoder.decode(c.getValue(), StandardCharsets.UTF_8);
                    break;
                }
            }
        }

        StringBuilder body = new StringBuilder("<h2>Read Cookie</h2>");
        if (username != null) {
            body.append("Welcome back, ").append(HtmlUtil.escape(username))
                .append("<br>(value read from the browser's cookie)");
        } else {
            body.append("No 'username' cookie found. Go to the home page and use the Cookies button first.");
        }
        out.println(HtmlUtil.page("Read Cookie", body.toString()));
    }
}
