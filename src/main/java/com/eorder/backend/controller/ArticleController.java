package com.eorder.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eorder.backend.dto.ArticleDto;
import com.eorder.backend.entity.Article;
import com.eorder.backend.entity.User;
import com.eorder.backend.repository.UserRepository;
import com.eorder.backend.service.ArticleService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/article")
public class ArticleController {
    //
    @Autowired
    private ArticleService articleService;
    
    @GetMapping("/findArticleById")
    public Article findArticleById(@RequestParam Long articleId) {
    	return articleService.getArticleByArticleId(articleId);
    }
    
    @GetMapping("/list")
    public List<Article> getArticles(@RequestParam String author) {
        //
        List<Article> lists = articleService.getArticlesByEmail(author);
        return lists;
    }
    
    @PostMapping("/register")
    public Article registerArticle(@RequestBody ArticleDto article) {
        //
        return articleService.saveArticle(article);
    }
}
