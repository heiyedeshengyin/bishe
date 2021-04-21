package com.hjr.interceptor;

import com.hjr.been.Admin;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
public class HasAdminSessionInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        Object admin = session.getAttribute("admin");

        if (admin instanceof Admin) {
            log.info("Admin Session Found! Handler: " + handler.toString());
            return true;
        }

        log.info("Session Not Found! Handler: " + handler.toString());
        response.sendRedirect(request.getContextPath());
        return false;
    }
}
