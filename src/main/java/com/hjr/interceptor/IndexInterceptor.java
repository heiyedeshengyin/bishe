package com.hjr.interceptor;

import com.hjr.been.Admin;
import com.hjr.been.Student;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
public class IndexInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        Object student = session.getAttribute("student");
        if (student instanceof Student) {
            request.setAttribute("sessionFlag", "student");
            log.info("sessionFlag set student");
            return true;
        }

        Object admin = session.getAttribute("admin");
        if (admin instanceof Admin) {
            request.setAttribute("sessionFlag", "admin");
            log.info("sessionFlag set admin");
            return true;
        }

        request.setAttribute("sessionFlag", "none");
        log.info("sessionFlag set none");
        return true;
    }
}
