package com.hjr.controller;

import com.hjr.been.College;
import com.hjr.service.CollegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CollegeController {

    @Autowired
    private CollegeService collegeService;

    @RequestMapping("/collegeinfo")
    public String collegeinfo(HttpServletRequest request, @RequestParam("id") Integer collegeId) {
        College college = collegeService.findCollegeById(collegeId);
        request.setAttribute("college", college);

        return "collegeinfo";
    }
}
