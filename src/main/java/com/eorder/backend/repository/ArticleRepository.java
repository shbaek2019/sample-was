package com.eorder.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.eorder.backend.entity.Article;
import com.eorder.backend.entity.User;

@Transactional(readOnly=true)
public interface ArticleRepository extends JpaRepository<Article, Long>{
    List<Article> getArticlesByAuthor(User author);
    Article getOneByAuthor(User author);
    Article getOneById(long articleId);
}
