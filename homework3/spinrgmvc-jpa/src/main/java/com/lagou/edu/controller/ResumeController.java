package com.lagou.edu.controller;

import com.lagou.edu.pojo.Resume;
import com.lagou.edu.service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/resume")
public class ResumeController {
    @Autowired
    private ResumeService resumeService;

    @RequestMapping("/queryAll")
    public @ResponseBody
    List<Resume> queryAll() {
        List<Resume> list = resumeService.queryAll();
        return list;
    }

    @RequestMapping("/save")
    public @ResponseBody Boolean saveResume(@RequestBody Resume resume) {
        if(resumeService.saveResume(resume)==null){
            return false;
        }
        return true;
    }

    @RequestMapping("/delete")
    public @ResponseBody Boolean deleteResume(@RequestBody Resume resume) {
        resumeService.deleteResume(resume.getId());
        return true;
    }
}
