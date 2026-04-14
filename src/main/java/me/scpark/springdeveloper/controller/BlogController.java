package me.scpark.springdeveloper.controller;

import lombok.RequiredArgsConstructor;
import me.scpark.springdeveloper.dao.Article;
import me.scpark.springdeveloper.dto.AddArticleRequest;
import me.scpark.springdeveloper.dto.ArticleResponse;
import me.scpark.springdeveloper.service.BlogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class BlogController {
    private final BlogService blogService;

    @PostMapping("/api/articles")
    public ResponseEntity<Article> addArticle(@RequestBody AddArticleRequest articleRequest){
        Article article = blogService.save(articleRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(article);
    }
    @GetMapping("/api/articles")
    public ResponseEntity<List<ArticleResponse>>findAllArticles() {
        List<Article> articles = blogService.findAll();
        List<ArticleResponse> result = new ArrayList<>();
        for (Article article : articles) {
            result.add(new ArticleResponse(article));
        }

        return ResponseEntity.ok().body(result);
    }
        //public  BlogController(BlogService service){
        //   this.blogService = service;
        //}
    @GetMapping("/api/articles/{id}")
    public ResponseEntity<ArticleResponse>findArticle(@PathVariable long id){
        Article article = blogService.findById(id);
        return ResponseEntity.ok().body(new ArticleResponse(article));

    }
}
