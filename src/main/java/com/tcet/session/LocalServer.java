package com.tcet.session;

import java.nio.file.Files;
import java.nio.file.Path;
import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;

/** Starts the web application without a separately installed servlet container. */
public final class LocalServer {
    private LocalServer() { }

    public static void main(String[] args) throws Exception {
        int port = Integer.parseInt(System.getProperty("app.port", "8080"));
        if (port < 1 || port > 65535) {
            throw new IllegalArgumentException("app.port must be between 1 and 65535");
        }
        Path project = Path.of(System.getProperty("app.directory", ".")).toAbsolutePath();
        Path base = project.resolve("target/tomcat");
        Files.createDirectories(base);
        Tomcat tomcat = new Tomcat();
        tomcat.setBaseDir(base.toString());
        tomcat.setPort(port);
        tomcat.getConnector().setProperty("address", "127.0.0.1");
        Context context = tomcat.addWebapp("/SessionHandling",
                project.resolve("src/main/webapp").toString());
        context.setParentClassLoader(LocalServer.class.getClassLoader());
        StandardRoot resources = new StandardRoot(context);
        resources.addPreResources(new DirResourceSet(resources, "/WEB-INF/classes",
                project.resolve("target/classes").toString(), "/"));
        context.setResources(resources);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                tomcat.stop();
                tomcat.destroy();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }));
        tomcat.start();
        if (!context.getState().isAvailable()) {
            throw new IllegalStateException("Web application failed to start; see Tomcat logs");
        }
        System.out.println("Open http://localhost:" + port + "/SessionHandling/");
        tomcat.getServer().await();
    }
}
