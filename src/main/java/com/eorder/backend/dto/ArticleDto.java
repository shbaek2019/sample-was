package com.eorder.backend.dto;

public class ArticleDto {
    //
    private String title;
    private String content;
    // email을 받는다.
    private String author;
    
    public ArticleDto() {
        
    }
    
    public ArticleDto(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
