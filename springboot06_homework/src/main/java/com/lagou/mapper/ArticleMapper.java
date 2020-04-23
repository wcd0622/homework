package com.lagou.mapper;

import com.lagou.pojo.Article;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleMapper {

    public Article selectArticle(Integer id);

    public List<Article> queryAll();

}
