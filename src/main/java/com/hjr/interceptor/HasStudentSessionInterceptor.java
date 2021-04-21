package com.hjr.interceptor;

import com.hjr.been.Student;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
public class HasStudentSessionInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        Object student = session.getAttribute("student");

        if (student instanceof Student) {
            log.info("Student Session Found! Handler: " + handler.toString());
            return true;
        }

        log.info("Student Session Not Found! Handler: " + handler.toString());
        response.sendRedirect(request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort());
        return false;
    }
}
