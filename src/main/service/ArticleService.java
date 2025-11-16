package src.main.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import src.main.dao.ArticleDAO;
import src.main.dao.ArticleDAOImplement;
import src.main.model.Article;

/**
 * Service class for handling article operations
 */
public class ArticleService {
	private ArticleDAO articleDAO;
	/**
	 * Constructor of ArticleService class
	 * @param articleDAO The ArticleDAO implementation to be used
	 */
	public ArticleService() {
		this.articleDAO = new ArticleDAOImplement();
	}
	
	/**
	 * Create JSON file for the article
	 */
	public void createJson(Article article) throws IOException {
		String pathFolder = "./output/" + article.getTopic() + "/";
		File dir = new File(pathFolder);
		if (!dir.exists()) {
			dir.mkdirs();
		}

		String path = "./output/" + article.getTopic() + "/";
		Map<String, Object> data = new HashMap<>();
        data.put("url", article.getUrl());
        data.put("time", article.getTime());
        data.put("title", article.getTitle());
		data.put("topic", article.getTopic());
		data.put("content", article.getContent());

        Gson gson = new GsonBuilder()
				.setPrettyPrinting()	
				.disableHtmlEscaping() 
                .create();

        try (FileWriter writer = new FileWriter(path + article.getTitle().replaceAll("[\\\\/:*?\"<>|]", "_") + ".json")) {
            gson.toJson(data, writer); 
            //System.out.println("Created JSON for article: " + article.getTitle());
        } catch (IOException e) {
            throw new IOException("IOException while creating JSON file for article: " + article.getTitle());
        }
	}

	/**
	 * Add article to the database
	 */
	public void addArticleToDB(Article article) {
		if (articleDAO.getArticleByUrlFromDB(article.getUrl()) != null) {
			updateArticleInDB(article);
			return;
		} else articleDAO.addArticleToDB(article);
	}

	/**
	 * Get article by url from the database
	 */
	public Article getArticleByUrlFromDB(String url) {
		return articleDAO.getArticleByUrlFromDB(url);
	}

	/**
	 * Get all articles from the database
	 */
	public java.util.List<Article> getAllArticlesFromDB() {
		return articleDAO.getAllArticlesFromDB();
	}

	/**
	 * Update article in the database
	 */
	public void updateArticleInDB(Article article) {
		articleDAO.updateArticleToDB(article);
	}

	/**
	 * Delete article by url from the database
	 */
	public void deleteArticleByUrlInDB(String url) {
		articleDAO.deleteArticleByURLFromDB(url);
	}

}
