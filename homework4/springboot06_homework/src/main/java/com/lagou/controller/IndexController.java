package com.lagou.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lagou.mapper.ArticleMapper;
import com.lagou.pojo.Article;
import org.hibernate.validator.internal.util.privilegedactions.GetMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class IndexController {
    @Autowired
   private ArticleMapper articleMapper;



   /*@RequestMapping("/test")
    public String list(Model model,HttpServletRequest request,
                       HttpServletResponse response,
                       @RequestParam(defaultValue = "1") Integer pageNum,
                       @RequestParam(defaultValue = "5") Integer pageSize){
        PageHelper.startPage(pageNum, pageSize);

        List<Article> articles = articleMapper.queryAll();
        PageInfo<Article> pageInfo = new PageInfo<>(articles);

        //获得当前页
        request.setAttribute("pageNum", pageInfo.getPageNum());
        //获得一页显示的条数
        request.setAttribute("pageSize", pageInfo.getPageSize());
        //是否是第一页
        request.setAttribute("isFirstPage", pageInfo.isIsFirstPage());
        //获得总页数
        request.setAttribute("totalPages", pageInfo.getPages());
        //是否是最后一页
        request.setAttribute("isLastPage", pageInfo.isIsLastPage());
//所有导航页号
        request.setAttribute("naviPageNums", pageInfo.getNavigatepageNums());

        request.setAttribute("articles", articles);

        return "client/index";
    }*/

    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public String Pages(Model model, @RequestParam(required=true,defaultValue="1")Integer page){
        //pageNum为页面数pageSize为数据条数
        PageHelper.startPage (page,3);
        List<Article> articles =  articleMapper.queryAll();
        model.addAttribute("page", page);
        PageInfo<Article> pageInfo = new PageInfo<>(articles);
        model.addAttribute("articles", articles);
        model.addAttribute("pageInfo", pageInfo);
        return "client/index";
    }






}
