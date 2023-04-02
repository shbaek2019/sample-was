package com.eorder.backend.dto;

import com.eorder.backend.entity.Article;

public class ArticleDto {
    //
    private String title;
    private String contents;
    private String publicYn;
    private String category;
    private String author;
    
    public ArticleDto() {
        
    }
    
    public ArticleDto(String title, String contents, String publicYn, String category, String author) {
        this.title = title;
        this.contents = contents;
        this.publicYn = publicYn;
        this.category = category;
        this.author = author;
    }
    
    public Article entityFactory() {
    	Article entity = new Article();
    	entity.setTitle(this.title);
    	entity.setContents(this.contents);
    	entity.setPublicYn(this.publicYn);
    	entity.setCategory(this.category);
    	return entity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContent(String contents) {
        this.contents = contents;
    }

    public String getPublicYn() {
		return publicYn;
	}

	public void setPublicYn(String publicYn) {
		this.publicYn = publicYn;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
