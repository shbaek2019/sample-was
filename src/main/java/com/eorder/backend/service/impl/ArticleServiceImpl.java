package com.eorder.backend.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eorder.backend.dto.ArticleDto;
import com.eorder.backend.entity.Article;
import com.eorder.backend.entity.User;
import com.eorder.backend.repository.ArticleRepository;
import com.eorder.backend.repository.UserRepository;
import com.eorder.backend.service.ArticleService;

@Service
public class ArticleServiceImpl implements ArticleService {
	//
	private final Logger LOGGER = LoggerFactory.getLogger(ArticleServiceImpl.class);
	
	private UserRepository userRepository;
	private ArticleRepository articleRepository;
	
	@Autowired
	public ArticleServiceImpl(UserRepository userRepository, ArticleRepository articleRepository) {
		//
		this.userRepository = userRepository;
		this.articleRepository = articleRepository;
	}

	@Override
	public Article saveArticle(ArticleDto dto) {
		Article readyEntity = dto.entityFactory();
		
		User author = userRepository.getByEmail(dto.getAuthor());
		readyEntity.setAuthor(author);
		
		Article result = articleRepository.save(readyEntity);
		return result;
	}

	@Override
	public Article getArticleByArticleId(long articleId) {
		//
		return articleRepository.getOneById(articleId);
	}

	@Override
	public List<Article> getArticlesByEmail(String email) {
		//
		User author = userRepository.getByEmail(email);
		return articleRepository.getArticlesByAuthor(author);
	}

}
