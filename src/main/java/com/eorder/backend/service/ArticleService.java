package com.eorder.backend.service;

import java.util.List;

import com.eorder.backend.dto.ArticleDto;
import com.eorder.backend.entity.Article;

public interface ArticleService {
	//
	Article saveArticle(ArticleDto dto);
	Article getArticleByArticleId(long articleId);
	List<Article> getArticlesByEmail(String email);
}
