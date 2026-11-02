package com.tcet.session;

/** Small helpers shared by the servlets. */
final class HtmlUtil {

    private HtmlUtil() { }

    /** Escapes user input before printing it into HTML (prevents XSS). */
    static String escape(String s) {
        if (s == null) {
            return "";
        }
        return s.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#x27;");
    }

    /** Returns the username, or "Guest" if it is missing/blank. */
    static String nameOrGuest(String s) {
        return (s == null || s.isBlank()) ? "Guest" : s.trim();
    }

    /** Wraps body HTML in a minimal page. */
    static String page(String title, String body) {
        return "<!DOCTYPE html><html><head><meta charset='UTF-8'><title>" + escape(title)
                + "</title></head><body style='font-family:Arial,sans-serif;margin:40px'>"
                + body
                + "<br><br><a href='index.jsp'>&larr; Back to home</a>"
                + "</body></html>";
    }
}
