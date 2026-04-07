package me.scpark.springdeveloper.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.scpark.springdeveloper.dao.Article;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddArticleRequest {
    private String title;
    private String content;
    public Article toEntity(){
        return Article.builder().title(title).content(content).build();
    }
    // new AddArticleRequest("제목","내용");
    // new AddArticleRequest()
}
