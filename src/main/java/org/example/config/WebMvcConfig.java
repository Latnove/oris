package org.example.config;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRegistration;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

//@Configuration
//@EnableWebMvc
//@ComponentScan(basePackages = {"org.example"})
public class WebMvcConfig {

    public void onStartup(ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(WebMvcConfig.class);

        servletContext.addListener(new ContextLoaderListener(rootContext));
        ServletRegistration.Dynamic dynamic = servletContext.addServlet("dispatcher", new DispatcherServlet(new GenericWebApplicationContext()));

        dynamic.addMapping("/");
        dynamic.setLoadOnStartup(1);
    }
}
