<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Session Handling</title>
  <style>
    body { font-family: Arial, sans-serif; margin: 40px; }
    button { margin: 4px 4px 4px 0; padding: 6px 12px; cursor: pointer; }
    .hint { color: #555; font-size: 0.9em; margin-top: 16px; }
  </style>
</head>
<body>
<h2>Session Handling using JSP/Servlet</h2>

<form method="post" action="session">
  Enter Username: <input type="text" name="username" required>
  <br><br>

  <!-- Each button overrides the form's action/method so one username box drives all 4 demos -->
  <button type="submit" formaction="URLRewriteServlet" formmethod="get">1. URL Rewriting</button>
  <button type="submit" formaction="HiddenFieldServlet" formmethod="get">2. Hidden Form Field</button>
  <button type="submit" formaction="CookieServlet" formmethod="get">3. Cookies</button>
  <button type="submit" formaction="session" formmethod="post">4. HttpSession</button>
</form>

<p class="hint">Context path: <%= request.getContextPath() %></p>
</body>
</html>
