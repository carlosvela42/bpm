package co.jp.nej.earth.config;

import co.jp.nej.earth.util.LoginUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*")
public class RequestLoggingFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(RequestLoggingFilter.class);
    private static final int customSessionExpiredErrorCode = 901;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession();
        String loginURI = request.getContextPath() + "/login";// imageDisplay login
        String requestUri = request.getRequestURI();

        if (LoginUtil.isLogin(session) || requestUri.equals(loginURI) || requestUri.contains("/resources/")
            || requestUri.contains("/WS/") || requestUri.contains("/imageviewer/")) {
            chain.doFilter(request, response);
        } else {
            logger.info("User session expired or not logged in yet");
            String ajaxHeader = ((HttpServletRequest) request).getHeader("X-Requested-With");

            if ("XMLHttpRequest".equals(ajaxHeader)) {
                logger.info("Ajax call detected, send {} error code", this.customSessionExpiredErrorCode);
                HttpServletResponse resp = (HttpServletResponse) response;
                resp.sendError(this.customSessionExpiredErrorCode);
            } else {
                response.sendRedirect(loginURI);
            }
        }
    }

    @Override
    public void destroy() {
    }

}
