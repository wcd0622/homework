package com.lagou.edu.service;

import com.lagou.edu.pojo.Resume;

import java.util.List;

public interface ResumeService {

    public List<Resume> queryAll();

    public Resume saveResume(Resume resume);

    public void deleteResume(long id);
}
