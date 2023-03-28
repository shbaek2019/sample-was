package com.eorder.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eorder.backend.dto.ArticleDto;
import com.eorder.backend.entity.Article;
import com.eorder.backend.entity.User;
import com.eorder.backend.repository.ArticleRepository;
import com.eorder.backend.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/article")
public class ArticleController {
    //
    @Autowired
    private ArticleRepository articleRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @GetMapping("/list")
    public List<Article> getArticles(@RequestParam String author) {
        User user = userRepository.getByUid(author);
        
        List<Article> lists = articleRepository.getArticlesByAuthor(user);
        return lists;
    }
    
    @PostMapping("/register")
    public void registerArticle(@RequestBody ArticleDto article) {
        //
        //1. author 검색
        User author = userRepository.getByUid(article.getAuthor());
        
        //2. 저장할 Article 정보 setting
        Article articleToSave = new Article();
        articleToSave.setAuthor(author);
        articleToSave.setTitle(article.getTitle());
        articleToSave.setContents(article.getContent());
        
        articleRepository.save(articleToSave);
    }
}
