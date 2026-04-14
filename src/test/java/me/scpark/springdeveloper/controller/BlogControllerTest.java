package me.scpark.springdeveloper.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.scpark.springdeveloper.dao.Article;
import me.scpark.springdeveloper.dto.AddArticleRequest;
import me.scpark.springdeveloper.repository.BlogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BlogControllerTest {
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected BlogRepository blogRepository;

    @BeforeEach
    public void deleteAll() {
        blogRepository.deleteAll();
    }

    @DisplayName("addArticle:블로그 글 추가에 성공한다")
    @Test
    public void addArticle() throws Exception{
        //given
        final String url="/api/articles";
        final String title="테스트";
        final String content="블로그 글 첫 번째 입니다";
        final AddArticleRequest article = new AddArticleRequest(title,content);
        final String requestBody = objectMapper.writeValueAsString(article);

        //when
        ResultActions result =  mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));
        //then
        result.andExpect(status().isCreated());
        List<Article> articles= blogRepository.findAll();
        assertThat(articles.size()).isEqualTo(1);
        assertThat(articles.get(0).getTitle()).isEqualTo(title);
        assertThat(articles.get(0).getContent()).isEqualTo(content);
    }

    @DisplayName("findAllArticles:블로글 글 목록 조회에 선공한다")
    @Test
    public void findAllArticles() throws Exception{
        //given
//     blogRepository.save(new Article("title","content"));
        blogRepository.save(Article.builder().title("title").content("content").build());

        //when
        final String url ="/api/articles";
        final ResultActions resultActions = mockMvc.perform(get(url).accept(MediaType.APPLICATION_JSON));

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].content").value("content"))
                .andExpect(jsonPath("$[0].title").value("title"));
    }

    @DisplayName("findArticle: 블로그 글 조회에 성공.")
    @Test
    public void findArticle() throws Exception{
        //given(데이터 분비)
        final String url = "/api/articles/{id}";
        final String title = "블로그 제목";
        final String content = "블로그 내용";

        Article savedArticle = blogRepository.save(Article.builder().title(title).content(content).build());

        //when(실행: 위헤서 생성된 블로그글을 조회)
        final ResultActions resultActions = mockMvc.perform(get(url,savedArticle.getId()));

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("&.title").value(title))
                .andExpect(jsonPath("$.content").value(content));
    }
}





















