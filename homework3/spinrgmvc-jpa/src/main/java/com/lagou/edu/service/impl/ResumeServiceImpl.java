package com.lagou.edu.service.impl;

import com.lagou.edu.dao.ResumeDao;
import com.lagou.edu.pojo.Resume;
import com.lagou.edu.service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("resumeService")
public class ResumeServiceImpl implements ResumeService {
    @Autowired
    private ResumeDao resumeDao;


    @Override
    public List<Resume> queryAll() {
        return resumeDao.findAll();
    }

    @Override
    public Resume saveResume(Resume resume) {
        return resumeDao.save(resume);
    }

    @Override
    public void deleteResume(long id) {
        resumeDao.deleteById(id);
    }
}
