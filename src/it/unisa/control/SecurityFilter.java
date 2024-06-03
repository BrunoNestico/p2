package it.unisa.control;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class SecurityFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
        // Inizializzazione del filtro
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String page = httpRequest.getParameter("page");
        
        if (page != null) {
            if (page.contains("META-INF/context.xml") || page.contains("WEB-INF/web.xml")) {
                httpResponse.sendRedirect("errorPage.jsp");
                return;
            } else {
                // Sanitize the page parameter
                page = page.replace("..", "").replace("/", "").replace("\\", "");
                
                httpRequest.setAttribute("sanitizedPage", page);
            }
        } else {
            httpRequest.setAttribute("sanitizedPage", "defaultPage");
        }

        chain.doFilter(request, response);
    }

    public void destroy() {
        // Pulizia del filtro
    	
    }
}