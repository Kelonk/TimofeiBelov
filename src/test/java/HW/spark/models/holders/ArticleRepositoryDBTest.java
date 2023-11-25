package HW.spark.models.holders;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArticleRepositoryDBTest {
  @Test
  void addArticle() {
    ArticleRepository articleRepository = new ArticleRepositoryMap();
    ArticleRepositoryTestDefault.addArticle(articleRepository);
  }

  @Test
  void getArticles() {
    ArticleRepository articleRepository = new ArticleRepositoryMap();
    ArticleRepositoryTestDefault.getArticles(articleRepository);
  }

  @Test
  void findArticle() {
    ArticleRepository articleRepository = new ArticleRepositoryMap();
    ArticleRepositoryTestDefault.findArticle(articleRepository);
  }

  @Test
  void delete() {
    ArticleRepository articleRepository = new ArticleRepositoryMap();
    ArticleRepositoryTestDefault.delete(articleRepository);
  }

  @Test
  void replace() {
    ArticleRepository articleRepository = new ArticleRepositoryMap();
    ArticleRepositoryTestDefault.replace(articleRepository);
  }

  @Test
  void getNewID(){
    ArticleRepository articleRepository = new ArticleRepositoryMap();
    ArticleRepositoryTestDefault.getNewID(articleRepository);
  }
}